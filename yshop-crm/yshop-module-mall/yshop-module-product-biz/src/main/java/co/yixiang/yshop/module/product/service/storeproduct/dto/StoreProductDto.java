package co.yixiang.yshop.module.product.service.storeproduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;


/**
 * 商品对象DTO
 *
 * @author hupeng
 * @date 2020-04-23
 */
@Getter
@Setter
@ToString
public class StoreProductDto
{

    /** 商品id */
    private Long id;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /** 商品图片 */
    @NotBlank(message = "商品图片必传")
    private String image;

    /** 轮播图 */
    @NotNull(message = "轮播图不为空")
    @JsonProperty("slider_image")
    private List<String> sliderImage;

    /** 商品名称 */
    @NotBlank(message = "商品名称不能为空")
    @JsonProperty("store_name")
    private String storeName;

    /** 商品简介 */
    @JsonProperty("store_info")
    private String storeInfo;

    /** 关键字 */
    //@NotBlank(message = "关键字不能为空")
    private String keyword;

    /** 商品条码（一维码） */
    @JsonProperty("bar_code")
    private String barCode;

    /** 分类id */
    @NotNull(message = "分类id不能为空")
    @JsonProperty("cate_id")
    private String cateId;

    /** 商品价格 */
    private Double price;

    /** 市场价 */
    private Double otPrice;

    /** 邮费 */
    private Double postage;

    /** 单位名 */
    @JsonProperty("unit_name")
    private String unitName;

    /** 排序 */
    private Long sort;

    /** 销量 */
    private Long sales;

    /** 库存 */
    private Long stock;

    /** 状态（0：未上架，1：上架） */
    @JsonProperty("is_show")
    private Integer isShow;

    /** 是否热卖 */
    @JsonProperty("is_hot")
    private Integer isHot;

    /** 是否优惠 */
    @JsonProperty("is_benefit")
    private Integer isBenefit;

    /** 是否精品 */
    @JsonProperty("is_best")
    private Integer isBest;

    /** 是否新品 */
    @JsonProperty("is_new")
    private Integer isNew;

    /** 商品描述 */
    @NotBlank(message = "商品详情不能为空")
    private String description;


    /** 是否包邮 */
    @JsonProperty("is_postage")
    private Integer isPostage;

    /** 获得积分 */
    @JsonProperty("give_integral")
    private Double giveIntegral;

    /** 成本价 */
    private Double cost;


    /** 是否优品推荐 */
    @JsonProperty("is_good")
    private Integer isGood;

    /** 是否单独分佣 */
    @JsonProperty("is_sub")
    private Integer isSub;

    /** 是否开启啊积分兑换 */
    @JsonProperty("is_integral")
    private Integer isIntegral;

    /** 虚拟销量 */
    private Long ficti;

    /** 运费模板ID */
    @JsonProperty("temp_id")
    private Long tempId;

    /** 规格 0单 1多 */
    @JsonProperty("spec_type")
    private Integer specType;

    //属性项目
    private List<FromatDetailDto> items;

    //sku结果集
    private List<ProductFormatDto> attrs;


}
