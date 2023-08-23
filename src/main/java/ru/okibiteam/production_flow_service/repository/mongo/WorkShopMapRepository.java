package ru.okibiteam.production_flow_service.repository.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.okibiteam.production_flow_service.entity.mongo.WorkShopMapEntity;

@Repository
public interface WorkShopMapRepository extends MongoRepository<WorkShopMapEntity, ObjectId> {
}
