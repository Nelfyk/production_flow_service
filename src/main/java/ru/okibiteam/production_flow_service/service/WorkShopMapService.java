package ru.okibiteam.production_flow_service.service;

import com.google.protobuf.ByteString;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.okibiteam.production_flow_service.grpc.Coordinates;
import ru.okibiteam.production_flow_service.grpc.EquipmentsOnMap;
import ru.okibiteam.production_flow_service.grpc.WorkShopMap;
import ru.okibiteam.production_flow_service.grpc.WorkShopMapResponse;
import ru.okibiteam.production_flow_service.repository.mongo.WorkShopMapRepository;
import ru.okibiteam.production_flow_service.repository.postgres.SerialNumbersRepository;

import java.io.IOException;

@Service
public class WorkShopMapService {
    @Autowired
    private WorkShopMapRepository workShopMapRepository;
    @Autowired
    private SerialNumbersRepository serialNumbersRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Transactional(rollbackForClassName = {"java.lang.Exception"})
    public void getAllWorkShopMap(StreamObserver<WorkShopMapResponse> streamObserver) throws Exception {
        workShopMapRepository.findAll().stream()
                .map(e -> {
                    try {
                        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(e.getFilenameId())));
                        var resource = gridFsTemplate.getResource(file);
                        var inputStream = resource.getInputStream();
                        ByteString byteString = ByteString.copyFrom(inputStream.readAllBytes());
                        return WorkShopMapResponse.newBuilder()
                                .setId(e.getId().toString())
                                .setWorkShopMap(WorkShopMap.newBuilder()
                                        .setFileName(e.getFilename())
                                        .setMapImage(byteString)
                                        .addAllEquipments(e.getEquipmentEntities().stream()
                                                .map(el -> EquipmentsOnMap.newBuilder()
                                                        .setSerialNumbers(serialNumbersRepository.getById(el.getSerialNumbersId()).toProtoSerialNumbers())
                                                        .addAllCoordinates(el.getCoords().stream()
                                                                .map(elem -> Coordinates.newBuilder()
                                                                        .setX(elem.getX())
                                                                        .setY(elem.getY())
                                                                        .build())
                                                                .toList())
                                                        .build())
                                                .toList()))
                                .build();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).forEach(streamObserver::onNext);
        streamObserver.onCompleted();
    }
}
