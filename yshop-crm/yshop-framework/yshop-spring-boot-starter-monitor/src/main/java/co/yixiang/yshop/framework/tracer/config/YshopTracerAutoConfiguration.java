package co.yixiang.yshop.framework.tracer.config;

import co.yixiang.yshop.framework.common.enums.WebFilterOrderEnum;
import co.yixiang.yshop.framework.tracer.core.aop.BizTraceAspect;
import co.yixiang.yshop.framework.tracer.core.filter.TraceFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Tracer 配置类
 *
 * @author mashu
 */
@AutoConfiguration
@ConditionalOnClass({BizTraceAspect.class})
@EnableConfigurationProperties(TracerProperties.class)
@ConditionalOnProperty(prefix = "yshop.tracer", value = "enable", matchIfMissing = true)
public class YshopTracerAutoConfiguration {

    // TODO @芋艿：重要。目前 opentracing 版本存在冲突，要么保证 skywalking，要么保证阿里云短信 sdk
//    @Bean
//    public TracerProperties bizTracerProperties() {
//        return new TracerProperties();
//    }
//
//    @Bean
//    public BizTraceAspect bizTracingAop() {
//        return new BizTraceAspect(tracer());
//    }
//
//    @Bean
//    public Tracer tracer() {
//        // 创建 SkywalkingTracer 对象
//        SkywalkingTracer tracer = new SkywalkingTracer();
//        // 设置为 GlobalTracer 的追踪器
//        GlobalTracer.register(tracer);
//        return tracer;
//    }

    /**
     * 创建 TraceFilter 过滤器，响应 header 设置 traceId
     */
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TRACE_FILTER);
        return registrationBean;
    }

}
