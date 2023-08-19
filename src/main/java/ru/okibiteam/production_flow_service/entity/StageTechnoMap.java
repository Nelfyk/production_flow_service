package ru.okibiteam.production_flow_service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "stage_techno_map")
@Data
public class StageTechnoMap {
    @Id
    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    private Stages stages;
    @Id
    @ManyToOne
    @JoinColumn(name = "techno_map_id", referencedColumnName = "id")
    private TechnoMaps technoMaps;
}
