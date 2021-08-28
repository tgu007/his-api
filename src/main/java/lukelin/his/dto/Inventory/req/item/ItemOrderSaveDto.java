package lukelin.his.dto.Inventory.req.item;

import lukelin.his.domain.entity.inventory.item.ItemOrder;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.inventory.item.ItemOrderRequest;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderRequest;
import lukelin.his.dto.Inventory.req.BaseOrderSaveDto;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderSaveDto extends BaseOrderSaveDto {
    private List<ItemOrderLineSaveDto> orderLineList;

    public List<ItemOrderLineSaveDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<ItemOrderLineSaveDto> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public ItemOrder toEntity() {
        ItemOrder itemOrder = new ItemOrder();
        super.setOrderProperty(itemOrder);
        List<ItemOrderLine> lineList = new ArrayList<>();
        for (ItemOrderLineSaveDto itemOrderLineSaveDto : this.getOrderLineList())
            lineList.add(itemOrderLineSaveDto.toEntity());
        itemOrder.setLineList(lineList);

        if (this.getOrderRequestId() != null)
            itemOrder.setOrderRequest(new ItemOrderRequest(this.getOrderRequestId()));
        return itemOrder;
    }
}
