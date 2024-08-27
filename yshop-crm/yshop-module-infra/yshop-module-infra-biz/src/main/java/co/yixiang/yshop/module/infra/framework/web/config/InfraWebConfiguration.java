package co.yixiang.yshop.module.infra.framework.web.config;

import co.yixiang.yshop.framework.swagger.config.YshopSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * infra 模块的 web 组件的 Configuration
 *
 * @author yshop
 */
@Configuration(proxyBeanMethods = false)
public class InfraWebConfiguration {

    /**
     * infra 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi infraGroupedOpenApi() {
        return YshopSwaggerAutoConfiguration.buildGroupedOpenApi("infra");
    }

}
