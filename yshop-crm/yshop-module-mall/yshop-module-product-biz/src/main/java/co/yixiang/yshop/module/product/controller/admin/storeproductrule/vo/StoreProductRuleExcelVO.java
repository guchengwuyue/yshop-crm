package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品规则值(规格) Excel VO
 *
 * @author yshop
 */
@Data
public class StoreProductRuleExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("规格名称")
    private String ruleName;

    @ExcelProperty("规格值")
    private JSONArray ruleValue;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
