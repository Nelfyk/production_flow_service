package ru.okibiteam.production_flow_service.entity.mongo;

import lombok.Data;
import ru.okibiteam.production_flow_service.grpc.Coordinates;

@Data
public class CoordinatesEntity {
    private int x;
    private int y;

    public Coordinates toProtoCoordinates() {
        return Coordinates.newBuilder()
                .setX(x)
                .setY(y)
                .build();
    }
}
