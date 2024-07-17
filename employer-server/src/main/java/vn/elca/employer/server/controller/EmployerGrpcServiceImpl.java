package vn.elca.employer.server.controller;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.server.adapter.EmployerServiceAdapter;
import vn.elca.employer.common.*;

@GrpcService
public class EmployerGrpcServiceImpl extends EmployerServiceGrpc.EmployerServiceImplBase {
    @Autowired
    private EmployerServiceAdapter employerServiceAdapter;

    @Override
    public void getEmployer(EmployerGetRequest request, StreamObserver<EmployerGetResponse> responseObserver) {
        responseObserver.onNext(employerServiceAdapter.getByRequest(request));
        responseObserver.onCompleted();
    }

    @Override
    public void setEmployer(EmployerSetRequest request, StreamObserver<EmployerSetResponse> responseObserver) {
        responseObserver.onNext(employerServiceAdapter.setByRequest(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEmployer(EmployerDeleteRequest request, StreamObserver<EmployerDeleteResponse> responseObserver) {
        responseObserver.onNext(employerServiceAdapter.deleteByRequest(request));
        responseObserver.onCompleted();
    }
}
