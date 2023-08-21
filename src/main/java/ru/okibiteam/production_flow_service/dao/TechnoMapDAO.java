package ru.okibiteam.production_flow_service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.okibiteam.production_flow_service.entity.postgres.*;
import ru.okibiteam.production_flow_service.grpc.CreateTechnoMapRequest;
import ru.okibiteam.production_flow_service.grpc.Stage;
import ru.okibiteam.production_flow_service.repository.postgres.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TechnoMapDAO {
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

    @Transactional(rollbackForClassName = {"java.lang.Exception"})
    public TechnoMapEntity saveNewTechnoMap(CreateTechnoMapRequest request) throws Exception {
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
        return technoMapEntity;
    }
}
