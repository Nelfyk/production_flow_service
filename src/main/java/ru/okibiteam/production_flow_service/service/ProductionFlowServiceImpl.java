package ru.okibiteam.production_flow_service.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.okibiteam.production_flow_service.ProductionFlowServiceGrpc;
import ru.okibiteam.production_flow_service.ProductionFlowServiceOuterClass;

@GrpcService
public class ProductionFlowServiceImpl extends ProductionFlowServiceGrpc.ProductionFlowServiceImplBase {
    @Override
    public void getAllCommodityItems(Empty empty, StreamObserver<ProductionFlowServiceOuterClass.CommodityItem> streamObserver) {

    }

    @Override
    public void getAllWorkShopMap(Empty empty, StreamObserver<ProductionFlowServiceOuterClass.WorkShopMap> streamObserver) {

    }

    @Override
    public void createTechnoMap(ProductionFlowServiceOuterClass.CreateTechnoMapRequest request,
                                StreamObserver<ProductionFlowServiceOuterClass.CreateTechnoMapResponse> streamObserver) {

    }

    @Override
    public void getTechnoMapStages(ProductionFlowServiceOuterClass.TechnoMap request,
                                   StreamObserver<ProductionFlowServiceOuterClass.TechnoMapStages> streamObserver) {

    }
}
