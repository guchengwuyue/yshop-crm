package co.yixiang.yshop.module.crm.service.crmcustomercontacts;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomercontacts.CrmCustomerContactsDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 联系人 Service 接口
 *
 * @author yshop
 */
public interface CrmCustomerContactsService {

    /**
     * 创建联系人
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerContacts(@Valid CrmCustomerContactsSaveReqVO createReqVO);

    /**
     * 更新联系人
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerContacts(@Valid CrmCustomerContactsSaveReqVO updateReqVO);

    /**
     * 删除联系人
     *
     * @param id 编号
     */
    void deleteCustomerContacts(Long id);

    /**
     * 获得联系人
     *
     * @param id 编号
     * @return 联系人
     */
    CrmCustomerContactsRespVO getCustomerContacts(Long id);

    /**
     * 获得联系人分页
     *
     * @param pageReqVO 分页查询
     * @return 联系人分页
     */
    PageResult<CrmCustomerContactsRespVO> getCustomerContactsPage(CrmCustomerContactsPageReqVO pageReqVO);

}