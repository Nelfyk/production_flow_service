package ru.okibiteam.production_flow_service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "techno_maps")
@Data
public class TechnoMaps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "commodity_items_id", referencedColumnName = "id")
    private CommodityItems commodityItems;
}
