package co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 审核日志新增/修改 Request VO")
@Data
public class CrmFlowLogSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28396")
    private Long id;

    @Schema(description = "审批流程ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9346")
    @NotNull(message = "审批流程ID不能为空")
    private Long flowId;

    @Schema(description = "对象ID（如审批类型ID）", requiredMode = Schema.RequiredMode.REQUIRED, example = "12336")
    @NotNull(message = "对象ID（如审批类型ID）不能为空")
    private Long typesId;

    @Schema(description = "审核人", requiredMode = Schema.RequiredMode.REQUIRED, example = "30258")
    @NotNull(message = "审核人不能为空")
    private Long adminId;

    @Schema(description = "关联对象 contract-合同 receivables-回款 invoice-发票", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "关联对象 contract-合同 receivables-回款 invoice-发票不能为空")
    private String types;

    @Schema(description = "审核人名称", example = "芋艿")
    private String nickname;

    @Schema(description = "审核意见", example = "随便")
    private String remark;

    @Schema(description = "1审核结束", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "1审核结束不能为空")
    private Integer isEnd;

    @Schema(description = "0审核未通过,1审核通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "0审核未通过,1审核通过不能为空")
    private Integer status;

}