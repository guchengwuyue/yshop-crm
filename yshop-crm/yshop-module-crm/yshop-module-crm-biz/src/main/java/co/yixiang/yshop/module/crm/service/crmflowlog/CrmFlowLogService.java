package co.yixiang.yshop.module.crm.service.crmflowlog;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogPageReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;

/**
 * 审核日志 Service 接口
 *
 * @author yshop
 */
public interface CrmFlowLogService {


    /**
     * 获得审核日志分页
     *
     * @param pageReqVO 分页查询
     * @return 审核日志分页
     */
    PageResult<CrmFlowLogDO> getFlowLogPage(CrmFlowLogPageReqVO pageReqVO);

}