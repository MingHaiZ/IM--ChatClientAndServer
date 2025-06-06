package com.easychat.redis;

import com.easychat.config.AppConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;


@Configuration
public class RedisConfig<V> {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean(name = "redissonClient", destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        try {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
            RedissonClient redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            logger.error("redis配置错误,请检查redis配置", e);
        }
        return null;
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, V> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, V> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
//        设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
//        设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
//        设置hash的key序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
//        设置hash的value序列化方式
        template.setHashValueSerializer(RedisSerializer.json());
        template.afterPropertiesSet();
        return template;

    }
}
