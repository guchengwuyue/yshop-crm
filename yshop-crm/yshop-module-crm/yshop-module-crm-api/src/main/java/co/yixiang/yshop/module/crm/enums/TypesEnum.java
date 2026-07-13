package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author hupeng
 * 类型枚举
 */
@Getter
@AllArgsConstructor
public enum TypesEnum {
    CUSTOMER("customer","客户"),
    BUSINESS("business","商机"),
    CLUES("clues","线索"),
    CONTRACT("contract","合同"),
    RECEIVABLES("receivables","回款"),
    INVOICE("invoice","发票");

    private String value;
    private String desc;

    public static TypesEnum toType(String value) {
        return Stream.of(TypesEnum.values())
                .filter(p -> p.value.equals(value))
                .findAny()
                .orElse(null);
    }
}
