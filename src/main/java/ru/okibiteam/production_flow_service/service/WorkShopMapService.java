package ru.okibiteam.production_flow_service.service;

import com.google.protobuf.ByteString;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.grpc.stub.StreamObserver;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.okibiteam.production_flow_service.entity.mongo.CoordinatesEntity;
import ru.okibiteam.production_flow_service.entity.mongo.EquipmentEntity;
import ru.okibiteam.production_flow_service.entity.mongo.WorkShopMapEntity;
import ru.okibiteam.production_flow_service.grpc.EquipmentsOnMap;
import ru.okibiteam.production_flow_service.grpc.WorkShopMap;
import ru.okibiteam.production_flow_service.grpc.WorkShopMapResponse;
import ru.okibiteam.production_flow_service.repository.mongo.WorkShopMapRepository;
import ru.okibiteam.production_flow_service.repository.postgres.SerialNumbersRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WorkShopMapService {
    @Autowired
    private WorkShopMapRepository workShopMapRepository;
    @Autowired
    private SerialNumbersRepository serialNumbersRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @PostConstruct
    public void init() {
        workShopMapRepository.deleteAll();
        gridFsTemplate.delete(new Query());
        var equipmentEntities = List.of(new EquipmentEntity(
                1,
                1,
                List.of(new CoordinatesEntity(100, 100))
        ));
        Arrays.asList(
                new File("src/main/resources/Images/1.jpg"),
                new File("src/main/resources/Images/2.jpg"),
                new File("src/main/resources/Images/3.jpg")
        ).forEach(e -> {
            try {
                ObjectId storedFile = gridFsTemplate.store(new FileInputStream(e), e.getName());
                workShopMapRepository.save(new WorkShopMapEntity(e.getName(), storedFile, equipmentEntities));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

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
                                                                .map(CoordinatesEntity::toProtoCoordinates)
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
