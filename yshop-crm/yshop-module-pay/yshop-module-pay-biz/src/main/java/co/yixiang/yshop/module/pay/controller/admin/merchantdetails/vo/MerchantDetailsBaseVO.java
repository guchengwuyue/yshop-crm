package co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo;

import co.yixiang.yshop.framework.desensitize.core.slider.annotation.SliderDesensitize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.*;

/**
* 支付服务商配置 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class MerchantDetailsBaseVO {

    private String detailsId;

    @Schema(description = "支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等", required = true, example = "2")
    @NotNull(message = "支付类型(支付渠道)等不能为空")
    private String payType;

    //@SliderDesensitize(prefixKeep=3 ,suffixKeep=4)
    @Schema(description = "应用id", example = "1718")
    private String appid;

    @Schema(description = "商户id，商户号，合作伙伴id等等", example = "21574")
    //@SliderDesensitize(prefixKeep=2 ,suffixKeep=2)
    private String mchId;

    @Schema(description = "当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM,CLASS_PATH,URL", example = "1")
    private String certStoreType;

    @Schema(description = "私钥或私钥证书")
    //@SliderDesensitize(prefixKeep=4 ,suffixKeep=4)
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

    @Schema(description = "签名方式,目前已实现多种签名方式详情查看com.egzosn.pay.common.util.sign.encrypt。MD5,RSA等等", required = true, example = "1")
    @NotNull(message = "签名方式不能为空")
    private String signType;

    @Schema(description = "收款账号，暂时只有支付宝部分使用，可根据开发者自行使用")
    private String seller;

    @Schema(description = "子appid", example = "13761")
    private String subAppId;

    @Schema(description = "子商户id", example = "10127")
    private String subMchId;

    @Schema(description = "编码类型，大部分为utf-8", required = true)
    //@NotNull(message = "编码类型，大部分为utf-8不能为空")
    private String inputCharset;

    @Schema(description = "是否为测试环境: 0 否，1 测试环境", required = true)
    @NotNull(message = "是否为测试环境: 0 否，1 测试环境不能为空")
    private Integer isTest;

}
