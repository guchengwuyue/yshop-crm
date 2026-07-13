package co.yixiang.yshop.framework.idempotent.config;

import co.yixiang.yshop.framework.idempotent.core.aop.IdempotentAspect;
import co.yixiang.yshop.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import co.yixiang.yshop.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import co.yixiang.yshop.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import co.yixiang.yshop.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import co.yixiang.yshop.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import co.yixiang.yshop.framework.redis.config.YshopRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = YshopRedisAutoConfiguration.class)
public class YshopIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
