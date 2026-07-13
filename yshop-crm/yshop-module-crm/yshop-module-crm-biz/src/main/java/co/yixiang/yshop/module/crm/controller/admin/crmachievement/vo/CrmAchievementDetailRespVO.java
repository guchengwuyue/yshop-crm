package co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "管理后台 - 业绩详细目标 Response VO")
@Data
@Builder
public class CrmAchievementDetailRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20637")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    private String name;

    @Schema(description = "1销售（目标）2回款（目标）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer config;

    @Schema(description = "3部门2员工", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "对象ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29701")
    private Long typeId;

    @Schema(description = "年", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer year;

    @Schema(description = "一月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> january;

    @Schema(description = "二月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> february;

    @Schema(description = "三月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> march;

    @Schema(description = "四月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> april;

    @Schema(description = "五月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> may;

    @Schema(description = "六月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> june;

    @Schema(description = "七月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> july;

    @Schema(description = "八月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> august;

    @Schema(description = "九月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> september;

    @Schema(description = "十月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> october;

    @Schema(description = "十一月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> november;

    @Schema(description = "十二月", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,String> december;

    @Schema(description = "年目标", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal yeartarget;

    @Schema(description = "添加时间")
    private LocalDateTime createTime;

}