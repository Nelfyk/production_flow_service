package ru.okibiteam.production_flow_service.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document
@Data
public class EquipmentEntity {
    @Field(name = "equipment_instance_id")
    private int equipmentInstanceId;
    @Field(name = "serial_numbers_id")
    private int serialNumbersId;
    private List<CoordinatesEntity> coords;

    public EquipmentEntity() {
    }

    public EquipmentEntity(int equipmentInstanceId, int serialNumbersId, List<CoordinatesEntity> coords) {
        this.equipmentInstanceId = equipmentInstanceId;
        this.serialNumbersId = serialNumbersId;
        this.coords = coords;
    }
}
