package co.yixiang.yshop.module.product.dal.dataobject.storeproductrule;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 商品规则值(规格) DO
 *
 * @author yshop
 */
@TableName(value = "yshop_store_product_rule",autoResultMap = true)
@KeySequence("yshop_store_product_rule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductRuleDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 规格名称
     */
    private String ruleName;
    /**
     * 规格值
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray ruleValue;

}
