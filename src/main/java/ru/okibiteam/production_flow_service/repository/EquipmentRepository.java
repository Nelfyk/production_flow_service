package ru.okibiteam.production_flow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.okibiteam.production_flow_service.entity.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
}
