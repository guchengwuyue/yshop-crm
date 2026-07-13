package co.yixiang.yshop.framework.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 支付ID枚举
 */
@Getter
@AllArgsConstructor
public enum PayIdEnum {
    WX_H5("wx_h5","微信支付H5"),
    WX_MINIAPP("wx_miniapp","微信支付小程序"),
    WX_WECHAT("wx_wechat","微信支付公众号"),
    WX_PC("wx_pc","微信支付pc"),
    WX_APP("wx_app","微信支付app"),
    ALI_H5("ali_h5","支付宝H5"),
    ALI_MINIAPP("ali_miniapp","支付宝小程序"),
    ALI_WECHAT("ali_wechat","支付宝公众号"),
    ALI_PC("ali_pc","支付宝pc"),
    ALI_APP("ali_app","支付宝app");

    private String value;
    private String desc;
}
