package vn.elca.employer.client.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import vn.elca.employer.client.language.ObservableResourceFactory;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"vn.elca.employer.client"})
@EnableAutoConfiguration
public class EmployerApplicationConfig {

    @Bean
    public ObservableResourceFactory observableResourceFactory() {
        ObservableResourceFactory observableResourceFactory = new ObservableResourceFactory();
        observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.FR);
        return observableResourceFactory;
    }
}
