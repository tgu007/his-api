package lukelin.his.dto.Inventory.resp.medicine;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.dto.basic.resp.entity.MedicineRespDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineUsageRespDto {

    @JsonIgnore
    private Medicine medicineEntity;

    private MedicineRespDto medicine;

    private BigDecimal usageQuantity;

    private String usageQuantityInfo;

    private BigDecimal stockQuantity;

    private String stockQuantityInfo;

    public BigDecimal getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(BigDecimal stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getStockQuantityInfo() {
        return stockQuantityInfo;
    }

    public void setStockQuantityInfo(String stockQuantityInfo) {
        this.stockQuantityInfo = stockQuantityInfo;
    }


    public Medicine getMedicineEntity() {
        return medicineEntity;
    }

    public void setMedicineEntity(Medicine medicineEntity) {
        this.medicineEntity = medicineEntity;
    }

    public MedicineRespDto getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineRespDto medicine) {
        this.medicine = medicine;
    }

    public BigDecimal getUsageQuantity() {
        return usageQuantity;
    }

    public void setUsageQuantity(BigDecimal usageQuantity) {
        this.usageQuantity = usageQuantity;
    }

    public String getUsageQuantityInfo() {
        return usageQuantityInfo;
    }

    public void setUsageQuantityInfo(String usageQuantityInfo) {
        this.usageQuantityInfo = usageQuantityInfo;
    }
}