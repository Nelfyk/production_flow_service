package ru.okibiteam.production_flow_service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commodity_items")
@Data
public class CommodityItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
}
