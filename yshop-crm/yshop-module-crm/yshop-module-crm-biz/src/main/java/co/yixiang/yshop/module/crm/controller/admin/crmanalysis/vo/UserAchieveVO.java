package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 用户业绩分析信息 Response VO")
@Data
public class UserAchieveVO {

    @Schema(description = "用户编号", example = "1024")
    private Long id;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "合同数量")
    private Long contractCount;

    @Schema(description = "合同金额")
    private BigDecimal contractMoney;

    @Schema(description = "回款金额")
    private BigDecimal receivablesMoney;

    @Schema(description = "线索数量")
    private Long cluesCount;

    @Schema(description = "转客数量")
    private Long cluesToCustomerCount;

    @Schema(description = "转客比率")
    private String cluesToCustomerPer;

}
