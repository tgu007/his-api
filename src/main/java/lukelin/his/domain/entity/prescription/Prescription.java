package lukelin.his.domain.entity.prescription;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiClientException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.SnapshotSignInInfoInterface;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.entity.BaseMedicine;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionChangeAction;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderLineCommonDto;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderLineRespDto;
import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.mini_porgram.MiniPrescriptionDto;
import lukelin.his.dto.prescription.response.*;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;
import lukelin.his.system.Utils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "prescription.prescription")
public class Prescription extends BaseEntity implements DtoConvertible<PrescriptionRespDto>, SnapshotSignInInfoInterface {
    @Column(name = "type", nullable = false)
    private PrescriptionType prescriptionType;

    @Column(nullable = false)
    private PrescriptionStatus status;

    private String reference;

    @Column(name = "is_one_off", nullable = false)
    private boolean oneOff;

    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL)
    private List<PrescriptionChangeLog> changeLogList;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private PrescriptionGroup prescriptionGroup;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prescription")
    private PrescriptionChargeable prescriptionChargeable;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prescription")
    private PrescriptionMedicineText prescriptionMedicineText;

    @OneToMany(mappedBy = "prescription")
    private List<Fee> feeList;

    @ManyToOne
    @JoinColumn(name = "execute_department_id", nullable = false)
    private DepartmentTreatment department;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private WardRoomBed bed;

    @Column(name = "ward_name", nullable = false)
    private String WardName;

    @ManyToOne
    @JoinColumn(name = "ward_department_id")
    private DepartmentTreatment wardDepartment;

    @Column(name = "ward_department_name", nullable = false)
    private String wardDepartmentName;

//    @Column(name = " order_date")
//    private Date orderDate;

    //排序用
    @Column(name = " start_date")
    private Date startDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

//    public Date getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(Date orderDate) {
//        this.orderDate = orderDate;
//    }

    @Transient
    private Integer executedCount = 0;

    public Integer getExecutedCount() {
        return executedCount;
    }

    public void setExecutedCount(Integer executedCount) {
        this.executedCount = executedCount;
    }

    public DepartmentTreatment getWardDepartment() {
        return wardDepartment;
    }

    public void setWardDepartment(DepartmentTreatment wardDepartment) {
        this.wardDepartment = wardDepartment;
    }

    public String getWardDepartmentName() {
        return wardDepartmentName;
    }

    public void setWardDepartmentName(String wardDepartmentName) {
        this.wardDepartmentName = wardDepartmentName;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public WardRoomBed getBed() {
        return bed;
    }

    public void setBed(WardRoomBed bed) {
        this.bed = bed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Fee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
    }

    public lukelin.his.domain.entity.prescription.PrescriptionMedicineText getPrescriptionMedicineText() {
        return prescriptionMedicineText;
    }

    public void setPrescriptionMedicineText(lukelin.his.domain.entity.prescription.PrescriptionMedicineText prescriptionMedicineText) {
        this.prescriptionMedicineText = prescriptionMedicineText;
    }

    public PrescriptionChargeable getPrescriptionChargeable() {
        return prescriptionChargeable;
    }

    public void setPrescriptionChargeable(PrescriptionChargeable prescriptionChargeable) {
        this.prescriptionChargeable = prescriptionChargeable;
    }

    public PrescriptionGroup getPrescriptionGroup() {
        return prescriptionGroup;
    }

    public void setPrescriptionGroup(PrescriptionGroup prescriptionGroup) {
        this.prescriptionGroup = prescriptionGroup;
    }

    public PrescriptionType getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(PrescriptionType prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isOneOff() {
        return oneOff;
    }

    public void setOneOff(boolean oneOff) {
        this.oneOff = oneOff;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
        this.snapshotSignIn(patientSignIn.getUuid());
    }

    public List<PrescriptionChangeLog> getChangeLogList() {
        return changeLogList;
    }

    public void setChangeLogList(List<PrescriptionChangeLog> changeLogList) {
        this.changeLogList = changeLogList;
    }

    public DepartmentTreatment getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentTreatment department) {
        this.department = department;
    }

    public PrescriptionListRespDto toListRespDto() {

        PrescriptionListRespDto prescriptionListRespDto = new PrescriptionListRespDto();
        prescriptionListRespDto.setPopUpInfo("医嘱类型:" + this.getPrescriptionType().PrescriptionType());
        prescriptionListRespDto.setType(this.getPrescriptionType());
        prescriptionListRespDto.setStartDate(this.getStartDate());
        prescriptionListRespDto.setExecuteDepartment(this.department.getDepartment().getName());

        PrescriptionChangeLog disableLog = this.findDisableLog();
        if (disableLog != null) {
            prescriptionListRespDto.setEndDate(disableLog.getDate());
            prescriptionListRespDto.setStoppedBy(disableLog.getLogUser());
        }

        PrescriptionChangeLog confirmDisableLog = this.findConfirmDisableLog();
        if (confirmDisableLog != null)
            prescriptionListRespDto.setConfirmStopBy(confirmDisableLog.getLogUser());

        Optional<PrescriptionChangeLog> optionalApproveLog = this.findLastApproveLog();
        optionalApproveLog.ifPresent(prescriptionChangeLog -> prescriptionListRespDto.setApprovedBy(prescriptionChangeLog.getLogUser()));

        if (this.getPrescriptionGroup() != null)
            prescriptionListRespDto.setGroupId(this.getPrescriptionGroup().getUuid());

        if (this.getPrescriptionChargeable() != null)
            this.getPrescriptionChargeable().copyValueToListDto(prescriptionListRespDto);
        if (this.getPrescriptionMedicineText() != null)
            this.getPrescriptionMedicineText().copyValueToListDto(prescriptionListRespDto);


        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionListRespDto);
        prescriptionListRespDto.setWhoCreated(this.getWhoCreatedName());
        if (this.getPrescriptionType() == PrescriptionType.Text) {
            prescriptionListRespDto.setAllowEdit(true); //纯文本医嘱，始终可以修改
            //文本医嘱把备注显示在名称COLUMN
            prescriptionListRespDto.setDescription(this.reference);
            prescriptionListRespDto.setReference("");
        }

        List<PrescriptionChangeLogRespDto> changeLogRespDtoList = new ArrayList<>();
        for (PrescriptionChangeLog changeLog : this.getChangeLogList().stream().sorted(Comparator.comparing(PrescriptionChangeLog::getWhenCreated)).collect(Collectors.toList()))
            changeLogRespDtoList.add(changeLog.toDto());
        prescriptionListRespDto.setChangeLogRespDtoList(changeLogRespDtoList);

        prescriptionListRespDto.setPrintSlipTypeOne(false);
        prescriptionListRespDto.setPrintSlipTypeTwo(false);
        prescriptionListRespDto.setPrintSlipChinese(false);
        if (this.getPrescriptionType() == PrescriptionType.Medicine) {
            Medicine medicine = this.getPrescriptionChargeable().getPrescriptionMedicine().getMedicine();
            prescriptionListRespDto.setPrintSlipTypeOne(medicine.getSlipTypeOne());
            prescriptionListRespDto.setPrintSlipTypeTwo(medicine.getSlipTypeTwo());
            prescriptionListRespDto.setPrintSlipChinese(medicine.chineseMedicine());
            prescriptionListRespDto.setPopUpInfo(prescriptionListRespDto.getPopUpInfo() + "  规格:" + medicine.getDepartmentModel() + "  产地:" + medicine.getManufacturerMedicine().getName());
        }

        return prescriptionListRespDto;
    }

    public PrescriptionChangeLog findConfirmDisableLog() {
        Optional<PrescriptionChangeLog> optionalDisableLog = this.getChangeLogList().stream()
                .filter(log -> log.getAction() == PrescriptionChangeAction.confirmDisable).findFirst();
        return optionalDisableLog.orElse(null);
    }

    public PrescriptionChangeLog findDisableLog() {
        Optional<PrescriptionChangeLog> optionalDisableLog = this.getChangeLogList().stream()
                .filter(log -> log.getAction() == PrescriptionChangeAction.disable
                        || log.getAction() == PrescriptionChangeAction.cancel
                        || log.getAction() == PrescriptionChangeAction.executed
                        || log.getAction() == PrescriptionChangeAction.signOut).findFirst();
        return optionalDisableLog.orElse(null);
    }

    public PrescriptionRespDto toDto() {
        PrescriptionRespDto respDto = DtoUtils.convertRawDto(this);
        respDto.setDepartment(this.department.toDto());
        return respDto;
    }

    public Prescription clone(UUID patientSignInId) {
        Prescription clonedPrescription = new Prescription();

        //clonedPrescription.setOrderDate(new Date());
        BeanUtils.copyPropertiesIgnoreNull(this, clonedPrescription);
        //ResetId
        clonedPrescription.setUuid(null);
        clonedPrescription.setStatus(PrescriptionStatus.created);
        clonedPrescription.setDepartment(null);
        clonedPrescription.setStartDate(null);
        clonedPrescription.setPrescriptionGroup(null);

        PatientSignIn toPatientSignIn = Ebean.find(PatientSignIn.class, patientSignInId);
        if (this.getDepartment().getType() == DepartmentTreatmentType.ward)
            clonedPrescription.setDepartment(toPatientSignIn.getDepartmentTreatment());
        else
            clonedPrescription.setDepartment(this.department);
        clonedPrescription.setPatientSignIn(toPatientSignIn);

//        if (this.getPrescriptionType() == PrescriptionType.Treatment) {
//            Treatment treatment = Ebean.find(Treatment.class, this.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment().getUuid());
//            if (treatment.getExecuteDepartmentType() == DepartmentTreatmentType.recover)
//                this.setDepartment(treatment.getDefaultExecuteDepartment());
//        }

        if (this.getPrescriptionMedicineText() != null)
            clonedPrescription.setPrescriptionMedicineText(this.getPrescriptionMedicineText().toCloneEntity());
        if (this.getPrescriptionChargeable() != null) {
            PrescriptionChargeable clonedPrescriptionChargeable = this.getPrescriptionChargeable().toCloneEntity();
            clonedPrescription.setPrescriptionChargeable(clonedPrescriptionChargeable);
        }
        return clonedPrescription;
    }

//    public Date findStartDate() {
//        //
//        if (this.getManualStartDate() != null)
//            return this.getManualStartDate();
//        if (this.getPrescriptionChargeable() != null && this.getPrescriptionChargeable().getManualStartDate() != null)
//            return this.getPrescriptionChargeable().getManualStartDate();
//        Optional<PrescriptionChangeLog> optionalStartLog = this.findLastApproveLog();
//        return optionalStartLog.map(PrescriptionChangeLog::getDate).orElse(null);
//    }

    private Optional<PrescriptionChangeLog> findLastApproveLog() {
        return this.getChangeLogList().stream()
                .filter(log -> log.getAction() == PrescriptionChangeAction.approve)
                .max(Comparator.comparing(PrescriptionChangeLog::getDate));
    }

    public List<Fee> generateNewFee(Date feeDate, List<UUID> childTreatmentIdList) {
        List<Fee> newFeeList = new ArrayList<>();
        if (feeDate == null)
            feeDate = new Date();
        Fee newFee = new Fee();
        this.setNewFeeValue(newFee, feeDate, null);
        Ebean.save(newFee);

        if (childTreatmentIdList != null) {
            newFee.setFeeStatus(FeeStatus.calculated); //建立一条虚拟的总费用，用来判定医嘱是否执行过
            //newFee.setUnitAmountInfo(this.getListPrice() + newFee.getUomName());
            //newFee.setTotalAmount(newFee.getUnitAmount().multiply(newFee.getQuantity()).setScale(2, RoundingMode.UP))
            for (UUID treatmentId : childTreatmentIdList) {
                Treatment treatment = Ebean.find(Treatment.class, treatmentId);
                Fee childFee = new Fee();
                this.setNewFeeValue(childFee, feeDate, treatment);
                childFee.setParentFee(newFee);
                newFeeList.add(childFee);
            }
            newFee.setUnitAmount(newFeeList.stream().map(Fee::getUnitAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            newFee.setUnitAmountInfo(newFee.getUnitAmount() + newFee.getUomName());
            newFee.setTotalAmount(newFee.getUnitAmount().multiply(newFee.getQuantity()).setScale(2, RoundingMode.HALF_UP));
        }

        newFeeList.add(newFee);
        return newFeeList;
    }

    private void setNewFeeValue(Fee newFee, Date feeDate, Treatment treatment) {
        newFee.setPrescription(this);
        newFee.setFeeStatus(FeeStatus.confirmed);
        newFee.setPatientSignIn(this.getPatientSignIn());
        newFee.setDepartment(this.getDepartment());
        newFee.setDepartmentName(this.getDepartment().getDepartment().getName());
        newFee.setFeeDate(feeDate);
        if (treatment == null)
            this.getPrescriptionChargeable().setFeeValue(newFee);
        else {
            newFee.setQuantity(this.getPrescriptionChargeable().getQuantity());
            treatment.setFeeValue(newFee);
        }
    }

    public PrescriptionExecutionListRespDto toExecutionListRespDto() {
        PrescriptionExecutionListRespDto dto = new PrescriptionExecutionListRespDto();
        dto.setListRespDto(this.toListRespDto());
        PrescriptionTreatment prescriptionTreatment = this.getPrescriptionChargeable().getPrescriptionTreatment();
        if (prescriptionTreatment.getTreatment().getLabSampleType() != null)
            dto.setSampleType(prescriptionTreatment.getTreatment().getLabSampleType().getName());
        dto.setAllowedExecutionCount(prescriptionTreatment.getAllowedExecutionCount());
        dto.setExecutedCount(this.getExecutedCount());
        this.copyPrescriptionValue(dto);
        if (prescriptionTreatment.getTreatment().getCombo()) {
            List<TreatmentRespDto> childTreatmentList = new ArrayList<>();
            for (Treatment childTreatment : prescriptionTreatment.getTreatment().getChildTreatmentSet())
                childTreatmentList.add(childTreatment.toDto());
            dto.setChildTreatmentList(childTreatmentList);
        }
        return dto;
    }

    public PrescriptionMedicineOrderLineRespDto toPendingOrderLineRespDto(Integer orderQuantity) {
        PrescriptionMedicineOrderLineRespDto dto = new PrescriptionMedicineOrderLineRespDto();
        dto.setStatus(PrescriptionMedicineOrderLineStatus.pending);
        dto.setPrescriptionMedicineId(this.getPrescriptionChargeable().getPrescriptionMedicine().getUuid());
        Medicine medicine = this.getPrescriptionChargeable().getPrescriptionMedicine().getMedicine();
        this.getPrescriptionChargeable().getPrescriptionMedicine().setPrescriptionInfo(dto);
        dto.setOrderLineCommon(this.toOrderLineCommonDto(medicine));
        dto.setOrderQuantity(orderQuantity);
        dto.setOrderQuantityInfo(medicine.getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(orderQuantity)));
        dto.setSlipRequired(medicine.getSlipTypeOne() || medicine.getSlipTypeTwo());
        this.copyPrescriptionValue(dto);
        return dto;
    }

    public PrescriptionMedicineOrderLineCommonDto toOrderLineCommonDto(BaseMedicine baseMedicine) {
        PrescriptionMedicineOrderLineCommonDto dto = new PrescriptionMedicineOrderLineCommonDto();
        PrescriptionMedicine prescriptionMedicine = this.getPrescriptionChargeable().getPrescriptionMedicine();
        dto.setPharmacyModel(baseMedicine.getDepartmentModel());
        dto.setPriceInfo(baseMedicine.getListPrice().divide(baseMedicine.getDepartmentToMinRate(), 4, RoundingMode.HALF_UP) + "元/" + baseMedicine.getMinUom().getName());
        dto.setMedicineName(baseMedicine.getName());
        dto.setManufacturer(baseMedicine.getManufacturerMedicine().getName());
        String serveQuantityInfo = prescriptionMedicine.getServeQuantity().toString() + baseMedicine.getServeUom().getName();
        dto.setUseMethodInfo(serveQuantityInfo + "/" + prescriptionMedicine.getPrescriptionChargeable().getFrequency().getCode() + "/" + prescriptionMedicine.getUseMethod().getName());
        //dto.setOrderQuantity(prescriptionMedicine.getIssueQuantity().intValue());
        //dto.setOrderQuantityInfo(dto.getOrderQuantity() + baseMedicine.getDepartmentUom().getName());
        return dto;
    }

    private void copyPrescriptionValue(BaseWardPatientListRespDto dto) {
        dto.setPatientSignInId(this.getPatientSignIn().getUuid());
        dto.setPatientName(this.getPatientSignIn().getPatient().getName());
    }


    public int daysFromPrescriptionStart(Date compareDate) {
        LocalDate prescriptionStartDate = LocalDateTime.ofInstant(this.getStartDate().toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localCompareDate = LocalDateTime.ofInstant(compareDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
        int daysBetween = (int) ((localCompareDate.toEpochDay() - prescriptionStartDate.toEpochDay())) + 1;
        if (!this.executeOnPrescriptionStartDate())
            daysBetween--;//如果未注明了首日，则开始日为开嘱日的后一天
        return daysBetween;
    }

    public boolean executeOnPrescriptionStartDate() {
        if (this.getPrescriptionChargeable() != null)
            return this.getPrescriptionChargeable().executeOnPrescriptionStartDate();
        else if (this.getPrescriptionMedicineText() != null) {
            String firstDayQuantity = this.getPrescriptionMedicineText().getFirstDayQuantity();
            if (firstDayQuantity != null && !StringUtils.isEmpty(firstDayQuantity))
                return true;
            else
                return false;
        } else return true;
    }

//    public Dictionary getUseMethod() {
//        if(this.getPrescriptionType() == PrescriptionType.Medicine)
//            return this.getPrescriptionChargeable().getPrescriptionMedicine().getUseMethod();
//        else if(this.getPrescriptionType() == PrescriptionType.TextMedicine)
//            return this.getPrescriptionMedicineText().getUseMethod();
//        else
//            throw new ApiClientException("Invalid prescription type");
//    }

    public Dictionary getFrequency() {
        if (this.getPrescriptionChargeable() != null)
            return getPrescriptionChargeable().getFrequency();
        else if (this.getPrescriptionType() == PrescriptionType.TextMedicine)
            return this.getPrescriptionMedicineText().getFrequency();
        else
            throw new ApiClientException("Invalid prescription type");

    }

//    public Prescription createNewPrescriptionFromThis(PrescriptionType prescriptionType, Boolean isOneOff) {
//        Prescription newPrescription = new Prescription();
//        newPrescription.setPrescriptionType(prescriptionType);
//        newPrescription.setDescription(this.getDescription());
//        newPrescription.setStatus(PrescriptionStatus.created);
//        newPrescription.setOneOff(isOneOff);
//        newPrescription.setPatientSignIn(this.getPatientSignIn());
//        newPrescription.setDepartment(this.getDepartment());
//        newPrescription.setReference(this.getReference());
//        return newPrescription;
//    }

    public PrescriptionNursingCardRespDto toNursingCardDto() {
        if (this.getPrescriptionType() == PrescriptionType.TextMedicine)
            return this.getPrescriptionMedicineText().toMedicineCardDto();
        else if (this.getPrescriptionType() == PrescriptionType.Medicine)
            return this.getPrescriptionChargeable().getPrescriptionMedicine().toMedicineCardDto();
        else if (this.getPrescriptionType() == PrescriptionType.Treatment)
            return this.getPrescriptionChargeable().getPrescriptionTreatment().toTreatmentCardDto();
        else
            throw new ApiClientException("Invalid prescription type");

    }

    public PrescriptionChangedListRespDto toChangedListRespDto() {
        PrescriptionChangedListRespDto dto = new PrescriptionChangedListRespDto();
        dto.setListRespDto(this.toListRespDto());
        if (dto.getListRespDto().getStatus() == PrescriptionStatus.approved) {
            if (this.processed())
                dto.setChangedStatus("已处理");
            else
                dto.setChangedStatus("新医嘱");
        } else
            dto.setChangedStatus(dto.getListRespDto().getStatus().toDescription());
        dto.setPatientSignInId(this.getPatientSignIn().getUuid());
        dto.setPatientName(this.getPatientSignIn().getPatient().getName());
        return dto;
    }

    private boolean processed() {
        if (this.getPrescriptionType() == PrescriptionType.Treatment)
            return this.getPrescriptionChargeable().getPrescriptionTreatment().getLastExecutionTime() != null;
        else if (this.getPrescriptionType() == PrescriptionType.Medicine)
            return this.getPrescriptionChargeable().getPrescriptionMedicine().getOrderLineList().size() > 0;
        else
            return true;
    }

    public MiniPrescriptionDto toMiniPrescriptionDto() {
        MiniPrescriptionDto dto = new MiniPrescriptionDto();
        dto.setUuid(this.getUuid());
        dto.setFrequency(this.getPrescriptionChargeable().getFrequency().getCode());
        dto.setDescription(this.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment().getName());
        dto.setAllowedExecutionCount(this.getPrescriptionChargeable().getPrescriptionTreatment().getAllowedExecutionCount());
        return dto;
    }

    public UUID getEntityId() {
        if (this.getPrescriptionType() == PrescriptionType.Medicine)
            return this.getPrescriptionChargeable().getPrescriptionMedicine().getMedicine().getUuid();
        else if (this.getPrescriptionType() == PrescriptionType.Treatment)
            return this.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment().getUuid();
        else
            return null;
    }

    public Integer getDuration() {
        Date startDate = this.getStartDate();
        PrescriptionChangeLog disableLog = this.findDisableLog();
        Date endDate;
        if (disableLog == null)
            endDate = new Date();
        else
            endDate = disableLog.getDate();
        return Utils.findDaysBetween(startDate, endDate) + 1;
    }

    public PrescriptionDescriptionListRespDto toPrescriptionDescriptionDto() {
        PrescriptionDescriptionListRespDto respDto = new PrescriptionDescriptionListRespDto();

        String description = "";
        PrescriptionChargeable prescriptionChargeable = this.getPrescriptionChargeable();

        if (this.getPrescriptionType() == PrescriptionType.Medicine) {
            PrescriptionMedicine prescriptionMedicine = prescriptionChargeable.getPrescriptionMedicine();
            description = this.getDescription() + " " + prescriptionMedicine.getServeQuantity().toString() + prescriptionMedicine.getMedicine().getServeUom().getName();
            description += " " + prescriptionChargeable.getFrequency().getName() + " " + prescriptionChargeable.getPrescriptionMedicine().getUseMethod().getName();
        } else if (this.getPrescriptionType() == PrescriptionType.TextMedicine) {
            description = this.getDescription();
            if (this.getPrescriptionMedicineText().getServeQuantity() != null)
                description += " " + this.getPrescriptionMedicineText().getServeQuantity();
            description += " " + this.getPrescriptionMedicineText().getFrequency().getName() + " " + this.prescriptionMedicineText.getUseMethod().getName();
        } else if (this.getPrescriptionType() == PrescriptionType.Treatment) {
            description = this.getDescription() + " " + prescriptionChargeable.getQuantity().toString() + prescriptionChargeable.getPrescriptionTreatment().getTreatment().getMinUom().getName()
                    + " " + this.prescriptionChargeable.getFrequency().getName();
        } else
            description = this.getDescription();
        respDto.setDescription(description);
        respDto.setStartDate(this.getStartDate());
        return respDto;
    }

    public PrescriptionExecutionLog generateLog() {
        PrescriptionExecutionLog log = new PrescriptionExecutionLog();
        log.setPatientSignIn(this.getPatientSignIn());
        log.setDepartment(this.getDepartment());
        log.setDescription(this.getDescription());
        log.setWardName(this.patientSignIn.getCurrentBed().getWardRoom().getWard().getName());
        log.setPrescription(this);
        log.setBedName(this.getPatientSignIn().getCurrentBed().getName());
        return log;
    }
}
