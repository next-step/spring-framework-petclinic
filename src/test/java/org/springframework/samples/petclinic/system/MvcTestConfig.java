package org.springframework.samples.petclinic.system;

import org.mockito.Mockito;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.service.ClinicService;

@Configuration
public class MvcTestConfig {

    @Bean
    public ClinicService clinicService() throws ClassNotFoundException {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(Mockito.mock(ClinicService.class));
        proxyFactoryBean.setProxyInterfaces(new Class[]{ClinicService.class});
        return (ClinicService) proxyFactoryBean.getObject();
    }
}
