package ru.okibiteam.production_flow_service.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.postgres.InputItemForProduction;

import java.util.List;

@Repository
public interface InputItemForProductionRepository extends JpaRepository<InputItemForProduction, Integer> {
    @Modifying
    @Query(value = "INSERT INTO input_item_for_production (item_for_production_id, techno_map_stages_id) VALUES (:itemForProductionId, :technoMapStagesId)", nativeQuery = true)
    void saveQuery(int itemForProductionId, int technoMapStagesId);

    List<InputItemForProduction> getByTechnoMapStagesEntityId(int id);
}
