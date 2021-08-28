package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.Interfaces.Inventory.CachedEntityStockInterface;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderStockRespDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "inventory.cached_medicine_stock")
public class CachedMedicineStock extends BaseCachedMedicineInventoryTransaction implements DtoConvertible<MedicineOrderStockRespDto>, CachedEntityStockInterface {


    @Override
    public MedicineOrderStockRespDto toDto() {
        MedicineOrderStockRespDto dto = new MedicineOrderStockRespDto();
        super.setStockDetailProperty(dto, this.getMedicine());
        dto.setOriginPurchaseOrderLine(this.getOriginPurchaseLine().toDto());
        dto.setTotalCost(this.getMedicine().calculateStockCost(this));
        return dto;
    }

    public void updateFromTransaction(CachedMedicineTransaction medicineTransaction) {
        super.updateFromTransaction(medicineTransaction);
        this.setMedicine(medicineTransaction.getMedicine());
    }

}
