package lukelin.his.dto.signin.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.dto.basic.resp.setup.FromHospitalRespDto;
import lukelin.his.dto.yb.SignInRecordRespDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PatientSignInListRespDto {
    private UUID uuid;

    private String patientName;

    private String signInNumber;

    private String department;

    private String Doctor;

    private String InsuranceType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signInDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signOutDate;

    private Integer age;

    private PatientSignInStatus status;

    private String currentBed;

    private UUID createdById;

    private Boolean selfPay;

    private Boolean hasCardInfo;

    private Boolean ybSignedIn;

    private String ybId;

    private Boolean pendingFeeToUpload;

    private String invoiceNumber;

    private SignInRecordRespDto ybSignInRecord;

    private FromHospitalRespDto fromHospital;

    private String gender;


    private String mainDiagnose;

    private BigDecimal totalFee;

    private String reference;

    private Integer signInDays;

    public Integer getSignInDays() {
        return signInDays;
    }

    public void setSignInDays(Integer signInDays) {
        this.signInDays = signInDays;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getMainDiagnose() {
        return mainDiagnose;
    }

    public void setMainDiagnose(String mainDiagnose) {
        this.mainDiagnose = mainDiagnose;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public FromHospitalRespDto getFromHospital() {
        return fromHospital;
    }

    public void setFromHospital(FromHospitalRespDto fromHospital) {
        this.fromHospital = fromHospital;
    }

    public SignInRecordRespDto getYbSignInRecord() {
        return ybSignInRecord;
    }

    public void setYbSignInRecord(SignInRecordRespDto ybSignInRecord) {
        this.ybSignInRecord = ybSignInRecord;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Boolean getPendingFeeToUpload() {
        return pendingFeeToUpload;
    }

    public void setPendingFeeToUpload(Boolean pendingFeeToUpload) {
        this.pendingFeeToUpload = pendingFeeToUpload;
    }

    public String getYbId() {
        return ybId;
    }

    public void setYbId(String ybId) {
        this.ybId = ybId;
    }

    public Boolean getYbSignedIn() {
        return ybSignedIn;
    }

    public void setYbSignedIn(Boolean ybSignedIn) {
        this.ybSignedIn = ybSignedIn;
    }

    public Boolean getHasCardInfo() {
        return hasCardInfo;
    }

    public void setHasCardInfo(Boolean hasCardInfo) {
        this.hasCardInfo = hasCardInfo;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public Date getSignOutDate() {
        return signOutDate;
    }

    public void setSignOutDate(Date signOutDate) {
        this.signOutDate = signOutDate;
    }

    public String getCurrentBed() {
        return currentBed;
    }

    public void setCurrentBed(String currentBed) {
        this.currentBed = currentBed;
    }

    public PatientSignInStatus getStatus() {
        return status;
    }

    public void setStatus(PatientSignInStatus status) {
        this.status = status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSignInNumber() {
        return signInNumber;
    }

    public void setSignInNumber(String signInNumber) {
        this.signInNumber = signInNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getInsuranceType() {
        return InsuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        InsuranceType = insuranceType;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

}
