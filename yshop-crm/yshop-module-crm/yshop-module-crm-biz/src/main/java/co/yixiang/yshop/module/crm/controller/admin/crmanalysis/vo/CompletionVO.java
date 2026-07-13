package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 业绩完成度 Response VO")
@Data
public class CompletionVO {

    @Schema(description = "时间")
    private String time;

    @Schema(description = "目标金额")
    private BigDecimal targetPrice;

    @Schema(description = "成交金额")
    private BigDecimal successPrice;

    @Schema(description = "成交数量")
    private Long successCount;

    @Schema(description = "平均客单价")
    private BigDecimal perPrice;



}
