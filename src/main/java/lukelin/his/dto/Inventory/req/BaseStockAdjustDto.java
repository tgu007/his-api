package lukelin.his.dto.Inventory.req;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.inventory.BaseStockAdjustment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BaseStockAdjustDto {
    private UUID inventoryEntityId;

    private UUID warehouseId;

    private BigDecimal newQuantity;

    private BigDecimal oldQuantity;

    private String reference;

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigDecimal getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(BigDecimal newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public UUID getInventoryEntityId() {
        return inventoryEntityId;
    }

    public void setInventoryEntityId(UUID inventoryEntityId) {
        this.inventoryEntityId = inventoryEntityId;
    }

    public BigDecimal getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(BigDecimal oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    protected void updateEntityValue(BaseStockAdjustment newAdjustment) {
        BeanUtils.copyPropertiesIgnoreNull(this, newAdjustment);
        newAdjustment.setWarehouse(new DepartmentWarehouse(this.getWarehouseId()));
    }

    public boolean quantityChanged() {
        return this.getNewQuantity().subtract(this.getOldQuantity()) != BigDecimal.ZERO;
    }
}
