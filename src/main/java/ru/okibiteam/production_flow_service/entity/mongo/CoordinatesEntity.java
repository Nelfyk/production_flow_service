package ru.okibiteam.production_flow_service.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.okibiteam.production_flow_service.grpc.Coordinates;

@Document
@Data
public class CoordinatesEntity {
    private int x;
    private int y;

    public CoordinatesEntity() {
    }

    public CoordinatesEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates toProtoCoordinates() {
        return Coordinates.newBuilder()
                .setX(x)
                .setY(y)
                .build();
    }
}
