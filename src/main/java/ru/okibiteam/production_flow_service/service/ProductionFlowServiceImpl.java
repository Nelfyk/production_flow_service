package ru.okibiteam.production_flow_service.service;

import com.google.protobuf.Empty;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSUploadStream;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.tika.Tika;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import ru.okibiteam.production_flow_service.entity.mongo.WorkShopMapEntity;
import ru.okibiteam.production_flow_service.grpc.*;
import ru.okibiteam.production_flow_service.repository.postgres.CommodityItemsRepository;
import ru.okibiteam.production_flow_service.repository.postgres.TechnoMapsRepository;
import org.bson.Document;
import com.mongodb.client.gridfs.model.GridFSFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@GrpcService
public class ProductionFlowServiceImpl extends ProductionFlowServiceGrpc.ProductionFlowServiceImplBase {
    @Autowired
    CommodityItemsRepository commodityItemsRepository;
    @Autowired
    TechnoMapsRepository technoMapsRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public void getAllCommodityItems(Empty empty, StreamObserver<CommodityItemResponse> streamObserver) {
        var technoMaps = technoMapsRepository.findAll();
        technoMaps.forEach(e -> streamObserver.onNext(CommodityItemResponse.newBuilder()
                .setId(e.getCommodityItem().getId())
                .setName(e.getCommodityItem().getName())
                .setTechnoMapId(e.getId())
                .setTechnoMapName(e.getName())
                .build()));
        streamObserver.onCompleted();
    }

    @Override
    public void createWorkShopMap(WorkShopMap request, StreamObserver<WorkShopMapResponse> streamObserver) {
        String filename = request.getFileName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        String contentType = new Tika().detect(filename);
        var workShopMap = new WorkShopMapEntity(filename, contentType,);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getMapImage().toByteArray());

        Document metaData = new Document("contentType", contentType);
        ObjectId fileId = gridFsTemplate.store(inputStream, filename, metaData);

    }

    @Override
    public void getAllWorkShopMap(Empty empty, StreamObserver<WorkShopMapResponse> streamObserver) {

    }

    @Override
    public void createTechnoMap(CreateTechnoMapRequest request,
                                StreamObserver<CreateTechnoMapResponse> streamObserver) {

    }

    @Override
    public void getTechnoMap(TechnoMapRequest request,
                             StreamObserver<TechnoMapResponse> streamObserver) {

    }
}
