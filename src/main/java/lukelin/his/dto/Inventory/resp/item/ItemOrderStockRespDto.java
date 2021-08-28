package lukelin.his.dto.Inventory.resp.item;

import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;
import lukelin.his.dto.basic.resp.entity.ItemRespDto;

public class ItemOrderStockRespDto extends BaseStockSummaryRespDto {

    private ItemOrderLineRespDto originPurchaseOrderLine;

    public ItemOrderLineRespDto getOriginPurchaseOrderLine() {
        return originPurchaseOrderLine;
    }

    public void setOriginPurchaseOrderLine(ItemOrderLineRespDto originPurchaseOrderLine) {
        this.originPurchaseOrderLine = originPurchaseOrderLine;
    }


}
