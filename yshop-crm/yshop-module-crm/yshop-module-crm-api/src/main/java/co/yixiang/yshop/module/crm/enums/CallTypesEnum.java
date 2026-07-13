package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 呼叫类型枚举
 */
@Getter
@AllArgsConstructor
public enum CallTypesEnum {
    LOCAL("Local","手机"),
    SIP("sip","sip"),
    GATEWAY("gateway","语音网关");

    private String value;
    private String desc;
}
