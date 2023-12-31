package ru.okibiteam.production_flow_service.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.postgres.InputCommodityItem;

import java.util.List;

@Repository
public interface InputCommodityItemRepository extends JpaRepository<InputCommodityItem, Integer> {
    @Modifying
    @Query(value = "INSERT INTO input_commodity_items (commodity_item_id, techno_map_stages_id) VALUES (:commodityItemId, :technoMapStagesId);", nativeQuery = true)
    void saveQuery(int commodityItemId, int technoMapStagesId);

    List<InputCommodityItem> getByTechnoMapStagesEntityId(int id);
}
