package lukelin.his.dto.Inventory.req.medicine;

import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.inventory.medicine.MedicineTransferLine;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.dto.Inventory.req.BaseTransferLineSaveDto;

import java.math.BigDecimal;
import java.util.UUID;

public class MedicineTransferLineSaveDto extends BaseTransferLineSaveDto {
    private UUID medicineId;

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public MedicineTransferLine toEntity() {
        if (this.getQuantity().compareTo(BigDecimal.ZERO) != 1)
            throw new ApiValidationException("invalid quantity");

        MedicineTransferLine transferLine = new MedicineTransferLine();
        BeanUtils.copyPropertiesIgnoreNull(this, transferLine);
        transferLine.setUom(new UnitOfMeasure(this.getUomId()));
        Medicine medicine = new Medicine(this.getMedicineId());
        transferLine.setMedicine(medicine);
        MedicineSnapshot snapshot = medicine.findLatestSnapshot();
        transferLine.setMedicineSnapshot(snapshot);
        return transferLine;
    }

}
