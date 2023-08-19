package ru.okibiteam.production_flow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.okibiteam.production_flow_service.entity.CommodityItems;

import java.util.List;

public interface CommodityItemsRepository extends JpaRepository<CommodityItems, Integer> {
}
