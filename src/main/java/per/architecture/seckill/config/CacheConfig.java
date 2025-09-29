package per.architecture.seckill.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Value("${seckill.local-cache.maximum-size:10000}")
    private long maximumSize;

    @Value("${seckill.local-cache.expire-seconds:60}")
    private long expireSeconds;

    @Bean
    public Cache<String, Boolean> localStockCache() {
        return Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build();
    }
}
