package com.hcg.simpletaskmanager.configuration;


import com.hcg.simpletaskmanager.configuration.constants.ApplicationConfigurationConstant;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

@EnableAutoConfiguration
@EntityScan(basePackages = {ApplicationConfigurationConstant.BASE_PACKAGES})
@ComponentScan(basePackages = {ApplicationConfigurationConstant.BASE_PACKAGES})
@TestPropertySource(locations = ApplicationConfigurationConstant.TEST_PROPERTIES)
@EnableJpaRepositories
public class SimpleTaskManagerTestConfiguration {
}
