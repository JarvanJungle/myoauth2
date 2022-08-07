package com.doxa.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author vuducnoi
 */
@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {
    public static final String ENTITIES_SERVICE_CACHE = "EntitiesService";
    public static final String OAUTH_SERVICE_CACHE = "OauthService";
    public static final String AUTHORITY_CACHE = "AuthorityCache";
    public static final String LOCAL_CACHE = "Local-PurchaseService";

    private final RedisCacheProperties redisCacheProperties;

    @Autowired
    public CacheConfiguration(RedisCacheProperties redisCacheProperties) {
        this.redisCacheProperties = redisCacheProperties;
    }

    @Bean
    RedisStandaloneConfiguration redisConfiguration() {
        return new RedisStandaloneConfiguration(redisCacheProperties.getHost(), redisCacheProperties.getPort());
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(redisConfiguration());
    }

    @Bean
    @Primary
    public RedisCacheConfiguration connexCacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(redisCacheProperties.getTtlInSeconds()))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheConfiguration authorityCacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(1800))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

    }


    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder
                .withCacheConfiguration(ENTITIES_SERVICE_CACHE, connexCacheConfig())
                .withCacheConfiguration(LOCAL_CACHE, connexCacheConfig())
                .withCacheConfiguration(AUTHORITY_CACHE, connexCacheConfig())
                .withCacheConfiguration(OAUTH_SERVICE_CACHE, connexCacheConfig());

    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new ConnexCacheErrorHandler();
    }

    @Bean
    KeyGenerator connexCacheKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder paramsSb = new StringBuilder();
            for (Object p : params) {
                if (p == null) {
                    p = "";
                }
                paramsSb.append(p.toString());
            }
            return method.getName() + paramsSb.toString();
        };
    }

    @Bean
    KeyGenerator authorityCacheKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder paramsSb = new StringBuilder();
            for (Object p : params) {
                if (p == null) {
                    p = "";
                }
                paramsSb.append(p);
            }
            return "authority" + paramsSb;
        };
    }
}
