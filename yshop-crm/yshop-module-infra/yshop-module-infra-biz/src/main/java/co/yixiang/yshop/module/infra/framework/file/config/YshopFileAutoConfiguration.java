package co.yixiang.yshop.module.infra.framework.file.config;

import co.yixiang.yshop.module.infra.framework.file.core.client.FileClientFactory;
import co.yixiang.yshop.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author yshop
 */
@Configuration(proxyBeanMethods = false)
public class YshopFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
