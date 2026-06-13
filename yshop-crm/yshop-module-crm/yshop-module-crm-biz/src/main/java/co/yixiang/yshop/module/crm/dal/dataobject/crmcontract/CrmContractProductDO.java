package co.yixiang.yshop.module.crm.dal.dataobject.crmcontract;

import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 合同产品关系 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_contract_product")
@KeySequence("yshop_crm_contract_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmContractProductDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 合同ID
     */
    private Long contractId;
    /**
     * 产品ID
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
     * 产品单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer nums;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 小计（折扣后价格）
     */
    private BigDecimal subtotal;
    /**
     * 备注
     */
    private String remarks;

}