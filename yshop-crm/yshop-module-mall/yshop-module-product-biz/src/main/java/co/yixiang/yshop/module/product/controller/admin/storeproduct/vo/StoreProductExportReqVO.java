package co.yixiang.yshop.module.product.controller.admin.storeproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 商品 Excel 导出 Request VO，参数和 StoreProductPageReqVO 是一致的")
@Data
public class StoreProductExportReqVO {

    @Schema(description = "商品名称", example = "张三")
    private String storeName;

    @Schema(description = "是否包邮")
    private Byte isPostage;

}
