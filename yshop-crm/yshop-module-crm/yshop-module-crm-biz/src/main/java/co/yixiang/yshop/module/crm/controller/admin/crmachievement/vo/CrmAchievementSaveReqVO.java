package co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 业绩目标新增/修改 Request VO")
@Data
public class CrmAchievementSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20637")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    private String name;

    @Schema(description = "1销售（目标）2回款（目标）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "业绩方式不能为空")
    private Integer config;

    @Schema(description = "2部门3员工", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "对象ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29701")
    private Long typeId;

    @Schema(description = "年", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "年不能为空")
    private Integer year;

    @Schema(description = "一月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal january;

    @Schema(description = "二月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal february;

    @Schema(description = "三月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal march;

    @Schema(description = "四月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal april;

    @Schema(description = "五月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal may;

    @Schema(description = "六月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal june;

    @Schema(description = "七月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal july;

    @Schema(description = "八月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal august;

    @Schema(description = "九月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal september;

    @Schema(description = "十月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal october;

    @Schema(description = "十一月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal november;

    @Schema(description = "十二月", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal december;

    @Schema(description = "年目标", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal yeartarget;

}