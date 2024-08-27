package co.yixiang.yshop.module.shop.framework.web.config;

import co.yixiang.yshop.framework.swagger.config.YshopSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shop 模块的 web 组件的 Configuration
 *
 * @author yshop
 */
@Configuration(proxyBeanMethods = false)
public class ShopWebConfiguration {

    /**
     * promotion 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi shopGroupedOpenApi() {
        return YshopSwaggerAutoConfiguration.buildGroupedOpenApi("shop");
    }

}
