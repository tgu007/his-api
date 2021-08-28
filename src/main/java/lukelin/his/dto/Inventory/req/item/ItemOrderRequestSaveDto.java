package lukelin.his.dto.Inventory.req.item;

import lukelin.his.domain.entity.inventory.BaseOrderRequest;
import lukelin.his.domain.entity.inventory.item.ItemOrder;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.inventory.item.ItemOrderRequest;
import lukelin.his.domain.entity.inventory.item.ItemOrderRequestLine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderRequest;
import lukelin.his.dto.Inventory.req.BaseOrderRequestSaveDto;
import lukelin.his.dto.Inventory.req.BaseOrderSaveDto;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderRequestSaveDto extends BaseOrderRequestSaveDto {
    private List<ItemOrderRequestLineSaveDto> requestLineList;

    public List<ItemOrderRequestLineSaveDto> getRequestLineList() {
        return requestLineList;
    }

    public void setRequestLineList(List<ItemOrderRequestLineSaveDto> requestLineList) {
        this.requestLineList = requestLineList;
    }

    public ItemOrderRequest toEntity() {
        ItemOrderRequest itemOrderRequest = new ItemOrderRequest();
        super.setOrderRequestProperty(itemOrderRequest);
        List<ItemOrderRequestLine> lineList = new ArrayList<>();
        for (ItemOrderRequestLineSaveDto requestLineSaveDto : this.getRequestLineList())
            lineList.add(requestLineSaveDto.toEntity());
        itemOrderRequest.setLineList(lineList);

        return itemOrderRequest;
    }
}
