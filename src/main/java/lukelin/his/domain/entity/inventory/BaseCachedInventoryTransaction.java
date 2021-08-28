package lukelin.his.domain.entity.inventory;

import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;
import lukelin.his.system.NoStockException;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BaseCachedInventoryTransaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private DepartmentWarehouse warehouse;

    @Column(nullable = false, name = "min_uom_quantity")
    private BigDecimal minUomQuantity;

    public DepartmentWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(DepartmentWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getMinUomQuantity() {
        return minUomQuantity;
    }

    public void setMinUomQuantity(BigDecimal minUomQuantity) {
        this.minUomQuantity = minUomQuantity;
    }


    protected void setStockDetailProperty(BaseStockSummaryRespDto stockSummaryRespDto, InventoryEntityInterface inventoryInfo) {
        stockSummaryRespDto.setMinUomQuantity(this.getMinUomQuantity());
        stockSummaryRespDto.setWarehouse(this.getWarehouse().toDto());
        stockSummaryRespDto.setDisplayQuantity(inventoryInfo.getDisplayQuantity(this.getWarehouse().getWarehouseType(), this.getMinUomQuantity()));
        stockSummaryRespDto.setWarehouseDisplayQuantity(inventoryInfo.getDisplayQuantity(WarehouseType.levelOneWarehouse, this.getMinUomQuantity()));
    }

    protected void updateFromTransaction(BaseCachedInventoryTransaction inventoryTransaction) {
        BigDecimal quantityAfterUpdate;
        if (this.getUuid() != null)
            quantityAfterUpdate = this.getMinUomQuantity().add(inventoryTransaction.getMinUomQuantity());
        else
            quantityAfterUpdate = inventoryTransaction.getMinUomQuantity();

        if (quantityAfterUpdate.compareTo(BigDecimal.ZERO) == -1)
            throw new NoStockException("inventory.error.notLessThenZero");

        this.setMinUomQuantity(quantityAfterUpdate);
        this.setWarehouse(inventoryTransaction.getWarehouse());
    }
}
