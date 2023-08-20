package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "stages")
@Data
public class Stages {
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
    private int moneyExpensesInRubles;
    @Column(name = "workshop_map_id")
    private int workshopMapId;
    @Column(name = "equipment_instance_id")
    private int equipmentInstanceId;
    @Column(name = "create_time")
    private ZonedDateTime createTime;
    @Column(name = "creater_id")
    private int createrId;
    @OneToOne
    @JoinColumn(name = "equipment_instance_id", referencedColumnName = "id")
    private EquipmentInstance equipmentInstance;
}
