package co.yixiang.yshop.module.crm.service.crmcustomer;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import jakarta.validation.Valid;

/**
 * 客户 Service 接口
 *
 * @author yshop
 */
public interface CrmCustomerService {

    /**
     * 创建客户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomer(@Valid CrmCustomerSaveReqVO createReqVO);

    /**
     * 更新客户
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomer(@Valid CrmCustomerSaveReqVO updateReqVO);

    /**
     * 删除客户
     *
     * @param id 编号
     */
    void deleteCustomer(Long id);

    /**
     * 投入公海
     * @param id
     */
    void openCustomer(Long id);

    /**
     * 领取公海客户
     * @param id
     */
    void receiveCustomer(Long id);

    /**
     * 获得客户
     *
     * @param id 编号
     * @return 客户
     */
    CrmCustomerDO getCustomer(Long id);

    /**
     * 获得客户分页
     *
     * @param pageReqVO 分页查询
     * @return 客户分页
     */
    PageResult<CrmCustomerRespVO> getCustomerPage(CrmCustomerPageReqVO pageReqVO);

    /**
     * 获得客户分页2
     *
     * @param pageReqVO 分页查询
     * @return 客户分页
     */
    PageResult<CrmCustomerRespVO> getCustomerPage2(CrmCustomerPageReqVO pageReqVO);

    /**
     * 发送短信
     * @param sendVO
     * @return
     */
    void sendSms(SmsTemplateSendVO sendVO);

    /**
     * 发送邮件
     * @param sendVO
     * @return
     */
    void sendMail(MailTemplateSendVO sendVO);



}