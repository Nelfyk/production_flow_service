package ru.okibiteam.production_flow_service.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.okibiteam.production_flow_service.*;
import ru.okibiteam.production_flow_service.entity.TechnoMaps;
import ru.okibiteam.production_flow_service.repository.CommodityItemsRepository;
import ru.okibiteam.production_flow_service.repository.TechnoMapsRepository;

@GrpcService
public class ProductionFlowServiceImpl extends ProductionFlowServiceGrpc.ProductionFlowServiceImplBase {
    @Autowired
    CommodityItemsRepository commodityItemsRepository;
    @Autowired
    TechnoMapsRepository technoMapsRepository;

    @Override
    public void getAllCommodityItems(Empty empty, StreamObserver<CommodityItem> streamObserver) {
        var technoMaps = technoMapsRepository.findAll();
        var commodityItems = technoMaps.stream().map(TechnoMaps::getCommodityItems).toList();

        commodityItems.forEach(e -> streamObserver.onNext(CommodityItem.newBuilder()
                .setId(e.getId())
                .setName(e.getName())
                .addAllTechnoMap(
                        technoMaps.stream()
                                .filter(el -> el.getCommodityItems().getId() == e.getId())
                                .map(el -> TechnoMap.newBuilder().setId(el.getId()).setName(el.getName()).build())
                                .toList()
                ).build()));
        streamObserver.onCompleted();
    }

    @Override
    public void getAllWorkShopMap(Empty empty, StreamObserver<WorkShopMap> streamObserver) {

    }

    @Override
    public void createTechnoMap(CreateTechnoMapRequest request,
                                StreamObserver<CreateTechnoMapResponse> streamObserver) {

    }

    @Override
    public void getTechnoMapStages(TechnoMap request,
                                   StreamObserver<TechnoMapStages> streamObserver) {

    }
}
