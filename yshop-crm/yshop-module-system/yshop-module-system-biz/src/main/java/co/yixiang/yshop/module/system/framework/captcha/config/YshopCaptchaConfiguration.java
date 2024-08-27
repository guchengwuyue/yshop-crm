package co.yixiang.yshop.module.system.framework.captcha.config;

import co.yixiang.yshop.module.system.framework.captcha.core.RedisCaptchaServiceImpl;
import com.xingyuv.captcha.properties.AjCaptchaProperties;
import com.xingyuv.captcha.service.CaptchaCacheService;
import com.xingyuv.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 验证码的配置类
 *
 * @author yshop
 */
@Configuration(proxyBeanMethods = false)
public class YshopCaptchaConfiguration {

    @Bean
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config,
                                                   StringRedisTemplate stringRedisTemplate) {
        CaptchaCacheService captchaCacheService = CaptchaServiceFactory.getCache(config.getCacheType().name());
        if (captchaCacheService instanceof RedisCaptchaServiceImpl) {
            ((RedisCaptchaServiceImpl) captchaCacheService).setStringRedisTemplate(stringRedisTemplate);
        }
        return captchaCacheService;
    }

}
