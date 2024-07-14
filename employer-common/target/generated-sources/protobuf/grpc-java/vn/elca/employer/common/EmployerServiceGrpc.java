package vn.elca.employer.common;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: EmployerService.proto")
public final class EmployerServiceGrpc {

  private EmployerServiceGrpc() {}

  public static final String SERVICE_NAME = "EmployerService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<vn.elca.employer.common.EmployerGetRequest,
      vn.elca.employer.common.EmployerGetResponse> METHOD_GET_EMPLOYER =
      io.grpc.MethodDescriptor.<vn.elca.employer.common.EmployerGetRequest, vn.elca.employer.common.EmployerGetResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "EmployerService", "getEmployer"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.employer.common.EmployerGetRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.employer.common.EmployerGetResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<vn.elca.employer.common.EmployerSetRequest,
      vn.elca.employer.common.EmployerSetResponse> METHOD_SET_EMPLOYER =
      io.grpc.MethodDescriptor.<vn.elca.employer.common.EmployerSetRequest, vn.elca.employer.common.EmployerSetResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "EmployerService", "setEmployer"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.employer.common.EmployerSetRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.employer.common.EmployerSetResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<vn.elca.employer.common.EmployerDeleteRequest,
      vn.elca.employer.common.EmployerDeleteResponse> METHOD_DELETE_EMPLOYER =
      io.grpc.MethodDescriptor.<vn.elca.employer.common.EmployerDeleteRequest, vn.elca.employer.common.EmployerDeleteResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "EmployerService", "deleteEmployer"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.employer.common.EmployerDeleteRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.employer.common.EmployerDeleteResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EmployerServiceStub newStub(io.grpc.Channel channel) {
    return new EmployerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EmployerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EmployerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EmployerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EmployerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class EmployerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getEmployer(vn.elca.employer.common.EmployerGetRequest request,
        io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerGetResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_EMPLOYER, responseObserver);
    }

    /**
     */
    public void setEmployer(vn.elca.employer.common.EmployerSetRequest request,
        io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerSetResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SET_EMPLOYER, responseObserver);
    }

    /**
     */
    public void deleteEmployer(vn.elca.employer.common.EmployerDeleteRequest request,
        io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerDeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DELETE_EMPLOYER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_EMPLOYER,
            asyncUnaryCall(
              new MethodHandlers<
                vn.elca.employer.common.EmployerGetRequest,
                vn.elca.employer.common.EmployerGetResponse>(
                  this, METHODID_GET_EMPLOYER)))
          .addMethod(
            METHOD_SET_EMPLOYER,
            asyncUnaryCall(
              new MethodHandlers<
                vn.elca.employer.common.EmployerSetRequest,
                vn.elca.employer.common.EmployerSetResponse>(
                  this, METHODID_SET_EMPLOYER)))
          .addMethod(
            METHOD_DELETE_EMPLOYER,
            asyncUnaryCall(
              new MethodHandlers<
                vn.elca.employer.common.EmployerDeleteRequest,
                vn.elca.employer.common.EmployerDeleteResponse>(
                  this, METHODID_DELETE_EMPLOYER)))
          .build();
    }
  }

  /**
   */
  public static final class EmployerServiceStub extends io.grpc.stub.AbstractStub<EmployerServiceStub> {
    private EmployerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployerServiceStub(channel, callOptions);
    }

    /**
     */
    public void getEmployer(vn.elca.employer.common.EmployerGetRequest request,
        io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerGetResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_EMPLOYER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setEmployer(vn.elca.employer.common.EmployerSetRequest request,
        io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerSetResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SET_EMPLOYER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteEmployer(vn.elca.employer.common.EmployerDeleteRequest request,
        io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerDeleteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETE_EMPLOYER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EmployerServiceBlockingStub extends io.grpc.stub.AbstractStub<EmployerServiceBlockingStub> {
    private EmployerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public vn.elca.employer.common.EmployerGetResponse getEmployer(vn.elca.employer.common.EmployerGetRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_EMPLOYER, getCallOptions(), request);
    }

    /**
     */
    public vn.elca.employer.common.EmployerSetResponse setEmployer(vn.elca.employer.common.EmployerSetRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SET_EMPLOYER, getCallOptions(), request);
    }

    /**
     */
    public vn.elca.employer.common.EmployerDeleteResponse deleteEmployer(vn.elca.employer.common.EmployerDeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETE_EMPLOYER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EmployerServiceFutureStub extends io.grpc.stub.AbstractStub<EmployerServiceFutureStub> {
    private EmployerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<vn.elca.employer.common.EmployerGetResponse> getEmployer(
        vn.elca.employer.common.EmployerGetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_EMPLOYER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<vn.elca.employer.common.EmployerSetResponse> setEmployer(
        vn.elca.employer.common.EmployerSetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SET_EMPLOYER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<vn.elca.employer.common.EmployerDeleteResponse> deleteEmployer(
        vn.elca.employer.common.EmployerDeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETE_EMPLOYER, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_EMPLOYER = 0;
  private static final int METHODID_SET_EMPLOYER = 1;
  private static final int METHODID_DELETE_EMPLOYER = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EmployerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EmployerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_EMPLOYER:
          serviceImpl.getEmployer((vn.elca.employer.common.EmployerGetRequest) request,
              (io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerGetResponse>) responseObserver);
          break;
        case METHODID_SET_EMPLOYER:
          serviceImpl.setEmployer((vn.elca.employer.common.EmployerSetRequest) request,
              (io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerSetResponse>) responseObserver);
          break;
        case METHODID_DELETE_EMPLOYER:
          serviceImpl.deleteEmployer((vn.elca.employer.common.EmployerDeleteRequest) request,
              (io.grpc.stub.StreamObserver<vn.elca.employer.common.EmployerDeleteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class EmployerServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return vn.elca.employer.common.EmployerServiceOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EmployerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EmployerServiceDescriptorSupplier())
              .addMethod(METHOD_GET_EMPLOYER)
              .addMethod(METHOD_SET_EMPLOYER)
              .addMethod(METHOD_DELETE_EMPLOYER)
              .build();
        }
      }
    }
    return result;
  }
}
