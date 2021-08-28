package lukelin.his.domain.entity.patient_sign_in;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.Internal_account.AutoFee;
import lukelin.his.domain.entity.Internal_account.Fee;
import lukelin.his.domain.entity.Internal_account.FeePaymentSummary;
import lukelin.his.domain.entity.Internal_account.Payment;
import lukelin.his.domain.entity.account.ViewFeeSummary;
import lukelin.his.domain.entity.account.ViewPaymentSummary;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.codeEntity.FromHospital;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.entity.yb.drg.DrgRecord;
import lukelin.his.domain.enums.PatientSignIn.DrgGroupType;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.dto.basic.resp.setup.BaseCodeDto;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.signin.response.PatientSignInListRespDto;
import lukelin.his.dto.signin.response.PatientSignInRespDto;
import lukelin.his.dto.yb.req.settlement.ConfirmSettlementDto;
import lukelin.his.dto.yb.req.settlement.SelfSettleUploadDetailReq;
import lukelin.his.dto.yb.req.settlement.SelfSettleUploadReq;
import lukelin.his.dto.yb.req.settlement.SettleUploadReq;
import lukelin.his.system.Utils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "patient_sign_in.patient_sign_in")
public class PatientSignIn extends BasePatientSignIn implements DtoConvertible<PatientSignInRespDto> {
    public PatientSignIn() {
    }

    public PatientSignIn(UUID uuid) {
        this.setUuid(uuid);
    }


    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<AutoFee> internalAutoFeeList;

    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<Fee> internalFeeList;

    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<Payment> internalPaymentList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientSignIn")
    private Settlement settlement;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientSignIn")
    private PreSettlement preSettlement;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientSignIn")
    private FeeDownload ybFeeDownload;

    @ManyToOne
    @JoinColumn(name = "drg_group_id")
    private DrgGroup drgGroup;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patientSignIn")
    private DrgRecord drgRecord;

    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<PatientSignInDepartmentChange> departmentChangeList;

    @OneToMany(mappedBy = "patientSignIn", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecordList;


    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }

    public void setMedicalRecordList(List<MedicalRecord> medicalRecordList) {
        this.medicalRecordList = medicalRecordList;
    }

    public List<PatientSignInDepartmentChange> getDepartmentChangeList() {
        return departmentChangeList;
    }

    public void setDepartmentChangeList(List<PatientSignInDepartmentChange> departmentChangeList) {
        this.departmentChangeList = departmentChangeList;
    }

    public DrgRecord getDrgRecord() {
        return drgRecord;
    }

    public void setDrgRecord(DrgRecord drgRecord) {
        this.drgRecord = drgRecord;
    }

    public FeeDownload getYbFeeDownload() {
        return ybFeeDownload;
    }

    public void setYbFeeDownload(FeeDownload ybFeeDownload) {
        this.ybFeeDownload = ybFeeDownload;
    }

    public DrgGroup getDrgGroup() {
        return drgGroup;
    }

    public void setDrgGroup(DrgGroup drgGroup) {
        this.drgGroup = drgGroup;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public PreSettlement getPreSettlement() {
        return preSettlement;
    }

    public void setPreSettlement(PreSettlement preSettlement) {
        this.preSettlement = preSettlement;
    }

    public List<Fee> getInternalFeeList() {
        return internalFeeList;
    }

    public void setInternalFeeList(List<Fee> internalFeeList) {
        this.internalFeeList = internalFeeList;
    }

    public List<Payment> getInternalPaymentList() {
        return internalPaymentList;
    }

    public void setInternalPaymentList(List<Payment> internalPaymentList) {
        this.internalPaymentList = internalPaymentList;
    }

    public List<AutoFee> getInternalAutoFeeList() {
        return internalAutoFeeList;
    }

    public void setInternalAutoFeeList(List<AutoFee> internalAutoFeeList) {
        this.internalAutoFeeList = internalAutoFeeList;
    }

    @Override
    public PatientSignInRespDto toDto() {
        PatientSignInRespDto respDto = DtoUtils.convertRawDto(this);
        respDto.setSignInDateTime(this.getSignInDate());
        respDto.setPatient(this.getPatient().toDto());
        respDto.setNursingLevel(this.getNursingLevel().toDto());
        respDto.setDepartmentTreatment(this.getDepartmentTreatment().toDto());
        respDto.setDoctor(this.getDoctor().toListDto());
        respDto.setInsuranceType(this.getInsuranceType().toDto());
        respDto.setSignInMethod(this.getSignInMethod().toDto());
        respDto.setPatientCondition(this.getPatientCondition().toDto());
        respDto.setAge(Utils.getAge(this.getPatient().getBirthday()));
        Optional<ViewPaymentSummary> optionalPaymentInfo = Ebean.find(ViewPaymentSummary.class).where().eq("patientSignInId", this.getUuid()).findOneOrEmpty();
        optionalPaymentInfo.ifPresent(viewPaymentSummary -> respDto.setTotalPaidAmount(viewPaymentSummary.getTotalPayment()));

        Optional<ViewFeeSummary> optionalFeeInfo = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", this.getUuid()).findOneOrEmpty();
        if (optionalFeeInfo.isPresent()) {
            ViewFeeSummary feeSummary = optionalFeeInfo.get();
            respDto.setTotalFeeAmount(feeSummary.getTotalAmount());
            respDto.setPendingFeeAmount(feeSummary.getPendingFeeAmount());
            if (this.selfPay()) {
                respDto.setCoveredFeeAmount(BigDecimal.ZERO);
                respDto.setSelfPayFeeAmount(feeSummary.getTotalAmount());
            } else {
                if (this.getPreSettlement() != null) {
                    respDto.setCoveredFeeAmount(this.getPreSettlement().getBXJE());
                    respDto.setSelfPayFeeAmount(this.getPreSettlement().getXJJE());
                    respDto.setDeductAmountFromCard(this.preSettlement.getDNZHZF().add(this.preSettlement.getLNZHZF()));
                    respDto.setLastSettlementDate(this.preSettlement.getWhenCreated());
                }
            }
        }

        Optional<FeePaymentSummary> optionalInternalFeePaymentSummary = Ebean.find(FeePaymentSummary.class).where().eq("patientSignIn.uuid", this.getUuid()).findOneOrEmpty();
        if (optionalInternalFeePaymentSummary.isPresent()) {
            FeePaymentSummary internalFeePaymentSummary = optionalInternalFeePaymentSummary.get();
            respDto.setInternalTotalFeeAmount(internalFeePaymentSummary.getTotalFeeAmount());
            respDto.setInternalTotalPaidAmount(internalFeePaymentSummary.getTotalPaidAmount());
            respDto.setInternalBalanceAmount(internalFeePaymentSummary.getBalanceAmount());
        }


        List<DiagnoseDto> diagnoseDtoList = new ArrayList<>();
        for (Diagnose diagnose : this.getDiagnoseSet())
            diagnoseDtoList.add(diagnose.toDto());
        respDto.setDiagnoseList(diagnoseDtoList);
        respDto.setDiagnoseString(respDto.getDiagnoseList().stream().map(BaseCodeDto::getName).collect(Collectors.joining()));
        respDto.setDiagnoseCodeString(respDto.getDiagnoseList().stream().map(BaseCodeDto::getCode).collect(Collectors.joining()));
        WardRoomBed bed = this.findLastOrCurrentBed();
        if (bed != null)
            respDto.setCurrentBed(bed.toPatientBedDto());
        respDto.setCreatedById(this.getWhoCreatedId());

        if (this.getCardInfo() != null)
            respDto.setCardInfoId(this.getCardInfo().getUuid());

        respDto.setSelfPay(this.selfPay());
        respDto.setYbSignedIn(this.getYbSignIn() != null);
        if (this.getYbSignIn() != null)
            respDto.setYbId(this.getYbSignIn().getId());

        if (this.getPreSettlement() != null)
            respDto.setPreSettlement(this.getPreSettlement().toPreSettlementDto());

        if (this.getSettlement() != null)
            respDto.setSettlement(this.getSettlement().toSettlementDto());

        if (this.getSingOutRequest() != null) {
            respDto.setSignOutReq(this.getSingOutRequest().toDto());
            respDto.setSignOutDate(this.getSingOutRequest().getSignOutDate());
        }

        if (this.drgGroup != null)
            respDto.setDrgGroup(this.getDrgGroup().toDto());

        respDto.setSignedInDays(this.getPatientSignInDays() + 1);

        respDto.setAverageFeeAmount(respDto.getTotalFeeAmount().divide(new BigDecimal(respDto.getSignedInDays()), BigDecimal.ROUND_HALF_UP).setScale(2));
        if (this.getDrgGroup() != null) {
            BigDecimal amountToCheck;
            if (this.getDrgGroup().getGroupType() == DrgGroupType.average)
                amountToCheck = respDto.getAverageFeeAmount();
            else
                amountToCheck = respDto.getTotalFeeAmount();

            if (amountToCheck.compareTo(this.getDrgGroup().getUpperLimit()) > 0)
                if (this.getDrgGroup().getGroupType() == DrgGroupType.average)
                    respDto.setAverageUpperLimit(this.drgGroup.getUpperLimit());
                else
                    respDto.setTotalUpperLimit(this.drgGroup.getUpperLimit());


            if (this.getDrgGroup().getLowerLimit() != null &&
                    this.getDrgGroup().getLowerLimit().compareTo(BigDecimal.ZERO) > 0
                    && this.getDrgGroup().getLowerLimit().compareTo(amountToCheck) > 0
            ) {
                if (amountToCheck.compareTo(this.getDrgGroup().getLowerLimit()) < 0)
                    if (this.getDrgGroup().getGroupType() == DrgGroupType.average)
                        respDto.setAverageLowerLimit(this.drgGroup.getUpperLimit());
                    else
                        respDto.setAverageLowerLimit(this.drgGroup.getUpperLimit());
            }

        }

        if (this.getYbSignInRecord() != null) {
            Date endDate = this.getYbSignInRecord().getEndDate();
            respDto.setYbSignInRecordDate(endDate);
            if (endDate.before(new Date())) {
                respDto.setYbSignInRecordDaysLeft(-1);
            } else {
                LocalDateTime localEndDate = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
                int daysBetween = Math.abs((int) ((localEndDate.toLocalDate().toEpochDay() - LocalDate.now().toEpochDay())));
                respDto.setYbSignInRecordDaysLeft(daysBetween);
            }

        } else
            respDto.setYbSignInRecordDaysLeft(100);

        if (this.getDrgRecord() != null)
            respDto.setDrgRecordId(this.getDrgRecord().getUuid());

        List<UUID> previousDepartmentIdList =
                this.getDepartmentChangeList().stream()
                        .map(dc -> dc.getFromDepartment().getUuid()).distinct()
                        .filter(td -> !td.equals(this.getDepartmentTreatment().getUuid())).collect(Collectors.toList());
        respDto.setPreviousWardDepartmentIdList(previousDepartmentIdList);

        if (this.getFromHospital() != null)
            respDto.setFromHospital(this.getFromHospital().toDto());

        Integer pendingPrescriptionCount = (int) this.getPrescriptionList().stream().filter(p -> p.getStatus() == PrescriptionStatus.submitted).count();
        respDto.setPendingPrescriptionCount(pendingPrescriptionCount);
        return respDto;

    }

    public WardRoomBed findLastOrCurrentBed() {
        WardRoomBed bed = this.getCurrentBed();
        if (bed == null) {
            List<PatientSignInBed> bedList = this.getBedList().stream()
                    .sorted(Comparator.comparing(PatientSignInBed::getWhenCreated).reversed()).collect(Collectors.toList());
            if (bedList.size() > 0)
                bed = bedList.get(0).getWardRoomBed();
        }
        return bed;
    }


    public Integer getPatientSignInWeeks() {
        Integer daysBetween = this.getPatientSignInDays();
        return daysBetween / 7;
    }

    public SettleUploadReq toSettleUploadReq(String newUuid) {
        SettleUploadReq req = new SettleUploadReq();
        req.setJzxh(this.getYbSignIn().getId());
        req.setSfsygzzf("1");//个账支付 1使用，2不使用
        req.setCjsbh(newUuid);
        req.setZyjslx("1"); //住院结算类型
        req.setSffhjsmx("1");//返回明细
        req.setJsbh(this.getPreSettlement().getJsbh());
        return req;
    }

    public SelfSettleUploadReq toSelfSettleUploadReq(String newId) {
        SelfSettleUploadReq req = new SelfSettleUploadReq();
        req.setJzxh(this.getYbSignIn().getId());
        req.setSfsygzzf("0");//个账支付
        req.setCjsbh(newId);
        req.setZyjslx("1"); //住院结算类型
        Optional<ViewFeeSummary> optionalFeeInfo = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", this.getUuid()).findOneOrEmpty();
        if (optionalFeeInfo.isPresent())
            req.setFyze(optionalFeeInfo.get().getTotalAmount());
//        SelfSettleUploadDetailReq detailReq = new SelfSettleUploadDetailReq();
//        req.setYbjsxx(detailReq);
//
//        if (optionalFeeInfo.isPresent()) {
//            ViewFeeSummary feeSummary = optionalFeeInfo.get();
//            detailReq.setYBFY(BigDecimal.ZERO);
//            detailReq.setGRXJJE(feeSummary.getTotalAmount());
//            detailReq.setZLZE(BigDecimal.ZERO);
//            detailReq.setZFZE(feeSummary.getTotalAmount());
//            detailReq.setJE1(BigDecimal.ZERO);
//            detailReq.setJE2(BigDecimal.ZERO);
//            detailReq.setBXJE(BigDecimal.ZERO);
//        }
        return req;
    }


}
