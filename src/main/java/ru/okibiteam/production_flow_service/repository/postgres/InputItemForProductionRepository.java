package ru.okibiteam.production_flow_service.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.postgres.InputItemForProduction;
import ru.okibiteam.production_flow_service.entity.postgres.InputItemForProductionId;

@Repository
public interface InputItemForProductionRepository extends JpaRepository<InputItemForProduction, InputItemForProductionId> {
}
