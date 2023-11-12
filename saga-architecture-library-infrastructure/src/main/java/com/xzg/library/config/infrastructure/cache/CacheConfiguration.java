package com.xzg.library.config.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.cache
 * @className: CacheConfiguration
 * @author: xzg
 * @description: 提供一些代码上使用的缓存
 * @date: 11/11/2023-下午 2:49
 * @version: 1.0
 */
@Configuration
public class CacheConfiguration {

    /**
     * 系统默认缓存TTL时间：4分钟
     * 一些需要用到缓存的数据，譬如支付单，需要按此数据来规划过期时间
     */
    public static final long SYSTEM_DEFAULT_EXPIRES = 4 * 60 * 1000;

    @Bean
    public CacheManager configCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(SYSTEM_DEFAULT_EXPIRES, TimeUnit.MILLISECONDS));
        return manager;
    }

}

    