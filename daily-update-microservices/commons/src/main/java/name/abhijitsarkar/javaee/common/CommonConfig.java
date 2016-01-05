package name.abhijitsarkar.javaee.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.javaee.common.domain.HazelcastMancenterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Abhijit Sarkar
 */
@Configuration
@EnableCaching
//@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = "180")
//@ImportAutoConfiguration({ CacheAutoConfiguration.class, HazelcastHttpSessionConfiguration.class })
@EnableAutoConfiguration
@Slf4j
public class CommonConfig {
    @Autowired
    private HazelcastMancenterConfig mancenterConfig;

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.newInstance();
    }

    /* Good config example: http://docs.hazelcast.org/docs/3.5/manual/html-single/index.html#jcache-programmatic-configuration */
    @Bean
    CacheManager cacheManager() {
        /* We're a JCache server as well as a client that may connect to Hazelcast Mancenter. */
        System.setProperty("hazelcast.jcache.provider.type", "server");

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

    /* Good config example: http://docs.hazelcast.org/docs/3.5/manual/html-single/index.html#configuration-overview */
    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setInstanceName("daily-update-microsevices");

        ManagementCenterConfig mancenterConfig = new ManagementCenterConfig();

        if (this.mancenterConfig.isMancenterEnabled()) {
            String defaultMancenterUrl = "http://localhost:12000/mancenter";

            if (isEmpty(this.mancenterConfig.getMancenterURL())) {
                log.warn("System property 'HAZELCAST_MANCENTER_URL' not found. " +
                        "Defaulting to: {}.", defaultMancenterUrl);

                mancenterConfig.setUrl(defaultMancenterUrl);
            } else {
                mancenterConfig.setUrl(this.mancenterConfig.getMancenterURL());
            }

            mancenterConfig.setEnabled(this.mancenterConfig.isMancenterEnabled());

            config.setManagementCenterConfig(mancenterConfig);
        }

        return config;
    }

//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }
}
