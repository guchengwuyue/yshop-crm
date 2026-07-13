package co.yixiang.yshop.module.crm.service.crmachievement;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import co.yixiang.yshop.framework.common.enums.CommonStatusEnum;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables.CrmContractReceivablesMapper;
import co.yixiang.yshop.module.crm.enums.ContractStatusEnum;
import co.yixiang.yshop.module.crm.enums.FlowStepEnum;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import co.yixiang.yshop.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import co.yixiang.yshop.module.system.controller.admin.user.vo.user.UserPageReqVO;
import co.yixiang.yshop.module.system.dal.dataobject.dept.DeptDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import co.yixiang.yshop.module.system.dal.mysql.user.AdminUserMapper;
import co.yixiang.yshop.module.system.service.dept.DeptService;
import co.yixiang.yshop.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmachievement.CrmAchievementDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;

import co.yixiang.yshop.module.crm.dal.mysql.crmachievement.CrmAchievementMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.*;

/**
 * 业绩目标 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmAchievementServiceImpl implements CrmAchievementService {

    @Resource
    private CrmAchievementMapper achievementMapper;
    @Resource
    private DeptService deptService;
    @Resource
    private AdminUserService userService;
    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmContractReceivablesMapper contractReceivablesMapper;
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private AdminUserApi adminUserApi;


    @Override
    public void createAchievement(DeptAchieveSaveVO createReqVO) {

        if(createReqVO.getId() == 0){
            //批量
            if(FlowStepEnum.TYPE_3.getValue().equals(createReqVO.getAchievementRespVO().getType())){
                List<DeptDO> list = deptService.getDeptList(
                        new DeptListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
                list.forEach(dept ->{
                    createReqVO.setId(dept.getId());
                    createReqVO.setName(dept.getName());
                    saveAchievement(createReqVO);
                });
            }else{
                List<AdminUserDO> list = userService.getUserListByStatus(CommonStatusEnum.ENABLE.getStatus());
                list.forEach(user ->{
                    createReqVO.setId(user.getId());
                    createReqVO.setName(user.getNickname());
                    saveAchievement(createReqVO);
                });
            }
        }else{
            //单个操作
            if(FlowStepEnum.TYPE_3.getValue().equals(createReqVO.getAchievementRespVO().getType())){
                createReqVO.setName(createReqVO.getName());
            }else{
                createReqVO.setName(createReqVO.getNickname());
            }
            saveAchievement(createReqVO);
        }


    }

    /**
     * 添加或者更新
     * @param createReqVO
     */
    private void saveAchievement(DeptAchieveSaveVO createReqVO){
        // 插入
        CrmAchievementDO achievement = BeanUtils.toBean(createReqVO.getAchievementRespVO(), CrmAchievementDO.class);
        achievement.setTypeId(createReqVO.getId());
        achievement.setName(createReqVO.getName());
        LambdaQueryWrapper<CrmAchievementDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmAchievementDO::getType,achievement.getType())
                .eq(CrmAchievementDO::getTypeId,achievement.getTypeId())
                .eq(CrmAchievementDO::getConfig,achievement.getConfig())
                .eq(CrmAchievementDO::getYear,achievement.getYear());
        CrmAchievementDO achievementOld = achievementMapper.selectOne(wrapper);
        if(achievementOld == null){
            achievementMapper.insert(achievement);
        }else {
            achievement.setId(achievementOld.getId());
            achievementMapper.updateById(achievement);
        }

        //更新年目标
        List<BigDecimal> bigDecimals = Arrays.asList(
                achievement.getJanuary(),achievement.getFebruary(),achievement.getMarch(),achievement.getApril(),
                achievement.getMay(),achievement.getJune(),achievement.getJuly(),achievement.getAugust(),
                achievement.getSeptember(),achievement.getOctober(),achievement.getNovember(),achievement.getDecember()
        );
        BigDecimal yearTarget = bigDecimals.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        CrmAchievementDO achievementDO = CrmAchievementDO.builder().yeartarget(yearTarget).id(achievement.getId()).build();
        achievementMapper.updateById(achievementDO);
    }

    @Override
    public void updateAchievement(CrmAchievementSaveReqVO updateReqVO) {
        // 校验存在
        validateAchievementExists(updateReqVO.getId());
        // 更新
        CrmAchievementDO updateObj = BeanUtils.toBean(updateReqVO, CrmAchievementDO.class);
        achievementMapper.updateById(updateObj);
    }

    @Override
    public void deleteAchievement(Long id) {
        // 校验存在
        validateAchievementExists(id);
        // 删除
        achievementMapper.deleteById(id);
    }

    private void validateAchievementExists(Long id) {
        if (achievementMapper.selectById(id) == null) {
            throw exception(ACHIEVEMENT_NOT_EXISTS);
        }
    }

    @Override
    public CrmAchievementDO getAchievement(Long id) {
        return achievementMapper.selectById(id);
    }

    @Override
    public PageResult<UserAchieveRespVO> getAchievementPage(CrmAchievementPageReqVO pageReqVO) {
        UserPageReqVO userPageReqVO = new UserPageReqVO();
        userPageReqVO.setPageNo(pageReqVO.getPageNo());
        userPageReqVO.setPageSize(pageReqVO.getPageSize());
        PageResult<AdminUserDO> pageResult = userService.getUserPage(userPageReqVO);
        PageResult<UserAchieveRespVO> pageResult1 = BeanUtils.toBean(pageResult,UserAchieveRespVO.class);
        pageResult1.getList().forEach(v -> {
            CrmAchievementDO crmAchievementDO = achievementMapper.selectOne(new LambdaQueryWrapperX<CrmAchievementDO>()
                    .eq(CrmAchievementDO::getType, FlowStepEnum.TYPE_2.getValue())
                    .eq(CrmAchievementDO::getTypeId,v.getId())
                    .eqIfPresent(CrmAchievementDO::getConfig,pageReqVO.getConfig())
                    .eqIfPresent(CrmAchievementDO::getYear,pageReqVO.getYear()));
            if(crmAchievementDO == null){
                crmAchievementDO = CrmAchievementDO.builder()
                        .january(BigDecimal.ZERO).february(BigDecimal.ZERO).march(BigDecimal.ZERO)
                        .april(BigDecimal.ZERO).may(BigDecimal.ZERO).june(BigDecimal.ZERO)
                        .july(BigDecimal.ZERO).august(BigDecimal.ZERO).september(BigDecimal.ZERO)
                        .october(BigDecimal.ZERO).november(BigDecimal.ZERO).december(BigDecimal.ZERO)
                        .yeartarget(BigDecimal.ZERO)
                        .build();
            }
            v.setAchievementRespVO(BeanUtils.toBean(crmAchievementDO,CrmAchievementRespVO.class));


        });

        return pageResult1;
    }

    @Override
    public List<UserAchieveDetailRespVO> getAchievementDetailCount(CrmAchievementPageReqVO pageReqVO){
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        List<Long> ids = new ArrayList<>();
        ids.add(adminId);
        ids.addAll(adminUserApi.getUserListBySubordinateIds(adminId));
        List<AdminUserDO> adminUserDOList = userService.getUserList(ids);
        List<UserAchieveDetailRespVO> userAchieveRespVOS = BeanUtils.toBean(adminUserDOList,UserAchieveDetailRespVO.class);
        userAchieveRespVOS.forEach(v -> {
            if(adminId.equals(v.getId())){
                v.setNickname(v.getNickname()+"(本人)");
            }else{
                v.setNickname(v.getNickname()+"(下属)");
            }
            CrmAchievementDO crmAchievementDO = achievementMapper.selectOne(new LambdaQueryWrapperX<CrmAchievementDO>()
                    .eq(CrmAchievementDO::getType, FlowStepEnum.TYPE_2.getValue())
                    .eq(CrmAchievementDO::getTypeId,v.getId())
                    .eqIfPresent(CrmAchievementDO::getConfig,pageReqVO.getConfig())
                    .eqIfPresent(CrmAchievementDO::getYear,pageReqVO.getYear()));
            if(ObjectUtil.isNull(crmAchievementDO)){
                crmAchievementDO = CrmAchievementDO.builder()
                        .january(BigDecimal.ZERO).february(BigDecimal.ZERO).march(BigDecimal.ZERO)
                        .april(BigDecimal.ZERO).may(BigDecimal.ZERO).june(BigDecimal.ZERO)
                        .july(BigDecimal.ZERO).august(BigDecimal.ZERO).september(BigDecimal.ZERO)
                        .october(BigDecimal.ZERO).november(BigDecimal.ZERO).december(BigDecimal.ZERO)
                        .yeartarget(BigDecimal.ZERO)
                        .build();
            }
            CrmAchievementDetailRespVO achievementDetailRespVO = CrmAchievementDetailRespVO.builder()
                    .january(getMap(v.getId(),pageReqVO.getYear(),1,crmAchievementDO.getJanuary(),pageReqVO.getConfig()))
                    .february(getMap(v.getId(),pageReqVO.getYear(),2,crmAchievementDO.getFebruary(),pageReqVO.getConfig()))
                    .march(getMap(v.getId(),pageReqVO.getYear(),3,crmAchievementDO.getMarch(),pageReqVO.getConfig()))
                    .april(getMap(v.getId(),pageReqVO.getYear(),4,crmAchievementDO.getApril(),pageReqVO.getConfig()))
                    .may(getMap(v.getId(), pageReqVO.getYear(),5,crmAchievementDO.getMay(),pageReqVO.getConfig()))
                    .june(getMap(v.getId(),pageReqVO.getYear(),6,crmAchievementDO.getJune(),pageReqVO.getConfig()))
                    .july(getMap(v.getId(),pageReqVO.getYear(),7,crmAchievementDO.getJuly(),pageReqVO.getConfig()))
                    .august(getMap(v.getId(),pageReqVO.getYear(),8,crmAchievementDO.getAugust(),pageReqVO.getConfig()))
                    .september(getMap(v.getId(),pageReqVO.getYear(),9,crmAchievementDO.getSeptember(),pageReqVO.getConfig()))
                    .october(getMap(v.getId(),pageReqVO.getYear(),10,crmAchievementDO.getOctober(),pageReqVO.getConfig()))
                    .november(getMap(v.getId(),pageReqVO.getYear(),11,crmAchievementDO.getNovember(),pageReqVO.getConfig()))
                    .december(getMap(v.getId(), pageReqVO.getYear(),12,crmAchievementDO.getDecember(),pageReqVO.getConfig()))
                    .build();
            v.setAchievementRespVO(achievementDetailRespVO);


        });

        return userAchieveRespVOS;

    }


    /**
     * 获取用户每个月合同与回款完成度
     * @param userId 用户ID
     * @param year 年度
     * @param month 月份
     * @param target 合同或者回款目标
     * @param config 1合同 2回款
     * @return
     */
    private Map<String, String> getMap(Long userId,Integer year,Integer month,BigDecimal target,Integer config) {
        //根据某一年某一月 获取当前时间开始与结束时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month - 1,1);
        Date starTime = DateUtil.beginOfMonth(calendar.getTime());
        Date endTime = DateUtil.endOfMonth(calendar.getTime());

        BigDecimal contractSuccessMoney = BigDecimal.ZERO;
        BigDecimal receivablesSuccessMoney = BigDecimal.ZERO;
        String rate = "0.00%";
        if(ShopCommonEnum.ACH_1.getValue().equals( config)){
            List<CrmContractDO> crmContractDOS = contractMapper.selectList(new LambdaQueryWrapper<CrmContractDO>()
                    .eq(CrmContractDO::getOwnerUserId,userId)
                    .eq(CrmContractDO::getCheckStatus, ContractStatusEnum.STATUS_2.getValue())
                    .between(CrmContractDO::getOrderTime,starTime,endTime));
            contractSuccessMoney = crmContractDOS
                    .stream()
                    .map(CrmContractDO::getMoney)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //计算比率
            if(target.compareTo(BigDecimal.ZERO) != 0){
                rate = new DecimalFormat("0.00%").format(contractSuccessMoney.divide(target,2, RoundingMode.HALF_UP));
            }
        }else{
            List<CrmContractReceivablesDO> crmContractReceivablesDOS = contractReceivablesMapper.selectList(new LambdaQueryWrapper<CrmContractReceivablesDO>()
                    .eq(CrmContractReceivablesDO::getOwnerUserId,userId)
                    .eq(CrmContractReceivablesDO::getCheckStatus, ShopCommonEnum.IS_FINISH_1.getValue())
                    .between(CrmContractReceivablesDO::getCreateTime,starTime,endTime));
            receivablesSuccessMoney = crmContractReceivablesDOS
                    .stream()
                    .map(CrmContractReceivablesDO::getMoney)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if(target.compareTo(BigDecimal.ZERO) != 0) {
                rate = new DecimalFormat("0.00%").format(receivablesSuccessMoney.divide(target, 2, RoundingMode.HALF_UP));
            }
        }

        Map<String, String> map = new HashMap<>();
        map.put("finish",contractSuccessMoney.toString());
        map.put("target",target.toString());
        map.put("rate",rate);

        return map;

    }

    @Override
    public List<DeptAchieveRespVO> getDeptAchieve(Integer type, Integer year) {
        List<DeptDO> list = deptService.getDeptList(
                new DeptListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        List<DeptAchieveRespVO> achieveRespVOS = BeanUtils.toBean(list,DeptAchieveRespVO.class);
        achieveRespVOS.forEach(vo -> {
            CrmAchievementDO crmAchievementDO = achievementMapper.selectOne(new LambdaQueryWrapperX<CrmAchievementDO>()
                    .eq(CrmAchievementDO::getType, FlowStepEnum.TYPE_3.getValue())
                    .eq(CrmAchievementDO::getTypeId,vo.getId())
                    .eqIfPresent(CrmAchievementDO::getConfig,type)
                    .eqIfPresent(CrmAchievementDO::getYear,year));
            if(crmAchievementDO == null){
                crmAchievementDO = CrmAchievementDO.builder()
                        .january(BigDecimal.ZERO).february(BigDecimal.ZERO).march(BigDecimal.ZERO)
                        .april(BigDecimal.ZERO).may(BigDecimal.ZERO).june(BigDecimal.ZERO)
                        .july(BigDecimal.ZERO).august(BigDecimal.ZERO).september(BigDecimal.ZERO)
                        .october(BigDecimal.ZERO).november(BigDecimal.ZERO).december(BigDecimal.ZERO)
                        .yeartarget(BigDecimal.ZERO)
                        .build();
            }
            vo.setAchievementRespVO(BeanUtils.toBean(crmAchievementDO,CrmAchievementRespVO.class));
        });
        return achieveRespVOS;
    }

    @Override
    public AchieveCountRespVO getCount(Integer type, Integer year, Integer month) {
        List<Long> userIds = new ArrayList<>();
        Long typeId = SecurityFrameworkUtils.getLoginUserId();
        if(FlowStepEnum.TYPE_2.getValue().equals(type)){
            userIds.add(typeId);
        }else{
            //部门
            AdminUserDO adminUserDO = userMapper.selectById(typeId);
            typeId = adminUserDO.getDeptId();
            Set<Long> deptIds = adminUserService.getDeptCondition(adminUserDO.getDeptId());
            List<AdminUserDO> adminUserDOS =  userMapper.selectList(new LambdaQueryWrapperX<AdminUserDO>()
                    .in(AdminUserDO::getDeptId,deptIds));
            userIds = adminUserDOS.stream().map(AdminUserDO::getId).collect(Collectors.toList());
        }
        Date starTime = new Date();
        Date endTime = new Date();
        Calendar calendar = Calendar.getInstance();

        if(month == 0){
            //全年
            calendar.set(year,1,1);
            starTime = DateUtil.beginOfYear(calendar.getTime());
            endTime = DateUtil.endOfYear(calendar.getTime());
        }else {
            //某个月
            calendar.set(year,month - 1,1);
            starTime = DateUtil.beginOfMonth(calendar.getTime());
            endTime = DateUtil.endOfMonth(calendar.getTime());
        }

        //合同目标
        CrmAchievementDO crmAchievementDO = achievementMapper.selectOne(new LambdaQueryWrapperX<CrmAchievementDO>()
                .eq(CrmAchievementDO::getType, type)
                .eq(CrmAchievementDO::getTypeId,typeId)
                .eqIfPresent(CrmAchievementDO::getConfig, ShopCommonEnum.ACH_1.getValue())
                .eqIfPresent(CrmAchievementDO::getYear,year));
        //回款目标
        CrmAchievementDO crmAchievementDO2 = achievementMapper.selectOne(new LambdaQueryWrapperX<CrmAchievementDO>()
                .eq(CrmAchievementDO::getType, type)
                .eq(CrmAchievementDO::getTypeId,typeId)
                .eqIfPresent(CrmAchievementDO::getConfig, ShopCommonEnum.ACH_2.getValue())
                .eqIfPresent(CrmAchievementDO::getYear,year));



        List<CrmContractDO> crmContractDOS = contractMapper.selectList(new LambdaQueryWrapper<CrmContractDO>()
                .in(CrmContractDO::getOwnerUserId,userIds)
                .eq(CrmContractDO::getCheckStatus, ContractStatusEnum.STATUS_2.getValue())
                .between(CrmContractDO::getOrderTime,starTime,endTime));
        BigDecimal contractSuccessMoney = BigDecimal.ZERO;
        if(!crmContractDOS.isEmpty()){
            contractSuccessMoney = crmContractDOS
                    .stream()
                    .map(CrmContractDO::getMoney)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        List<CrmContractReceivablesDO> crmContractReceivablesDOS = contractReceivablesMapper
                .selectList(new LambdaQueryWrapper<CrmContractReceivablesDO>()
                .in(CrmContractReceivablesDO::getOwnerUserId,userIds)
                .eq(CrmContractReceivablesDO::getCheckStatus, ContractStatusEnum.STATUS_2.getValue())
                .between(CrmContractReceivablesDO::getReturnTime,starTime,endTime));
        BigDecimal receivablesSuccessMoney = BigDecimal.ZERO;
        if(!crmContractReceivablesDOS.isEmpty()){
            receivablesSuccessMoney = crmContractReceivablesDOS
                    .stream()
                    .map(CrmContractReceivablesDO::getMoney)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        BigDecimal contractMoney = BigDecimal.ZERO;
        BigDecimal receivablesMoney = BigDecimal.ZERO;
        String contractPer = "0";
        String receivablesPer = "0";
        switch (month){
            case 0 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getYeartarget();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getYeartarget();
                }
            }
            case 1 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getJanuary();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getJanuary();
                }
            }
            case 2 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getFebruary();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getFebruary();
                }
            }
            case 3 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getMarch();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getMarch();
                }
            }
            case 4 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getApril();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getApril();
                }
            }
            case 5 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getMay();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getMay();
                }
            }
            case 6 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getJune();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getJune();
                }
            }
            case 7 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getJuly();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getJuly();
                }
            }
            case 8 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getAugust();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getAugust();
                }
            }
            case 9 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getSeptember();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getSeptember();
                }
            }
            case 10 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getOctober();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getOctober();
                }
            }
            case 11 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getNovember();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getNovember();
                }
            }
            case 12 -> {
                if(crmAchievementDO != null){
                    contractMoney = crmAchievementDO.getDecember();
                }
                if(crmAchievementDO2 != null){
                    receivablesMoney = crmAchievementDO2.getDecember();
                }
            }



        }
        if(contractMoney.compareTo(BigDecimal.ZERO) > 0){
            contractPer = NumberUtil.round(NumberUtil.div(contractSuccessMoney,contractMoney),2)
                    .multiply(new BigDecimal("100")).toString();
        }
        if(receivablesMoney.compareTo(BigDecimal.ZERO) > 0){
            receivablesPer = NumberUtil.round(NumberUtil.div(receivablesSuccessMoney,receivablesMoney),2)
                    .multiply(new BigDecimal("100")).toString();
        }

        return AchieveCountRespVO.builder()
                .contractMoney(contractMoney)
                .contractSuccessMoney(contractSuccessMoney)
                .receivablesMoney(receivablesMoney)
                .receivablesSuccessMoney(receivablesSuccessMoney)
                .contractPer(contractPer)
                .receivablesPer(receivablesPer)
                .build();
    }
}