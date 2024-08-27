package co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 支付服务商配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MerchantDetailsPageReqVO extends PageParam {

    @Schema(description = "支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等", example = "2")
    private String payType;

    @Schema(description = "应用id", example = "1718")
    private String appid;

    @Schema(description = "商户id，商户号，合作伙伴id等等", example = "21574")
    private String mchId;

    @Schema(description = "当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM,CLASS_PATH,URL", example = "1")
    private String certStoreType;

    @Schema(description = "私钥或私钥证书")
    private String keyPrivate;

    @Schema(description = "公钥或公钥证书")
    private String keyPublic;

    @Schema(description = "key证书,附加证书使用，如SSL证书，或者银联根级证书方面")
    private String keyCert;

    @Schema(description = "私钥证书或key证书的密码")
    private String keyCertPwd;

    @Schema(description = "异步回调", example = "https://www.yixiang.co")
    private String notifyUrl;

    @Schema(description = "同步回调地址，大部分用于付款成功后页面转跳", example = "https://www.yixiang.co")
    private String returnUrl;

    @Schema(description = "签名方式,目前已实现多种签名方式详情查看com.egzosn.pay.common.util.sign.encrypt。MD5,RSA等等", example = "1")
    private String signType;

    @Schema(description = "收款账号，暂时只有支付宝部分使用，可根据开发者自行使用")
    private String seller;

    @Schema(description = "子appid", example = "13761")
    private String subAppId;

    @Schema(description = "子商户id", example = "10127")
    private String subMchId;

    @Schema(description = "编码类型，大部分为utf-8")
    private String inputCharset;

    @Schema(description = "是否为测试环境: 0 否，1 测试环境")
    private Boolean isTest;

}
