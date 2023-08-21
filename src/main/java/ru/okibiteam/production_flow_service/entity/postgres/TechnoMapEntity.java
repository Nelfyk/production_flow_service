package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "techno_map")
@Data
public class TechnoMapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "commodity_item_id", referencedColumnName = "id")
    private CommodityItem commodityItem;
    public TechnoMapEntity() {
    }

    public TechnoMapEntity(String name, CommodityItem commodityItem) {
        this.name = name;
        this.commodityItem = commodityItem;
    }
}
