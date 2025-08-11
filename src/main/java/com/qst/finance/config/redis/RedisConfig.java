package com.qst.finance.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 * <p>
 * 作用：
 * 1. 创建并配置 RedisTemplate，替换默认的 JDK 序列化方式
 * 2. 使用 fastjson2 作为 Value 序列化器，提高可读性与性能
 * 3. 保证 key 使用字符串序列化，便于直接查看和调试
 *
 * @Date 2025/08/09 16:36
 * @Author YanShijie
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义 RedisTemplate Bean
     *
     * @param redisConnectionFactory Spring Boot 自动配置的 Redis 连接工厂
     * @return 配置完成的 RedisTemplate
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        // 设置 Redis 连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 创建 fastjson2 序列化器（用于序列化 Value）
        // Object.class 表示支持任意类型对象
        FastJson2RedisSerializer<Object> fastJsonRedisSerializer =
                new FastJson2RedisSerializer<>(Object.class);


        // key 和 hashKey 序列化方式：String
        // 使用 StringRedisSerializer 保证 key 是可读字符串（而不是二进制）
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // value 和 hashValue 序列化方式：fastjson2
        // 使用 fastjson2 进行 JSON 序列化，提高可读性与跨语言兼容性
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);

        template.setDefaultSerializer(fastJsonRedisSerializer);

        return template;
    }

}
