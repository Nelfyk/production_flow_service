package ru.okibiteam.production_flow_service.entity.postgres;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StageTechnoMapId implements Serializable {
    @Column(name = "stage_id")
    private Integer stageId;

    @Column(name = "techno_map_id")
    private Integer technoMapId;
}
