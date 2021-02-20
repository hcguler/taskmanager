package com.hcg.simpletaskmanager.configuration;

import com.hcg.simpletaskmanager.configuration.constants.ApplicationConfigurationConstant;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Application configuration class
 * Spring context bean scannign directory give in @ComponentScan
 * Entity mapping for data base scanning directory give @EntityScan
 * Scannign @Repository bean for JPA using @EnableJpaRepositories
 */
@Configuration
@ComponentScan(basePackages = {ApplicationConfigurationConstant.BASE_PACKAGES})
@EntityScan(basePackages = {ApplicationConfigurationConstant.BASE_PACKAGES})
@EnableJpaRepositories(basePackages = {ApplicationConfigurationConstant.BASE_PACKAGES})
@EnableWebMvc
public class SimpleTaskManagerConfiguration {


}
