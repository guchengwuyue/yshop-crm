package co.yixiang.yshop.module.crm.service.crmanalysis;

import cn.hutool.core.date.DateUtil;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo.SalesVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmclues.CrmCluesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables.CrmContractReceivablesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 业绩目标 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class AchievementServiceImpl implements AchievementService {

    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmContractReceivablesMapper contractReceivablesMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmBusinessMapper businessMapper;
    @Resource
    private CrmCluesMapper crmCluesMapper;
    @Resource
    private AdminUserApi adminUserApi;





    @Override
    public List<SalesVO> getSales(String relation) {
        Integer year = DateUtil.year(new Date());
        List<Long> userIds = new ArrayList<>();
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(relation)){
            userIds.add(userId);
        }else if(RelationEnum.SUB.getValue().equals(relation)){
            userIds = adminUserApi.getUserListBySubordinateIds(userId);
        }
        List<SalesVO> salesVOS = new ArrayList<>();
        int i = 1;
        while (i <= 12) {
            String time = "" + year;
            if(i < 10) {
                time = time + "-0" + i;
            }else{
                time = time + "-" + i;
            }
            SalesVO salesVO = new SalesVO();
            salesVO.setTime(time);

            Date starTime = new Date();
            Date endTime = new Date();
            Calendar calendar = Calendar.getInstance();
            //某个月
            calendar.set(year,i - 1,1);
            starTime = DateUtil.beginOfMonth(calendar.getTime());
            endTime = DateUtil.endOfMonth(calendar.getTime());

            Long cluesCount = crmCluesMapper.selectCount( new LambdaQueryWrapper<CrmCluesDO>()
                    .in(CrmCluesDO::getOwnerUserId,userIds)
                    .between(CrmCluesDO::getCreateTime,starTime,endTime));
            Long customerCount = customerMapper.selectCount( new LambdaQueryWrapper<CrmCustomerDO>()
                    .in(CrmCustomerDO::getOwnerUserId,userIds)
                    .between(CrmCustomerDO::getCreateTime,starTime,endTime));
            Long businessCount = businessMapper.selectCount( new LambdaQueryWrapper<CrmBusinessDO>()
                    .in(CrmBusinessDO::getOwnerUserId,userIds)
                    .between(CrmBusinessDO::getCreateTime,starTime,endTime));
            Long contractCount = contractMapper.selectCount( new LambdaQueryWrapper<CrmContractDO>()
                    .in(CrmContractDO::getOwnerUserId,userIds)
                    .between(CrmContractDO::getOrderTime,starTime,endTime));
            Long receivablesCount = contractReceivablesMapper.selectCount( new LambdaQueryWrapper<CrmContractReceivablesDO>()
                    .in(CrmContractReceivablesDO::getOwnerUserId,userIds)
                    .between(CrmContractReceivablesDO::getReturnTime,starTime,endTime));
            salesVO.setCluesCount(contractCount);
            salesVO.setCustomerCount(customerCount);
            salesVO.setBusinessCount(businessCount);
            salesVO.setContractCount(contractCount);
            salesVO.setReceivablesCount(receivablesCount);
            salesVOS.add(salesVO);
            i++;
        }
        return salesVOS;
    }
}