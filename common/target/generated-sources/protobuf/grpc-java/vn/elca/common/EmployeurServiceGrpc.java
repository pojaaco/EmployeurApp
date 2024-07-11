package vn.elca.common;

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
    comments = "Source: EmployeurService.proto")
public final class EmployeurServiceGrpc {

  private EmployeurServiceGrpc() {}

  public static final String SERVICE_NAME = "EmployeurService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<vn.elca.common.EmployeurSearchRequest,
      vn.elca.common.EmployeurSearchResponse> METHOD_SEARCH =
      io.grpc.MethodDescriptor.<vn.elca.common.EmployeurSearchRequest, vn.elca.common.EmployeurSearchResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "EmployeurService", "search"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.common.EmployeurSearchRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              vn.elca.common.EmployeurSearchResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EmployeurServiceStub newStub(io.grpc.Channel channel) {
    return new EmployeurServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EmployeurServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EmployeurServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EmployeurServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EmployeurServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class EmployeurServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void search(vn.elca.common.EmployeurSearchRequest request,
        io.grpc.stub.StreamObserver<vn.elca.common.EmployeurSearchResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SEARCH,
            asyncUnaryCall(
              new MethodHandlers<
                vn.elca.common.EmployeurSearchRequest,
                vn.elca.common.EmployeurSearchResponse>(
                  this, METHODID_SEARCH)))
          .build();
    }
  }

  /**
   */
  public static final class EmployeurServiceStub extends io.grpc.stub.AbstractStub<EmployeurServiceStub> {
    private EmployeurServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployeurServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployeurServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployeurServiceStub(channel, callOptions);
    }

    /**
     */
    public void search(vn.elca.common.EmployeurSearchRequest request,
        io.grpc.stub.StreamObserver<vn.elca.common.EmployeurSearchResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EmployeurServiceBlockingStub extends io.grpc.stub.AbstractStub<EmployeurServiceBlockingStub> {
    private EmployeurServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployeurServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployeurServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployeurServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public vn.elca.common.EmployeurSearchResponse search(vn.elca.common.EmployeurSearchRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EmployeurServiceFutureStub extends io.grpc.stub.AbstractStub<EmployeurServiceFutureStub> {
    private EmployeurServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EmployeurServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployeurServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EmployeurServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<vn.elca.common.EmployeurSearchResponse> search(
        vn.elca.common.EmployeurSearchRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH, getCallOptions()), request);
    }
  }

  private static final int METHODID_SEARCH = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EmployeurServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EmployeurServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEARCH:
          serviceImpl.search((vn.elca.common.EmployeurSearchRequest) request,
              (io.grpc.stub.StreamObserver<vn.elca.common.EmployeurSearchResponse>) responseObserver);
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

  private static final class EmployeurServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return vn.elca.common.EmployeurServiceOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EmployeurServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EmployeurServiceDescriptorSupplier())
              .addMethod(METHOD_SEARCH)
              .build();
        }
      }
    }
    return result;
  }
}
