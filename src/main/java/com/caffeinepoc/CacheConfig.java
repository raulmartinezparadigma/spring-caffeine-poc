package com.caffeinepoc;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@ComponentScan("com.caffeinepoc")
public class CacheConfig {

    private Logger logger = LoggerFactory.getLogger(CacheConfig.class);


    public static final String SOURCE_INFO_CACHE = "SOURCE_INFO";


    @Bean
    protected CacheManager cacheManager() {
        List<CaffeineCache> caffeineCaches = new ArrayList<CaffeineCache>();
        //Expirará e 1 minuto con un size de 500 elementos
        caffeineCaches.add(buildCaffeineCache(SOURCE_INFO_CACHE,1L,TimeUnit.MINUTES,500L));


        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;

    }

    /**
     * Nos permite construir multiples caches
     *
     * @param cacheName
     * @param ttl
     * @param ttlUnit
     * @param size
     * @return
     */
    private  CaffeineCache buildCaffeineCache(String cacheName,long ttl, TimeUnit ttlUnit,long size) {
        return new CaffeineCache(cacheName, Caffeine.newBuilder()
            .expireAfterWrite(ttl, ttlUnit)
            .maximumSize(size)
            .removalListener((Object key, Object value, RemovalCause cause) ->
                logger.info(String.format("Key %s was removed (%s)%n", key, cause)))
            .recordStats()
            .build());

    }
}
