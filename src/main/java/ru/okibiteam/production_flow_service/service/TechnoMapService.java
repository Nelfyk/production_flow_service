package ru.okibiteam.production_flow_service.service;

import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.okibiteam.production_flow_service.entity.postgres.CommodityItem;
import ru.okibiteam.production_flow_service.entity.postgres.ItemForProduction;
import ru.okibiteam.production_flow_service.entity.postgres.*;
import ru.okibiteam.production_flow_service.grpc.*;
import ru.okibiteam.production_flow_service.repository.postgres.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TechnoMapService {
    @Autowired
    private CommodityItemsRepository commodityItemsRepository;
    @Autowired
    private TechnoMapsRepository technoMapsRepository;
    @Autowired
    private EquipmentInstanceRepository equipmentInstanceRepository;
    @Autowired
    private StagesEntityRepository stagesEntityRepository;
    @Autowired
    private InputItemForProductionRepository inputItemForProductionRepository;
    @Autowired
    private ItemForProductionRepository itemForProductionRepository;
    @Autowired
    private InputCommodityItemRepository inputCommodityItemRepository;
    @Autowired
    private StageTechnoMapRepository stageTechnoMapRepository;

    @Transactional(rollbackForClassName = {"java.lang.Exception"})
    public void saveNewTechnoMap(CreateTechnoMapRequest request,
                                 StreamObserver<CreateTechnoMapResponse> streamObserver) throws Exception {
        CommodityItem commodityItem = commodityItemsRepository.getById(request.getCommodityItemId());
        var technoMapEntity = technoMapsRepository.save(new TechnoMapEntity(request.getTechnoMapName(), commodityItem));
        List<Stage> technoMapStages = request.getTechnoMapStagesList();
        for (var stages : technoMapStages) {
            var equipmentInstance = equipmentInstanceRepository.getById(stages.getEquipmentInstanceId());
            StageEntity stageEntity = stagesEntityRepository.save(new StageEntity(
                    stages.getName(),
                    stages.getIndexInTechnoMap(),
                    stages.getTimeSpent(),
                    new BigDecimal(stages.getMoneyExpensesInRubles().getRubles() +
                            (stages.getMoneyExpensesInRubles().getCents() / 100.)),
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
                .setTechnoMapId(technoMapEntity.getId())
                .build());
        streamObserver.onCompleted();
    }

    @Transactional(rollbackForClassName = {"java.lang.Exception"})
    public void getTechnoMap(TechnoMapRequest request,
                             StreamObserver<TechnoMapResponse> streamObserver) {
        var technoMapEntities = stageTechnoMapRepository.findByTechnoMapEntityId(request.getId());
        var stages = technoMapEntities.stream().map(StageTechnoMap::getStageEntity).map(e -> Stage.newBuilder()
                .setName(e.getName())
                .setIndexInTechnoMap(e.getIndexInTechnomap())
                .setTimeSpent(e.getTimeSpentInSeconds())
                .setMoneyExpensesInRubles(Money.newBuilder()
                        .setRubles(e.getMoneyExpensesInRubles().longValue())
                        .setCents(e.getMoneyExpensesInRubles()
                                .remainder(BigDecimal.ONE)
                                .multiply(new BigDecimal(100)).intValue())
                        .build())
                .setWorkshopMapId(e.getWorkshopMapId())
                .setEquipmentInstanceId(e.getEquipmentInstanceId())
                .setCreaterId(e.getCreaterId())
                .addAllInputItemsForProduction(inputItemForProductionRepository.getByStageEntityId(e.getId())
                        .stream().map(InputItemForProduction::getItemForProduction)
                        .map(el -> ru.okibiteam.production_flow_service.grpc.ItemForProduction.newBuilder()
                                .setId(el.getId())
                                .setName(el.getName())
                                .build())
                        .toList())
                .addAllInputCommodityItem(inputCommodityItemRepository.getByStageEntityId(e.getId())
                        .stream().map(InputCommodityItem::getCommodityItem)
                        .map(el -> ru.okibiteam.production_flow_service.grpc.CommodityItem.newBuilder()
                                .setId(el.getId())
                                .setName(el.getName())
                                .build())
                        .toList())
                .build()
        ).toList();
        var technoMapEntity = technoMapEntities.stream().map(StageTechnoMap::getTechnoMapEntity).findFirst().get();
        streamObserver.onNext(TechnoMapResponse.newBuilder()
                .setId(technoMapEntity.getId())
                .setTechnoMap(TechnoMap
                        .newBuilder()
                        .setName(technoMapEntity.getName())
                        .addAllStages(stages)
                        .build())
                .build()
        );
        streamObserver.onCompleted();
    }
}