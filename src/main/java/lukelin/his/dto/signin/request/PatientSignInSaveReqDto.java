package lukelin.his.dto.signin.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.codeEntity.FromHospital;
import lukelin.his.domain.entity.patient_sign_in.DrgGroup;
import lukelin.his.domain.entity.patient_sign_in.Patient;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.dto.yb.resp.SignInRespDto;

import java.math.BigDecimal;
import java.util.*;

public class PatientSignInSaveReqDto {
    private UUID uuid;

    private UUID patientId;

    private Integer signInMethodId;

    private Integer nursingLevelId;

    private BigDecimal owingLimit;

    private Integer patientConditionId;

    private UUID departmentId;

    private UUID doctorId;

    private Integer insuranceTypeId;

    private String insuranceNumber;

    //YB SignIn
    private SignInRespDto ybSignIn;

    private String reference;

    private UUID drgGroupId;

    private UUID fromHospitalId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signInDate;

    private Integer medTypeId;

    private Integer insuranceAreaId;

    public Integer getInsuranceAreaId() {
        return insuranceAreaId;
    }

    public void setInsuranceAreaId(Integer insuranceAreaId) {
        this.insuranceAreaId = insuranceAreaId;
    }

    public Integer getMedTypeId() {
        return medTypeId;
    }

    public void setMedTypeId(Integer medTypeId) {
        this.medTypeId = medTypeId;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public UUID getFromHospitalId() {
        return fromHospitalId;
    }

    public void setFromHospitalId(UUID fromHospitalId) {
        this.fromHospitalId = fromHospitalId;
    }

    public UUID getDrgGroupId() {
        return drgGroupId;
    }

    public void setDrgGroupId(UUID drgGroupId) {
        this.drgGroupId = drgGroupId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public SignInRespDto getYbSignIn() {
        return ybSignIn;
    }

    public void setYbSignIn(SignInRespDto ybSignIn) {
        this.ybSignIn = ybSignIn;
    }

    private List<UUID> diseaseIdList;

    public List<UUID> getDiseaseIdList() {
        return diseaseIdList;
    }

    public void setDiseaseIdList(List<UUID> diseaseIdList) {
        this.diseaseIdList = diseaseIdList;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Integer getSignInMethodId() {
        return signInMethodId;
    }

    public void setSignInMethodId(Integer signInMethodId) {
        this.signInMethodId = signInMethodId;
    }

    public Integer getNursingLevelId() {
        return nursingLevelId;
    }

    public void setNursingLevelId(Integer nursingLevelId) {
        this.nursingLevelId = nursingLevelId;
    }

    public BigDecimal getOwingLimit() {
        return owingLimit;
    }

    public void setOwingLimit(BigDecimal owingLimit) {
        this.owingLimit = owingLimit;
    }

    public Integer getPatientConditionId() {
        return patientConditionId;
    }

    public void setPatientConditionId(Integer patientConditionId) {
        this.patientConditionId = patientConditionId;
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

    public Integer getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(Integer insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void copyValue(PatientSignIn patientSignIn) {
        BeanUtils.copyPropertiesIgnoreNull(this, patientSignIn);
        patientSignIn.setPatient(Ebean.find(Patient.class, this.getPatientId()));
        patientSignIn.setSignInMethod(Ebean.find(Dictionary.class, this.getSignInMethodId()));
        patientSignIn.setNursingLevel(Ebean.find(Dictionary.class, this.getNursingLevelId()));
        patientSignIn.setPatientCondition(Ebean.find(Dictionary.class, this.getPatientConditionId()));
        patientSignIn.setDepartmentTreatment(Ebean.find(DepartmentTreatment.class, this.getDepartmentId()));
        patientSignIn.setDoctor(Ebean.find(Employee.class, this.getDoctorId()));
        patientSignIn.setInsuranceType(Ebean.find(Dictionary.class, this.getInsuranceTypeId()));
        patientSignIn.setMedType(Ebean.find(Dictionary.class, this.getMedTypeId()));
       // patientSignIn.setInsuranceArea(Ebean.find(Dictionary.class, this.getInsuranceAreaId()));
        Set<Diagnose> diagnoseSet = new HashSet<>();
        for (UUID diagnoseUuid : this.getDiseaseIdList()) {
            diagnoseSet.add(Ebean.find(Diagnose.class, diagnoseUuid));
        }
        patientSignIn.setDiagnoseSet(diagnoseSet);
        if (this.drgGroupId != null)
            patientSignIn.setDrgGroup(Ebean.find(DrgGroup.class, this.getDrgGroupId()));

        if (this.fromHospitalId != null)
            patientSignIn.setFromHospital(Ebean.find(FromHospital.class, this.getFromHospitalId()));
    }

}
