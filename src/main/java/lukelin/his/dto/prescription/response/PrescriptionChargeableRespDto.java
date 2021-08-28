package lukelin.his.dto.prescription.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PrescriptionChargeableRespDto {
    private UUID uuid;

    private DictionaryDto frequency;

    private BigDecimal quantity;

    private BigDecimal firstDayQuantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date manualStartDate;

    private PrescriptionRespDto prescription;

    public PrescriptionRespDto getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionRespDto prescription) {
        this.prescription = prescription;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Date getManualStartDate() {
        return manualStartDate;
    }

    public void setManualStartDate(Date manualStartDate) {
        this.manualStartDate = manualStartDate;
    }
}
