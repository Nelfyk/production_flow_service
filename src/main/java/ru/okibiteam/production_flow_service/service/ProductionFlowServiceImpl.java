package ru.okibiteam.production_flow_service.service;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.okibiteam.production_flow_service.dao.TechnoMapDAO;
import ru.okibiteam.production_flow_service.entity.postgres.TechnoMapEntity;
import ru.okibiteam.production_flow_service.grpc.*;
import ru.okibiteam.production_flow_service.repository.postgres.CommodityItemsRepository;
import ru.okibiteam.production_flow_service.repository.postgres.TechnoMapsRepository;

@GrpcService
public class ProductionFlowServiceImpl extends ProductionFlowServiceGrpc.ProductionFlowServiceImplBase {
    @Autowired
    private TechnoMapDAO technoMapDAO;
    @Autowired
    private CommodityItemsRepository commodityItemsRepository;
    @Autowired
    private TechnoMapsRepository technoMapsRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    @Transactional
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
    @Transactional
    public void createWorkShopMap(WorkShopMap request, StreamObserver<WorkShopMapResponse> streamObserver) {
//        String filename = request.getFileName();
//        String extension = filename.substring(filename.lastIndexOf(".") + 1);
//        String contentType = new Tika().detect(filename);
//        var list = request.getEquipmentsList().stream().map(e -> )
//        var workShopMap = new WorkShopMapEntity(filename, contentType, );
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getMapImage().toByteArray());
//
//        Document metaData = new Document("contentType", contentType);
//        ObjectId fileId = gridFsTemplate.store(inputStream, filename, metaData);

    }

    @Override
    @Transactional
    public void getAllWorkShopMap(Empty empty, StreamObserver<WorkShopMapResponse> streamObserver) {

    }

    @Override
    public void createTechnoMap(CreateTechnoMapRequest request,
                                StreamObserver<CreateTechnoMapResponse> streamObserver) {
        try {
            TechnoMapEntity technoMapEntity = technoMapDAO.saveNewTechnoMap(request);
            streamObserver.onNext(CreateTechnoMapResponse.newBuilder()
                    .setTechnoMapId(technoMapEntity.getId())
                    .build());
            streamObserver.onCompleted();
        } catch (Exception e) {
            Status status = Status.INVALID_ARGUMENT;
            streamObserver.onError(status.asRuntimeException());
        }
    }

    @Override
    @Transactional
    public void getTechnoMap(TechnoMapRequest request,
                             StreamObserver<TechnoMapResponse> streamObserver) {
        var technoMapEntity = technoMapsRepository.getById(request.getId());
    }
}
