package lukelin.his.dto.Inventory.req.item;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.inventory.item.*;
import lukelin.his.dto.Inventory.req.BasePartialOrderSaveDto;

import java.util.ArrayList;
import java.util.List;

public class ItemPartialOrderSaveDto extends BasePartialOrderSaveDto {
    private List<ItemPartialOrderLineSaveDto> orderLineList;

    public List<ItemPartialOrderLineSaveDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<ItemPartialOrderLineSaveDto> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public ItemPartialOrder toEntity() {
        ItemPartialOrder itemPartialOrder = new ItemPartialOrder();
        BeanUtils.copyPropertiesIgnoreNull(this, itemPartialOrder);
        List<ItemPartialOrderLine> lineList = new ArrayList<>();
        for (ItemPartialOrderLineSaveDto itemPartialOrderLineSaveDto : this.getOrderLineList())
            lineList.add(itemPartialOrderLineSaveDto.toEntity());
        itemPartialOrder.setLineList(lineList);
        itemPartialOrder.setMasterOrder(new ItemOrder(this.getMasterOrderId()));
        return itemPartialOrder;
    }
}
