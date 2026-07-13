package co.yixiang.yshop.module.product.controller.admin.storeproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 商品更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreProductUpdateReqVO extends StoreProductBaseVO {

    @Schema(description = "商品id", required = true, example = "1175")
    @NotNull(message = "商品id不能为空")
    private Long id;

    @Schema(description = "轮播图", required = true)
    @NotNull(message = "轮播图不能为空")
    private String sliderImage;

    @Schema(description = "商品简介", required = true)
    @NotNull(message = "商品简介不能为空")
    private String storeInfo;

    @Schema(description = "关键字", required = true)
    @NotNull(message = "关键字不能为空")
    private String keyword;

    @Schema(description = "产品条码（一维码）")
    private String barCode;

    @Schema(description = "分类id", required = true, example = "4928")
    @NotNull(message = "分类id不能为空")
    private String cateId;

    @Schema(description = "会员价格", example = "18248")
    private BigDecimal vipPrice;

    @Schema(description = "市场价", example = "14818")
    private BigDecimal otPrice;

    @Schema(description = "邮费")
    private BigDecimal postage;

    @Schema(description = "排序")
    private Short sort;

    @Schema(description = "状态（0：未上架，1：上架）")
    private Integer isShow;

    @Schema(description = "是否热卖")
    private Boolean isHot;

    @Schema(description = "是否优惠")
    private Boolean isBenefit;

    @Schema(description = "是否精品")
    private Boolean isBest;

    @Schema(description = "是否新品")
    private Integer isNew;

    @Schema(description = "产品描述", example = "你说的对")
    private String description;

    @Schema(description = "商户是否代理 0不可代理1可代理")
    private Byte merUse;

    @Schema(description = "获得积分")
    private BigDecimal giveIntegral;

    @Schema(description = "成本价")
    private BigDecimal cost;

    @Schema(description = "秒杀状态 0 未开启 1已开启")
    private Byte isSeckill;

    @Schema(description = "砍价状态 0未开启 1开启")
    private Byte isBargain;

    @Schema(description = "是否优品推荐")
    private Boolean isGood;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "浏览量")
    private Integer browse;

    @Schema(description = "产品二维码地址(用户小程序海报)", required = true)
    @NotNull(message = "产品二维码地址(用户小程序海报)不能为空")
    private String codePath;

    @Schema(description = "是否单独分佣")
    private Boolean isSub;

    @Schema(description = "运费模板ID", example = "18065")
    private Integer tempId;

    @Schema(description = "规格 0单 1多", example = "1")
    private Integer specType;

    @Schema(description = "是开启积分兑换")
    private Byte isIntegral;

    @Schema(description = "需要多少积分兑换 只在开启积分兑换时生效")
    private Integer integral;

}
