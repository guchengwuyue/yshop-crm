package co.yixiang.yshop.module.product.enums;

import co.yixiang.yshop.framework.common.exception.ErrorCode;

/**
 * Product 错误码枚举类
 *
 * product 系统，使用 1-008-000-000 段
 */
public interface ErrorCodeConstants {

    ErrorCode PARAM_ERROR = new ErrorCode(1008001000, "参数非法");
    // ========== 商品分类相关 1008001000 ============
    ErrorCode CATEGORY_NOT_EXISTS = new ErrorCode(1008001000, "商品分类不存在");
    ErrorCode CATEGORY_PARENT_NOT_EXISTS = new ErrorCode(1008001001, "父分类不存在");
    ErrorCode CATEGORY_PARENT_NOT_FIRST_LEVEL = new ErrorCode(1008001002, "分类最多只有二级哦");
    ErrorCode CATEGORY_EXISTS_CHILDREN = new ErrorCode(1008001003, "存在子分类，无法删除");
    ErrorCode CATEGORY_DISABLED = new ErrorCode(1008001004, "商品分类({})已禁用，无法使用");

    // ========== 商品品牌相关编号 1008002000 ==========
    ErrorCode BRAND_NOT_EXISTS = new ErrorCode(1008002000, "品牌不存在");
    ErrorCode BRAND_DISABLED = new ErrorCode(1008002001, "品牌不存在");
    ErrorCode BRAND_NAME_EXISTS = new ErrorCode(1008002002, "品牌名称已存在");

    // ========== 商品 1008003000 ==========
    ErrorCode STORE_PRODUCT_NOT_EXISTS = new ErrorCode(1008003000, "商品不存在");
    ErrorCode STORE_PRODUCT_ATTR_NOT_EXISTS = new ErrorCode(1008003001, "商品属性不存在");
    ErrorCode STORE_PRODUCT_ATTR_RESULT_NOT_EXISTS = new ErrorCode(1008003002, "商品属性详情不存在");
    ErrorCode STORE_PRODUCT_ATTR_VALUE_NOT_EXISTS = new ErrorCode(1008003003, "商品属性值不存在");
    ErrorCode STORE_PRODUCT_RULE_NOT_EXISTS = new ErrorCode(1008003004, "商品规则值(规格)不存在");
    ErrorCode STORE_PRODUCT_RULE_NEED = new ErrorCode(1008003005, "请至少添加一个规格值哦");
    ErrorCode STORE_PRODUCT_RULE_RE = new ErrorCode(1008003006, "规格值里包含'-',请重新添加");
    ErrorCode STORE_PRODUCT_STOCK_ERROR = new ErrorCode(1008003007, "库存不能低于0");
    ErrorCode STORE_PRODUCT_SLIDER_ERROR = new ErrorCode(1008003008, "请上传轮播图");
    ErrorCode STORE_PRODUCT_ATTR_NEED = new ErrorCode(1008003009, "请设置至少一个属性");

    // ========== 运费模板 1008004000 ==========
    ErrorCode SHIPPING_TEMPLATES_NOT_EXISTS = new ErrorCode(1008004000, "运费模板不存在");
    ErrorCode SHIPPING_TEMPLATES_FREE_NOT_EXISTS = new ErrorCode(1008004001, "请添加包邮区域");
    ErrorCode SHIPPING_TEMPLATES_REGION_NOT_EXISTS = new ErrorCode(1008004002, "请添加区域");
    ErrorCode SHIPPING_TEMPLATES_FREE_NEED = new ErrorCode(1008004000, "请指定包邮地区");


    // ========== 评论 1008005000 ==========
    ErrorCode STORE_PRODUCT_REPLY_NOT_EXISTS = new ErrorCode(1008005000, "评论不存在");
    ErrorCode STORE_PRODUCT_RELATION_NOT_EXISTS = new ErrorCode(1008005001, "商品点赞和收藏不存在");
    ErrorCode STORE_PRODUCT_RELATION_EXISTS = new ErrorCode(1008005002, "已经收藏过");
    ErrorCode PRODUCT_STOCK_LESS = new ErrorCode(1008005003, "商品库存不足");


}
