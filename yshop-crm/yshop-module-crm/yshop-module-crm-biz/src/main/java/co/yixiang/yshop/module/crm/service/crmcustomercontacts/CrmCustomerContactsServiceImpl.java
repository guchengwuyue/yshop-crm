package co.yixiang.yshop.module.crm.service.crmcustomercontacts;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo.CrmCustomerContactsPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo.CrmCustomerContactsRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo.CrmCustomerContactsSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomercontacts.CrmCustomerContactsDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomercontacts.CrmCustomerContactsMapper;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.CUSTOMER_CONTACTS_NOT_EXISTS;

/**
 * 联系人 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmCustomerContactsServiceImpl implements CrmCustomerContactsService {

    @Resource
    private CrmCustomerContactsMapper customerContactsMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createCustomerContacts(CrmCustomerContactsSaveReqVO createReqVO) {
        // 插入
        CrmCustomerContactsDO customerContacts = BeanUtils.toBean(createReqVO, CrmCustomerContactsDO.class);
        customerContactsMapper.insert(customerContacts);
        // 返回
        return customerContacts.getId();
    }

    @Override
    public void updateCustomerContacts(CrmCustomerContactsSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerContactsExists(updateReqVO.getId());
        // 更新
        CrmCustomerContactsDO updateObj = BeanUtils.toBean(updateReqVO, CrmCustomerContactsDO.class);
        customerContactsMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerContacts(Long id) {
        // 校验存在
        validateCustomerContactsExists(id);
        // 删除
        customerContactsMapper.deleteById(id);
    }

    private void validateCustomerContactsExists(Long id) {
        if (customerContactsMapper.selectById(id) == null) {
            throw exception(CUSTOMER_CONTACTS_NOT_EXISTS);
        }
    }

    @Override
    public CrmCustomerContactsRespVO getCustomerContacts(Long id) {
        CrmCustomerContactsDO crmCustomerContactsDO =  customerContactsMapper.selectById(id);
        CrmCustomerContactsRespVO contactsRespVO = BeanUtils.toBean(crmCustomerContactsDO,CrmCustomerContactsRespVO.class);
        CrmCustomerDO crmCustomerDO = customerMapper.selectById(crmCustomerContactsDO.getCustomerId());
        contactsRespVO.setCustomerName(crmCustomerDO.getName());
        return contactsRespVO;
    }

    @Override
    public PageResult<CrmCustomerContactsRespVO> getCustomerContactsPage(CrmCustomerContactsPageReqVO pageReqVO) {
        List<Long> ids = new ArrayList<>();
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
            ids.add(adminId);
        }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
            ids = adminUserApi.getUserListBySubordinateIds(adminId);
        }
        return customerContactsMapper.selectPage(pageReqVO,ids);
    }

}