package lukelin.his.domain.entity.prescription;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionMedicineDto;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionTreatmentDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "prescription.prescription_predefined_medicine")
public class PreDefinedPrescriptionMedicine extends BaseEntity implements DtoConvertible<PreDefinedPrescriptionMedicineDto> {

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private PreDefinedPrescription preDefinedGroup;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

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

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @Override
    public PreDefinedPrescriptionMedicineDto toDto() {
        PreDefinedPrescriptionMedicineDto dto = DtoUtils.convertRawDto(this);
        dto.setFrequency(this.getFrequency().toDto());
        dto.setMedicine(this.getMedicine().toDto());
        //dto.setGroupId(this.getPreDefinedGroup().getUuid());
        return dto;
    }

    public Prescription toNewPrescription(PatientSignIn patientSignIn, Integer totalNumber) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionType(PrescriptionType.Medicine);
        prescription.setOneOff(this.getPreDefinedGroup().isOneOff());
        prescription.setStatus(PrescriptionStatus.created);
        prescription.setPatientSignIn(patientSignIn);
        prescription.setDescription(this.getMedicine().getName());
        prescription.setDepartment(patientSignIn.getDepartmentTreatment());
        prescription.setStartDate(new Date());

        PrescriptionChargeable prescriptionChargeable = new PrescriptionChargeable();
        prescription.setPrescriptionChargeable(prescriptionChargeable);
        prescriptionChargeable.setFirstDayQuantity(this.getFirstDayQuantity());
        prescriptionChargeable.setFrequency(this.getFrequency());
        //prescriptionChargeable.setQuantity(this.getQuantity());

        PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
        prescriptionChargeable.setPrescriptionMedicine(prescriptionMedicine);
        prescriptionMedicine.setMedicine(this.getMedicine());
        prescriptionMedicine.setMedicineSnapshot(this.getMedicine().findLatestSnapshot());
        prescriptionMedicine.setServeQuantity(this.getQuantity());
        prescriptionChargeable.setQuantity(this.getQuantity().divide(this.getMedicine().getServeToMinRate(), 0, RoundingMode.CEILING));
        if (prescription.isOneOff()) {
            prescriptionMedicine.setFixedChineseMedicineQuantity(null);
            prescriptionMedicine.setFixedQuantity(new BigDecimal(totalNumber));
            if (prescriptionMedicine.getMedicine().chineseMedicine()) {
                prescriptionMedicine.setFixedChineseMedicineQuantity(new BigDecimal(totalNumber));
                //总数量帖数乘以数量
                prescriptionMedicine.setFixedQuantity(prescriptionMedicine.getFixedChineseMedicineQuantity().multiply(prescriptionChargeable.getQuantity()).setScale(2, RoundingMode.CEILING));
            }
        }

        //todo refactor later
        Dictionary useMethod = Ebean.find(Dictionary.class).where().eq("group.code", "用药途径").eq("code", "煎服").findOne();
        prescriptionMedicine.setUseMethod(useMethod);
        return prescription;
    }
}
