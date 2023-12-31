package ru.okibiteam.production_flow_service.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.okibiteam.production_flow_service.grpc.*;
import ru.okibiteam.production_flow_service.service.CommodityItemService;
import ru.okibiteam.production_flow_service.service.TechnoMapService;
import ru.okibiteam.production_flow_service.service.WorkShopMapService;

@GrpcService
public class ProductionFlowServiceController extends ProductionFlowServiceGrpc.ProductionFlowServiceImplBase {
    @Autowired
    private TechnoMapService technoMapService;
    @Autowired
    private CommodityItemService commodityItemService;
    @Autowired
    private WorkShopMapService workShopMapService;

    @Override
    public void getAllCommodityItems(Empty empty, StreamObserver<CommodityItemResponse> streamObserver) {
        try {
            commodityItemService.getAllCommodityItems(streamObserver);
        } catch (Exception e) {
            Status status = Status.NOT_FOUND;
            streamObserver.onError(status.asRuntimeException());
            e.printStackTrace();
        }
    }

    @Override
    public void getAllWorkShopMap(Empty empty, StreamObserver<WorkShopMapResponse> streamObserver) {
        try {
            workShopMapService.getAllWorkShopMap(streamObserver);
        } catch (Exception e) {
            Status status = Status.NOT_FOUND;
            streamObserver.onError(status.asRuntimeException());
            e.printStackTrace();
        }
    }

    @Override
    public void createTechnoMap(CreateTechnoMapRequest request,
                                StreamObserver<CreateTechnoMapResponse> streamObserver) {
        try {
            technoMapService.saveNewTechnoMap(request, streamObserver);
        } catch (Exception e) {
            Status status = Status.INVALID_ARGUMENT;
            streamObserver.onError(status.asRuntimeException());
            e.printStackTrace();
        }
    }

    @Override
    public void getTechnoMap(TechnoMapRequest request,
                             StreamObserver<TechnoMapResponse> streamObserver) {
        try {
            technoMapService.getTechnoMap(request, streamObserver);
        } catch (Exception e) {
            Status status = Status.INVALID_ARGUMENT;
            streamObserver.onError(status.asRuntimeException());
            e.printStackTrace();
        }
    }
}
