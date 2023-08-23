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
    private SerialNumbersRepository serialNumbersRepository;
    @Autowired
    private TechnoMapStagesEntityRepository technoMapStagesEntityRepository;
    @Autowired
    private InputItemForProductionRepository inputItemForProductionRepository;
    @Autowired
    private ItemForProductionRepository itemForProductionRepository;
    @Autowired
    private InputCommodityItemRepository inputCommodityItemRepository;

    @Transactional(rollbackForClassName = {"java.lang.Exception"})
    public void saveNewTechnoMap(CreateTechnoMapRequest request,
                                 StreamObserver<CreateTechnoMapResponse> streamObserver) throws Exception {
        CommodityItem commodityItem = commodityItemsRepository.getById(request.getCommodityItemId());
        var technoMapEntity = technoMapsRepository.save(new TechnoMapEntity(request.getTechnoMapName(), commodityItem));
        List<Stage> technoMapStages = request.getTechnoMapStagesList();
        for (var stages : technoMapStages) {
            var serialNumbers = serialNumbersRepository.getById(stages.getSerialNumbersId());
            TechnoMapStagesEntity technoMapStagesEntity = technoMapStagesEntityRepository.save(new TechnoMapStagesEntity(
                    stages.getName(),
                    stages.getIndexInTechnoMap(),
                    stages.getTimeSpent(),
                    BigDecimal.valueOf(stages.getMoneyExpensesInRubles().getRubles() +
                            (stages.getMoneyExpensesInRubles().getCents() / 100.)),
                    stages.getWorkshopMapId(),
                    stages.getCreatorId(),
                    technoMapEntity,
                    serialNumbers
            ));
            stages.getInputItemsForProductionList().forEach(e -> {
                if (e.getId() != 0) {
                    inputItemForProductionRepository.saveQuery(e.getId(), technoMapStagesEntity.getId());
                } else {
                    var itemForProduction = itemForProductionRepository.save(new ItemForProduction(e.getName(), technoMapStagesEntity));
                    inputItemForProductionRepository.save(new InputItemForProduction(
                            itemForProduction,
                            technoMapStagesEntity
                    ));
                }
            });
            stages.getInputCommodityItemList().forEach(e -> {
                if (e.getId() != 0) {
                    inputCommodityItemRepository.saveQuery(e.getId(), technoMapStagesEntity.getId());
                } else {
                    var commodityItemNew = commodityItemsRepository.save(new CommodityItem(e.getName()));
                    inputCommodityItemRepository.save(new InputCommodityItem(
                            commodityItemNew,
                            technoMapStagesEntity
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
        var technoMapEntity = technoMapsRepository.getById(request.getId());
        var stages = technoMapStagesEntityRepository.findTechnoMapStagesEntitiesByTechnoMapEntityId(technoMapEntity.getId()).
                stream().map(e -> Stage.newBuilder()
                        .setName(e.getName())
                        .setIndexInTechnoMap(e.getIndexInTechnoMap())
                        .setTimeSpent(e.getTimeSpentInSeconds())
                        .setMoneyExpensesInRubles(Money.newBuilder()
                                .setRubles(e.getMoneyExpensesInRubles().longValue())
                                .setCents(e.getMoneyExpensesInRubles()
                                        .remainder(BigDecimal.ONE)
                                        .multiply(new BigDecimal(100)).intValue())
                                .build())
                        .setWorkshopMapId(e.getWorkshopMapId())
                        .setSerialNumbersId(e.getSerialNumbersEntity().getId())
                        .setCreatorId(e.getCreatorId())
                        .addAllInputItemsForProduction(inputItemForProductionRepository.getByTechnoMapStagesEntityId(e.getId())
                                .stream().map(InputItemForProduction::getItemForProduction)
                                .map(el -> ru.okibiteam.production_flow_service.grpc.ItemForProduction.newBuilder()
                                        .setId(el.getId())
                                        .setName(el.getName())
                                        .build())
                                .toList())
                        .addAllInputCommodityItem(inputCommodityItemRepository.getByTechnoMapStagesEntityId(e.getId())
                                .stream().map(InputCommodityItem::getCommodityItem)
                                .map(el -> ru.okibiteam.production_flow_service.grpc.CommodityItem.newBuilder()
                                        .setId(el.getId())
                                        .setName(el.getName())
                                        .build())
                                .toList())
                        .build()
                ).toList();
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
