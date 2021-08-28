package lukelin.his.dto.Inventory.req.filter;

import lukelin.his.dto.basic.SearchCodeDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StockFilterDto extends SearchCodeDto {
    private UUID inventorEntityId;

    private List<UUID> warehouseIdList;

    private Date expireDate;

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public List<UUID> getWarehouseIdList() {
        return warehouseIdList;
    }

    public void setWarehouseIdList(List<UUID> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }

    public UUID getInventorEntityId() {
        return inventorEntityId;
    }

    public void setInventorEntityId(UUID inventorEntityId) {
        this.inventorEntityId = inventorEntityId;
    }
}
