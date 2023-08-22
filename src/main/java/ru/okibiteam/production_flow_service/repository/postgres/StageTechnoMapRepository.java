package ru.okibiteam.production_flow_service.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.postgres.StageTechnoMap;

import java.util.List;

@Repository
public interface StageTechnoMapRepository extends JpaRepository<StageTechnoMap, Integer> {
//    @Query(value = "SELECT * FROM stage_techno_map WHERE techno_map_id = :id", nativeQuery = true)
    List<StageTechnoMap> findByTechnoMapEntityId(int id);
}
