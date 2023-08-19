package ru.okibiteam.production_flow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.okibiteam.production_flow_service.entity.CommodityItems;
import ru.okibiteam.production_flow_service.entity.TechnoMaps;

import java.util.List;

public interface TechnoMapsRepository extends JpaRepository<TechnoMaps, Integer> {
//    @Query(value = "SELECT * FROM techno_maps", nativeQuery = true)
//    List<TechnoMaps> getAll();
}
