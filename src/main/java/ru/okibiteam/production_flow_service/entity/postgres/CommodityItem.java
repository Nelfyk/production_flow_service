package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;
import ru.okibiteam.production_flow_service.grpc.TechnoMap;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "commodity_item")
@Data
public class CommodityItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "commodityItem", fetch = FetchType.EAGER)
    private List<TechnoMapEntity> technoMaps;

    public CommodityItem() {
    }

    public CommodityItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CommodityItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
