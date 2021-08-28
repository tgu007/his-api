package lukelin.his.dto.prescription.response;

import lukelin.his.dto.basic.resp.entity.MedicineRespDto;
import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.math.BigDecimal;
import java.util.UUID;

public class PreDefinedPrescriptionMedicineDto {

    private UUID uuid;

    private MedicineRespDto medicine;

    private DictionaryDto frequency;

    private BigDecimal quantity;

    private BigDecimal firstDayQuantity;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public MedicineRespDto getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineRespDto medicine) {
        this.medicine = medicine;
    }

    public DictionaryDto getFrequency() {
        return frequency;
    }

    public void setFrequency(DictionaryDto frequency) {
        this.frequency = frequency;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFirstDayQuantity() {
        return firstDayQuantity;
    }

    public void setFirstDayQuantity(BigDecimal firstDayQuantity) {
        this.firstDayQuantity = firstDayQuantity;
    }
}
