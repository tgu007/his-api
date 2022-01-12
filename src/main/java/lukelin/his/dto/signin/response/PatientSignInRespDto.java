package lukelin.his.dto.signin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.dto.basic.resp.setup.*;
import lukelin.his.dto.basic.resp.ward.PatientSignInBedDto;
import lukelin.his.dto.internal_account.FeePaymentSummaryRespDto;
import lukelin.his.dto.yb.resp.PreSettlementDto;
import lukelin.his.dto.yb.resp.SettlementDto;
import lukelin.his.dto.yb.resp.SignOutReqDto;
import lukelin.his.dto.yb_hy.Resp.SettlementResp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PatientSignInRespDto {
    private UUID uuid;

    private String signInNumberCode;

    private DictionaryDto signInMethod;

    private DictionaryDto nursingLevel;

    private BigDecimal owingLimit;

    private DictionaryDto patientCondition;

    private DepartmentTreatmentDto departmentTreatment;

    private EmployeeListDto doctor;

    private DictionaryDto insuranceType;

    private String insuranceNumber;

    private PatientSignInStatus status;

    private List<DiagnoseDto> diagnoseList;

    private Date signInDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signInDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signOutDate;

    private PatientDto patient;

    private Integer age;

    private String diagnoseString;

    private String diagnoseCodeString;

    private BigDecimal totalPaidAmount = BigDecimal.ZERO;

    private BigDecimal totalFeeAmount = BigDecimal.ZERO;

    private BigDecimal coveredFeeAmount = BigDecimal.ZERO;

    private BigDecimal pendingFeeAmount = BigDecimal.ZERO;

    private BigDecimal selfPayFeeAmount = BigDecimal.ZERO;

    private BigDecimal deductAmountFromCard = BigDecimal.ZERO;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastSettlementDate;

    private PatientSignInBedDto currentBed;

    private String currentBedInfo;

    private UUID createdById;

    private UUID cardInfoId;

    private String cardNumber;

    private Boolean selfPay;

    private Boolean ybSignedIn;

    private String ybId;

    private PatientSignOutRequestDto signOutReq;

    private String reference;

    //private SettlementDto settlement;

    private SettlementResp settlement;

    //private PreSettlementDto preSettlement;

    private SettlementResp preSettlement;


    private BigDecimal internalTotalFeeAmount = BigDecimal.ZERO;

    private BigDecimal internalTotalPaidAmount = BigDecimal.ZERO;

    private BigDecimal internalBalanceAmount = BigDecimal.ZERO;

    private DrgGroupRespDto drgGroup;

    private Integer signedInDays;

    private BigDecimal averageFeeAmount;

    private BigDecimal averageUpperLimit;

    private BigDecimal averageLowerLimit;

    private BigDecimal totalUpperLimit;

    private BigDecimal totalLowerLimit;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ybSignInRecordDate;

    private Integer ybSignInRecordDaysLeft;

    private UUID drgRecordId;

    private List<UUID> previousWardDepartmentIdList;

    private FromHospitalRespDto fromHospital;

    private Integer pendingPrescriptionCount;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getPendingPrescriptionCount() {
        return pendingPrescriptionCount;
    }

    private DictionaryDto medType;

//    private DictionaryDto insuranceArea;
//
//    public DictionaryDto getInsuranceArea() {
//        return insuranceArea;
//    }
//
//    public void setInsuranceArea(DictionaryDto insuranceArea) {
//        this.insuranceArea = insuranceArea;
//    }

    public DictionaryDto getMedType() {
        return medType;
    }

    public void setMedType(DictionaryDto medType) {
        this.medType = medType;
    }

    public void setPendingPrescriptionCount(Integer pendingPrescriptionCount) {
        this.pendingPrescriptionCount = pendingPrescriptionCount;
    }

    public BigDecimal getDeductAmountFromCard() {
        return deductAmountFromCard;
    }

    public void setDeductAmountFromCard(BigDecimal deductAmountFromCard) {
        this.deductAmountFromCard = deductAmountFromCard;
    }

    public Date getLastSettlementDate() {
        return lastSettlementDate;
    }

    public void setLastSettlementDate(Date lastSettlementDate) {
        this.lastSettlementDate = lastSettlementDate;
    }

    public FromHospitalRespDto getFromHospital() {
        return fromHospital;
    }

    public void setFromHospital(FromHospitalRespDto fromHospital) {
        this.fromHospital = fromHospital;
    }

    public List<UUID> getPreviousWardDepartmentIdList() {
        return previousWardDepartmentIdList;
    }

    public void setPreviousWardDepartmentIdList(List<UUID> previousWardDepartmentIdList) {
        this.previousWardDepartmentIdList = previousWardDepartmentIdList;
    }

    public Date getSignInDateTime() {
        return signInDateTime;
    }

    public void setSignInDateTime(Date signInDateTime) {
        this.signInDateTime = signInDateTime;
    }

    public UUID getDrgRecordId() {
        return drgRecordId;
    }

    public void setDrgRecordId(UUID drgRecordId) {
        this.drgRecordId = drgRecordId;
    }

    public Date getYbSignInRecordDate() {
        return ybSignInRecordDate;
    }

    public void setYbSignInRecordDate(Date ybSignInRecordDate) {
        this.ybSignInRecordDate = ybSignInRecordDate;
    }

    public Integer getYbSignInRecordDaysLeft() {
        return ybSignInRecordDaysLeft;
    }

    public void setYbSignInRecordDaysLeft(Integer ybSignInRecordDaysLeft) {
        this.ybSignInRecordDaysLeft = ybSignInRecordDaysLeft;
    }

    public Integer getSignedInDays() {
        return signedInDays;
    }

    public void setSignedInDays(Integer signedInDays) {
        this.signedInDays = signedInDays;
    }

    public BigDecimal getAverageFeeAmount() {
        return averageFeeAmount;
    }

    public void setAverageFeeAmount(BigDecimal averageFeeAmount) {
        this.averageFeeAmount = averageFeeAmount;
    }

    public BigDecimal getAverageUpperLimit() {
        return averageUpperLimit;
    }

    public void setAverageUpperLimit(BigDecimal averageUpperLimit) {
        this.averageUpperLimit = averageUpperLimit;
    }

    public BigDecimal getAverageLowerLimit() {
        return averageLowerLimit;
    }

    public void setAverageLowerLimit(BigDecimal averageLowerLimit) {
        this.averageLowerLimit = averageLowerLimit;
    }

    public BigDecimal getTotalUpperLimit() {
        return totalUpperLimit;
    }

    public void setTotalUpperLimit(BigDecimal totalUpperLimit) {
        this.totalUpperLimit = totalUpperLimit;
    }

    public BigDecimal getTotalLowerLimit() {
        return totalLowerLimit;
    }

    public void setTotalLowerLimit(BigDecimal totalLowerLimit) {
        this.totalLowerLimit = totalLowerLimit;
    }

    public DrgGroupRespDto getDrgGroup() {
        return drgGroup;
    }

    public void setDrgGroup(DrgGroupRespDto drgGroup) {
        this.drgGroup = drgGroup;
    }

    public PatientSignOutRequestDto getSignOutReq() {
        return signOutReq;
    }

    public void setSignOutReq(PatientSignOutRequestDto signOutReq) {
        this.signOutReq = signOutReq;
    }

    public BigDecimal getInternalTotalFeeAmount() {
        return internalTotalFeeAmount;
    }

    public void setInternalTotalFeeAmount(BigDecimal internalTotalFeeAmount) {
        this.internalTotalFeeAmount = internalTotalFeeAmount;
    }

    public BigDecimal getInternalTotalPaidAmount() {
        return internalTotalPaidAmount;
    }

    public void setInternalTotalPaidAmount(BigDecimal internalTotalPaidAmount) {
        this.internalTotalPaidAmount = internalTotalPaidAmount;
    }

    public BigDecimal getInternalBalanceAmount() {
        return internalBalanceAmount;
    }

    public void setInternalBalanceAmount(BigDecimal internalBalanceAmount) {
        this.internalBalanceAmount = internalBalanceAmount;
    }

    public void setCurrentBedInfo(String currentBedInfo) {
        this.currentBedInfo = currentBedInfo;
    }

    public String getCurrentBedInfo() {
        String currentBedInfo = "";
        if (this.currentBed != null)
            currentBedInfo = this.getCurrentBed().getWardRoom().getWard().getName() + "-" + this.getCurrentBed().getName();
        return currentBedInfo;
    }


    public SettlementResp getSettlement() {
        return settlement;
    }

    public void setSettlement(SettlementResp settlement) {
        this.settlement = settlement;
    }

    public SettlementResp getPreSettlement() {
        return preSettlement;
    }

    public void setPreSettlement(SettlementResp preSettlement) {
        this.preSettlement = preSettlement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    public BigDecimal getCoveredFeeAmount() {
        return coveredFeeAmount;
    }

    public void setCoveredFeeAmount(BigDecimal coveredFeeAmount) {
        this.coveredFeeAmount = coveredFeeAmount;
    }

    public BigDecimal getPendingFeeAmount() {
        return pendingFeeAmount;
    }

    public void setPendingFeeAmount(BigDecimal pendingFeeAmount) {
        this.pendingFeeAmount = pendingFeeAmount;
    }

    public BigDecimal getSelfPayFeeAmount() {
        return selfPayFeeAmount;
    }

    public void setSelfPayFeeAmount(BigDecimal selfPayFeeAmount) {
        this.selfPayFeeAmount = selfPayFeeAmount;
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

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public UUID getCardInfoId() {
        return cardInfoId;
    }

    public void setCardInfoId(UUID cardInfoId) {
        this.cardInfoId = cardInfoId;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public PatientSignInBedDto getCurrentBed() {
        return currentBed;
    }

    public void setCurrentBed(PatientSignInBedDto currentBed) {
        this.currentBed = currentBed;
    }

    public String getDiagnoseCodeString() {
        return diagnoseCodeString;
    }

    public void setDiagnoseCodeString(String diagnoseCodeString) {
        this.diagnoseCodeString = diagnoseCodeString;
    }

    public BigDecimal getAccountBalance() {
        return totalPaidAmount.subtract(this.selfPayFeeAmount);
    }

    public BigDecimal getOverallAccountBalance() {
        return this.getAccountBalance().add(this.getInternalBalanceAmount());
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public BigDecimal getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(BigDecimal totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public String getDiagnoseString() {
        return diagnoseString;
    }

    public void setDiagnoseString(String diagnoseString) {
        this.diagnoseString = diagnoseString;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getSignInNumberCode() {
        return signInNumberCode;
    }

    public void setSignInNumberCode(String signInNumberCode) {
        this.signInNumberCode = signInNumberCode;
    }

    public DictionaryDto getSignInMethod() {
        return signInMethod;
    }

    public void setSignInMethod(DictionaryDto signInMethod) {
        this.signInMethod = signInMethod;
    }

    public DictionaryDto getNursingLevel() {
        return nursingLevel;
    }

    public void setNursingLevel(DictionaryDto nursingLevel) {
        this.nursingLevel = nursingLevel;
    }

    public BigDecimal getOwingLimit() {
        return owingLimit;
    }

    public void setOwingLimit(BigDecimal owingLimit) {
        this.owingLimit = owingLimit;
    }

    public DictionaryDto getPatientCondition() {
        return patientCondition;
    }

    public void setPatientCondition(DictionaryDto patientCondition) {
        this.patientCondition = patientCondition;
    }

    public DepartmentTreatmentDto getDepartmentTreatment() {
        return departmentTreatment;
    }

    public void setDepartmentTreatment(DepartmentTreatmentDto departmentTreatment) {
        this.departmentTreatment = departmentTreatment;
    }

    public EmployeeListDto getDoctor() {
        return doctor;
    }

    public void setDoctor(EmployeeListDto doctor) {
        this.doctor = doctor;
    }

    public DictionaryDto getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(DictionaryDto insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public PatientSignInStatus getStatus() {
        return status;
    }

    public void setStatus(PatientSignInStatus status) {
        this.status = status;
    }

    public List<DiagnoseDto> getDiagnoseList() {
        return diagnoseList;
    }

    public void setDiagnoseList(List<DiagnoseDto> diagnoseList) {
        this.diagnoseList = diagnoseList;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public Date getSignOutDate() {
        return signOutDate;
    }

    public void setSignOutDate(Date signOutDate) {
        this.signOutDate = signOutDate;
    }
}
