package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 部门业绩信息 Response VO")
@Data
public class DeptAchieveVO {

    @Schema(description = "部门编号", example = "1024")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "yshop")
    private String name;

    @Schema(description = "父部门 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long parentId;

    @Schema(description = "合同数量")
    private Long contractCount;

    @Schema(description = "合同金额")
    private BigDecimal contractMoney;

    @Schema(description = "回款金额")
    private BigDecimal receivablesMoney;



}
