package org.springframework.samples.petclinic.system;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:spring/data-access.properties")
@EnableTransactionManagement
@ComponentScan("org.springframework.samples.petclinic.service")
@Import(DataSourceConfig.class)
public class BusinessConfig {

    private final DataSource dataSource;

    @Autowired
    public BusinessConfig(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("org.springframework.samples.petclinic");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Configuration
    @Profile("jdbc")
    @ComponentScan(basePackages = "org.springframework.samples.petclinic.repository.jdbc")
    static class JdbcConfig {

        private final DataSource dataSource;

        @Autowired
        public JdbcConfig(final DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public JdbcClient jdbcClient() {
            return JdbcClient.create(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Configuration
    @Profile("jpa")
    @ComponentScan(basePackages = "org.springframework.samples.petclinic.repository.jpa")
    static class JpaConfig {
    }

    @Configuration
    @Profile("spring-data-jpa")
    @EnableJpaRepositories(basePackages = "org.springframework.samples.petclinic.repository.springdatajpa")
    static class SpringDataJpaConfig {
    }
}
