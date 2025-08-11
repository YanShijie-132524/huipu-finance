package com.qst.finance.config.redis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 使用 Fastjson2 实现 Redis 序列化/反序列化的工具类
 * <p>
 * 特点：
 * 1. 基于泛型 T，可以序列化/反序列化任意类型对象
 * 2. 使用 Fastjson2 的 WriteClassName / SupportClassForName 保留并解析类型信息
 * 3. 实现 Spring Data Redis 提供的 RedisSerializer 接口，可直接在 RedisTemplate 中使用
 *
 * @param type 目标类型的 Class 对象，用于反序列化时确定对象类型
 * @param <T>  序列化的泛型类型
 */
public record FastJson2RedisSerializer<T>(Class<T> type) implements RedisSerializer<T> {

    /**
     * 序列化方法：Java 对象 -> byte[]
     *
     * @param value 要序列化的 Java 对象
     * @return 序列化后的字节数组，如果对象为 null 则返回空数组
     */
    @Override
    public byte[] serialize(T value) {
        if (value == null) return new byte[0]; // 空对象返回空字节数组，避免 Redis 存储 null
        // WriteClassName 特性：在 JSON 中保留 @type 类型信息，方便反序列化时恢复原始类型
        return JSON.toJSONBytes(value, JSONWriter.Feature.WriteClassName);
    }

    /**
     * 反序列化方法：byte[] -> Java 对象
     *
     * @param bytes 从 Redis 中取出的字节数组
     * @return 反序列化后的 Java 对象，如果字节为空则返回 null
     */
    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null; // 空字节返回 null
        // SupportClassForName 特性：允许解析 @type 中的类名，恢复原始对象类型
        return JSON.parseObject(bytes, type, JSONReader.Feature.SupportClassForName);
    }
}
