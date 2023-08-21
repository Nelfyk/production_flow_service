package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "input_item_for_production")
@Data
public class InputItemForProduction {
    //    @EmbeddedId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "item_for_production_id", referencedColumnName = "id")
    private ItemForProduction itemForProduction;
    @OneToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    private StageEntity stageEntity;

    public InputItemForProduction() {
    }

    public InputItemForProduction(ItemForProduction itemForProduction, StageEntity stageEntity) {
        this.itemForProduction = itemForProduction;
        this.stageEntity = stageEntity;
    }
}
