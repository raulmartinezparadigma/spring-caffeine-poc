package com.caffeinepoc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
@ComponentScan("com.caffeinepoc")
public class CacheConfig {

    public static final String SOURCE_INFO_CACHE = "SOURCE_INFO";
    public static final String PAYMENTMETHOD_INFO_CACHE = "PAYMENTMETHOD_INFO";


    @Bean
    protected CacheManager cacheManager() {
        List<CaffeineCache> caffeineCaches = new ArrayList<CaffeineCache>();
        //Expirará e 1 minuto con un size de 500 elementos
        caffeineCaches.add(buildCaffeineCache(SOURCE_INFO_CACHE,1L,TimeUnit.MINUTES,500L));
        //caffeineCaches.add(buildCaffeineCache(PAYMENTMETHOD_INFO_CACHE,1L,TimeUnit.MINUTES,500L));


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
    private static CaffeineCache buildCaffeineCache(String cacheName,long ttl, TimeUnit ttlUnit,long size) {
        return new CaffeineCache(cacheName,Caffeine.newBuilder().expireAfterWrite(ttl, ttlUnit).maximumSize(size).build() );
    }
}
