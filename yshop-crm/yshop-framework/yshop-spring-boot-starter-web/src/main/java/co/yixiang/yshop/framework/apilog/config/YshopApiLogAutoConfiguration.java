package co.yixiang.yshop.framework.apilog.config;

import co.yixiang.yshop.framework.apilog.core.filter.ApiAccessLogFilter;
import co.yixiang.yshop.framework.apilog.core.interceptor.ApiAccessLogInterceptor;
import co.yixiang.yshop.framework.apilog.core.service.ApiAccessLogFrameworkService;
import co.yixiang.yshop.framework.apilog.core.service.ApiAccessLogFrameworkServiceImpl;
import co.yixiang.yshop.framework.apilog.core.service.ApiErrorLogFrameworkService;
import co.yixiang.yshop.framework.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
import co.yixiang.yshop.framework.common.enums.WebFilterOrderEnum;
import co.yixiang.yshop.framework.web.config.WebProperties;
import co.yixiang.yshop.framework.web.config.YshopWebAutoConfiguration;
import co.yixiang.yshop.module.infra.api.logger.ApiAccessLogApi;
import co.yixiang.yshop.module.infra.api.logger.ApiErrorLogApi;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration(after = YshopWebAutoConfiguration.class)
public class YshopApiLogAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ApiAccessLogFrameworkService apiAccessLogFrameworkService(ApiAccessLogApi apiAccessLogApi) {
        return new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi);
    }

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
        return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
    }

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "yshop.access-log", value = "enable", matchIfMissing = true) // 允许使用 yshop.access-log.enable=false 禁用访问日志
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiAccessLogInterceptor());
    }

}
