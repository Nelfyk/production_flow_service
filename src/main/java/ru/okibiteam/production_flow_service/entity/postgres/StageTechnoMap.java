package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "stage_techno_map")
@Data
public class StageTechnoMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "techno_map_stages_id", referencedColumnName = "id")
    private TechnoMapStagesEntity technoMapStagesEntity;
    @ManyToOne
    @JoinColumn(name = "techno_map_id", referencedColumnName = "id")
    private TechnoMapEntity technoMapEntity;
}
