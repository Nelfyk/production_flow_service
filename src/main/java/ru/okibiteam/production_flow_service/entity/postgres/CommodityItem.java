package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commodity_item")
@Data
public class CommodityItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    public CommodityItem() {
    }

    public CommodityItem(String name) {
        this.name = name;
    }
}
