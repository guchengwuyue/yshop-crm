package co.yixiang.yshop.module.crm.controller.admin.crmflow.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;

@Schema(description = "管理后台 - 审批流程新增/修改 Request VO")
@Data
public class CrmFlowSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21989")
    private Long id;

    @Schema(description = "审批流名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "审批流名称不能为空")
    private String name;

    @Schema(description = "关联对象 contract-合同 receivables-回款 invoice-发票", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "清选择类型")
    private String types;

    @Schema(description = "流程说明", requiredMode = Schema.RequiredMode.REQUIRED, example = "你说的对")
    private String remark;

    @Schema(description = "状态 1启用，0禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "审批步骤列表")
    private List<CrmFlowStepDO> flowSteps;

}