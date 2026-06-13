package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 商品规则值(规格)创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductRuleCreateReqVO extends StoreProductRuleBaseVO {

}
