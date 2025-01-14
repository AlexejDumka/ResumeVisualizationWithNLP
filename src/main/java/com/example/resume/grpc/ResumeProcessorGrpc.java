package com.example.resume.grpc;
import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@jakarta.annotation.Generated(
    value = "by gRPC proto compiler (version 1.65.1)",
    comments = "Source: gRPC.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ResumeProcessorGrpc {

  private ResumeProcessorGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ResumeProcessor";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<GRPC.GraphRequest,
      GRPC.ImageResponse> getGenerateGraphMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateGraph",
      requestType = GRPC.GraphRequest.class,
      responseType = GRPC.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GRPC.GraphRequest,
      GRPC.ImageResponse> getGenerateGraphMethod() {
    io.grpc.MethodDescriptor<GRPC.GraphRequest, GRPC.ImageResponse> getGenerateGraphMethod;
    if ((getGenerateGraphMethod = ResumeProcessorGrpc.getGenerateGraphMethod) == null) {
      synchronized (ResumeProcessorGrpc.class) {
        if ((getGenerateGraphMethod = ResumeProcessorGrpc.getGenerateGraphMethod) == null) {
          ResumeProcessorGrpc.getGenerateGraphMethod = getGenerateGraphMethod =
              io.grpc.MethodDescriptor.<GRPC.GraphRequest, GRPC.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateGraph"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.GraphRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ResumeProcessorMethodDescriptorSupplier("GenerateGraph"))
              .build();
        }
      }
    }
    return getGenerateGraphMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GRPC.WordCloudRequest,
      GRPC.ImageResponse> getGenerateWordCloudMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateWordCloud",
      requestType = GRPC.WordCloudRequest.class,
      responseType = GRPC.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GRPC.WordCloudRequest,
      GRPC.ImageResponse> getGenerateWordCloudMethod() {
    io.grpc.MethodDescriptor<GRPC.WordCloudRequest, GRPC.ImageResponse> getGenerateWordCloudMethod;
    if ((getGenerateWordCloudMethod = ResumeProcessorGrpc.getGenerateWordCloudMethod) == null) {
      synchronized (ResumeProcessorGrpc.class) {
        if ((getGenerateWordCloudMethod = ResumeProcessorGrpc.getGenerateWordCloudMethod) == null) {
          ResumeProcessorGrpc.getGenerateWordCloudMethod = getGenerateWordCloudMethod =
              io.grpc.MethodDescriptor.<GRPC.WordCloudRequest, GRPC.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateWordCloud"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.WordCloudRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ResumeProcessorMethodDescriptorSupplier("GenerateWordCloud"))
              .build();
        }
      }
    }
    return getGenerateWordCloudMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ResumeProcessorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ResumeProcessorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ResumeProcessorStub>() {
        @java.lang.Override
        public ResumeProcessorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ResumeProcessorStub(channel, callOptions);
        }
      };
    return ResumeProcessorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ResumeProcessorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ResumeProcessorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ResumeProcessorBlockingStub>() {
        @java.lang.Override
        public ResumeProcessorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ResumeProcessorBlockingStub(channel, callOptions);
        }
      };
    return ResumeProcessorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ResumeProcessorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ResumeProcessorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ResumeProcessorFutureStub>() {
        @java.lang.Override
        public ResumeProcessorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ResumeProcessorFutureStub(channel, callOptions);
        }
      };
    return ResumeProcessorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void generateGraph(GRPC.GraphRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateGraphMethod(), responseObserver);
    }

    /**
     */
    default void generateWordCloud(GRPC.WordCloudRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateWordCloudMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ResumeProcessor.
   */
  public static abstract class ResumeProcessorImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ResumeProcessorGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ResumeProcessor.
   */
  public static final class ResumeProcessorStub
      extends io.grpc.stub.AbstractAsyncStub<ResumeProcessorStub> {
    private ResumeProcessorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResumeProcessorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ResumeProcessorStub(channel, callOptions);
    }

    /**
     */
    public void generateGraph(GRPC.GraphRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateGraphMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void generateWordCloud(GRPC.WordCloudRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateWordCloudMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ResumeProcessor.
   */
  public static final class ResumeProcessorBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ResumeProcessorBlockingStub> {
    private ResumeProcessorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResumeProcessorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ResumeProcessorBlockingStub(channel, callOptions);
    }

    /**
     */
    public GRPC.ImageResponse generateGraph(GRPC.GraphRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateGraphMethod(), getCallOptions(), request);
    }

    /**
     */
    public GRPC.ImageResponse generateWordCloud(GRPC.WordCloudRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateWordCloudMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ResumeProcessor.
   */
  public static final class ResumeProcessorFutureStub
      extends io.grpc.stub.AbstractFutureStub<ResumeProcessorFutureStub> {
    private ResumeProcessorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResumeProcessorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ResumeProcessorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GRPC.ImageResponse> generateGraph(
        GRPC.GraphRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGenerateGraphMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GRPC.ImageResponse> generateWordCloud(
        GRPC.WordCloudRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGenerateWordCloudMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GENERATE_GRAPH = 0;
  private static final int METHODID_GENERATE_WORD_CLOUD = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GENERATE_GRAPH:
          serviceImpl.generateGraph((GRPC.GraphRequest) request,
              (io.grpc.stub.StreamObserver<GRPC.ImageResponse>) responseObserver);
          break;
        case METHODID_GENERATE_WORD_CLOUD:
          serviceImpl.generateWordCloud((GRPC.WordCloudRequest) request,
              (io.grpc.stub.StreamObserver<GRPC.ImageResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGenerateGraphMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              GRPC.GraphRequest,
              GRPC.ImageResponse>(
                service, METHODID_GENERATE_GRAPH)))
        .addMethod(
          getGenerateWordCloudMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              GRPC.WordCloudRequest,
              GRPC.ImageResponse>(
                service, METHODID_GENERATE_WORD_CLOUD)))
        .build();
  }

  private static abstract class ResumeProcessorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ResumeProcessorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return GRPC.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ResumeProcessor");
    }
  }

  private static final class ResumeProcessorFileDescriptorSupplier
      extends ResumeProcessorBaseDescriptorSupplier {
    ResumeProcessorFileDescriptorSupplier() {}
  }

  private static final class ResumeProcessorMethodDescriptorSupplier
      extends ResumeProcessorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ResumeProcessorMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ResumeProcessorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ResumeProcessorFileDescriptorSupplier())
              .addMethod(getGenerateGraphMethod())
              .addMethod(getGenerateWordCloudMethod())
              .build();
        }
      }
    }
    return result;
  }
}
