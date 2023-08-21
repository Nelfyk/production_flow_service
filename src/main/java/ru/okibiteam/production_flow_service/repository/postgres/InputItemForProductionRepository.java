package ru.okibiteam.production_flow_service.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.postgres.InputItemForProduction;
import ru.okibiteam.production_flow_service.entity.postgres.InputItemForProductionId;

@Repository
public interface InputItemForProductionRepository extends JpaRepository<InputItemForProduction, Integer> {
    @Query(value = "INSERT INTO input_item_for_production (item_for_production_id, stage_id) VALUES (:itemForProductionId, :stageId)", nativeQuery = true)
    void saveQuery(int itemForProductionId, int stageId);
}
