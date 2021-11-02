package lukelin.his.domain.entity.patient_sign_in;

import io.ebean.Ebean;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.account.ViewFeeSummary;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.codeEntity.FromHospital;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.yb.CardInfo;
import lukelin.his.domain.entity.yb.YBSignIn;
import lukelin.his.domain.entity.yb.YBSignInRecord;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignOutStatus;
import lukelin.his.dto.mini_porgram.MiniPatientDto;
import lukelin.his.dto.mini_porgram.MiniPrescriptionDto;
import lukelin.his.dto.prescription.response.PrescriptionPatientNursingCardRespDto;
import lukelin.his.dto.signin.response.Patient3024CheckRespDto;
import lukelin.his.dto.signin.response.PatientSignInListRespDto;
import lukelin.his.dto.signin.response.PatientSignInMessageDto;
import lukelin.his.dto.signin.response.PatientSignInMobileDto;
import lukelin.his.dto.yb.SignInReqDto;
import lukelin.his.dto.yb.req.signIn.*;
import lukelin.his.dto.yb.resp.*;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@MappedSuperclass
public abstract class BasePatientSignIn extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "sign_in_method_id", nullable = false)
    private Dictionary signInMethod;

    @ManyToOne
    @JoinColumn(name = "nursing_level_id")
    private Dictionary nursingLevel;

    @Column(name = "owing_limit")
    private BigDecimal owingLimit;

    @ManyToOne
    @JoinColumn(name = "patient_condition_id", nullable = false)
    private Dictionary patientCondition;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentTreatment departmentTreatment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    @ManyToOne
    @JoinColumn(name = "insurance_type_id", nullable = false)
    private Dictionary insuranceType;

    @Column(name = "insurance_number", length = 50)
    private String insuranceNumber;

    @Column(name = "sign_in_number", nullable = false, insertable = false, updatable = false)
    private Integer signInNumber;

    @Column(name = "status", nullable = false)
    private PatientSignInStatus status;

    @ManyToMany
    @JoinTable(name = "patient_sign_in.patient_sign_in_diagnose",
            joinColumns = {@JoinColumn(name = "sign_in_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "diagnose_id", referencedColumnName = "uuid")})
    private Set<Diagnose> diagnoseSet;

    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<PatientSignInBed> bedList;

    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<Prescription> prescriptionList;

    @Column(name = "sign_in_date")
    private Date signInDate;

    @Column(name = "sign_in_number_code", length = 50)
    private String signInNumberCode;

    @OneToMany(mappedBy = "patientSignIn")
    private List<PrescriptionMedicineOrderLine> medicineOrderLineList;

    @OneToMany(mappedBy = "patientSignIn")
    private List<NursingRecord> nursingRecordList;

    @OneToMany(mappedBy = "patientSignIn")
    private List<TempRecord> tempRecordList;

    @OneToOne(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private PatientSignOutRequest singOutRequest;

    @ManyToOne
    @JoinColumn(name = "current_bed_id")
    private WardRoomBed currentBed;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientSignIn")
    private CardInfo cardInfo;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientSignIn")
    private YBSignIn ybSignIn;

    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<Fee> feeList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientSignIn")
    private YBSignInRecord ybSignInRecord;

    private String reference;

    @ManyToOne
    @JoinColumn(name = "from_hospital_id")
    private FromHospital fromHospital;

    public FromHospital getFromHospital() {
        return fromHospital;
    }

    public void setFromHospital(FromHospital fromHospital) {
        this.fromHospital = fromHospital;
    }

    public YBSignInRecord getYbSignInRecord() {
        return ybSignInRecord;
    }

    public void setYbSignInRecord(YBSignInRecord ybSignInRecord) {
        this.ybSignInRecord = ybSignInRecord;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Fee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
    }

    public YBSignIn getYbSignIn() {
        return ybSignIn;
    }

    public void setYbSignIn(YBSignIn ybSignIn) {
        this.ybSignIn = ybSignIn;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public void setCurrentBed(WardRoomBed currentBed) {
        this.currentBed = currentBed;
    }

    public WardRoomBed getCurrentBed() {
        return this.currentBed;
    }

    public PatientSignOutRequest getSingOutRequest() {
        return singOutRequest;
    }

    public void setSingOutRequest(PatientSignOutRequest singOutRequest) {
        this.singOutRequest = singOutRequest;
    }

    public List<NursingRecord> getNursingRecordList() {
        return nursingRecordList;
    }

    public void setNursingRecordList(List<NursingRecord> nursingRecordList) {
        this.nursingRecordList = nursingRecordList;
    }

    public List<TempRecord> getTempRecordList() {
        return tempRecordList;
    }

    public void setTempRecordList(List<TempRecord> tempRecordList) {
        this.tempRecordList = tempRecordList;
    }

    public List<PrescriptionMedicineOrderLine> getMedicineOrderLineList() {
        return medicineOrderLineList;
    }

    public void setMedicineOrderLineList(List<PrescriptionMedicineOrderLine> medicineOrderLineList) {
        this.medicineOrderLineList = medicineOrderLineList;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }


    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public List<PatientSignInBed> getBedList() {
        return bedList;
    }

    public void setBedList(List<PatientSignInBed> bedList) {
        this.bedList = bedList;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Dictionary getSignInMethod() {
        return signInMethod;
    }

    public void setSignInMethod(Dictionary signInMethod) {
        this.signInMethod = signInMethod;
    }

    public Dictionary getNursingLevel() {
        return nursingLevel;
    }

    public void setNursingLevel(Dictionary nursingLevel) {
        this.nursingLevel = nursingLevel;
    }

    public BigDecimal getOwingLimit() {
        return owingLimit;
    }

    public void setOwingLimit(BigDecimal owingLimit) {
        this.owingLimit = owingLimit;
    }

    public Dictionary getPatientCondition() {
        return patientCondition;
    }

    public void setPatientCondition(Dictionary patientCondition) {
        this.patientCondition = patientCondition;
    }

    public DepartmentTreatment getDepartmentTreatment() {
        return departmentTreatment;
    }

    public void setDepartmentTreatment(DepartmentTreatment departmentTreatment) {
        this.departmentTreatment = departmentTreatment;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public Dictionary getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(Dictionary insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getSignInNumberCode() {
        return signInNumberCode.substring(4);
    }

    public void setSignInNumberCode(String signInNumberCode) {
        this.signInNumberCode = signInNumberCode;
    }

    public Integer getSignInNumber() {
        return signInNumber;
    }

    public void setSignInNumber(Integer signInNumber) {
        this.signInNumber = signInNumber;
    }

    public PatientSignInStatus getStatus() {
        return status;
    }

    public void setStatus(PatientSignInStatus status) {
        this.status = status;
    }

    public Set<Diagnose> getDiagnoseSet() {
        return diagnoseSet;
    }

    public void setDiagnoseSet(Set<Diagnose> diagnoseSet) {
        this.diagnoseSet = diagnoseSet;
    }


    public WardRoomBed getBedByDate(Date date) {
        Optional<PatientSignInBed> optionalPatientSignInBed = this.getBedList().stream().filter
                (b -> date.compareTo(b.getStartDate()) >= 0
                        && (b.getEndDate() == null || date.compareTo(b.getEndDate()) < 0)).findFirst();

        return optionalPatientSignInBed.map(PatientSignInBed::getWardRoomBed).orElse(null);
    }

    public PatientSignInListRespDto toListDto() {
        PatientSignInListRespDto dto = new PatientSignInListRespDto();
        this.setListRespDtoValue(dto);
        return dto;
    }

    public PatientSignInListRespDto toCheck3034Dto() {
        Patient3024CheckRespDto dto = new Patient3024CheckRespDto();
        this.setListRespDtoValue(dto);

        Optional<ViewFeeSummary> optionalFeeInfo = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", this.getUuid()).findOneOrEmpty();
        if (optionalFeeInfo.isPresent()) {
            ViewFeeSummary feeSummary = optionalFeeInfo.get();
            dto.setTotalFeeAmount(feeSummary.getTotalAmount());

            List<Fee> patient2034FeeList = Ebean.find(Fee.class).where()
                    .eq("patientSignIn.uuid", this.getUuid())
                    .eq("feeStatus", FeeStatus.confirmed)
                    .eq("entityType", EntityType.treatment)
                    .eq("treatmentSnapshot.treatment.yb3024Group", true)
                    .findList();
            BigDecimal total3024Amount = patient2034FeeList.stream()
                    .map(Fee::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
            dto.setTotal3024Amount(total3024Amount);

            if (dto.getTotalFeeAmount().compareTo(BigDecimal.ZERO) > 0)
                dto.setAmount3024Percentage(dto.getTotal3024Amount().divide(dto.getTotalFeeAmount(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Integer day3024Count = (int) patient2034FeeList.stream().map(f -> simpleDateFormat.format(f.getFeeDate())).distinct().count();
            dto.setDay3024Count(day3024Count);
        }

        dto.setSignedInDays(this.getPatientSignInDays());
        dto.setDay3024Percentage(new BigDecimal(dto.getDay3024Count()).divide(new BigDecimal(dto.getSignedInDays()), 2, RoundingMode.UP).multiply(new BigDecimal("100")));
        //Todo check value need to be configured
        if (dto.getAmount3024Percentage().compareTo(new BigDecimal("20")) < 0)
            dto.setAmount3024Valid(false);
        if (dto.getDay3024Percentage().compareTo(new BigDecimal("70")) < 0)
            dto.setDay3024Valid(false);


        return dto;
    }

    private void setListRespDtoValue(PatientSignInListRespDto dto) {
        dto.setUuid(this.getUuid());
        dto.setDepartment(this.getDepartmentTreatment().getDepartment().getName());
        dto.setDoctor(this.getDoctor().getName());
        dto.setInsuranceType(this.getInsuranceType().getCode());
        dto.setPatientName(this.getPatient().getName());
        dto.setSignInDate(this.getSignInDate());
        if (this.getSingOutRequest() != null && this.getSingOutRequest().getStatus() == PatientSignOutStatus.signedOut)
            dto.setSignOutDate(this.getSingOutRequest().getSignOutDate());
        dto.setSignInNumber(this.getSignInNumberCode());
        dto.setAge(Utils.getAge(this.getPatient().getBirthday()));
        dto.setStatus(this.getStatus());
        dto.setCreatedById(this.getWhoCreatedId());
        WardRoomBed currentBed = this.getCurrentBed();
        if (currentBed != null)
            dto.setCurrentBed(currentBed.getFullWardInfo());

        dto.setHasCardInfo(this.getCardInfo() != null);
        dto.setSelfPay(this.selfPay());
        dto.setYbSignedIn(this.getYbSignIn() != null);
        if (this.getYbSignIn() != null)
            dto.setYbId(this.getYbSignIn().getId());

        dto.setPendingFeeToUpload(this.pendingUploadFee());


        if (this.getYbSignInRecord() != null)
            dto.setYbSignInRecord(this.getYbSignInRecord().toDto());

        if (this.getFromHospital() != null)
            dto.setFromHospital(this.getFromHospital().toDto());

        dto.setReference(this.getReference());
        dto.setSignInDays(this.getPatientSignInDays());
        dto.setGender(this.getPatient().getGender().getName());
        if (this.getDiagnoseSet().size() > 0)
            dto.setMainDiagnose(((Diagnose) this.getDiagnoseSet().toArray()[0]).getName());

        Optional<ViewFeeSummary> optionalFeeInfo = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", this.getUuid()).findOneOrEmpty();
        if (optionalFeeInfo.isPresent()) {
            ViewFeeSummary feeSummary = optionalFeeInfo.get();
            dto.setTotalFee(feeSummary.getTotalAmount());
        }

    }


    public PrescriptionPatientNursingCardRespDto toPatientNursingCardDto() {
        PrescriptionPatientNursingCardRespDto patientNursingCard = new PrescriptionPatientNursingCardRespDto();
        patientNursingCard.setPatientAge(Utils.getAge(this.getPatient().getBirthday()));
        patientNursingCard.setPatientName(this.getPatient().getName());
        patientNursingCard.setPatientSignInId(this.getUuid());
        patientNursingCard.setBedInfo(this.getCurrentBed().getFullWardInfo());
        patientNursingCard.setSignInCode(this.getSignInNumberCode());
        return patientNursingCard;
    }

    public PatientSignInMessageDto toMessageDto() {
        PatientSignInMessageDto patientSignInMessageDto = new PatientSignInMessageDto();
        patientSignInMessageDto.setPatientSignInId(this.getUuid());
        patientSignInMessageDto.setName(this.getPatient().getName());
        if (currentBed != null)
            patientSignInMessageDto.setCurrentBedDto(this.currentBed.toPatientBedDto());
        return patientSignInMessageDto;
    }

    public MiniPatientDto toMinPatientDto(List<Prescription> patientPrescription) {
        MiniPatientDto patientDto = new MiniPatientDto();
        patientDto.setUuid(this.getUuid());
        patientDto.setBedNumber(this.getCurrentBed().getName());
        patientDto.setName(this.getPatient().getName());
        patientDto.setSignInNumberCode(this.getSignInNumberCode());
        List<MiniPrescriptionDto> prescriptionList = new ArrayList<>();
        for (Prescription prescription : patientPrescription)
            prescriptionList.add(prescription.toMiniPrescriptionDto());
        patientDto.setPrescriptionList(prescriptionList);
        return patientDto;
    }

    public PatientSignInMobileDto toMobileDto() {
        PatientSignInMobileDto dto = new PatientSignInMobileDto();
        dto.setUuid(this.getUuid());
        dto.setPatientName(this.getPatient().getName());
        dto.setSignInDate(this.signInDate);
        return dto;
    }

    public SignInReqDto toYBSignIn() {
        SignInReqDto reqDto = new SignInReqDto();
        this.setYbSignInReqValue(reqDto);
        if (insuranceType.getName().equals("工伤"))
            reqDto.setYWLX("44");
        else
            reqDto.setYWLX("21");
        reqDto.setRYLX(signInMethod.getExtraInfo());
        reqDto.setXZKS(departmentTreatment.getSequence().toString());

        SignInDiagnoseReqDto diagnoseReq = new SignInDiagnoseReqDto();
        reqDto.setJZXX(diagnoseReq);
        if (this.getCardInfo() != null)
            diagnoseReq.setYlrylb(this.getCardInfo().getPatientType());
        this.setYbSignInDiagnoseValue(diagnoseReq);
        return reqDto;
    }

    public SelfSignInReqDto toSelfYBSignIn() {
        SelfSignInReqDto reqDto = new SelfSignInReqDto();
        this.setYbSignInReqValue(reqDto);
        if (doctor.getDoctorAgreementNumber() != null)
            reqDto.setXyysbh(doctor.getDoctorAgreementNumber().getAgreementNumber());
        SelfSignInDiagnoseReq diagnoseReq = new SelfSignInDiagnoseReq();
        reqDto.setJzxx(diagnoseReq);
        this.setYbSignInDiagnoseValue(diagnoseReq);
        return reqDto;
    }

    public void setYbSignInDiagnoseValue(BaseSignInDiagnoseReq diagnoseReq) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        diagnoseReq.setXm(this.getPatient().getName());
        diagnoseReq.setXb(this.getPatient().getGender().getExtraInfo());
        diagnoseReq.setCsrq(df.format(this.getPatient().getBirthday()));
        diagnoseReq.setZjbh(this.getPatient().getIdNumber());
        diagnoseReq.setLxdh(this.getPatient().getPatientContactList().get(0).getPhoneNumber());
        Diagnose diagnose = (Diagnose) this.getDiagnoseSet().toArray()[0];
        diagnoseReq.setJbbm(diagnose.getCode());
        diagnoseReq.setJbmc(diagnose.getName());
        if (this.getCardInfo() != null) {
            diagnoseReq.setKxxy(this.getCardInfo().getCardId());
            diagnoseReq.setYbszdbh(this.getCardInfo().getAreaCode());
        }
        //todo add selection on ui now default to 0 普通接诊类型
        diagnoseReq.setJzlx("0");
        if (doctor.getDoctorAgreementNumber() != null)
            diagnoseReq.setXyysbh(doctor.getDoctorAgreementNumber().getAgreementNumber());
    }

    public void setYbSignInReqValue(BaseSignInReqDto reqDto) {
        reqDto.setCJZXH(this.getUuid().toString());
        reqDto.setZYH(this.getSignInNumber().toString());
        //reqDto.setCJZXH("123321");
        //reqDto.setZYH("123321");
        if (this.getCurrentBed() != null) {
            reqDto.setCWKS(this.getCurrentBed().getWardRoom().getWard().getSequence().toString());
            reqDto.setCWH(this.getCurrentBed().getSequence().toString());
        } else {
            reqDto.setCWKS("001");
            reqDto.setCWH("1");
        }
        //todo not hard code
        Dictionary insuranceType = this.getInsuranceType();

        signInMethod = this.getSignInMethod();

        DepartmentTreatment departmentTreatment = this.getDepartmentTreatment();
        reqDto.setZGKS(departmentTreatment.getSequence().toString());

        Employee doctor;
        doctor = this.getDoctor();
        reqDto.setZGYS(doctor.getName());

        reqDto.setCZKS(departmentTreatment.getSequence().toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        reqDto.setRYRQ(df.format(this.getSignInDate()));
        //reqDto.setRYRQ("20210403");
        reqDto.setRYKSMC(departmentTreatment.getDepartment().getName());

    }

    public SignInChangeReq toYBSignInChange() {
        SignInChangeReq reqDto = new SignInChangeReq();
        reqDto.setJzxh(this.getYbSignIn().getId());
        DepartmentTreatment departmentTreatment;
        departmentTreatment = this.getDepartmentTreatment();
        reqDto.setZgks(departmentTreatment.getSequence().toString());
        Employee doctor = this.getDoctor();
        reqDto.setZgys(doctor.getName());
        reqDto.setYsbh(doctor.getDoctorAgreementNumber().getAgreementNumber());
        if (this.getCurrentBed() != null) {
            reqDto.setCwks(this.getCurrentBed().getWardRoom().getWard().getSequence().toString());
            reqDto.setCwh(this.getCurrentBed().getSequence().toString());
        }
        Diagnose diagnose = (Diagnose) this.getDiagnoseSet().toArray()[0];
        reqDto.setJbbm((diagnose.getCode()));
        reqDto.setJbmc((diagnose.getName()));
        return reqDto;
    }

    public boolean selfPay() {
        if (this.getInsuranceType() != null)
            return this.getInsuranceType().getExtraInfo().equals("selfPay");
        else
            return true;
    }

    public FeeUploadReq toUploadDto(List<Fee> pendingUploadFeeList) {
        FeeUploadReq req = new FeeUploadReq();
        if (this.getCardInfo() != null)
            req.setGrbh(this.getCardInfo().getPatientNumber());
        req.setJzxh(this.getYbSignIn().getId());
        req.setSignInId(this.getUuid());
        List<FeeUploadLineReq> lineReqList = new ArrayList<>();
        for (Fee pendingFee : pendingUploadFeeList) {
            FeeUploadLineReq lineReq = pendingFee.toUploadDto();
            lineReqList.add(lineReq);
        }
        req.setFyxx(lineReqList);
        return req;
    }

    public Boolean pendingUploadFee() {
        if (this.selfPay())
            return false;
        else {
            int pendingUploadFeeCount =
                    Ebean.find(Fee.class).where()
                            .eq("feeUploadResult", null)
                            .eq("feeStatus", FeeStatus.confirmed)
                            .eq("patientSignIn.uuid", this.getUuid())
                            .ne("selfPay", true)
                            .findCount();
            return pendingUploadFeeCount > 0;
        }
    }

    public Integer getPatientSignInDays() {
        if (this.getSignInDate() == null)
            return 0;
        Date endDate = null;
        if (this.getSingOutRequest() != null && this.getSingOutRequest().getStatus() == PatientSignOutStatus.signedOut)
            endDate = this.getSingOutRequest().getSignOutDate();
        if (endDate == null)
            endDate = new Date();
        return Utils.findDaysBetween(this.getSignInDate(), endDate);
    }
}
