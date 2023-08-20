package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "input_item_for_production")
@Data
public class InputItemForProduction {
    @EmbeddedId
    private InputItemForProductionId id;
    @OneToOne
    @JoinColumn(name = "item_for_production_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ItemForProduction itemForProduction;
    @OneToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Stages stages;
}
