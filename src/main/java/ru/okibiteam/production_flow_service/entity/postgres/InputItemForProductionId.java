package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
@Embeddable
@Data
public class InputItemForProductionId implements Serializable {
    @Column(name = "item_for_production_id")
    private Integer itemForProductionId;
    @Column(name = "stage_id")
    private Integer stageId;
}
