package vn.elca.employer.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.elca.employer.client.model.stub.EmployerServiceGrpcStub;

@Configuration
public class EmployerGrpcConfig {

    @Value("${grpc.connection.host}")
    private String host;

    @Value("${grpc.connection.port}")
    private int port;

    @Bean
    public EmployerServiceGrpcStub employerServiceGrpcStub() {
        return new EmployerServiceGrpcStub(host, port);
    }
}
