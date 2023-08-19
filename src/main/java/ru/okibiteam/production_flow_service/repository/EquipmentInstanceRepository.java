package ru.okibiteam.production_flow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.okibiteam.production_flow_service.entity.EquipmentInstance;

public interface EquipmentInstanceRepository extends JpaRepository<EquipmentInstance, Integer> {
}
