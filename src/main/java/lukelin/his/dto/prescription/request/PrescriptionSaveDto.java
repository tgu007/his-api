package lukelin.his.dto.prescription.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.PrescriptionSaveDtoInterface;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;

import java.util.Date;
import java.util.UUID;

public class PrescriptionSaveDto implements PrescriptionSaveDtoInterface {
    private UUID uuid;

    private PrescriptionType prescriptionType;

    private String reference;

    @JsonProperty("isOneOff")
    private boolean isOneOff;

    private UUID patientSignInId;

    private PrescriptionStatus status;

    private UUID departmentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date manualStartDate;

    public Date getManualStartDate() {
        return manualStartDate;
    }

    public void setManualStartDate(Date manualStartDate) {
        this.manualStartDate = manualStartDate;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public PrescriptionType getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(PrescriptionType prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isOneOff() {
        return isOneOff;
    }

    public void setOneOff(boolean oneOff) {
        isOneOff = oneOff;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Prescription toEntity() {
        Prescription prescription = new Prescription();
//        if (this.getUuid() == null)
//            prescription.setOrderDate(new Date());
        BeanUtils.copyPropertiesIgnoreNull(this, prescription);
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, this.getPatientSignInId());
        prescription.setPatientSignIn(patientSignIn);
        prescription.setDepartment(new DepartmentTreatment(this.departmentId));
        prescription.setDescription(this.getReference());
//        if (this.getManualStartDate() != null) {
//            if (this.getManualStartDate().before(patientSignIn.getSignInDate()))
//                throw new ApiValidationException("开嘱日不能早于入院日期");
//            prescription.setOrderDate(this.getManualStartDate());
//        }
        return prescription;
    }
}
