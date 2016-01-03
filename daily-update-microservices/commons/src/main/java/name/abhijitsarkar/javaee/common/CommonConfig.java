package name.abhijitsarkar.javaee.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import java.util.Collection;

import static java.util.Collections.singleton;
import static javax.cache.expiry.Duration.ONE_DAY;

/**
 * @author Abhijit Sarkar
 */
@Configuration
@EnableCaching
@ImportAutoConfiguration(CacheAutoConfiguration.class)
public class CommonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.newInstance();
    }

    @Bean
    CacheManager cacheManager() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        javax.cache.CacheManager jCacheManager = cachingProvider.getCacheManager();

        MutableConfiguration<String, Object> config = new MutableConfiguration<>();
        config.setStoreByValue(true);
        config.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(
                ONE_DAY));
        jCacheManager.createCache("global-cache", config);

        return new JCacheCacheManager(jCacheManager);
    }

    @Bean
    CacheResolver cacheResolver() {
        return new CacheResolver() {
            @Override
            public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
                return singleton(cacheManager().getCache("global-cache"));
            }
        };
    }
}
