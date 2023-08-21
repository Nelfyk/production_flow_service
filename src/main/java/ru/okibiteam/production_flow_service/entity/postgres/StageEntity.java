package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "stage")
@Data
public class StageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "index_in_technomap")
    private int indexInTechnomap;
    @Column(name = "time_spent_in_seconds")
    private int timeSpentInSeconds;
    @Column(name = "money_expenses_in_rubles")
    private BigDecimal moneyExpensesInRubles;
    @Column(name = "workshop_map_id")
    private int workshopMapId;
    @Column(name = "equipment_instance_id")
    private int equipmentInstanceId;
    @Column(name = "create_time")
    private ZonedDateTime createTime;
    @Column(name = "creater_id")
    private int createrId;
    @OneToOne
    @JoinColumn(name = "equipment_instance_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EquipmentInstance equipmentInstance;

    public StageEntity() {
    }

    public StageEntity(String name, int indexInTechnomap, int timeSpentInSeconds,
                       BigDecimal moneyExpensesInRubles, int workshopMapId, int equipmentInstanceId,
                       int createrId, EquipmentInstance equipmentInstance) {
        this.name = name;
        this.indexInTechnomap = indexInTechnomap;
        this.timeSpentInSeconds = timeSpentInSeconds;
        this.moneyExpensesInRubles = moneyExpensesInRubles;
        this.workshopMapId = workshopMapId;
        this.equipmentInstanceId = equipmentInstanceId;
        this.createTime = ZonedDateTime.now();
        this.createrId = createrId;
        this.equipmentInstance = equipmentInstance;
    }
}
