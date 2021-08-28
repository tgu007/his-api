package lukelin.his.domain.entity.yb.drg;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.CardInfo;
import lukelin.his.dto.yb_drg.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@javax.persistence.Entity
@Table(name = "yb_drg.drg_record")
public class DrgRecord extends BaseEntity implements DtoConvertible<DrgRecordRespDto> {
    @OneToOne
    @JoinColumn(name = "patient_sign_in_id")
    private PatientSignIn patientSignIn;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "pregnant_number")
    private Integer numberOfPregnant;

    @Column(name = "birth_number")
    private Integer numberOfBirth;

    @ManyToOne
    @JoinColumn(name = "marriage_status_id")
    private Dictionary marriageStatus;

    @Column(name = "new_born_birthday")
    private Date newBornBirthday;

    @Column(name = "new_born_weight")
    private BigDecimal newBornWeight;

    @Column(name = "new_born_out_weight")
    private BigDecimal newBornOutWeight;

    @Column(name = "sign_in_date")
    private Date signInDate;

    @Column(name = "sign_out_date")
    private Date signOutDate;

    @Column(name = "bed_number")
    private String bedNumber;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Employee doctor;

    @ManyToOne
    @JoinColumn(name = "allergy_medicine_id")
    private AllergyMedicine allergyMedicine;

    @ManyToOne
    @JoinColumn(name = "blood_type_id")
    private BloodType bloodType;

    @ManyToOne
    @JoinColumn(name = "blood_rh_type_id")
    private BloodTypeRH bloodRhType;

    @Column(name = "infection")
    private String infection;

    @ManyToOne
    @JoinColumn(name = "sign_out_method_id")
    private SignOutMethod signOutMethod;

    @ManyToOne
    @JoinColumn(name = "special_disease_id")
    private SpecialDisease specialDisease;

    @ManyToOne
    @JoinColumn(name = "sign_in_diagnose_id")
    private Diagnose signInDiagnose;

    @ManyToOne
    @JoinColumn(name = "sign_in_diagnose_direction_id")
    private DiagnoseDirection signInDiagnoseDirection;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id")
    private Diagnose signOutDiagnose;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_direction_id")
    private DiagnoseDirection signOutDiagnoseDirection;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_1")
    private Diagnose signOutDiagnose1;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_2")
    private Diagnose signOutDiagnose2;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_3")
    private Diagnose signOutDiagnose3;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_4")
    private Diagnose signOutDiagnose4;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_5")
    private Diagnose signOutDiagnose5;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_6")
    private Diagnose signOutDiagnose6;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_7")
    private Diagnose signOutDiagnose7;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_8")
    private Diagnose signOutDiagnose8;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_9")
    private Diagnose signOutDiagnose9;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_10")
    private Diagnose signOutDiagnose10;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_11")
    private Diagnose signOutDiagnose11;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_12")
    private Diagnose signOutDiagnose12;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_13")
    private Diagnose signOutDiagnose13;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_14")
    private Diagnose signOutDiagnose14;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_15")
    private Diagnose signOutDiagnose15;

    @ManyToOne
    @JoinColumn(name = "sign_out_diagnose_id_16")
    private Diagnose signOutDiagnose16;

    @Column(name = "mr_main")
    private String medicalRecordMain;

    @Column(name = "mr_current_condition")
    private String medicalRecordInCondition;

    @Column(name = "mr_operation_history")
    private String medicalRecordOperationHistory;

    @Column(name = "mr_blood_taken_history")
    private String medicalRecordBloodTakenHistory;

    @Column(name = "mr_sign_in_condition")
    private String medicalRecordBSignInCondition;

    @Column(name = "mr_treatment_process")
    private String medicalRecordBTreatmentProcess;

    @Column(name = "mr_sign_out_condition")
    private String medicalRecordBSignOutCondition;

    @Column(name = "mr_sign_out_prescription")
    private String medicalRecordBSignOutPrescription;

    @ManyToOne
    @JoinColumn(name = "sign_out_reason_id")
    private SignOutReason signOutReason;

    @OneToMany(mappedBy = "drgRecord", cascade = CascadeType.ALL)
    private List<DrgRecordOperation> operationList;

    @Column(name = "bin_li")
    private String binli;

    @Column(name = "is_uploaded")
    private Boolean uploaded;

    @Column(name = "upload_response")
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

    public List<DrgRecordOperation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<DrgRecordOperation> operationList) {
        this.operationList = operationList;
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

    public Dictionary getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Dictionary marriageStatus) {
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

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public AllergyMedicine getAllergyMedicine() {
        return allergyMedicine;
    }

    public void setAllergyMedicine(AllergyMedicine allergyMedicine) {
        this.allergyMedicine = allergyMedicine;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public BloodTypeRH getBloodRhType() {
        return bloodRhType;
    }

    public void setBloodRhType(BloodTypeRH bloodRhType) {
        this.bloodRhType = bloodRhType;
    }

    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public SignOutMethod getSignOutMethod() {
        return signOutMethod;
    }

    public void setSignOutMethod(SignOutMethod signOutMethod) {
        this.signOutMethod = signOutMethod;
    }

    public SpecialDisease getSpecialDisease() {
        return specialDisease;
    }

    public void setSpecialDisease(SpecialDisease specialDisease) {
        this.specialDisease = specialDisease;
    }

    public Diagnose getSignInDiagnose() {
        return signInDiagnose;
    }

    public void setSignInDiagnose(Diagnose signInDiagnose) {
        this.signInDiagnose = signInDiagnose;
    }

    public DiagnoseDirection getSignInDiagnoseDirection() {
        return signInDiagnoseDirection;
    }

    public void setSignInDiagnoseDirection(DiagnoseDirection signInDiagnoseDirection) {
        this.signInDiagnoseDirection = signInDiagnoseDirection;
    }

    public Diagnose getSignOutDiagnose() {
        return signOutDiagnose;
    }

    public void setSignOutDiagnose(Diagnose signOutDiagnose) {
        this.signOutDiagnose = signOutDiagnose;
    }

    public DiagnoseDirection getSignOutDiagnoseDirection() {
        return signOutDiagnoseDirection;
    }

    public void setSignOutDiagnoseDirection(DiagnoseDirection signOutDiagnoseDirection) {
        this.signOutDiagnoseDirection = signOutDiagnoseDirection;
    }

    public Diagnose getSignOutDiagnose1() {
        return signOutDiagnose1;
    }

    public void setSignOutDiagnose1(Diagnose signOutDiagnose1) {
        this.signOutDiagnose1 = signOutDiagnose1;
    }

    public Diagnose getSignOutDiagnose2() {
        return signOutDiagnose2;
    }

    public void setSignOutDiagnose2(Diagnose signOutDiagnose2) {
        this.signOutDiagnose2 = signOutDiagnose2;
    }

    public Diagnose getSignOutDiagnose3() {
        return signOutDiagnose3;
    }

    public void setSignOutDiagnose3(Diagnose signOutDiagnose3) {
        this.signOutDiagnose3 = signOutDiagnose3;
    }

    public Diagnose getSignOutDiagnose4() {
        return signOutDiagnose4;
    }

    public void setSignOutDiagnose4(Diagnose signOutDiagnose4) {
        this.signOutDiagnose4 = signOutDiagnose4;
    }

    public Diagnose getSignOutDiagnose5() {
        return signOutDiagnose5;
    }

    public void setSignOutDiagnose5(Diagnose signOutDiagnose5) {
        this.signOutDiagnose5 = signOutDiagnose5;
    }

    public Diagnose getSignOutDiagnose6() {
        return signOutDiagnose6;
    }

    public void setSignOutDiagnose6(Diagnose signOutDiagnose6) {
        this.signOutDiagnose6 = signOutDiagnose6;
    }

    public Diagnose getSignOutDiagnose7() {
        return signOutDiagnose7;
    }

    public void setSignOutDiagnose7(Diagnose signOutDiagnose7) {
        this.signOutDiagnose7 = signOutDiagnose7;
    }

    public Diagnose getSignOutDiagnose8() {
        return signOutDiagnose8;
    }

    public void setSignOutDiagnose8(Diagnose signOutDiagnose8) {
        this.signOutDiagnose8 = signOutDiagnose8;
    }

    public Diagnose getSignOutDiagnose9() {
        return signOutDiagnose9;
    }

    public void setSignOutDiagnose9(Diagnose signOutDiagnose9) {
        this.signOutDiagnose9 = signOutDiagnose9;
    }

    public Diagnose getSignOutDiagnose10() {
        return signOutDiagnose10;
    }

    public void setSignOutDiagnose10(Diagnose signOutDiagnose10) {
        this.signOutDiagnose10 = signOutDiagnose10;
    }

    public Diagnose getSignOutDiagnose11() {
        return signOutDiagnose11;
    }

    public void setSignOutDiagnose11(Diagnose signOutDiagnose11) {
        this.signOutDiagnose11 = signOutDiagnose11;
    }

    public Diagnose getSignOutDiagnose12() {
        return signOutDiagnose12;
    }

    public void setSignOutDiagnose12(Diagnose signOutDiagnose12) {
        this.signOutDiagnose12 = signOutDiagnose12;
    }

    public Diagnose getSignOutDiagnose13() {
        return signOutDiagnose13;
    }

    public void setSignOutDiagnose13(Diagnose signOutDiagnose13) {
        this.signOutDiagnose13 = signOutDiagnose13;
    }

    public Diagnose getSignOutDiagnose14() {
        return signOutDiagnose14;
    }

    public void setSignOutDiagnose14(Diagnose signOutDiagnose14) {
        this.signOutDiagnose14 = signOutDiagnose14;
    }

    public Diagnose getSignOutDiagnose15() {
        return signOutDiagnose15;
    }

    public void setSignOutDiagnose15(Diagnose signOutDiagnose15) {
        this.signOutDiagnose15 = signOutDiagnose15;
    }

    public Diagnose getSignOutDiagnose16() {
        return signOutDiagnose16;
    }

    public void setSignOutDiagnose16(Diagnose signOutDiagnose16) {
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

    public SignOutReason getSignOutReason() {
        return signOutReason;
    }

    public void setSignOutReason(SignOutReason signOutReason) {
        this.signOutReason = signOutReason;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    @Override
    public DrgRecordRespDto toDto() {
        DrgRecordRespDto dto = DtoUtils.convertRawDto(this);
        if (this.getMarriageStatus() != null)
            dto.setMarriageStatus(this.getMarriageStatus().toDto());
        dto.setDoctor(this.getDoctor().toDto());
        dto.setBloodType(this.getBloodType().toDto());
        dto.setBloodRhType(this.getBloodRhType().toDto());
        if (this.getAllergyMedicine() != null)
            dto.setAllergyMedicine(this.getAllergyMedicine().toDto());
        dto.setSignInDiagnose(this.getSignInDiagnose().toDto());
        dto.setSignOutDiagnose(this.getSignOutDiagnose().toDto());
        if (this.getSignInDiagnoseDirection() != null)
            dto.setDiseaseInDirection(this.getSignInDiagnoseDirection().toDto());
        if (this.getSignOutDiagnoseDirection() != null)
            dto.setDiseaseOutDirection(this.getSignOutDiagnoseDirection().toDto());
        dto.setSignOutMethod(this.getSignOutMethod().toDto());

        dto.setSpecialDisease(this.getSpecialDisease().toDto());
        if (this.getSignOutDiagnose1() != null)
            dto.setSignOutDiagnose1(this.getSignOutDiagnose1().toDto());
        if (this.getSignOutDiagnose2() != null)
            dto.setSignOutDiagnose2(this.getSignOutDiagnose2().toDto());
        if (this.getSignOutDiagnose3() != null)
            dto.setSignOutDiagnose3(this.getSignOutDiagnose3().toDto());
        if (this.getSignOutDiagnose4() != null)
            dto.setSignOutDiagnose4(this.getSignOutDiagnose4().toDto());
        if (this.getSignOutDiagnose5() != null)
            dto.setSignOutDiagnose5(this.getSignOutDiagnose5().toDto());
        if (this.getSignOutDiagnose6() != null)
            dto.setSignOutDiagnose6(this.getSignOutDiagnose6().toDto());
        if (this.getSignOutDiagnose7() != null)
            dto.setSignOutDiagnose7(this.getSignOutDiagnose7().toDto());
        if (this.getSignOutDiagnose8() != null)
            dto.setSignOutDiagnose8(this.getSignOutDiagnose8().toDto());
        if (this.getSignOutDiagnose9() != null)
            dto.setSignOutDiagnose9(this.getSignOutDiagnose9().toDto());
        if (this.getSignOutDiagnose10() != null)
            dto.setSignOutDiagnose10(this.getSignOutDiagnose10().toDto());
        if (this.getSignOutDiagnose11() != null)
            dto.setSignOutDiagnose11(this.getSignOutDiagnose11().toDto());
        if (this.getSignOutDiagnose12() != null)
            dto.setSignOutDiagnose12(this.getSignOutDiagnose12().toDto());
        if (this.getSignOutDiagnose13() != null)
            dto.setSignOutDiagnose13(this.getSignOutDiagnose13().toDto());
        if (this.getSignOutDiagnose14() != null)
            dto.setSignOutDiagnose14(this.getSignOutDiagnose14().toDto());
        if (this.getSignOutDiagnose15() != null)
            dto.setSignOutDiagnose15(this.getSignOutDiagnose15().toDto());
        if (this.getSignOutDiagnose16() != null)
            dto.setSignOutDiagnose16(this.getSignOutDiagnose16().toDto());
//        if (this.getSignOutDiagnose1() != null)
//            dto.setSignOutReason(this.getSignOutReason().toDto());
        List<DrgRecordOperationRespDto> operationList = new ArrayList<>();
        dto.setOperationList(operationList);
        for (DrgRecordOperation operation : this.getOperationList())
            operationList.add(operation.toDto());
        return dto;
    }

    public DrgUploadDataDto toUploadDto(String hospitalCode, Boolean mockDrgUpload) {
        DrgUploadDataDto dto = new DrgUploadDataDto();
        dto.setMedical(this.toMedicalDto(hospitalCode, mockDrgUpload));
        //dto.setLeaveHospital(this.toLeaveHospitalDto());
        List<DrgUploadListOperationDto> operationList = new ArrayList<>();
        for (DrgRecordOperation operation : this.getOperationList())
            operationList.add(operation.toDrgOperationUploadDto());
        dto.setListOperation(operationList);
        return dto;
    }

    private DrgUploadMedicalDto toMedicalDto(String hospitalCode, Boolean mockDrgUpload) {
        DrgUploadMedicalDto dto = new DrgUploadMedicalDto();
        //Todo not hardcode
        dto.setHospitalId(hospitalCode);
        if (this.getPatientSignIn().getCardInfo() == null && !mockDrgUpload)
            throw new ApiValidationException("缺少医保卡信息");
        if (this.getPatientSignIn().getYbSignIn() == null && !mockDrgUpload)
            throw new ApiValidationException("缺少医保登记信息");
        PatientSignIn patientSignIn = this.getPatientSignIn();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (mockDrgUpload) {
            dto.setAdmissionNo("test001");
            dto.setSciCardNo("123");
            dto.setSciCardIdentified("12345");
        } else {
            CardInfo cardInfo = patientSignIn.getCardInfo();
            dto.setAdmissionNo(this.getPatientSignIn().getYbSignIn().getId());
            dto.setSciCardNo(cardInfo.getIdNumber());
            dto.setSciCardIdentified(cardInfo.getPatientNumber());
        }

        dto.setOutBedNum(this.getBedNumber());
        dto.setAdmissionDate(sdf.format(this.getSignInDate()));
        dto.setDischargeDate(sdf.format(this.getSignOutDate()));
        if (StringUtils.isBlank(this.getDoctor().getCertificationNumber()))
            throw new ApiValidationException("缺少责任医生" + this.getDoctor().getName() + "证书号,请责任医生登录自己的账号，点击自己的名字，输入自己的医生编码后再重新尝试");
        dto.setDoctorCode(this.getDoctor().getCertificationNumber());
        dto.setDoctorName(this.getDoctor().getName());
        if (this.getAllergyMedicine() == null)
            dto.setIsDrugAllergy("0");
        else {
            dto.setIsDrugAllergy("1");
            dto.setAllergyDrugCode(this.getAllergyMedicine().getCode());
            dto.setAllergyDrugName(this.getAllergyMedicine().getName());
        }
        if (!StringUtils.isBlank(this.getBinli())) {
            dto.setIsPathologicalExamination("1");
            dto.setPathologyCode(this.getBedNumber());
        } else
            dto.setIsPathologicalExamination("0");

        if (!StringUtils.isBlank(this.getInfection())) {
            dto.setIsHospitalInfected("1");
            dto.setHospitalInfectedCode(this.getInfection());
        } else
            dto.setIsHospitalInfected("0");

        dto.setBloodTypeS(this.getBloodType().getCode());
        dto.setBloodTypeE(this.getBloodRhType().getCode());
        dto.setLeaveHospitalType(this.getSignOutMethod().getCode());
        dto.setChiefComplaint(this.getMedicalRecordMain());
        dto.setMedicalHistory(this.getMedicalRecordBSignInCondition());
        dto.setSurgeryHistory(this.getMedicalRecordOperationHistory());
        dto.setBloodTransHistory(this.getMedicalRecordBloodTakenHistory());
        dto.setMarriage(this.getMarriageStatus().getCode());
        if (this.getWeight() != null)
            dto.setHeight(this.getHeight().intValue());
        if (this.getWeight() != null)
            dto.setWeight(this.getWeight().intValue());
        dto.setAdmissionDiseaseId(this.signInDiagnose.getCode());
        dto.setAdmissionDiseaseName(this.getSignInDiagnose().getName());
        if (this.getSignInDiagnoseDirection() != null)
            dto.setDiagnosePosition1(this.getSignInDiagnoseDirection().getCode());
        dto.setDischargeDiseaseId(this.getSignOutDiagnose().getCode());
        dto.setDischargeDiseaseName(this.getSignOutDiagnose().getName());
        dto.setTsblbs(this.getSpecialDisease().getCode());
        if (this.getSignOutDiagnoseDirection() != null)
            dto.setDiagnosePosition1(this.getSignOutDiagnoseDirection().getCode());
        if (this.getSignOutDiagnose1() != null) {
            dto.setDiagnosisCode1(this.getSignOutDiagnose1().getCode());
            dto.setDiagnosisName1(this.getSignOutDiagnose1().getName());
        }
        if (this.getSignOutDiagnose2() != null) {
            dto.setDiagnosisCode2(this.getSignOutDiagnose2().getCode());
            dto.setDiagnosisName2(this.getSignOutDiagnose2().getName());
        }
        if (this.getSignOutDiagnose3() != null) {
            dto.setDiagnosisCode3(this.getSignOutDiagnose3().getCode());
            dto.setDiagnosisName3(this.getSignOutDiagnose3().getName());
        }
        if (this.getSignOutDiagnose4() != null) {
            dto.setDiagnosisCode4(this.getSignOutDiagnose4().getCode());
            dto.setDiagnosisName4(this.getSignOutDiagnose4().getName());
        }
        if (this.getSignOutDiagnose5() != null) {
            dto.setDiagnosisCode5(this.getSignOutDiagnose5().getCode());
            dto.setDiagnosisName5(this.getSignOutDiagnose5().getName());
        }
        if (this.getSignOutDiagnose6() != null) {
            dto.setDiagnosisCode6(this.getSignOutDiagnose6().getCode());
            dto.setDiagnosisName6(this.getSignOutDiagnose6().getName());
        }
        if (this.getSignOutDiagnose7() != null) {
            dto.setDiagnosisCode7(this.getSignOutDiagnose7().getCode());
            dto.setDiagnosisName7(this.getSignOutDiagnose7().getName());
        }
        if (this.getSignOutDiagnose8() != null) {
            dto.setDiagnosisCode8(this.getSignOutDiagnose8().getCode());
            dto.setDiagnosisName8(this.getSignOutDiagnose8().getName());
        }
        if (this.getSignOutDiagnose9() != null) {
            dto.setDiagnosisCode9(this.getSignOutDiagnose9().getCode());
            dto.setDiagnosisName9(this.getSignOutDiagnose9().getName());
        }
        if (this.getSignOutDiagnose10() != null) {
            dto.setDiagnosisCode10(this.getSignOutDiagnose10().getCode());
            dto.setDiagnosisName10(this.getSignOutDiagnose10().getName());
        }
        if (this.getSignOutDiagnose11() != null) {
            dto.setDiagnosisCode11(this.getSignOutDiagnose11().getCode());
            dto.setDiagnosisName11(this.getSignOutDiagnose11().getName());
        }
        if (this.getSignOutDiagnose12() != null) {
            dto.setDiagnosisCode12(this.getSignOutDiagnose12().getCode());
            dto.setDiagnosisName12(this.getSignOutDiagnose12().getName());
        }
        if (this.getSignOutDiagnose13() != null) {
            dto.setDiagnosisCode13(this.getSignOutDiagnose13().getCode());
            dto.setDiagnosisName13(this.getSignOutDiagnose13().getName());
        }
        if (this.getSignOutDiagnose14() != null) {
            dto.setDiagnosisCode14(this.getSignOutDiagnose14().getCode());
            dto.setDiagnosisName14(this.getSignOutDiagnose14().getName());
        }
        if (this.getSignOutDiagnose15() != null) {
            dto.setDiagnosisCode15(this.getSignOutDiagnose15().getCode());
            dto.setDiagnosisName15(this.getSignOutDiagnose15().getName());
        }
        if (this.getSignOutDiagnose16() != null) {
            dto.setDiagnosisCode16(this.getSignOutDiagnose16().getCode());
            dto.setDiagnosisName16(this.getSignOutDiagnose16().getName());
        }
        return dto;
    }

    private DrgUploadLeaveHospitalDto toLeaveHospitalDto() {
        DrgUploadLeaveHospitalDto dto = new DrgUploadLeaveHospitalDto();
        dto.setDischargeOutcome(this.getSignOutReason().getCode());
        dto.setLeaveHospitalStatus(this.getMedicalRecordBSignOutCondition());
        dto.setHospitalizationSituation(this.getMedicalRecordBSignInCondition());
        dto.setDtProcess(this.getMedicalRecordBTreatmentProcess());
        dto.setLeaveDoctorAdvice(this.getMedicalRecordBSignOutPrescription());
        return dto;
    }

    public DrgCancelDataDto toCancelDto(String ybHospitalCode, Boolean mockDrgUpload) {
        DrgCancelDataDto cancelDataDto = new DrgCancelDataDto();
        if (mockDrgUpload)
            cancelDataDto.setAdmissionNo("test001");
        else
            cancelDataDto.setAdmissionNo(this.getPatientSignIn().getYbSignIn().getId());
        cancelDataDto.setHospitalId(ybHospitalCode);
        return cancelDataDto;
    }
}
