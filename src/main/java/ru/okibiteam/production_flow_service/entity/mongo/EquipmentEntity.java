package ru.okibiteam.production_flow_service.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class EquipmentEntity {
    @Field(name = "equipment_instance_id")
    private int equipmentInstanceId;
    @Field(name = "serial_numbers_id")
    private int serialNumbersId;
    private List<Coordinates> coords;
}
