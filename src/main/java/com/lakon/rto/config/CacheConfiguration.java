package com.lakon.rto.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.lakon.rto.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.lakon.rto.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Well.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Well.class.getName() + ".wellbores", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Wellbore.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Wellbore.class.getName() + ".rigs", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Rig.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Rig.class.getName() + ".jobs", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Job.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.Job.class.getName() + ".rigs", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.WitsService.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.RecordType.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.RecordType.class.getName() + ".recordItems", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.RecordItem.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.BaseUnit.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.BaseUnit.class.getName() + ".unitTypes", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.BaseUnit.class.getName() + ".derivedUnits", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.DerivedUnit.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.UnitType.class.getName(), jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.UnitType.class.getName() + ".unitTypeItems", jcacheConfiguration);
            cm.createCache(com.lakon.rto.domain.UnitTypeItem.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
