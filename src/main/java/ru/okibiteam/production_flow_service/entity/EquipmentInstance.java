package ru.okibiteam.production_flow_service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
@Data
public class EquipmentInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;
}
