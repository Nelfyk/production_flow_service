package ru.okibiteam.production_flow_service.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Equipment {
    @Field(name = "equipment_instance_id")
    private String equipmentInstanceId;
    private List<Coordinates> coords;
}
