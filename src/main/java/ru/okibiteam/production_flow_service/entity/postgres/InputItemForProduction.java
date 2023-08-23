package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "input_item_for_production")
@Data
public class InputItemForProduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "item_for_production_id", referencedColumnName = "id")
    private ItemForProduction itemForProduction;
    @OneToOne
    @JoinColumn(name = "techno_map_stages_id", referencedColumnName = "id")
    private TechnoMapStagesEntity technoMapStagesEntity;

    public InputItemForProduction() {
    }

    public InputItemForProduction(ItemForProduction itemForProduction, TechnoMapStagesEntity technoMapStagesEntity) {
        this.itemForProduction = itemForProduction;
        this.technoMapStagesEntity = technoMapStagesEntity;
    }
}
