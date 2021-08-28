package lukelin.his.dto.Inventory.resp.item;

import lukelin.his.dto.Inventory.resp.BaseOrderRespDto;

public class ItemOrderRespDto extends BaseOrderRespDto {
    private ItemOrderRequestListRespDto orderRequest;

    public ItemOrderRequestListRespDto getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(ItemOrderRequestListRespDto orderRequest) {
        this.orderRequest = orderRequest;
    }
}
