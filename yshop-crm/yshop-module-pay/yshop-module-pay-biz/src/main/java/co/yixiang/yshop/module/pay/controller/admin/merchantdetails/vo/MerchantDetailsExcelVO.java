package co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo;

import lombok.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 支付服务商配置 Excel VO
 *
 * @author yshop
 */
@Data
public class MerchantDetailsExcelVO {

    @ExcelProperty("列表id")
    private String detailsId;

    @ExcelProperty("支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等")
    private String payType;

    @ExcelProperty("应用id")
    private String appid;

    @ExcelProperty("商户id，商户号，合作伙伴id等等")
    private String mchId;

    @ExcelProperty("当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM,CLASS_PATH,URL")
    private String certStoreType;

    @ExcelProperty("私钥或私钥证书")
    private String keyPrivate;

    @ExcelProperty("公钥或公钥证书")
    private String keyPublic;

    @ExcelProperty("key证书,附加证书使用，如SSL证书，或者银联根级证书方面")
    private String keyCert;

    @ExcelProperty("私钥证书或key证书的密码")
    private String keyCertPwd;

    @ExcelProperty("异步回调")
    private String notifyUrl;

    @ExcelProperty("同步回调地址，大部分用于付款成功后页面转跳")
    private String returnUrl;

    @ExcelProperty("签名方式,目前已实现多种签名方式详情查看com.egzosn.pay.common.util.sign.encrypt。MD5,RSA等等")
    private String signType;

    @ExcelProperty("收款账号，暂时只有支付宝部分使用，可根据开发者自行使用")
    private String seller;

    @ExcelProperty("子appid")
    private String subAppId;

    @ExcelProperty("子商户id")
    private String subMchId;

    @ExcelProperty("编码类型，大部分为utf-8")
    private String inputCharset;

    @ExcelProperty("是否为测试环境: 0 否，1 测试环境")
    private Integer isTest;

}
