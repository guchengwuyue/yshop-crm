//package co.yixiang.yshop.framework.redis.config;
//
//
//import cn.hutool.core.util.StrUtil;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RedissonConfig {
//    @Value("${spring.data.redis.host}")
//    private String host;
//
//    @Value("${spring.data.redis.port}")
//    private String port;
//
//    @Value("${spring.data.redis.password}")
//    private String password;
//
//    @Value("${spring.data.redis.database}")
//    private Integer database;
//
//
//
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setDatabase(database)
//                .setAddress("redis://"+host+":"+port);
//
//                //.setPassword(password);
//        if(StrUtil.isNotEmpty(password)){
//            config.useSingleServer().setPassword(password);
//        }
//
//        return Redisson.create(config);
//    }
//
//
//}
