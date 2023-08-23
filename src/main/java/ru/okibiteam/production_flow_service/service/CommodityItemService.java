package ru.okibiteam.production_flow_service.service;

import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okibiteam.production_flow_service.grpc.CommodityItemResponse;
import ru.okibiteam.production_flow_service.grpc.CommodityItemTechnoMaps;
import ru.okibiteam.production_flow_service.repository.postgres.CommodityItemsRepository;

@Service
public class CommodityItemService {
    @Autowired
    private CommodityItemsRepository commodityItemsRepository;

    public void getAllCommodityItems(StreamObserver<CommodityItemResponse> streamObserver) {
        commodityItemsRepository.findAll().forEach(e -> streamObserver.onNext(
                CommodityItemResponse.newBuilder()
                        .setId(e.getId())
                        .setName(e.getName())
                        .addAllCommodityItemTechnoMaps(e.getTechnoMaps().stream().map(
                                        el -> CommodityItemTechnoMaps.newBuilder()
                                                .setTechnoMapId(el.getId())
                                                .setTechnoMapName(el.getName())
                                                .build())
                                .toList())
                        .build()));
        streamObserver.onCompleted();
    }
}
