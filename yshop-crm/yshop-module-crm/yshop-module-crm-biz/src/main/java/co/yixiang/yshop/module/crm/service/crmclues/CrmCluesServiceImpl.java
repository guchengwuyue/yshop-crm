package co.yixiang.yshop.module.crm.service.crmclues;

import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmrecord.CrmRecordMapper;
import co.yixiang.yshop.module.crm.enums.CluesStatusEnum;
import co.yixiang.yshop.module.crm.enums.CustomerTypesEnum;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.crm.service.crmrecord.CrmRecordService;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import co.yixiang.yshop.module.crm.controller.admin.crmclues.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;

import co.yixiang.yshop.module.crm.dal.mysql.crmclues.CrmCluesMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.*;

/**
 * 线索 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmCluesServiceImpl implements CrmCluesService {

    @Resource
    private CrmCluesMapper cluesMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmRecordMapper crmRecordMapper;
    @Resource
    private CrmRecordService crmRecordService;

    @Override
    public Long createClues(CrmCluesSaveReqVO createReqVO) {
        // 插入
        CrmCluesDO clues = BeanUtils.toBean(createReqVO, CrmCluesDO.class);
        clues.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        cluesMapper.insert(clues);
        // 返回
        return clues.getId();
    }

    @Override
    public void updateClues(CrmCluesSaveReqVO updateReqVO) {
        // 校验存在
        validateCluesExists(updateReqVO.getId());
        // 更新
        CrmCluesDO updateObj = BeanUtils.toBean(updateReqVO, CrmCluesDO.class);
        cluesMapper.updateById(updateObj);
    }

    @Override
    public void deleteClues(Long id) {
        // 校验存在
        validateCluesExists(id);
        // 删除
        cluesMapper.deleteById(id);

        crmRecordService.deleteByType(TypesEnum.CLUES.getValue(),id);
    }

    private void validateCluesExists(Long id) {
        if (cluesMapper.selectById(id) == null) {
            throw exception(CLUES_NOT_EXISTS);
        }
    }

    @Override
    public CrmCluesDO getClues(Long id) {
        return cluesMapper.selectById(id);
    }

    @Override
    public PageResult<CrmCluesRespVO> getCluesPage(CrmCluesPageReqVO pageReqVO) {
        if(CustomerTypesEnum.OPEN.getValue().equals(pageReqVO.getType())){
            return cluesMapper.selectPage2(pageReqVO);
        }
        List<Long> ids = new ArrayList<>();
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
            ids.add(adminId);
        }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
            ids = adminUserApi.getUserListBySubordinateIds(adminId);
        }
        return cluesMapper.selectPage(pageReqVO,ids);
    }

    @Override
    public void openClues(Long id) {
        CrmCluesDO crmCluesDO = cluesMapper.selectById(id);
        if(CluesStatusEnum.STATUS_0.getValue().equals(crmCluesDO.getStatus())){
            crmCluesDO.setStatus(CluesStatusEnum.STATUS_NEG_1.getValue());
        }
        crmCluesDO.setOwnerUserId(0L);
        cluesMapper.updateById(crmCluesDO);

        crmRecordService.deleteByType(TypesEnum.CLUES.getValue(),id);
    }

    @Override
    public void receiveClues(Long id) {
        CrmCluesDO crmCluesDO = cluesMapper.selectById(id);
        if (crmCluesDO == null) {
            throw exception(CLUES_NOT_EXISTS);
        }
        crmCluesDO.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        crmCluesDO.setCollectTime(LocalDateTime.now());
        crmCluesDO.setFollowTime(LocalDateTime.now());
        crmCluesDO.setStatus(CluesStatusEnum.STATUS_0.getValue());
        cluesMapper.updateById(crmCluesDO);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCustomer(CrmCustomerSaveReqVO createReqVO) {
        CrmCluesDO crmCluesDO = cluesMapper.selectById(createReqVO.getId());
        if (crmCluesDO == null) {
            throw exception(CLUES_NOT_EXISTS);
        }
        createReqVO.setId(null);
        CrmCustomerDO customerDO = BeanUtils.toBean(createReqVO,CrmCustomerDO.class);
        customerDO.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        customerDO.setNextTime(LocalDateTime.now());
        customerDO.setCollectTime(LocalDateTime.now());
        customerMapper.insert(customerDO);

        //更新线索
        crmCluesDO.setStatus(CluesStatusEnum.STATUS_1.getValue());
        crmCluesDO.setCustomerId(customerDO.getId());
        cluesMapper.updateById(crmCluesDO);

        //是否更新跟踪记录
        if(ShopCommonEnum.ENABLE_1.getValue().equals(createReqVO.getIsTransfer())){
            crmRecordMapper.update(CrmRecordDO.builder()
                    .types(TypesEnum.CUSTOMER.getValue())
                    .typesId(customerDO.getId()).build(),
                    new LambdaQueryWrapper<CrmRecordDO>().eq(CrmRecordDO::getTypesId,crmCluesDO.getId()));
        }
    }
}