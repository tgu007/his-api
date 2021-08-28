package lukelin.his.dto.Inventory.req.filter;

import lukelin.his.dto.basic.SearchCodeDto;

import java.util.List;
import java.util.UUID;

public class MedicineStockFilterDto extends StockFilterDto {
    private UUID medicineId;

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

}
