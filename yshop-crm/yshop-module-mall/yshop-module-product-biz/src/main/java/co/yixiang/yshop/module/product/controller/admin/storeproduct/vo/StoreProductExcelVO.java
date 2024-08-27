package co.yixiang.yshop.module.product.controller.admin.storeproduct.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品 Excel VO
 *
 * @author yshop
 */
@Data
public class StoreProductExcelVO {

    @ExcelProperty("商品id")
    private Long id;

    @ExcelProperty("商品图片")
    private String image;

    @ExcelProperty("商品名称")
    private String storeName;

    @ExcelProperty("商品价格")
    private BigDecimal price;

    @ExcelProperty("单位名")
    private String unitName;

    @ExcelProperty("销量")
    private Integer sales;

    @ExcelProperty("库存")
    private Integer stock;

    @ExcelProperty("添加时间")
    private LocalDateTime createTime;

    @ExcelProperty("是否包邮")
    private Integer isPostage;

}
