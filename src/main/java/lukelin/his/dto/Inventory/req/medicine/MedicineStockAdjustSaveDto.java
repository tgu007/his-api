package lukelin.his.dto.Inventory.req.medicine;

import lukelin.his.domain.entity.inventory.medicine.MedicineStockAdjustment;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.dto.Inventory.req.BaseStockAdjustDto;

public class MedicineStockAdjustSaveDto extends BaseStockAdjustDto {

    public MedicineStockAdjustment toEntity() {
        MedicineStockAdjustment newAdjustment = new MedicineStockAdjustment();
        newAdjustment.setMedicine(new Medicine(this.getInventoryEntityId()));
        super.updateEntityValue(newAdjustment);
        return newAdjustment;
    }
}
