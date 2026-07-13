package co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 审核日志新增/修改 Request VO")
@Data
public class CrmFlowLogSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28396")
    private Long id;

    @Schema(description = "审核意见", example = "随便")
    private String remark;

    @Schema(description = "审核状态 agree-同意 refuse-拒绝", requiredMode = Schema.RequiredMode.REQUIRED)
    private String checkType;


}