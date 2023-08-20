package ru.okibiteam.production_flow_service.entity.postgres;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class InputCommodityItemId implements Serializable {
    @Column(name = "commodity_item_id")
    private Integer commodityItemId;
    @Column(name = "stage_id")
    private Integer stageId;
}
