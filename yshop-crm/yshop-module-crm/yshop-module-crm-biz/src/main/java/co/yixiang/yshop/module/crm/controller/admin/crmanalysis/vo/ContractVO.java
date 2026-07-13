package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 合同与回款 Response VO")
@Data
public class ContractVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "数量")
    private Long count;

    @Schema(description = "金额")
    private BigDecimal money;




}
