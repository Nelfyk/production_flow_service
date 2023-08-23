package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "item_for_production")
@Data
public class ItemForProduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "created_techno_map_stages_id", referencedColumnName = "id")
    private TechnoMapStagesEntity technoMapStagesEntity;

    public ItemForProduction() {
    }

    public ItemForProduction(String name, TechnoMapStagesEntity technoMapStagesEntity) {
        this.name = name;
        this.technoMapStagesEntity = technoMapStagesEntity;
    }
}
