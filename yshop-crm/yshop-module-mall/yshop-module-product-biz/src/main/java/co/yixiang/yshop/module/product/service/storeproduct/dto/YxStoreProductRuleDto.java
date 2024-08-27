/**
* Copyright (C) 2018-2022
* All rights reserved, Designed By www.yixiang.co
* 注意：
* 本软件为www.yixiang.co开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/
package co.yixiang.yshop.module.product.service.storeproduct.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author hupeng
* @date 2020-06-28
*/
@Data
public class YxStoreProductRuleDto implements Serializable {

    private Integer id;

    /** 规格名称 */
    private String ruleName;

    /** 规格值 */
    private JSONArray ruleValue;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer isDel;
}
