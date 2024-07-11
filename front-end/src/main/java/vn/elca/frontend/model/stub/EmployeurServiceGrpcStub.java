package vn.elca.frontend.model.stub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import vn.elca.common.EmployeurSearchRequest;
import vn.elca.common.EmployeurSearchResponse;
import vn.elca.common.EmployeurServiceGrpc;

public class EmployeurServiceGrpcStub {

    private final EmployeurServiceGrpc.EmployeurServiceBlockingStub blockingStub;

    public EmployeurServiceGrpcStub() {
        // TODO: Find a way to use properties in application.properties
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = EmployeurServiceGrpc.newBlockingStub(channel);
    }

    public EmployeurSearchResponse search(EmployeurSearchRequest request) {
        return blockingStub.search(request);
    }
}
