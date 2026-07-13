///**
// * Copyright (C) 2018-2022
// * All rights reserved, Designed By www.yixiang.co
// * 注意：
// * 本软件为www.yixiang.co开发研制，未经购买不得使用
// * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
// * 一经发现盗用、分享等行为，将追究法律责任，后果自负
// */
//package co.yixiang.yshop.module.product.service.storeproduct.dto;
//
//import co.yixiang.modules.product.domain.YxStoreProduct;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//
///**
// * @author hupeng
// * @date 2020-09-03
// */
//@Data
//public class YxStoreProductRelationDto implements Serializable {
//
//    private Long id;
//
//    /** 用户ID */
//    private Long uid;
//
//    private String userName;
//
//    /** 商品ID */
//    private Long productId;
//
//    private YxStoreProduct product;
//
//    /** 类型(收藏(collect）、点赞(like)) */
//    private String type;
//
//    /** 某种类型的商品(普通商品、秒杀商品) */
//    private String category;
//
//    /** 添加时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Timestamp createTime;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Timestamp updateTime;
//
//    private Integer isDel;
//}
//
