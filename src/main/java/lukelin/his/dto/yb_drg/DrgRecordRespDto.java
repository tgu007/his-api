package lukelin.his.dto.yb_drg;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DrgRecordRespDto {
    private UUID uuid;

    private UUID patientSignInId;

    private String patientName;

    private BigDecimal height;

    private BigDecimal weight;

    private Integer numberOfPregnant;

    private Integer numberOfBirth;

    private DictionaryDto marriageStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date newBornBirthday;

    private BigDecimal newBornWeight;

    private BigDecimal newBornOutWeight;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signInDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signOutDate;

    private String bedNumber;

    private EmployeeDto doctor;

    private AllergyMedicineDto allergyMedicine;

    private BloodTypeDto bloodType;

    private BloodTypeRhDto bloodRhType;

    private String infection;

    private SignOutMethodDto signOutMethod;

    private SpecialDiseaseDto specialDisease;

    private DiagnoseDto signInDiagnose;

    private DiagnoseDirectionDto diseaseInDirection;

    private DiagnoseDto signOutDiagnose;

    private DiagnoseDirectionDto diseaseOutDirection;

    private DiagnoseDto signOutDiagnose1;

    private DiagnoseDto signOutDiagnose2;

    private DiagnoseDto signOutDiagnose3;

    private DiagnoseDto signOutDiagnose4;

    private DiagnoseDto signOutDiagnose5;

    private DiagnoseDto signOutDiagnose6;

    private DiagnoseDto signOutDiagnose7;

    private DiagnoseDto signOutDiagnose8;

    private DiagnoseDto signOutDiagnose9;

    private DiagnoseDto signOutDiagnose10;

    private DiagnoseDto signOutDiagnose11;

    private DiagnoseDto signOutDiagnose12;

    private DiagnoseDto signOutDiagnose13;

    private DiagnoseDto signOutDiagnose14;

    private DiagnoseDto signOutDiagnose15;

    private DiagnoseDto signOutDiagnose16;

    private String medicalRecordMain;

    private String medicalRecordInCondition;

    private String medicalRecordOperationHistory;

    private String medicalRecordBloodTakenHistory;

    private String medicalRecordBSignInCondition;

    private String medicalRecordBTreatmentProcess;

    private String medicalRecordBSignOutCondition;

    private String medicalRecordBSignOutPrescription;

    private SignOutReasonDto signOutReason;

    private List<DrgRecordOperationRespDto> operationList;

    private String binli;

    private Boolean uploaded;

    private String uploadResponse;

    public String getUploadResponse() {
        return uploadResponse;
    }

    public void setUploadResponse(String uploadResponse) {
        this.uploadResponse = uploadResponse;
    }

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getBinli() {
        return binli;
    }

    public void setBinli(String binli) {
        this.binli = binli;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getNumberOfPregnant() {
        return numberOfPregnant;
    }

    public void setNumberOfPregnant(Integer numberOfPregnant) {
        this.numberOfPregnant = numberOfPregnant;
    }

    public Integer getNumberOfBirth() {
        return numberOfBirth;
    }

    public void setNumberOfBirth(Integer numberOfBirth) {
        this.numberOfBirth = numberOfBirth;
    }

    public DictionaryDto getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(DictionaryDto marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public Date getNewBornBirthday() {
        return newBornBirthday;
    }

    public void setNewBornBirthday(Date newBornBirthday) {
        this.newBornBirthday = newBornBirthday;
    }

    public BigDecimal getNewBornWeight() {
        return newBornWeight;
    }

    public void setNewBornWeight(BigDecimal newBornWeight) {
        this.newBornWeight = newBornWeight;
    }

    public BigDecimal getNewBornOutWeight() {
        return newBornOutWeight;
    }

    public void setNewBornOutWeight(BigDecimal newBornOutWeight) {
        this.newBornOutWeight = newBornOutWeight;
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

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public EmployeeDto getDoctor() {
        return doctor;
    }

    public void setDoctor(EmployeeDto doctor) {
        this.doctor = doctor;
    }

    public AllergyMedicineDto getAllergyMedicine() {
        return allergyMedicine;
    }

    public void setAllergyMedicine(AllergyMedicineDto allergyMedicine) {
        this.allergyMedicine = allergyMedicine;
    }

    public BloodTypeDto getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodTypeDto bloodType) {
        this.bloodType = bloodType;
    }

    public BloodTypeRhDto getBloodRhType() {
        return bloodRhType;
    }

    public void setBloodRhType(BloodTypeRhDto bloodRhType) {
        this.bloodRhType = bloodRhType;
    }

    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public SignOutMethodDto getSignOutMethod() {
        return signOutMethod;
    }

    public void setSignOutMethod(SignOutMethodDto signOutMethod) {
        this.signOutMethod = signOutMethod;
    }

    public SpecialDiseaseDto getSpecialDisease() {
        return specialDisease;
    }

    public void setSpecialDisease(SpecialDiseaseDto specialDisease) {
        this.specialDisease = specialDisease;
    }

    public DiagnoseDto getSignInDiagnose() {
        return signInDiagnose;
    }

    public void setSignInDiagnose(DiagnoseDto signInDiagnose) {
        this.signInDiagnose = signInDiagnose;
    }

    public DiagnoseDirectionDto getDiseaseInDirection() {
        return diseaseInDirection;
    }

    public void setDiseaseInDirection(DiagnoseDirectionDto diseaseInDirection) {
        this.diseaseInDirection = diseaseInDirection;
    }

    public DiagnoseDto getSignOutDiagnose() {
        return signOutDiagnose;
    }

    public void setSignOutDiagnose(DiagnoseDto signOutDiagnose) {
        this.signOutDiagnose = signOutDiagnose;
    }

    public DiagnoseDirectionDto getDiseaseOutDirection() {
        return diseaseOutDirection;
    }

    public void setDiseaseOutDirection(DiagnoseDirectionDto diseaseOutDirection) {
        this.diseaseOutDirection = diseaseOutDirection;
    }

    public DiagnoseDto getSignOutDiagnose1() {
        return signOutDiagnose1;
    }

    public void setSignOutDiagnose1(DiagnoseDto signOutDiagnose1) {
        this.signOutDiagnose1 = signOutDiagnose1;
    }

    public DiagnoseDto getSignOutDiagnose2() {
        return signOutDiagnose2;
    }

    public void setSignOutDiagnose2(DiagnoseDto signOutDiagnose2) {
        this.signOutDiagnose2 = signOutDiagnose2;
    }

    public DiagnoseDto getSignOutDiagnose3() {
        return signOutDiagnose3;
    }

    public void setSignOutDiagnose3(DiagnoseDto signOutDiagnose3) {
        this.signOutDiagnose3 = signOutDiagnose3;
    }

    public DiagnoseDto getSignOutDiagnose4() {
        return signOutDiagnose4;
    }

    public void setSignOutDiagnose4(DiagnoseDto signOutDiagnose4) {
        this.signOutDiagnose4 = signOutDiagnose4;
    }

    public DiagnoseDto getSignOutDiagnose5() {
        return signOutDiagnose5;
    }

    public void setSignOutDiagnose5(DiagnoseDto signOutDiagnose5) {
        this.signOutDiagnose5 = signOutDiagnose5;
    }

    public DiagnoseDto getSignOutDiagnose6() {
        return signOutDiagnose6;
    }

    public void setSignOutDiagnose6(DiagnoseDto signOutDiagnose6) {
        this.signOutDiagnose6 = signOutDiagnose6;
    }

    public DiagnoseDto getSignOutDiagnose7() {
        return signOutDiagnose7;
    }

    public void setSignOutDiagnose7(DiagnoseDto signOutDiagnose7) {
        this.signOutDiagnose7 = signOutDiagnose7;
    }

    public DiagnoseDto getSignOutDiagnose8() {
        return signOutDiagnose8;
    }

    public void setSignOutDiagnose8(DiagnoseDto signOutDiagnose8) {
        this.signOutDiagnose8 = signOutDiagnose8;
    }

    public DiagnoseDto getSignOutDiagnose9() {
        return signOutDiagnose9;
    }

    public void setSignOutDiagnose9(DiagnoseDto signOutDiagnose9) {
        this.signOutDiagnose9 = signOutDiagnose9;
    }

    public DiagnoseDto getSignOutDiagnose10() {
        return signOutDiagnose10;
    }

    public void setSignOutDiagnose10(DiagnoseDto signOutDiagnose10) {
        this.signOutDiagnose10 = signOutDiagnose10;
    }

    public DiagnoseDto getSignOutDiagnose11() {
        return signOutDiagnose11;
    }

    public void setSignOutDiagnose11(DiagnoseDto signOutDiagnose11) {
        this.signOutDiagnose11 = signOutDiagnose11;
    }

    public DiagnoseDto getSignOutDiagnose12() {
        return signOutDiagnose12;
    }

    public void setSignOutDiagnose12(DiagnoseDto signOutDiagnose12) {
        this.signOutDiagnose12 = signOutDiagnose12;
    }

    public DiagnoseDto getSignOutDiagnose13() {
        return signOutDiagnose13;
    }

    public void setSignOutDiagnose13(DiagnoseDto signOutDiagnose13) {
        this.signOutDiagnose13 = signOutDiagnose13;
    }

    public DiagnoseDto getSignOutDiagnose14() {
        return signOutDiagnose14;
    }

    public void setSignOutDiagnose14(DiagnoseDto signOutDiagnose14) {
        this.signOutDiagnose14 = signOutDiagnose14;
    }

    public DiagnoseDto getSignOutDiagnose15() {
        return signOutDiagnose15;
    }

    public void setSignOutDiagnose15(DiagnoseDto signOutDiagnose15) {
        this.signOutDiagnose15 = signOutDiagnose15;
    }

    public DiagnoseDto getSignOutDiagnose16() {
        return signOutDiagnose16;
    }

    public void setSignOutDiagnose16(DiagnoseDto signOutDiagnose16) {
        this.signOutDiagnose16 = signOutDiagnose16;
    }

    public String getMedicalRecordMain() {
        return medicalRecordMain;
    }

    public void setMedicalRecordMain(String medicalRecordMain) {
        this.medicalRecordMain = medicalRecordMain;
    }

    public String getMedicalRecordInCondition() {
        return medicalRecordInCondition;
    }

    public void setMedicalRecordInCondition(String medicalRecordInCondition) {
        this.medicalRecordInCondition = medicalRecordInCondition;
    }

    public String getMedicalRecordOperationHistory() {
        return medicalRecordOperationHistory;
    }

    public void setMedicalRecordOperationHistory(String medicalRecordOperationHistory) {
        this.medicalRecordOperationHistory = medicalRecordOperationHistory;
    }

    public String getMedicalRecordBloodTakenHistory() {
        return medicalRecordBloodTakenHistory;
    }

    public void setMedicalRecordBloodTakenHistory(String medicalRecordBloodTakenHistory) {
        this.medicalRecordBloodTakenHistory = medicalRecordBloodTakenHistory;
    }

    public String getMedicalRecordBSignInCondition() {
        return medicalRecordBSignInCondition;
    }

    public void setMedicalRecordBSignInCondition(String medicalRecordBSignInCondition) {
        this.medicalRecordBSignInCondition = medicalRecordBSignInCondition;
    }

    public String getMedicalRecordBTreatmentProcess() {
        return medicalRecordBTreatmentProcess;
    }

    public void setMedicalRecordBTreatmentProcess(String medicalRecordBTreatmentProcess) {
        this.medicalRecordBTreatmentProcess = medicalRecordBTreatmentProcess;
    }

    public String getMedicalRecordBSignOutCondition() {
        return medicalRecordBSignOutCondition;
    }

    public void setMedicalRecordBSignOutCondition(String medicalRecordBSignOutCondition) {
        this.medicalRecordBSignOutCondition = medicalRecordBSignOutCondition;
    }

    public String getMedicalRecordBSignOutPrescription() {
        return medicalRecordBSignOutPrescription;
    }

    public void setMedicalRecordBSignOutPrescription(String medicalRecordBSignOutPrescription) {
        this.medicalRecordBSignOutPrescription = medicalRecordBSignOutPrescription;
    }

    public SignOutReasonDto getSignOutReason() {
        return signOutReason;
    }

    public void setSignOutReason(SignOutReasonDto signOutReason) {
        this.signOutReason = signOutReason;
    }

    public List<DrgRecordOperationRespDto> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<DrgRecordOperationRespDto> operationList) {
        this.operationList = operationList;
    }
}
