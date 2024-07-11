package vn.elca.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.elca.frontend.model.stub.EmployeurServiceGrpcStub;

@Configuration
public class GrpcConfig {

    @Bean
    public EmployeurServiceGrpcStub employeurServiceGrpcStub() {
        return new EmployeurServiceGrpcStub();
    }
}
