package lukelin.his.dto.prescription.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionChargeable;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PrescriptionChargeableSaveDto {
    private UUID uuid;


    private Integer frequencyId;

    private BigDecimal quantity;

    private BigDecimal firstDayQuantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "manual_start_date")
    private Date manualStartDate;

    private PrescriptionSaveDto prescriptionSaveDto;

    public Date getManualStartDate() {
        return manualStartDate;
    }

    public void setManualStartDate(Date manualStartDate) {
        this.manualStartDate = manualStartDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public PrescriptionSaveDto getPrescriptionSaveDto() {
        return prescriptionSaveDto;
    }

    public void setPrescriptionSaveDto(PrescriptionSaveDto prescriptionSaveDto) {
        this.prescriptionSaveDto = prescriptionSaveDto;
    }


    public Integer getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Integer frequencyId) {
        this.frequencyId = frequencyId;
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

    public Prescription toEntity() {

        PrescriptionChargeable prescriptionChargeable = new PrescriptionChargeable();
        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionChargeable);
        if (this.getUuid() != null && prescriptionChargeable.hasFeeRecorded(this.getPrescriptionSaveDto().getUuid()))
            throw new ApiValidationException("prescription.error.hasFeeRecorded");

        Prescription prescription = this.prescriptionSaveDto.toEntity();
        prescription.setPrescriptionChargeable(prescriptionChargeable);

        //prescriptionChargeable.setPrescription(this.prescriptionSaveDto.toEntity());
        //补录医嘱，手动设定时间
//        if (this.getManualStartDate() != null)
//            prescription.setOrderDate(this.getManualStartDate());
        prescriptionChargeable.setFrequency(new Dictionary(this.getFrequencyId()));
        return prescription;
    }
}
