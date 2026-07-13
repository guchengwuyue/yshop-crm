package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 消息类型枚举
 */
@Getter
@AllArgsConstructor
public enum MessageTypesEnum {
    EXAMINE("examine","审核"),
    EXAMINE_REFUSE("examine_refuse","审核拒绝"),
    EXAMINE_FINISH("examine_finish","审核完成"),
    RECEIVABLES("receivables","回款"),
    RECORD_CLUES("record_clues","线索跟进"),
    RECORD_CUSTOMER("record_customer","客户跟进"),
    RECORD_BUSINESS("record_business","商机跟进"),
    INVOICE("invoice","发票"),
    CUSTOMER("customer","客户"),
    CONTRACT("contract","合同");

    private String value;
    private String desc;
}
