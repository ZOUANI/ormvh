package com.ormvah.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.ormvah.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.ormvah.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.ormvah.domain.User.class.getName());
            createCache(cm, com.ormvah.domain.Authority.class.getName());
            createCache(cm, com.ormvah.domain.User.class.getName() + ".authorities");
            createCache(cm, com.ormvah.domain.PersistentToken.class.getName());
            createCache(cm, com.ormvah.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.ormvah.domain.Courrier.class.getName());
            createCache(cm, com.ormvah.domain.Courrier.class.getName() + ".tasks");
            createCache(cm, com.ormvah.domain.Courrier.class.getName() + ".services");
            createCache(cm, com.ormvah.domain.NatureCourrier.class.getName());
            createCache(cm, com.ormvah.domain.Voie.class.getName());
            createCache(cm, com.ormvah.domain.Expeditor.class.getName());
            createCache(cm, com.ormvah.domain.ExpeditorType.class.getName());
            createCache(cm, com.ormvah.domain.Subdivision.class.getName());
            createCache(cm, com.ormvah.domain.LeService.class.getName());
            createCache(cm, com.ormvah.domain.LeService.class.getName() + ".courriers");
            createCache(cm, com.ormvah.domain.Employee.class.getName());
            createCache(cm, com.ormvah.domain.Task.class.getName());
            createCache(cm, com.ormvah.domain.Evaluation.class.getName());
            createCache(cm, com.ormvah.domain.CourrierObject.class.getName());
            createCache(cm, com.ormvah.domain.ModelLettreReponse.class.getName());
            createCache(cm, com.ormvah.domain.CategorieModelLettreReponse.class.getName());
            createCache(cm, com.ormvah.domain.Bordereau.class.getName());
            createCache(cm, com.ormvah.domain.Bordereau.class.getName() + ".courriers");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
