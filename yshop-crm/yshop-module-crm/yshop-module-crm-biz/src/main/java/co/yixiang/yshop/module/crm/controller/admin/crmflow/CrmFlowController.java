package co.yixiang.yshop.module.crm.controller.admin.crmflow;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

import co.yixiang.yshop.framework.excel.core.util.ExcelUtils;

import co.yixiang.yshop.framework.apilog.core.annotation.ApiAccessLog;
import static co.yixiang.yshop.framework.apilog.core.enums.OperateTypeEnum.*;

import co.yixiang.yshop.module.crm.controller.admin.crmflow.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;
import co.yixiang.yshop.module.crm.service.crmflow.CrmFlowService;

@Tag(name = "管理后台 - 审批流程")
@RestController
@RequestMapping("/crm/flow")
@Validated
public class CrmFlowController {

    @Resource
    private CrmFlowService flowService;

    @PostMapping("/create")
    @Operation(summary = "创建审批流程")
    @PreAuthorize("@ss.hasPermission('crm:flow:create')")
    public CommonResult<Long> createFlow(@Valid @RequestBody CrmFlowSaveReqVO createReqVO) {
        return success(flowService.createFlow(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新审批流程")
    @PreAuthorize("@ss.hasPermission('crm:flow:update')")
    public CommonResult<Boolean> updateFlow(@Valid @RequestBody CrmFlowSaveReqVO updateReqVO) {
        flowService.updateFlow(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除审批流程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:flow:delete')")
    public CommonResult<Boolean> deleteFlow(@RequestParam("id") Long id) {
        flowService.deleteFlow(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得审批流程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:flow:query')")
    public CommonResult<CrmFlowRespVO> getFlow(@RequestParam("id") Long id) {
        CrmFlowDO flow = flowService.getFlow(id);
        return success(BeanUtils.toBean(flow, CrmFlowRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得审批流程分页")
    @PreAuthorize("@ss.hasPermission('crm:flow:query')")
    public CommonResult<PageResult<CrmFlowRespVO>> getFlowPage(@Valid CrmFlowPageReqVO pageReqVO) {
        PageResult<CrmFlowDO> pageResult = flowService.getFlowPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CrmFlowRespVO.class));
    }


    // ==================== 子表（审批步骤） ====================

    @GetMapping("/flow-step/list-by-flow-id")
    @Operation(summary = "获得审批步骤列表")
    @Parameter(name = "flowId", description = "审批流程ID")
    @PreAuthorize("@ss.hasPermission('crm:flow:query')")
    public CommonResult<List<CrmFlowStepDO>> getFlowStepListByFlowId(@RequestParam("flowId") Long flowId) {
        return success(flowService.getFlowStepListByFlowId(flowId));
    }

    @GetMapping("/flow-users")
    @Operation(summary = "获得审批人列表")
    @Parameter(name = "flowType", description = "contract")
    public CommonResult<List<Long>> getFlowUserIds(@RequestParam("flowType") String flowType) {
        return success(flowService.getFlowUserIds(flowType,0L));
    }

    @DeleteMapping("/delete-step")
    @Operation(summary = "删除审批步骤")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteFlowStep(@RequestParam("id") Long id) {
        flowService.deleteFlowStep(id);
        return success(true);
    }


}