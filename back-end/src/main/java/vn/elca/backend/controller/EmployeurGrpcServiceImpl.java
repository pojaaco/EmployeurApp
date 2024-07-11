package vn.elca.backend.controller;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.backend.adapter.EmployeurAdapter;
import vn.elca.common.*;

@GrpcService
public class EmployeurGrpcServiceImpl extends EmployeurServiceGrpc.EmployeurServiceImplBase {
    @Autowired
    private EmployeurAdapter employeurAdapter;

    @Override
    public void search(EmployeurSearchRequest request, StreamObserver<EmployeurSearchResponse> responseObserver) {
        responseObserver.onNext(employeurAdapter.searchByCriteria(request));
        responseObserver.onCompleted();
    }
}
