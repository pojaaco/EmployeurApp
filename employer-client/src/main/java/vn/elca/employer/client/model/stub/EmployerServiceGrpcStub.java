package vn.elca.employer.client.model.stub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import vn.elca.employer.common.*;

public class EmployerServiceGrpcStub {

    private final EmployerServiceGrpc.EmployerServiceBlockingStub blockingStub;

    public EmployerServiceGrpcStub(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = EmployerServiceGrpc.newBlockingStub(channel);
    }

    public EmployerGetResponse getEmployer(EmployerGetRequest request) {
        return blockingStub.getEmployer(request);
    }

    public EmployerSetResponse setEmployer(EmployerSetRequest request) {
        return blockingStub.setEmployer(request);
    }

    public EmployerDelResponse delEmployer(EmployerDelRequest request) {
        return blockingStub.delEmployer(request);
    }
}
