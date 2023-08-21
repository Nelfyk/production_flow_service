package ru.okibiteam.production_flow_service.service;

import com.google.protobuf.Empty;
import com.google.rpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.okibiteam.production_flow_service.entity.postgres.*;
import ru.okibiteam.production_flow_service.entity.postgres.CommodityItem;
import ru.okibiteam.production_flow_service.entity.postgres.EquipmentInstance;
import ru.okibiteam.production_flow_service.entity.postgres.ItemForProduction;
import ru.okibiteam.production_flow_service.grpc.*;
import ru.okibiteam.production_flow_service.repository.postgres.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ProductionFlowServiceImpl extends ProductionFlowServiceGrpc.ProductionFlowServiceImplBase {
    @Autowired
    CommodityItemsRepository commodityItemsRepository;
    @Autowired
    TechnoMapsRepository technoMapsRepository;
    @Autowired
    ItemForProductionRepository itemForProductionRepository;
    @Autowired
    InputItemForProductionRepository inputItemForProductionRepository;
    @Autowired
    InputCommodityItemRepository inputCommodityItemRepository;
    @Autowired
    EquipmentInstanceRepository equipmentInstanceRepository;
    @Autowired
    StagesEntityRepository stagesEntityRepository;
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
    @Transactional
    public void createTechnoMap(CreateTechnoMapRequest request,
                                StreamObserver<CreateTechnoMapResponse> streamObserver) {
        try {
            CommodityItem commodityItem = commodityItemsRepository.getById(request.getCommodityItemId());
            var technoMapEntity = technoMapsRepository.save(new TechnoMapEntity(request.getTechnoMapName(), commodityItem));
            List<Stage> technoMapStages = request.getTechnoMapStagesList();
            for (var stages : technoMapStages) {
                var equipmentInstance = equipmentInstanceRepository.getById(stages.getEquipmentInstanceId());
                StageEntity stageEntity = stagesEntityRepository.save(new StageEntity(
                        stages.getName(),
                        stages.getIndexInTechnoMap(),
                        stages.getTimeSpent(),
                        new BigDecimal(stages.getMoneyExpensesInRubles().getIntegerPart()
                                + '.'
                                + stages.getMoneyExpensesInRubles().getFractionalPart()),
                        stages.getWorkshopMapId(),
                        equipmentInstance.getId(),
                        stages.getCreaterId(),
                        equipmentInstance
                ));
                stages.getInputItemsForProductionList().forEach(e -> {
                    if (e.getId() != 0) {
                        inputItemForProductionRepository.saveQuery(e.getId(), stageEntity.getId());
                    } else {
                        var itemForProduction = itemForProductionRepository.save(new ItemForProduction(e.getName(), stageEntity));
                        inputItemForProductionRepository.save(new InputItemForProduction(
                                itemForProduction,
                                stageEntity
                        ));
                    }
                });
                stages.getInputCommodityItemList().forEach(e -> {
                    if (e.getId() != 0) {
                        inputCommodityItemRepository.saveQuery(e.getId(), stageEntity.getId());
                    } else {
                        var commodityItemNew = commodityItemsRepository.save(new CommodityItem(e.getName()));
                        inputCommodityItemRepository.save(new InputCommodityItem(
                                commodityItemNew,
                                stageEntity
                        ));
                    }
                });
            }
            streamObserver.onNext(CreateTechnoMapResponse.newBuilder()
                    .setStatus(Status.newBuilder().setCode(0).setMessage("Ok").build())
                    .setTechnoMapId(technoMapEntity.getId())
                    .build());
            streamObserver.onCompleted();
        } catch (Exception e) {
            streamObserver.onNext(CreateTechnoMapResponse.newBuilder()
                    .setStatus(Status.newBuilder().setCode(1).setMessage("Error").build())
                    .setTechnoMapId(-1)
                    .build());
            streamObserver.onCompleted();
            throw e;
        }
    }

    @Override
    @Transactional
    public void getTechnoMap(TechnoMapRequest request,
                             StreamObserver<TechnoMapResponse> streamObserver) {

    }
}
