package co.yixiang.yshop.module.product.controller.admin.storeproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
* 商品 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class StoreProductBaseVO {

    @Schema(description = "商品图片", required = true)
    @NotNull(message = "商品图片不能为空")
    private String image;

    private String shopName;

    private Integer shopId;

    @Schema(description = "商品名称", required = true, example = "张三")
    @NotNull(message = "商品名称不能为空")
    private String storeName;

    @Schema(description = "商品价格", required = true, example = "18735")
    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;

    @Schema(description = "单位名", example = "李四")
    private String unitName;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "上架状态")
    private Integer isShow;

    @Schema(description = "是否包邮")
    private Integer isPostage;

}
