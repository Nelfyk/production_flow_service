package ru.okibiteam.production_flow_service.entity.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "work_shop_map")
@Data
public class WorkShopMapEntity {
    @Id
    private ObjectId id;
    private String filename;
    @Field(name = "filename_id")
    private ObjectId filenameId;
    @Field(name = "equipments")
    private List<EquipmentEntity> equipmentEntities;

    public WorkShopMapEntity() {
    }

    public WorkShopMapEntity(String filename, ObjectId filenameId, List<EquipmentEntity> equipmentEntities) {
        this.filename = filename;
        this.filenameId = filenameId;
        this.equipmentEntities = equipmentEntities;
    }
}
