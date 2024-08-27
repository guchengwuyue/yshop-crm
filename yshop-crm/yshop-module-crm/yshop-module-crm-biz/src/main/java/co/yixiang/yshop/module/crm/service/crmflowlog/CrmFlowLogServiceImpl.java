package co.yixiang.yshop.module.crm.service.crmflowlog;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogPageReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmflowlog.CrmFlowLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 审核日志 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmFlowLogServiceImpl implements CrmFlowLogService {

    @Resource
    private CrmFlowLogMapper flowLogMapper;

    @Override
    public PageResult<CrmFlowLogDO> getFlowLogPage(CrmFlowLogPageReqVO pageReqVO) {
        return flowLogMapper.selectPage(pageReqVO);
    }

}