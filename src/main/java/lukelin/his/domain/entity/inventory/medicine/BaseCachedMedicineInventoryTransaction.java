package lukelin.his.domain.entity.inventory.medicine;

import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.inventory.BaseCachedInventoryTransaction;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseCachedMedicineInventoryTransaction extends BaseCachedInventoryTransaction {
    @ManyToOne
    @JoinColumn(name = "origin_purchase_line_id")
    private MedicineOrderLine originPurchaseLine;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public MedicineOrderLine getOriginPurchaseLine() {
        return originPurchaseLine;
    }

    public void setOriginPurchaseLine(MedicineOrderLine originPurchaseLine) {
        this.originPurchaseLine = originPurchaseLine;
    }

    protected void updateFromTransaction(BaseCachedMedicineInventoryTransaction medicineInventoryTransaction) {
        super.updateFromTransaction(medicineInventoryTransaction);
        this.setOriginPurchaseLine(medicineInventoryTransaction.getOriginPurchaseLine());
    }

    public String getStockName(){
        return medicine.getName();
    }
}
