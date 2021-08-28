package lukelin.his.dto.Inventory.req.item;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.inventory.item.ItemTransfer;
import lukelin.his.domain.entity.inventory.item.ItemTransferLine;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.dto.Inventory.req.BaseTransferSaveDto;

import java.util.ArrayList;
import java.util.List;

public class ItemTransferSaveDto extends BaseTransferSaveDto {

    private List<ItemTransferLineSaveDto> transferLineList;

    public List<ItemTransferLineSaveDto> getTransferLineList() {
        return transferLineList;
    }

    public void setTransferLineList(List<ItemTransferLineSaveDto> transferLineList) {
        this.transferLineList = transferLineList;
    }

    public ItemTransfer toEntity() {
        ItemTransfer itemTransfer = new ItemTransfer();
        BeanUtils.copyPropertiesIgnoreNull(this, itemTransfer);
        itemTransfer.setToWarehouse(new DepartmentWarehouse(this.getToWarehouseId()));
        itemTransfer.setFromWarehouse(new DepartmentWarehouse(this.getFromWarehouseId()));
        List<ItemTransferLine> lineList = new ArrayList<>();
        for (ItemTransferLineSaveDto itemTransferLineSaveDto : this.transferLineList)
            lineList.add(itemTransferLineSaveDto.toEntity());
        itemTransfer.setLineList(lineList);
        return itemTransfer;
    }
}
