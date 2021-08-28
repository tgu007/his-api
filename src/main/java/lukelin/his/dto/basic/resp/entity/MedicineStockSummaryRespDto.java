package lukelin.his.dto.basic.resp.entity;

import java.util.Date;

public class MedicineStockSummaryRespDto extends BaseEntityStockSummaryRespDto {
    private MedicineRespDto stockEntity;

    private String lastPeriodUsage;

    public String getLastPeriodUsage() {
        return lastPeriodUsage;
    }

    public void setLastPeriodUsage(String lastPeriodUsage) {
        this.lastPeriodUsage = lastPeriodUsage;
    }

    public MedicineRespDto getStockEntity() {
        return stockEntity;
    }

    public void setStockEntity(MedicineRespDto stockEntity) {
        this.stockEntity = stockEntity;
    }
}
