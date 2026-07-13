package co.yixiang.yshop.module.product.controller.admin.storeproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 商品 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductRespVO extends StoreProductBaseVO {

    @Schema(description = "商品id", required = true, example = "1175")
    private Long id;

    @Schema(description = "添加时间")
    private LocalDateTime createTime;

}
