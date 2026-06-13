package co.yixiang.yshop.module.crm.service.crminvoice;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CheckInfoVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoicePageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoiceRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoiceSaveReqVO;
import jakarta.validation.Valid;

/**
 * 发票 Service 接口
 *
 * @author yshop
 */
public interface CrmInvoiceService {

    /**
     * 创建发票
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInvoice(@Valid CrmInvoiceSaveReqVO createReqVO);

    /**
     * 更新发票
     *
     * @param updateReqVO 更新信息
     */
    void updateInvoice(@Valid CrmInvoiceSaveReqVO updateReqVO);

    /**
     * 删除发票
     *
     * @param id 编号
     */
    void deleteInvoice(Long id);

    /**
     * 获得发票
     *
     * @param id 编号
     * @return 发票
     */
    CrmInvoiceRespVO getInvoice(Long id);

    /**
     * 获得发票分页
     *
     * @param pageReqVO 分页查询
     * @return 发票分页
     */
    PageResult<CrmInvoiceRespVO> getInvoicePage(CrmInvoicePageReqVO pageReqVO);

    /**
     * 审核发票
     * @param checkInfoVO
     */
    void check(CheckInfoVO checkInfoVO);

    /**
     * 开具发票
     * @param updateReqVO
     */
    void issueInvoice(@Valid CrmInvoiceSaveReqVO updateReqVO);

}