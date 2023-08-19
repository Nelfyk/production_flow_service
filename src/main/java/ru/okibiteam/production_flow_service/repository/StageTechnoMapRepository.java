package ru.okibiteam.production_flow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.okibiteam.production_flow_service.entity.StageTechnoMap;
import ru.okibiteam.production_flow_service.entity.StageTechnoMapId;

public interface StageTechnoMapRepository extends JpaRepository<StageTechnoMap, StageTechnoMapId> {
}
