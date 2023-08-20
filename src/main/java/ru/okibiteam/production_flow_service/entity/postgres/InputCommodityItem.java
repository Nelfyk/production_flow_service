package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "input_commodity_items")
@Data
public class InputCommodityItem {
    @EmbeddedId
    private InputCommodityItemId id;
    @OneToOne
    @JoinColumn(name = "commodity_item_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CommodityItem commodityItem;
    @OneToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Stages stages;
}
