package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BaseOrderRequestLineRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineRespDto;

public class MedicineOrderRequestLineRespDto extends BaseOrderRequestLineRespDto {
    private String lastPeriodUsage;

    public String getLastPeriodUsage() {
        return lastPeriodUsage;
    }

    public void setLastPeriodUsage(String lastPeriodUsage) {
        this.lastPeriodUsage = lastPeriodUsage;
    }
}
