package org.springframework.samples.petclinic.system;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.samples.petclinic.util.CallMonitoringAspect;

import java.util.Set;

@Configuration
@EnableAspectJAutoProxy
@EnableCaching
public class ToolsConfig {

    @Bean
    public CallMonitoringAspect callMonitor() {
        return new CallMonitoringAspect();
    }

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(Set.of("default", "vets"));
        return cacheManager;
    }
}
