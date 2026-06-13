package co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness;

import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 商机产品关联 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_business_product")
@KeySequence("yshop_crm_business_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmBusinessProductDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 商机id
     */
    private Long businessId;
    /**
     * 产品id
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 商品属性
     */
    private String productAttrUnique;

    @TableField(exist = false)
    private String sku;

    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private Integer nums;
    /**
     * 小计
     */
    private BigDecimal subtotal;
    /**
     * 备注
     */
    private String remarks;

}