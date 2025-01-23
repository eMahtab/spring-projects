package net.mahtabalam.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "net.mahtabalam.repository")
@EntityScan(basePackages = "net.mahtabalam.model")
@ComponentScan(basePackages = "net.mahtabalam.service")
public class AppConfig {

}
