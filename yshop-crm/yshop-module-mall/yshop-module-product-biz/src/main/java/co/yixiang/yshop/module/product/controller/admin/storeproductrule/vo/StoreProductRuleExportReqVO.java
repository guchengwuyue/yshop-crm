package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 商品规则值(规格) Excel 导出 Request VO，参数和 StoreProductRulePageReqVO 是一致的")
@Data
public class StoreProductRuleExportReqVO {

    @Schema(description = "规格名称", example = "赵六")
    private String ruleName;

}
