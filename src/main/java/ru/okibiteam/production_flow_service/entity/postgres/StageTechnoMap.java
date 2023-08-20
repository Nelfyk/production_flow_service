package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "stage_techno_map")
@Data
public class StageTechnoMap {
    @EmbeddedId
    private StageTechnoMapId id;
    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Stages stages;
    @ManyToOne
    @JoinColumn(name = "techno_map_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TechnoMapEntity technoMapEntity;
}
