package com.example.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig {

    @Bean
    fun redisConnectionFactory() : LettuceConnectionFactory{
        return LettuceConnectionFactory(RedisStandaloneConfiguration("localhost"))
    }

    @Bean
    fun redisTemplate(connectionFactory: LettuceConnectionFactory) : RedisTemplate<String, Long>{
        val redisTemplate = RedisTemplate<String, Long>()
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate
    }
}