package co.yixiang.yshop.module.crm.controller.admin.crmflow.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 审批流程下一步 Response VO")
@Data
@Builder
public class CrmFlowStepResultVO {

    @Schema(description = "是否审核完成")
    private Boolean isSuccess;

    @Schema(description = "审核步骤ID ")
    private Long stepId;



}