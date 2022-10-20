package ro.bithat.dms.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.bithat.dms.microservices.portal.ecitizen.cache.backend.CacheService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableCaching
public class CacheConfig {
 
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(
        		Stream.of(CacheService.cachesNames).map(s -> 
        			new ConcurrentMapCache(s)
		).collect(Collectors.toList()));
        return cacheManager;
    }
}