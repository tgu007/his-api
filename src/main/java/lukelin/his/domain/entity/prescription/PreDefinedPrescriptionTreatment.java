package lukelin.his.domain.entity.prescription;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionDto;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionTreatmentDto;
import sun.dc.pr.PRError;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "prescription.prescription_predefined_treatment")
public class PreDefinedPrescriptionTreatment extends BaseEntity implements DtoConvertible<PreDefinedPrescriptionTreatmentDto> {

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private PreDefinedPrescription preDefinedGroup;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @ManyToOne
    @JoinColumn(name = "frequency_id", nullable = false)
    private Dictionary frequency;

    private BigDecimal quantity;

    @Column(name = "quantity_first_day")
    private BigDecimal firstDayQuantity;

    public PreDefinedPrescription getPreDefinedGroup() {
        return preDefinedGroup;
    }

    public void setPreDefinedGroup(PreDefinedPrescription preDefinedGroup) {
        this.preDefinedGroup = preDefinedGroup;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
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

    @Override
    public PreDefinedPrescriptionTreatmentDto toDto() {
        PreDefinedPrescriptionTreatmentDto dto = DtoUtils.convertRawDto(this);
        dto.setFrequency(this.getFrequency().toDto());
        dto.setTreatment(this.getTreatment().toDto());
        //dto.setGroupId(this.getPreDefinedGroup().getUuid());
        return dto;
    }

    public Prescription toNewPrescription(PatientSignIn patientSignIn) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionType(PrescriptionType.Treatment);
        prescription.setOneOff(false);
        prescription.setStatus(PrescriptionStatus.created);
        prescription.setPatientSignIn(patientSignIn);
        prescription.setDescription(this.getTreatment().getName());
        if (this.getTreatment().getDefaultExecuteDepartment() != null)
            prescription.setDepartment(this.getTreatment().getDefaultExecuteDepartment());
        else
            prescription.setDepartment(patientSignIn.getDepartmentTreatment());

        PrescriptionChargeable prescriptionChargeable = new PrescriptionChargeable();
        prescriptionChargeable.setFirstDayQuantity(this.getFirstDayQuantity());
        prescriptionChargeable.setFrequency(this.getFrequency());
        prescriptionChargeable.setQuantity(this.getQuantity());


        PrescriptionTreatment prescriptionTreatment = new PrescriptionTreatment();
        prescriptionTreatment.setTreatment(this.getTreatment());
        prescriptionTreatment.setTreatmentSnapshot(this.getTreatment().findLatestSnapshot());
        prescriptionChargeable.setPrescriptionTreatment(prescriptionTreatment);
        prescription.setPrescriptionChargeable(prescriptionChargeable);
        return prescription;
    }
}
