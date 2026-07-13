package co.yixiang.yshop.module.crm.controller.admin.crmflowlog;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogSaveReqVO;
import co.yixiang.yshop.module.crm.service.crmflowlog.CrmFlowLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 审核日志")
@RestController
@RequestMapping("/crm/flow-log")
@Validated
public class CrmFlowLogController {

    @Resource
    private CrmFlowLogService flowLogService;


    @GetMapping("/page")
    @Operation(summary = "获得审核日志分页")
    public CommonResult<PageResult<CrmFlowLogRespVO>> getFlowLogPage(@Valid CrmFlowLogPageReqVO pageReqVO) {
        PageResult<CrmFlowLogRespVO> pageResult = flowLogService.getFlowLogPage(pageReqVO);
        return success(pageResult);
    }

    @PostMapping("/examine")
    @Operation(summary = "审核")
    public CommonResult<Boolean> examine(@Valid @RequestBody CrmFlowLogSaveReqVO saveReqVO) {
        flowLogService.examine(saveReqVO);
        return success(true);
    }



}