package lukelin.his.dto.yb_drg;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.drg.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DrgRecordSaveDto {
    private UUID uuid;

    private UUID patientSignInId;

    private String patientName;

    private BigDecimal height;

    private BigDecimal weight;

    private Integer numberOfPregnant;

    private Integer numberOfBirth;

    private Integer marriageStatusId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date newBornBirthday;

    private BigDecimal newBornWeight;

    private BigDecimal newBornOutWeight;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signInDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signOutDate;

    private String bedNumber;

    private UUID doctorId;

    private UUID allergyMedicineId;

    private UUID bloodTypeId;

    private UUID bloodRhTypeId;

    private String infection;

    private UUID signOutMethodId;

    private UUID specialDiseaseId;

    private UUID signInDiagnoseId;

    private UUID diseaseInDirectionId;

    private UUID signOutDiagnoseId;

    private UUID diseaseOutDirectionId;

    private UUID signOutDiagnoseId1;

    private UUID signOutDiagnoseId2;

    private UUID signOutDiagnoseId3;

    private UUID signOutDiagnoseId4;

    private UUID signOutDiagnoseId5;

    private UUID signOutDiagnoseId6;

    private UUID signOutDiagnoseId7;

    private UUID signOutDiagnoseId8;

    private UUID signOutDiagnoseId9;

    private UUID signOutDiagnoseId10;

    private UUID signOutDiagnoseId11;

    private UUID signOutDiagnoseId12;

    private UUID signOutDiagnoseId13;

    private UUID signOutDiagnoseId14;

    private UUID signOutDiagnoseId15;

    private UUID signOutDiagnoseId16;

    private String medicalRecordMain;

    private String medicalRecordInCondition;

    private String medicalRecordOperationHistory;

    private String medicalRecordBloodTakenHistory;

    private String medicalRecordBSignInCondition;

    private String medicalRecordBTreatmentProcess;

    private String medicalRecordBSignOutCondition;

    private String medicalRecordBSignOutPrescription;

    private UUID signOutReasonId;

    private List<DrgRecordOperationSaveDto> operationList;

    private String binli;

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

    public Integer getMarriageStatusId() {
        return marriageStatusId;
    }

    public void setMarriageStatusId(Integer marriageStatusId) {
        this.marriageStatusId = marriageStatusId;
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

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getAllergyMedicineId() {
        return allergyMedicineId;
    }

    public void setAllergyMedicineId(UUID allergyMedicineId) {
        this.allergyMedicineId = allergyMedicineId;
    }

    public UUID getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(UUID bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public UUID getBloodRhTypeId() {
        return bloodRhTypeId;
    }

    public void setBloodRhTypeId(UUID bloodRhTypeId) {
        this.bloodRhTypeId = bloodRhTypeId;
    }

    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public UUID getSignOutMethodId() {
        return signOutMethodId;
    }

    public void setSignOutMethodId(UUID signOutMethodId) {
        this.signOutMethodId = signOutMethodId;
    }

    public UUID getSpecialDiseaseId() {
        return specialDiseaseId;
    }

    public void setSpecialDiseaseId(UUID specialDiseaseId) {
        this.specialDiseaseId = specialDiseaseId;
    }

    public UUID getSignInDiagnoseId() {
        return signInDiagnoseId;
    }

    public void setSignInDiagnoseId(UUID signInDiagnoseId) {
        this.signInDiagnoseId = signInDiagnoseId;
    }

    public UUID getDiseaseInDirectionId() {
        return diseaseInDirectionId;
    }

    public void setDiseaseInDirectionId(UUID diseaseInDirectionId) {
        this.diseaseInDirectionId = diseaseInDirectionId;
    }

    public UUID getSignOutDiagnoseId() {
        return signOutDiagnoseId;
    }

    public void setSignOutDiagnoseId(UUID signOutDiagnoseId) {
        this.signOutDiagnoseId = signOutDiagnoseId;
    }

    public UUID getDiseaseOutDirectionId() {
        return diseaseOutDirectionId;
    }

    public void setDiseaseOutDirectionId(UUID diseaseOutDirectionId) {
        this.diseaseOutDirectionId = diseaseOutDirectionId;
    }

    public UUID getSignOutDiagnoseId1() {
        return signOutDiagnoseId1;
    }

    public void setSignOutDiagnoseId1(UUID signOutDiagnoseId1) {
        this.signOutDiagnoseId1 = signOutDiagnoseId1;
    }

    public UUID getSignOutDiagnoseId2() {
        return signOutDiagnoseId2;
    }

    public void setSignOutDiagnoseId2(UUID signOutDiagnoseId2) {
        this.signOutDiagnoseId2 = signOutDiagnoseId2;
    }

    public UUID getSignOutDiagnoseId3() {
        return signOutDiagnoseId3;
    }

    public void setSignOutDiagnoseId3(UUID signOutDiagnoseId3) {
        this.signOutDiagnoseId3 = signOutDiagnoseId3;
    }

    public UUID getSignOutDiagnoseId4() {
        return signOutDiagnoseId4;
    }

    public void setSignOutDiagnoseId4(UUID signOutDiagnoseId4) {
        this.signOutDiagnoseId4 = signOutDiagnoseId4;
    }

    public UUID getSignOutDiagnoseId5() {
        return signOutDiagnoseId5;
    }

    public void setSignOutDiagnoseId5(UUID signOutDiagnoseId5) {
        this.signOutDiagnoseId5 = signOutDiagnoseId5;
    }

    public UUID getSignOutDiagnoseId6() {
        return signOutDiagnoseId6;
    }

    public void setSignOutDiagnoseId6(UUID signOutDiagnoseId6) {
        this.signOutDiagnoseId6 = signOutDiagnoseId6;
    }

    public UUID getSignOutDiagnoseId7() {
        return signOutDiagnoseId7;
    }

    public void setSignOutDiagnoseId7(UUID signOutDiagnoseId7) {
        this.signOutDiagnoseId7 = signOutDiagnoseId7;
    }

    public UUID getSignOutDiagnoseId8() {
        return signOutDiagnoseId8;
    }

    public void setSignOutDiagnoseId8(UUID signOutDiagnoseId8) {
        this.signOutDiagnoseId8 = signOutDiagnoseId8;
    }

    public UUID getSignOutDiagnoseId9() {
        return signOutDiagnoseId9;
    }

    public void setSignOutDiagnoseId9(UUID signOutDiagnoseId9) {
        this.signOutDiagnoseId9 = signOutDiagnoseId9;
    }

    public UUID getSignOutDiagnoseId10() {
        return signOutDiagnoseId10;
    }

    public void setSignOutDiagnoseId10(UUID signOutDiagnoseId10) {
        this.signOutDiagnoseId10 = signOutDiagnoseId10;
    }

    public UUID getSignOutDiagnoseId11() {
        return signOutDiagnoseId11;
    }

    public void setSignOutDiagnoseId11(UUID signOutDiagnoseId11) {
        this.signOutDiagnoseId11 = signOutDiagnoseId11;
    }

    public UUID getSignOutDiagnoseId12() {
        return signOutDiagnoseId12;
    }

    public void setSignOutDiagnoseId12(UUID signOutDiagnoseId12) {
        this.signOutDiagnoseId12 = signOutDiagnoseId12;
    }

    public UUID getSignOutDiagnoseId13() {
        return signOutDiagnoseId13;
    }

    public void setSignOutDiagnoseId13(UUID signOutDiagnoseId13) {
        this.signOutDiagnoseId13 = signOutDiagnoseId13;
    }

    public UUID getSignOutDiagnoseId14() {
        return signOutDiagnoseId14;
    }

    public void setSignOutDiagnoseId14(UUID signOutDiagnoseId14) {
        this.signOutDiagnoseId14 = signOutDiagnoseId14;
    }

    public UUID getSignOutDiagnoseId15() {
        return signOutDiagnoseId15;
    }

    public void setSignOutDiagnoseId15(UUID signOutDiagnoseId15) {
        this.signOutDiagnoseId15 = signOutDiagnoseId15;
    }

    public UUID getSignOutDiagnoseId16() {
        return signOutDiagnoseId16;
    }

    public void setSignOutDiagnoseId16(UUID signOutDiagnoseId16) {
        this.signOutDiagnoseId16 = signOutDiagnoseId16;
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

    public UUID getSignOutReasonId() {
        return signOutReasonId;
    }

    public void setSignOutReasonId(UUID signOutReasonId) {
        this.signOutReasonId = signOutReasonId;
    }

    public List<DrgRecordOperationSaveDto> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<DrgRecordOperationSaveDto> operationList) {
        this.operationList = operationList;
    }

    public DrgRecord toEntity() {
        DrgRecord drgRecord;
        if (this.getUuid() != null)
            drgRecord = Ebean.find(DrgRecord.class, this.getUuid());
        else {
            drgRecord = new DrgRecord();
            drgRecord.setUploaded(false);
        }
        BeanUtils.copyProperties(this, drgRecord);
        drgRecord.setPatientSignIn(Ebean.find(PatientSignIn.class, this.getPatientSignInId()));
        drgRecord.setMarriageStatus(Ebean.find(Dictionary.class, this.getMarriageStatusId()));
        if (this.getAllergyMedicineId() != null)
            drgRecord.setAllergyMedicine(Ebean.find(AllergyMedicine.class, this.getAllergyMedicineId()));
        drgRecord.setBloodRhType(Ebean.find(BloodTypeRH.class, this.getBloodRhTypeId()));
        drgRecord.setBloodType(Ebean.find(BloodType.class, this.getBloodTypeId()));
        drgRecord.setDoctor(Ebean.find(Employee.class, this.getDoctorId()));
        drgRecord.setSignOutMethod(Ebean.find(SignOutMethod.class, this.getSignOutMethodId()));
        drgRecord.setSpecialDisease(Ebean.find(SpecialDisease.class, this.getSpecialDiseaseId()));
        drgRecord.setSignInDiagnose(Ebean.find(Diagnose.class, this.getSignInDiagnoseId()));
        if (this.getDiseaseInDirectionId() != null)
            drgRecord.setSignInDiagnoseDirection(Ebean.find(DiagnoseDirection.class, this.getDiseaseInDirectionId()));
        drgRecord.setSignOutDiagnose(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId()));
        if (this.getDiseaseOutDirectionId() != null)
            drgRecord.setSignOutDiagnoseDirection(Ebean.find(DiagnoseDirection.class, this.getDiseaseOutDirectionId()));
        if (this.getSignOutDiagnoseId1() != null)
            drgRecord.setSignOutDiagnose1(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId1()));
        if (this.getSignOutDiagnoseId2() != null)
            drgRecord.setSignOutDiagnose2(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId2()));
        if (this.getSignOutDiagnoseId3() != null)
            drgRecord.setSignOutDiagnose3(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId3()));
        if (this.getSignOutDiagnoseId4() != null)
            drgRecord.setSignOutDiagnose4(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId4()));
        if (this.getSignOutDiagnoseId5() != null)
            drgRecord.setSignOutDiagnose5(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId5()));
        if (this.getSignOutDiagnoseId6() != null)
            drgRecord.setSignOutDiagnose6(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId6()));
        if (this.getSignOutDiagnoseId7() != null)
            drgRecord.setSignOutDiagnose7(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId7()));
        if (this.getSignOutDiagnoseId8() != null)
            drgRecord.setSignOutDiagnose8(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId8()));
        if (this.getSignOutDiagnoseId9() != null)
            drgRecord.setSignOutDiagnose9(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId9()));
        if (this.getSignOutDiagnoseId10() != null)
            drgRecord.setSignOutDiagnose10(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId10()));
        if (this.getSignOutDiagnoseId11() != null)
            drgRecord.setSignOutDiagnose11(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId11()));
        if (this.getSignOutDiagnoseId12() != null)
            drgRecord.setSignOutDiagnose12(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId12()));
        if (this.getSignOutDiagnoseId13() != null)
            drgRecord.setSignOutDiagnose13(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId13()));
        if (this.getSignOutDiagnoseId14() != null)
            drgRecord.setSignOutDiagnose14(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId14()));
        if (this.getSignOutDiagnoseId15() != null)
            drgRecord.setSignOutDiagnose15(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId15()));
        if (this.getSignOutDiagnoseId16() != null)
            drgRecord.setSignOutDiagnose16(Ebean.find(Diagnose.class, this.getSignOutDiagnoseId16()));
        //drgRecord.setSignOutReason(Ebean.find(SignOutReason.class, this.getSignOutReasonId()));
        List<DrgRecordOperation> operationList = new ArrayList<>();
        drgRecord.setOperationList(operationList);
        for (DrgRecordOperationSaveDto saveDto : this.getOperationList())
            operationList.add(saveDto.toEntity());
        return drgRecord;
    }
}
