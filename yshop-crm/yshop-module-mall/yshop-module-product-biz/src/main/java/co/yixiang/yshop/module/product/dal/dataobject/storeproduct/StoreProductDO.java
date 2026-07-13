package co.yixiang.yshop.module.product.dal.dataobject.storeproduct;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 商品 DO
 *
 * @author yshop
 */
@TableName("yshop_store_product")
@KeySequence("yshop_store_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductDO extends BaseDO {

    /**
     * 商品id
     */
    @TableId
    private Long id;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 轮播图
     */
    private String sliderImage;
    /**
     * 商品名称
     */
    private String storeName;
    /**
     * 商品简介
     */
    private String storeInfo;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 产品条码（一维码）
     */
    private String barCode;
    /**
     * 分类id
     */
    private String cateId;

    //品牌id
    private Long brandId;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 会员价格
     */
    private BigDecimal vipPrice;
    /**
     * 市场价
     */
    private BigDecimal otPrice;
    /**
     * 邮费
     */
    private BigDecimal postage;
    /**
     * 单位名
     */
    private String unitName;
    /**
     * 排序
     */
    private Short sort;
    /**
     * 销量
     */
    private Integer sales;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 状态（0：未上架，1：上架）
     */
    private Integer isShow;
    /**
     * 是否热卖
     */
    private Boolean isHot;
    /**
     * 是否优惠
     */
    private Boolean isBenefit;
    /**
     * 是否精品
     */
    private Boolean isBest;
    /**
     * 是否新品
     */
    private Integer isNew;
    /**
     * 产品描述
     */
    private String description;
    /**
     * 是否包邮
     */
    private Integer isPostage;
    /**
     * 商户是否代理 0不可代理1可代理
     */
    private Byte merUse;
    /**
     * 获得积分
     */
    private BigDecimal giveIntegral;
    /**
     * 成本价
     */
    private BigDecimal cost;
    /**
     * 秒杀状态 0 未开启 1已开启
     */
    private Byte isSeckill;
    /**
     * 砍价状态 0未开启 1开启
     */
    private Byte isBargain;
    /**
     * 是否优品推荐
     */
    private Boolean isGood;
    /**
     * 虚拟销量
     */
    private Integer ficti;
    /**
     * 浏览量
     */
    private Integer browse;
    /**
     * 产品二维码地址(用户小程序海报)
     */
    private String codePath;
    /**
     * 是否单独分佣
     */
    private Boolean isSub;
    /**
     * 运费模板ID
     */
    private Integer tempId;
    /**
     * 规格 0单 1多
     */
    private Integer specType;
    /**
     * 是开启积分兑换
     */
    private Byte isIntegral;
    /**
     * 需要多少积分兑换 只在开启积分兑换时生效
     */
    private Integer integral;

}
