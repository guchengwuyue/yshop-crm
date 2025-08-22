package co.yixiang.yshop.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档地址
 *
 * @author yshop
 */
@Getter
@AllArgsConstructor
public enum DocumentEnum {

    REDIS_INSTALL("https://crm-demo.lvzheng.com", "Redis 安装文档"),
    TENANT("https://crm-demo.lvzheng.com", "SaaS 多租户文档");

    private final String url;
    private final String memo;

}
