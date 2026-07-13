package co.yixiang.yshop.module.pay.dal.dataobject.merchantdetails;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 支付服务商配置 DO
 *
 * @author yshop
 */
@TableName("merchant_details")
@KeySequence("merchant_details_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDetailsDO extends BaseDO {

    /**
     * 列表id
     */
    @TableId(type = IdType.INPUT)
    private String detailsId;
    /**
     * 支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等
     */
    private String payType;
    /**
     * 应用id
     */
    private String appid;
    /**
     * 商户id，商户号，合作伙伴id等等
     */
    private String mchId;
    /**
     * 当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM,CLASS_PATH,URL
     */
    private String certStoreType;
    /**
     * 私钥或私钥证书
     */
    private String keyPrivate;
    /**
     * 公钥或公钥证书
     */
    private String keyPublic;
    /**
     * key证书,附加证书使用，如SSL证书，或者银联根级证书方面
     */
    private String keyCert;
    /**
     * 私钥证书或key证书的密码
     */
    private String keyCertPwd;
    /**
     * 异步回调
     */
    private String notifyUrl;
    /**
     * 同步回调地址，大部分用于付款成功后页面转跳
     */
    private String returnUrl;
    /**
     * 签名方式,目前已实现多种签名方式详情查看com.egzosn.pay.common.util.sign.encrypt。MD5,RSA等等
     */
    private String signType;
    /**
     * 收款账号，暂时只有支付宝部分使用，可根据开发者自行使用
     */
    private String seller;
    /**
     * 子appid
     */
    private String subAppId;
    /**
     * 子商户id
     */
    private String subMchId;
    /**
     * 编码类型，大部分为utf-8
     */
    private String inputCharset;
    /**
     * 是否为测试环境: 0 否，1 测试环境
     */
    private Integer isTest;

}
