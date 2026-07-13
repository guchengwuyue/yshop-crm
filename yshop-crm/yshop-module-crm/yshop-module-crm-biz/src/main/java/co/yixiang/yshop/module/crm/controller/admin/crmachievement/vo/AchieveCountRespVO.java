package co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 业绩统计信息 Response VO")
@Data
@Builder
public class AchieveCountRespVO {

    @Schema(description = "合同目标")
    private BigDecimal contractMoney;

    @Schema(description = "合同成交")
    private BigDecimal contractSuccessMoney;

    @Schema(description = "回款目标")
    private BigDecimal receivablesMoney;

    @Schema(description = "回款成交")
    private BigDecimal receivablesSuccessMoney;

    @Schema(description = "合同完成百分比")
    private String contractPer;

    @Schema(description = "回款完成百分比")
    private String receivablesPer;

}
