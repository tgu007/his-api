package lukelin.his.dto.Inventory.resp.item;

import lukelin.his.dto.Inventory.resp.BasePartialOrderListRespDto;

public class ItemPartialOrderListRespDto extends BasePartialOrderListRespDto {
    private ItemOrderListRespDto masterOrder;

    public ItemOrderListRespDto getMasterOrder() {
        return masterOrder;
    }

    public void setMasterOrder(ItemOrderListRespDto masterOrder) {
        this.masterOrder = masterOrder;
    }
}
