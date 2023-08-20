package ru.okibiteam.production_flow_service.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.postgres.TechnoMapEntity;

@Repository
public interface TechnoMapsRepository extends JpaRepository<TechnoMapEntity, Integer> {
}
