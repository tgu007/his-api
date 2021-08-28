package lukelin.his.dto.Inventory.resp.item;

import lukelin.his.dto.Inventory.resp.BasePartialOrderLineRespDto;

public class ItemPartialOrderLineRespDto extends BasePartialOrderLineRespDto {
    private ItemOrderLineRespDto masterOrderLine;

    public ItemOrderLineRespDto getMasterOrderLine() {
        return masterOrderLine;
    }

    public void setMasterOrderLine(ItemOrderLineRespDto masterOrderLine) {
        this.masterOrderLine = masterOrderLine;
    }
}
