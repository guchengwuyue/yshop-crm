package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 商品规则值(规格) Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductRuleRespVO extends StoreProductRuleBaseVO {

    @Schema(description = "id", required = true, example = "2067")
    private Integer id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
