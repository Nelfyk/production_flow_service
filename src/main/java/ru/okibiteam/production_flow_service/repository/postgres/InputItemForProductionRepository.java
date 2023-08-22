package ru.okibiteam.production_flow_service.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.postgres.InputItemForProduction;
import ru.okibiteam.production_flow_service.entity.postgres.ItemForProduction;

import java.util.List;

@Repository
public interface InputItemForProductionRepository extends JpaRepository<InputItemForProduction, Integer> {
    @Modifying
    @Query(value = "INSERT INTO input_item_for_production (item_for_production_id, stage_id) VALUES (:itemForProductionId, :stageId)", nativeQuery = true)
    void saveQuery(int itemForProductionId, int stageId);
    List<InputItemForProduction> getByStageEntityId(int id);
}
