package com.example.resume.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@jakarta.annotation.Generated(
    value = "by gRPC proto compiler (version 1.65.1)",
    comments = "Source: gRPC.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TextProcessorGrpc {

  private TextProcessorGrpc() {}

  public static final java.lang.String SERVICE_NAME = "TextProcessor";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<GRPC.TextRequest,
      GRPC.ImageResponse> getGenerateGraphMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateGraph",
      requestType = GRPC.TextRequest.class,
      responseType = GRPC.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GRPC.TextRequest,
      GRPC.ImageResponse> getGenerateGraphMethod() {
    io.grpc.MethodDescriptor<GRPC.TextRequest, GRPC.ImageResponse> getGenerateGraphMethod;
    if ((getGenerateGraphMethod = TextProcessorGrpc.getGenerateGraphMethod) == null) {
      synchronized (TextProcessorGrpc.class) {
        if ((getGenerateGraphMethod = TextProcessorGrpc.getGenerateGraphMethod) == null) {
          TextProcessorGrpc.getGenerateGraphMethod = getGenerateGraphMethod =
              io.grpc.MethodDescriptor.<GRPC.TextRequest, GRPC.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateGraph"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.TextRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TextProcessorMethodDescriptorSupplier("GenerateGraph"))
              .build();
        }
      }
    }
    return getGenerateGraphMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GRPC.TextRequest,
      GRPC.ImageResponse> getGenerateWordCloudMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateWordCloud",
      requestType = GRPC.TextRequest.class,
      responseType = GRPC.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GRPC.TextRequest,
      GRPC.ImageResponse> getGenerateWordCloudMethod() {
    io.grpc.MethodDescriptor<GRPC.TextRequest, GRPC.ImageResponse> getGenerateWordCloudMethod;
    if ((getGenerateWordCloudMethod = TextProcessorGrpc.getGenerateWordCloudMethod) == null) {
      synchronized (TextProcessorGrpc.class) {
        if ((getGenerateWordCloudMethod = TextProcessorGrpc.getGenerateWordCloudMethod) == null) {
          TextProcessorGrpc.getGenerateWordCloudMethod = getGenerateWordCloudMethod =
              io.grpc.MethodDescriptor.<GRPC.TextRequest, GRPC.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateWordCloud"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.TextRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GRPC.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TextProcessorMethodDescriptorSupplier("GenerateWordCloud"))
              .build();
        }
      }
    }
    return getGenerateWordCloudMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TextProcessorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TextProcessorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TextProcessorStub>() {
        @java.lang.Override
        public TextProcessorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TextProcessorStub(channel, callOptions);
        }
      };
    return TextProcessorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TextProcessorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TextProcessorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TextProcessorBlockingStub>() {
        @java.lang.Override
        public TextProcessorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TextProcessorBlockingStub(channel, callOptions);
        }
      };
    return TextProcessorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TextProcessorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TextProcessorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TextProcessorFutureStub>() {
        @java.lang.Override
        public TextProcessorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TextProcessorFutureStub(channel, callOptions);
        }
      };
    return TextProcessorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void generateGraph(GRPC.TextRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateGraphMethod(), responseObserver);
    }

    /**
     */
    default void generateWordCloud(GRPC.TextRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateWordCloudMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service TextProcessor.
   */
  public static abstract class TextProcessorImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return TextProcessorGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service TextProcessor.
   */
  public static final class TextProcessorStub
      extends io.grpc.stub.AbstractAsyncStub<TextProcessorStub> {
    private TextProcessorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TextProcessorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TextProcessorStub(channel, callOptions);
    }

    /**
     */
    public void generateGraph(GRPC.TextRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateGraphMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void generateWordCloud(GRPC.TextRequest request,
        io.grpc.stub.StreamObserver<GRPC.ImageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateWordCloudMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service TextProcessor.
   */
  public static final class TextProcessorBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TextProcessorBlockingStub> {
    private TextProcessorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TextProcessorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TextProcessorBlockingStub(channel, callOptions);
    }

    /**
     */
    public GRPC.ImageResponse generateGraph(GRPC.TextRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateGraphMethod(), getCallOptions(), request);
    }

    /**
     */
    public GRPC.ImageResponse generateWordCloud(GRPC.TextRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateWordCloudMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service TextProcessor.
   */
  public static final class TextProcessorFutureStub
      extends io.grpc.stub.AbstractFutureStub<TextProcessorFutureStub> {
    private TextProcessorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TextProcessorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TextProcessorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GRPC.ImageResponse> generateGraph(
        GRPC.TextRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGenerateGraphMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GRPC.ImageResponse> generateWordCloud(
        GRPC.TextRequest request) {
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
          serviceImpl.generateGraph((GRPC.TextRequest) request,
              (io.grpc.stub.StreamObserver<GRPC.ImageResponse>) responseObserver);
          break;
        case METHODID_GENERATE_WORD_CLOUD:
          serviceImpl.generateWordCloud((GRPC.TextRequest) request,
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
              GRPC.TextRequest,
              GRPC.ImageResponse>(
                service, METHODID_GENERATE_GRAPH)))
        .addMethod(
          getGenerateWordCloudMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              GRPC.TextRequest,
              GRPC.ImageResponse>(
                service, METHODID_GENERATE_WORD_CLOUD)))
        .build();
  }

  private static abstract class TextProcessorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TextProcessorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return GRPC.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TextProcessor");
    }
  }

  private static final class TextProcessorFileDescriptorSupplier
      extends TextProcessorBaseDescriptorSupplier {
    TextProcessorFileDescriptorSupplier() {}
  }

  private static final class TextProcessorMethodDescriptorSupplier
      extends TextProcessorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    TextProcessorMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (TextProcessorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TextProcessorFileDescriptorSupplier())
              .addMethod(getGenerateGraphMethod())
              .addMethod(getGenerateWordCloudMethod())
              .build();
        }
      }
    }
    return result;
  }
}
