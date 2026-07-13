package co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo;


import com.alibaba.fastjson.JSONArray;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.*;


/**
* 商品规则值(规格) Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class StoreProductRuleBaseVO {

    @Schema(description = "id", required = false, example = "有值表示是更新没值是添加")
    private Integer id;

    @Schema(description = "规格名称", required = true, example = "赵六")
    @NotNull(message = "规格名称不能为空")
    private String ruleName;

    @Schema(description = "规格值", required = true)
    @NotNull(message = "规格值不能为空")
    private JSONArray ruleValue;

}
