package lukelin.his.dto.basic.req.filter;

public class ItemFilter extends InventoryEntityFilter {
    private Integer warehouseTypeId;

    private Boolean chargeableItem;


    public Boolean getChargeableItem() {
        return chargeableItem;
    }

    public void setChargeableItem(Boolean chargeableItem) {
        this.chargeableItem = chargeableItem;
    }

    public Integer getWarehouseTypeId() {
        return warehouseTypeId;
    }

    public void setWarehouseTypeId(Integer warehouseTypeId) {
        this.warehouseTypeId = warehouseTypeId;
    }
}
