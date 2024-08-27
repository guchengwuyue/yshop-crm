package co.yixiang.yshop.module.product.framework.web.config;

import co.yixiang.yshop.framework.swagger.config.YshopSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * product 模块的 web 组件的 Configuration
 *
 * @author yshop
 */
@Configuration(proxyBeanMethods = false)
public class ProductWebConfiguration {

    /**
     * product 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi productGroupedOpenApi() {
        return YshopSwaggerAutoConfiguration.buildGroupedOpenApi("product");
    }

}
