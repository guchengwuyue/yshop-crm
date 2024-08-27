package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 商品规则值(规格)更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductRuleUpdateReqVO extends StoreProductRuleBaseVO {

    @Schema(description = "id", required = true, example = "2067")
    @NotNull(message = "id不能为空")
    private Integer id;

}
