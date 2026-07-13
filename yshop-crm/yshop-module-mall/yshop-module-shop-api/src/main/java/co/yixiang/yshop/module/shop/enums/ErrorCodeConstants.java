package co.yixiang.yshop.module.shop.enums;

import co.yixiang.yshop.framework.common.exception.ErrorCode;

/**
 * Product 错误码枚举类
 *
 * product 系统，使用 1-008-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 素材相关 1009001000 ============
    ErrorCode MATERIAL_NOT_EXISTS = new ErrorCode(1009001000, "素材库不存在");
    ErrorCode MATERIAL_GROUP_NOT_EXISTS = new ErrorCode(1009001001, "素材分组不存在");
    ErrorCode ADS_NOT_EXISTS = new ErrorCode(1008017000, "广告图管理不存在");
    ErrorCode SERVICE_NOT_EXISTS = new ErrorCode(1008017001, "我的服务不存在");
    ErrorCode RECHARGE_NOT_EXISTS = new ErrorCode(1008017002, "充值金额不存在");

    // ========== 商品规则值(规格)  ==========


}
