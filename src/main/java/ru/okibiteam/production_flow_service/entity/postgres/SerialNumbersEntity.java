package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;
import ru.okibiteam.production_flow_service.grpc.Equipment;
import ru.okibiteam.production_flow_service.grpc.SerialNumbers;

import javax.persistence.*;

@Entity
@Table(name = "serial_numbers")
@Data
public class SerialNumbersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "serial_number")
    private String serialNumber;
    @OneToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private EquipmentEntity equipmentEntity;

    public SerialNumbers toProtoSerialNumbers() {
        return SerialNumbers.newBuilder()
                .setEquipment(Equipment.newBuilder()
                        .setName(equipmentEntity.getName())
                        .build())
                .setSerialNumber(serialNumber)
                .build();
    }
}
