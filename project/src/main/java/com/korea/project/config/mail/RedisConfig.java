package com.korea.project.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;



@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.mail.host}")
    private String host;
    @Value("${spring.data.redis.mail.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisMailConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    @Primary  // 이 빈을 기본 StringRedisTemplate으로 지정
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisMailConnectionFactory());
        return stringRedisTemplate;
    }
}