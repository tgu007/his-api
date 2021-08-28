package lukelin.his.domain.entity.prescription;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Frequency;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.dto.prescription.response.PrescriptionChargeableRespDto;
import lukelin.his.dto.prescription.response.PrescriptionListRespDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "prescription.prescription_chargeable")
public class PrescriptionChargeable extends BaseEntity implements DtoConvertible<PrescriptionChargeableRespDto> {


    @ManyToOne
    @JoinColumn(name = "frequency_id", nullable = false)
    private Dictionary frequency;

    private BigDecimal quantity;

    @Column(name = "quantity_first_day")
    private BigDecimal firstDayQuantity;

    @Column(name = "is_covered")
    private boolean covered = false;


    @OneToOne()
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prescriptionChargeable")
    private PrescriptionMedicine prescriptionMedicine;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prescriptionChargeable")
    private PrescriptionTreatment prescriptionTreatment;

    public PrescriptionMedicine getPrescriptionMedicine() {
        return prescriptionMedicine;
    }

    public void setPrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
        this.prescriptionMedicine = prescriptionMedicine;
    }

    public PrescriptionTreatment getPrescriptionTreatment() {
        return prescriptionTreatment;
    }

    public void setPrescriptionTreatment(PrescriptionTreatment prescriptionTreatment) {
        this.prescriptionTreatment = prescriptionTreatment;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }


    public Dictionary getFrequency() {
        return frequency;
    }

    public void setFrequency(Dictionary frequency) {
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

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }


    public void copyValueToListDto(PrescriptionListRespDto prescriptionListRespDto) {
        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionListRespDto);
        prescriptionListRespDto.setFrequency(this.getFrequency().getCode());
        prescriptionListRespDto.setFrequencyName(this.getFrequency().getName());
        prescriptionListRespDto.setPrescriptionChargeableId(this.getUuid());

        if (this.getPrescriptionMedicine() != null)
            this.getPrescriptionMedicine().copyValueToListDto(prescriptionListRespDto);
        if (this.getPrescriptionTreatment() != null)
            this.getPrescriptionTreatment().copyValueToListDto(prescriptionListRespDto);

        //prescriptionListRespDto.setAllowEdit(!this.hasFeeRecorded(this.prescription.getUuid()));
    }

    public boolean hasFeeRecorded(UUID prescriptionId) {
        //需检查医嘱是否已经产生费用
        return Ebean.find(Fee.class).where()
                .eq("prescription.uuid", prescriptionId)
                .setMaxRows(1)
                .findOneOrEmpty()
                .isPresent();

    }

    public PrescriptionChargeableRespDto toDto() {
        PrescriptionChargeableRespDto respDto = DtoUtils.convertRawDto(this);
        respDto.setFrequency(this.getFrequency().toDto());
        respDto.setPrescription(this.prescription.toDto());

        return respDto;
    }

    public PrescriptionChargeable toCloneEntity() {
        PrescriptionChargeable cloneEntity = new PrescriptionChargeable();
        BeanUtils.copyPropertiesIgnoreNull(this, cloneEntity);
        cloneEntity.setUuid(null);
        cloneEntity.setPrescription(null);
        //cloneEntity.setFrequency(this.getFrequency());
        //cloneEntity.setDepartment(this.getDepartment());
        if (this.getPrescriptionMedicine() != null)
            cloneEntity.setPrescriptionMedicine(this.getPrescriptionMedicine().toCloneEntity());
        if (this.getPrescriptionTreatment() != null)
            cloneEntity.setPrescriptionTreatment(this.getPrescriptionTreatment().toCloneEntity());

        return cloneEntity;
    }

    public void setFeeValue(Fee newFee) {
        newFee.setQuantity(this.getQuantity());
        if(this.getPrescriptionTreatment()!= null && this.getPrescriptionTreatment().getAdjustQuantity() != null)
            newFee.setQuantity(this.getPrescriptionTreatment().getAdjustQuantity());
//        if (this.getPrescriptionMedicine() != null)
//            this.getPrescriptionMedicine().setFeeValue(newFee);
        if (this.getPrescriptionTreatment() != null)
            this.getPrescriptionTreatment().setFeeValue(newFee);

    }

    public boolean executeOnPrescriptionStartDate() {
        if (this.getFirstDayQuantity() == null)
            return false;
        else
            return this.getFirstDayQuantity().compareTo(BigDecimal.ZERO) > 0;
    }

}
