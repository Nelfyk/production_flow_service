package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "input_commodity_items")
@Data
public class InputCommodityItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "commodity_item_id", referencedColumnName = "id")
    private CommodityItem commodityItem;
    @OneToOne
    @JoinColumn(name = "techno_map_stages_id", referencedColumnName = "id")
    private TechnoMapStagesEntity technoMapStagesEntity;

    public InputCommodityItem() {
    }

    public InputCommodityItem(CommodityItem commodityItem, TechnoMapStagesEntity technoMapStagesEntity) {
        this.commodityItem = commodityItem;
        this.technoMapStagesEntity = technoMapStagesEntity;
    }
}
