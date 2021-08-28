package lukelin.his.domain.entity.prescription;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.basic.entity.TreatmentSnapshot;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.dto.prescription.response.PrescriptionListRespDto;
import lukelin.his.dto.prescription.response.PrescriptionNursingCardRespDto;
import lukelin.his.dto.prescription.response.PrescriptionPatientTreatmentNursingCardRespDto;
import lukelin.his.dto.prescription.response.PrescriptionTreatmentRespDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "prescription.prescription_treatment")
public class PrescriptionTreatment extends BaseEntity implements DtoConvertible<PrescriptionTreatmentRespDto> {
    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @ManyToOne
    @JoinColumn(name = "treatment_snapshot_id")
    private TreatmentSnapshot treatmentSnapshot;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_chargeable_id")
    private PrescriptionChargeable prescriptionChargeable;

    @Column(name = "last_execution_date")
    private Date lastExecutionTime;

    @Transient
    private Integer allowedExecutionCount;


    @Column(name = " adjust_quantity")
    private BigDecimal adjustQuantity;

    public BigDecimal getAdjustQuantity() {
        return adjustQuantity;
    }

    public void setAdjustQuantity(BigDecimal adjustQuantity) {
        this.adjustQuantity = adjustQuantity;
    }


    public Integer getAllowedExecutionCount() {
        return allowedExecutionCount;
    }

    public void setAllowedExecutionCount(Integer allowedExecutionCount) {
        this.allowedExecutionCount = allowedExecutionCount;
    }

    public Date getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(Date lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }


    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public TreatmentSnapshot getTreatmentSnapshot() {
        return treatmentSnapshot;
    }

    public void setTreatmentSnapshot(TreatmentSnapshot treatmentSnapshot) {
        this.treatmentSnapshot = treatmentSnapshot;
    }

    public PrescriptionChargeable getPrescriptionChargeable() {
        return prescriptionChargeable;
    }

    public void setPrescriptionChargeable(PrescriptionChargeable prescriptionChargeable) {
        this.prescriptionChargeable = prescriptionChargeable;
    }

    public PrescriptionListRespDto copyValueToListDto(PrescriptionListRespDto prescriptionListRespDto) {
        // TreatmentSnapshot treatmentSnapshot = this.getTreatmentSnapshot();
        PrescriptionChargeable prescriptionChargeable = this.getPrescriptionChargeable();

        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionListRespDto);
        prescriptionListRespDto.setQuantityInfo(prescriptionChargeable.getQuantity().toString() + this.getTreatment().getMinUom().getName());
        if (prescriptionChargeable.getFirstDayQuantity() != null)
            prescriptionListRespDto.setFirstDayQuantityInfo(prescriptionChargeable.getFirstDayQuantity().setScale(0).toString());
        prescriptionListRespDto.setUnitPrice(this.getTreatment().getListPrice());
        prescriptionListRespDto.setDescription(this.getTreatment().getName());
        prescriptionListRespDto.setPrescriptionDetailId(this.getUuid());
        if (this.getAdjustQuantity() != null)
            prescriptionListRespDto.setAdjustQuantity(this.getAdjustQuantity());

        if (this.getTreatment().getExecuteDepartmentType() == DepartmentTreatmentType.jianyan)
            prescriptionListRespDto.setLabTest(true);

        if (this.getTreatment().getLabTreatmentType() != null)
            prescriptionListRespDto.setSampleType(this.getTreatment().getLabSampleType().getName());
        return prescriptionListRespDto;
    }

    public PrescriptionTreatmentRespDto toDto() {
        PrescriptionTreatmentRespDto respDto = DtoUtils.convertRawDto(this);
        respDto.setTreatment(this.getTreatmentSnapshot().getTreatment().toDto());
        respDto.setPrescriptionChargeable(this.getPrescriptionChargeable().toDto());
        return respDto;
    }

    public PrescriptionTreatment toCloneEntity() {
        PrescriptionTreatment prescriptionTreatment = new PrescriptionTreatment();
        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionTreatment);
        prescriptionTreatment.setUuid(null);
        prescriptionTreatment.setPrescriptionChargeable(null);
        prescriptionTreatment.setLastExecutionTime(null);
        // prescriptionTreatment.setTreatment(this.getTreatment());
        prescriptionTreatment.setTreatmentSnapshot(this.treatment.findLatestSnapshot());

        return prescriptionTreatment;
    }

    public void setFeeValue(Fee newFee) {
        //TreatmentSnapshot latestSnapshot = this.getTreatment().findLatestSnapshot();
        this.getTreatment().setFeeValue(newFee);

    }

    public PrescriptionNursingCardRespDto toTreatmentCardDto() {
        PrescriptionNursingCardRespDto dto = new PrescriptionNursingCardRespDto();
        dto.setPrescriptionId(this.getPrescriptionChargeable().getPrescription().getUuid());
        dto.setFrequency(this.getPrescriptionChargeable().getFrequency().getCode());
        dto.setEntityName(this.getTreatment().getName());
        dto.setQuantityInfo(prescriptionChargeable.getQuantity().toString() + this.getTreatment().getMinUom().getName());
        Prescription prescription = this.getPrescriptionChargeable().getPrescription();
        if (prescription.getPrescriptionGroup() != null)
            dto.setGroupId(prescription.getUuid());
        dto.setReference(prescription.getReference());
        dto.setSelfMedicine(false);
        if (prescriptionChargeable.getFirstDayQuantity() != null)
            dto.setFirstDayQuantityInfo(prescription.getPrescriptionChargeable().getFirstDayQuantity().setScale(0).toString());

        if (this.treatment.getExecuteDepartmentType() == DepartmentTreatmentType.jianyan) {
            Dictionary labTreatmentType = this.getTreatment().getLabTreatmentType();
            Dictionary labSampleType = this.getTreatment().getLabSampleType();
            if (labTreatmentType != null)
                dto.setBottleColour(labTreatmentType.getExtraInfo());
            if (labSampleType != null)
                dto.setSampleType(labSampleType.getName());
        }
        return dto;
    }

    public PrescriptionPatientTreatmentNursingCardRespDto toPatientTreatmentCardDto() {
        PrescriptionPatientTreatmentNursingCardRespDto dto = new PrescriptionPatientTreatmentNursingCardRespDto();
        dto.setPrescriptionId(this.getPrescriptionChargeable().getPrescription().getUuid());
        dto.setFrequency(this.getPrescriptionChargeable().getFrequency().getCode());
        dto.setQuantityInfo(prescriptionChargeable.getQuantity().toString() + this.getTreatment().getMinUom().getName());
        Prescription prescription = this.getPrescriptionChargeable().getPrescription();
        dto.setReference(prescription.getReference());
        if (prescriptionChargeable.getFirstDayQuantity() != null)
            dto.setFirstDayQuantityInfo(prescription.getPrescriptionChargeable().getFirstDayQuantity().setScale(0).toString());
        PatientSignIn patientSignIn = this.getPrescriptionChargeable().getPrescription().getPatientSignIn();
        dto.setPatientInfo(patientSignIn.getPatient().getName());
        dto.setPatientSignInId(patientSignIn.getUuid());
        return dto;
    }
}
