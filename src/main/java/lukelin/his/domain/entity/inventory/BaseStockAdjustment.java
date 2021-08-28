package lukelin.his.domain.entity.inventory;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BaseStockAdjustment extends BaseEntity {

    private String reference;

    @Column(name = "new_quantity")
    private BigDecimal newQuantity;

    @Column(name = "old_quantity")
    private BigDecimal oldQuantity;

    public String getReference() {
        return reference;
    }

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private DepartmentWarehouse warehouse;

    public DepartmentWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(DepartmentWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(BigDecimal newQuantity) {
        this.newQuantity = newQuantity;
    }

    public BigDecimal getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(BigDecimal oldQuantity) {
        this.oldQuantity = oldQuantity;
    }


}
