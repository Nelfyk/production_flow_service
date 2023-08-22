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
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    private StageEntity stageEntity;

    public InputCommodityItem() {
    }

    public InputCommodityItem(CommodityItem commodityItem, StageEntity stageEntity) {
        this.commodityItem = commodityItem;
        this.stageEntity = stageEntity;
    }
}
