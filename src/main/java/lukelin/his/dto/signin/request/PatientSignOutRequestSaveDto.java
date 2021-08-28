package lukelin.his.dto.signin.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.patient_sign_in.PatientSignOutRequest;
import lukelin.his.domain.enums.PatientSignIn.PatientSignOutStatus;

import java.util.*;

public class PatientSignOutRequestSaveDto {
    private UUID patientSignInId;

    private UUID uuid;

    private Integer reasonId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:", timezone = "GMT+8")
    private Date signOutDate;

    private String advise;

    private String reference;

    private UUID departmentId;

    private UUID doctorId;

    private List<UUID> diseaseIdList;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public List<UUID> getDiseaseIdList() {
        return diseaseIdList;
    }

    public void setDiseaseIdList(List<UUID> diseaseIdList) {
        this.diseaseIdList = diseaseIdList;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public Date getSignOutDate() {
        return signOutDate;
    }

    public void setSignOutDate(Date signOutDate) {
        this.signOutDate = signOutDate;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public PatientSignOutRequest toEntity() {
        PatientSignOutRequest request = null;
        if (this.getUuid() != null)
            request = Ebean.find(PatientSignOutRequest.class, this.getUuid());
        else {
            request = new PatientSignOutRequest();
            request.setStatus(PatientSignOutStatus.created);
        }
        BeanUtils.copyPropertiesIgnoreNull(this, request);
        request.setPatientSignIn(Ebean.find(PatientSignIn.class, this.getPatientSignInId()));
        request.setSignOutReason(Ebean.find(Dictionary.class, this.getReasonId()));
        request.setDepartmentTreatment(Ebean.find(DepartmentTreatment.class, this.getDepartmentId()));
        request.setDoctor(Ebean.find(Employee.class, this.getDoctorId()));
        Set<Diagnose> diagnoseSet = new HashSet<>();
        for (UUID diagnoseUuid : this.getDiseaseIdList()) {
            // Diagnose diagnose = this.findById(Diagnose.class, diagnoseUuid);
            diagnoseSet.add(Ebean.find(Diagnose.class, diagnoseUuid));
        }
        request.setDiagnoseSet(diagnoseSet);
        return request;
    }
}
