package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "techno_map_stages")
@Data
public class TechnoMapStagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "index_in_techno_map")
    private int indexInTechnoMap;
    @Column(name = "time_spent_in_seconds")
    private int timeSpentInSeconds;
    @Column(name = "money_expenses_in_rubles")
    private BigDecimal moneyExpensesInRubles;
    @Column(name = "workshop_map_id")
    private int workshopMapId;
    @Column(name = "create_time")
    private ZonedDateTime createTime;
    @Column(name = "creator_id")
    private int creatorId;
    @ManyToOne
    @JoinColumn(name = "techno_map_id", referencedColumnName = "id")
    private TechnoMapEntity technoMapEntity;
    @OneToOne
    @JoinColumn(name = "serial_numbers_id", referencedColumnName = "id")
    private SerialNumbersEntity serialNumbersEntity;

    public TechnoMapStagesEntity() {
    }

    public TechnoMapStagesEntity(String name, int indexInTechnoMap, int timeSpentInSeconds,
                                 BigDecimal moneyExpensesInRubles, int workshopMapId, int creatorId,
                                 TechnoMapEntity technoMapEntity, SerialNumbersEntity serialNumbersEntity) {
        this.name = name;
        this.indexInTechnoMap = indexInTechnoMap;
        this.timeSpentInSeconds = timeSpentInSeconds;
        this.moneyExpensesInRubles = moneyExpensesInRubles;
        this.workshopMapId = workshopMapId;
        this.createTime = ZonedDateTime.now();
        this.creatorId = creatorId;
        this.technoMapEntity = technoMapEntity;
        this.serialNumbersEntity = serialNumbersEntity;
    }
}
