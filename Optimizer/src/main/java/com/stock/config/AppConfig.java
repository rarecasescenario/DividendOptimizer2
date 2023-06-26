package com.stock.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@ComponentScan("com.stock")
@ComponentScan
@EnableJpaRepositories(basePackages = "com.stock.repositories")
@EntityScan(basePackages = "com.stock.model")
public class AppConfig {

//    @Bean(name = "entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//	LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//	return sessionFactory;
//    }

}
