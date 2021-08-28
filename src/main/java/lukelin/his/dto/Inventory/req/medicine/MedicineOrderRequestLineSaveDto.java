package lukelin.his.dto.Inventory.req.medicine;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderRequestLine;
import lukelin.his.dto.Inventory.req.BaseOrderRequestLineSaveDto;

import java.util.UUID;

public class MedicineOrderRequestLineSaveDto extends BaseOrderRequestLineSaveDto {
    private UUID medicineId;

    private String lastPeriodUsage;

    public String getLastPeriodUsage() {
        return lastPeriodUsage;
    }

    public void setLastPeriodUsage(String lastPeriodUsage) {
        this.lastPeriodUsage = lastPeriodUsage;
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public MedicineOrderRequestLine toEntity() {
        MedicineOrderRequestLine orderRequestLine = new MedicineOrderRequestLine();
        super.setRequestLineProperty(orderRequestLine);
        BeanUtils.copyPropertiesIgnoreNull(this, orderRequestLine);
        orderRequestLine.setMedicine(new Medicine(this.getMedicineId()));
        return orderRequestLine;
    }

}
