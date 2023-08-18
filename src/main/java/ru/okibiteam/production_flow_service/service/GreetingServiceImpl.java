package ru.okibiteam.production_flow_service.service;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.okibiteam.production_flow_service.GreetingServiceGrpc;
import ru.okibiteam.production_flow_service.HelloRequest;
import ru.okibiteam.production_flow_service.HelloResponse;

@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        System.out.println(request);
        HelloResponse response = HelloResponse.newBuilder().setText("Hello from server, " + request.getName()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
