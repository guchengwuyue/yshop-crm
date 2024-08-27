package co.yixiang.yshop.module.system.api.sms.dto.code;

import co.yixiang.yshop.framework.common.validation.InEnum;
import co.yixiang.yshop.framework.common.validation.Mobile;
import co.yixiang.yshop.module.system.enums.sms.SmsSceneEnum;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 短信验证码的发送 Request DTO
 *
 * @author yshop
 */
@Data
public class SmsCodeSendReqDTO {

    /**
     * 手机号
     */
    @Mobile
    @NotEmpty(message = "手机号不能为空")
    private String mobile;
    /**
     * 发送场景
     */
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;
    /**
     * 发送 IP
     */
    @NotEmpty(message = "发送 IP 不能为空")
    private String createIp;

}
