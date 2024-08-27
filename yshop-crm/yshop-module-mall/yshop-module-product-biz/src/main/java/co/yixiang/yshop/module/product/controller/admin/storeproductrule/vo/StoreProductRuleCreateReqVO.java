package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 商品规则值(规格)创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductRuleCreateReqVO extends StoreProductRuleBaseVO {

}
