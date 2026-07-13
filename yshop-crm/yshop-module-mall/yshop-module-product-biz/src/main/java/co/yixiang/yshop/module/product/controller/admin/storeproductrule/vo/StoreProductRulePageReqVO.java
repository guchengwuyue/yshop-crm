package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 商品规则值(规格)分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductRulePageReqVO extends PageParam {

    @Schema(description = "规格名称", example = "赵六")
    private String ruleName;

}
