package ru.okibiteam.production_flow_service.entity.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Document(collation = "work_shop_map")
@Data
public class WorkShopMapEntity {
    @Id
    private ObjectId id;
    private String filename;
    @Field(name = "content_type")
    private String contentType;
    @Field(name = "image_data")
    private List<Equipment> equipments;

    public WorkShopMapEntity() {
    }

    public WorkShopMapEntity(String filename, String contentType, List<Equipment> equipments) {
        this.filename = filename;
        this.contentType = contentType;
        this.equipments = equipments;
    }
}
