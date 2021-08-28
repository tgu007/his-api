package lukelin.his.dto.Inventory.resp.item;

import lukelin.his.dto.Inventory.resp.BasePartialOrderRespDto;

public class ItemPartialOrderRespDto extends BasePartialOrderRespDto {
    private ItemOrderListRespDto masterOrder;

    public ItemOrderListRespDto getMasterOrder() {
        return masterOrder;
    }

    public void setMasterOrder(ItemOrderListRespDto masterOrder) {
        this.masterOrder = masterOrder;
    }
}
