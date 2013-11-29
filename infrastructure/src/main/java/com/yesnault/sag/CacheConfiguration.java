package com.yesnault.sag;

import com.codahale.metrics.ehcache.InstrumentedEhcache;
import com.yesnault.sag.metrics.MetricsConfiguration;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfiguration.class);

    private net.sf.ehcache.CacheManager cacheManager;

    @PreDestroy
    public void destroy() {
        LOGGER.info("Closing Ehcache");
        cacheManager.shutdown();
    }

    @Bean
    public CacheManager cacheManager() {
        LOGGER.debug("Starting Ehcache");
        cacheManager = net.sf.ehcache.CacheManager.create();

        /* TODO
        LOGGER.debug("Registring Ehcache Metrics gauges");
        Cache userCache = cacheManager.getCache("com.mycompany.myapp.domain.User");
        Ehcache decoratedStatusCache = InstrumentedEhcache.instrument(MetricsConfiguration.METRIC_REGISTRY, userCache);
        cacheManager.replaceCacheWithDecoratedCache(userCache, decoratedStatusCache);

        Cache authoritiesCache = cacheManager.getCache("com.mycompany.myapp.domain.Authority");
        Ehcache decoratedAuthoritiesCache = InstrumentedEhcache.instrument(MetricsConfiguration.METRIC_REGISTRY, authoritiesCache);
        cacheManager.replaceCacheWithDecoratedCache(authoritiesCache, decoratedAuthoritiesCache);

        Cache persistentTokenCache = cacheManager.getCache("com.mycompany.myapp.domain.PersistentToken");
        Ehcache decoratedPersistentTokenCache = InstrumentedEhcache.instrument(MetricsConfiguration.METRIC_REGISTRY, persistentTokenCache);
        cacheManager.replaceCacheWithDecoratedCache(persistentTokenCache, decoratedPersistentTokenCache);
        */

        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }
}
