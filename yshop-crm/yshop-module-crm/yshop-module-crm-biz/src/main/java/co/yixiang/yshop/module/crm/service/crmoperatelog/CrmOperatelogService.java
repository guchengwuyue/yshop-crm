package co.yixiang.yshop.module.crm.service.crmoperatelog;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo.CrmOperatelogPageReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmoperatelog.CrmOperatelogDO;

/**
 * 操作日志 Service 接口
 *
 * @author yshop
 */
public interface CrmOperatelogService {

    /**
     * crm 操作日志
     * @param title 标题
     * @param customerId 客户ID
     * @param contractId 合同ID
     * @param receivableId 回款ID
     */
    void createLog(String title,Long customerId,Long contractId,Long receivableId);

    /**
     * 获得操作日志分页
     *
     * @param pageReqVO 分页查询
     * @return 操作日志分页
     */
    PageResult<CrmOperatelogDO> getOperatelogPage(CrmOperatelogPageReqVO pageReqVO);


}