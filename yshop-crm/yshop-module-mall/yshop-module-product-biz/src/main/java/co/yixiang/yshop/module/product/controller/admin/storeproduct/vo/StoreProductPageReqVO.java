package co.yixiang.yshop.module.product.controller.admin.storeproduct.vo;

import co.yixiang.yshop.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 商品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductPageReqVO extends PageParam {

    @Schema(description = "商品名称", example = "张三")
    private String storeName;

    private String shopName;

    @Schema(description = "是否包邮")
    private Byte isPostage;

    @Schema(description = "上下架", example = "1")
    private String isShow;

    @Schema(description = "库存售罄", example = "0")
    private String stock;

    @Schema(description = "库存售罄", example = "0")
    private String cateId;

    private List<Long> catIds;

}
