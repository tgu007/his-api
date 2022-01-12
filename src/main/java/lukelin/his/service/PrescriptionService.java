package lukelin.his.service;

import io.ebean.*;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.springboot.service.MessageService;
import lukelin.his.domain.Interfaces.PrescriptionSaveDtoInterface;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.Frequency;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.*;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.domain.enums.Prescription.*;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderLineRespDto;
import lukelin.his.dto.prescription.request.*;
import lukelin.his.dto.prescription.request.filter.PredefinePrescriptionFilterDto;
import lukelin.his.dto.prescription.request.filter.PrescriptionFilterDto;
import lukelin.his.dto.prescription.response.PrescriptionSignatureRespDto;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;
import lukelin.his.system.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static lukelin.his.domain.enums.Prescription.PrescriptionType.Medicine;

@Service
public class PrescriptionService extends BaseHisService {
    @Autowired
    private EbeanServer ebeanServer;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BasicService basicService;

    @Autowired
    private PatientSignInService patientSignInService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Transactional
    public void savePrescription(PrescriptionSaveDtoInterface prescriptionSaveDtoInterface) {
        Prescription prescription = prescriptionSaveDtoInterface.toEntity();

        this.patientSignInService.validatePatientSignInStatus(prescription.getPatientSignIn().getUuid(), PatientSignInStatus.signedIn);

        if (prescription.getUuid() != null)
            ebeanServer.update(prescription);
        else {
            prescription.setStartDate(new Date());
            ebeanServer.save(prescription);
        }


        if (prescriptionSaveDtoInterface instanceof PrescriptionMedicineSaveDto) {
            PrescriptionMedicineSaveDto medicineSaveDto = (PrescriptionMedicineSaveDto) prescriptionSaveDtoInterface;
            if (medicineSaveDto.getUuid() != null)
                return; //只能用于新医嘱
            if (medicineSaveDto.getBatchNewMedicineList() != null && medicineSaveDto.getBatchNewMedicineList().size() > 0) {

                List<Prescription> newPrescriptionList = new ArrayList<>();
                for (PrescriptionGroupMedicineSaveDto saveDto : medicineSaveDto.getBatchNewMedicineList()) {
                    Medicine medicine = ebeanServer.find(Medicine.class, saveDto.getMedicineId());
                    Prescription newPrescription = prescription.clone(prescription.getPatientSignIn().getUuid());
                    PrescriptionChargeable prescriptionChargeable = newPrescription.getPrescriptionChargeable();
                    PrescriptionMedicine prescriptionMedicine = prescriptionChargeable.getPrescriptionMedicine();

                    prescriptionMedicine.setMedicine(medicine);
                    MedicineSnapshot medicineSnapshot = medicine.findLatestSnapshot();
                    prescriptionMedicine.setMedicineSnapshot(medicineSnapshot);
                    prescriptionMedicine.setServeQuantity(saveDto.getServeQuantity());
                    prescriptionChargeable.setQuantity(saveDto.getServeQuantity().divide(medicineSnapshot.getServeToMinRate(), 0, RoundingMode.CEILING));
                    if (prescription.isOneOff()) {
                        prescriptionMedicine.setFixedChineseMedicineQuantity(null);
                        prescriptionMedicine.setFixedQuantity(saveDto.getIssueQuantity());
                        if (prescriptionMedicine.getMedicine().chineseMedicine()) {
                            prescriptionMedicine.setFixedChineseMedicineQuantity(medicineSaveDto.getFixedQuantity());
                            //总数量帖数乘以数量
                            prescriptionMedicine.setFixedQuantity(prescriptionMedicine.getFixedChineseMedicineQuantity().multiply(prescriptionChargeable.getQuantity()).setScale(2, RoundingMode.CEILING));
                        }
                    }

                    newPrescription.setStartDate(new Date());
                    newPrescription.setDescription(medicine.getName());
                    newPrescriptionList.add(newPrescription);
                }
                ebeanServer.saveAll(newPrescriptionList);

                //设立组套
                PrescriptionGroupReqDto groupReqDto = new PrescriptionGroupReqDto();
                groupReqDto.setGroup(true);
                groupReqDto.setPrescriptionIdList(newPrescriptionList.stream().map(Prescription::getUuid).collect(Collectors.toList()));
                groupReqDto.getPrescriptionIdList().add(prescription.getUuid());
                this.groupPrescription(groupReqDto);

            }
        }
    }

    public PrescriptionMedicine getMedicinePrescriptionDetail(UUID prescriptionId) {
        return this.findById(Prescription.class, prescriptionId).getPrescriptionChargeable().getPrescriptionMedicine();
    }

    public PrescriptionMedicineText getMedicineTextPrescriptionDetail(UUID prescriptionId) {
        return this.findById(Prescription.class, prescriptionId).getPrescriptionMedicineText();
    }

    public Prescription getTextPrescriptionDetail(UUID prescriptionId) {
        return this.findById(Prescription.class, prescriptionId);
    }

    public PrescriptionTreatment getTreatmentPrescriptionDetail(UUID prescriptionId) {
        return this.findById(Prescription.class, prescriptionId).getPrescriptionChargeable().getPrescriptionTreatment();
    }


    public List<Prescription> getPrescriptionList(PrescriptionFilterDto filterDto) {
        return this.buildPrescriptionListQuery(filterDto).findList();
    }

    public PagedList<Prescription> getPagedPrescriptionList(PrescriptionFilterDto filterDto, int pageNumber, int pageSize) {
        ExpressionList<Prescription> el = this.buildPrescriptionListQuery(filterDto).where();
        return this.findPagedList(el, pageNumber, pageSize);
    }

    @Transactional
    public List<Prescription> getChangedPrescriptionList(PrescriptionFilterDto filterDto) {
        PrescriptionStatus[] changedStatusArray = new PrescriptionStatus[]{PrescriptionStatus.approved, PrescriptionStatus.disabled, PrescriptionStatus.canceled};
        filterDto.setPrescriptionStatusList(Arrays.asList(changedStatusArray));
        PrescriptionType[] prescriptionTypeArray = new PrescriptionType[]{Medicine, PrescriptionType.Treatment, PrescriptionType.TextMedicine, PrescriptionType.Text};
        filterDto.setPrescriptionTypeList(Arrays.asList(prescriptionTypeArray));

        Query query = this.buildPrescriptionListQuery(filterDto).query();
        return query.findList();
    }

    @Transactional
    public List<Prescription> getPendingExecutionPrescriptionList(PrescriptionFilterDto filterDto, Boolean executedPrescription) {
        PrescriptionStatus[] changedStatusArray;
        if (executedPrescription) //查看做过的项目包含已经停止的医嘱
            changedStatusArray = new PrescriptionStatus[]{PrescriptionStatus.approved, PrescriptionStatus.disabled};
        else
            changedStatusArray = new PrescriptionStatus[]{PrescriptionStatus.approved};
        filterDto.setPrescriptionStatusList(Arrays.asList(changedStatusArray));
        PrescriptionType[] prescriptionTypeArray = new PrescriptionType[]{PrescriptionType.Treatment};
        filterDto.setPrescriptionTypeList(Arrays.asList(prescriptionTypeArray));

        List<UUID> treatmentIdList = null;
        if (filterDto.getEmployeeId() != null)
            treatmentIdList = this.findById(Employee.class, filterDto.getEmployeeId()).getTreatmentList().stream().map(Treatment::getUuid).collect(Collectors.toList());

        List<Prescription> allPatientPrescriptionList = this.buildPrescriptionListQuery(filterDto, treatmentIdList).query().findList();

        return this.filterOnExecutionHistory(allPatientPrescriptionList, filterDto.getRecordFeeDate(), executedPrescription);
    }

    private List<Prescription> filterOnExecutionHistory(List<Prescription> allPatientPrescriptionList, Date recordFeeDate, Boolean executedPrescription) {
        List<Prescription> filteredPrescriptionList = new ArrayList<>();
        for (Prescription prescription : allPatientPrescriptionList) {
            Integer allowedExecutionCount = this.getAllowedExecutionCount(prescription, recordFeeDate, prescription.getEntityId());
            if ((allowedExecutionCount > 0 && !executedPrescription) || (prescription.getExecutedCount() > 0 && executedPrescription)) {
                prescription.getPrescriptionChargeable().getPrescriptionTreatment().setAllowedExecutionCount(allowedExecutionCount);
                filteredPrescriptionList.add(prescription);
            }
        }
        return filteredPrescriptionList;
    }

    public Integer getAllowedExecutionCount(Prescription prescription, Date recordFeeDate, UUID feeEntityId) {
        //检查是否是首日
        LocalDateTime feeDate;
        if (recordFeeDate == null)
            feeDate = LocalDateTime.now();
        else
            feeDate = LocalDateTime.ofInstant(recordFeeDate.toInstant(), ZoneId.systemDefault());
        Integer allowedExecutionCount = 0;
        LocalDateTime prescriptionStartDate = LocalDateTime.ofInstant(prescription.getStartDate().toInstant(), ZoneId.systemDefault());
        PrescriptionChargeable prescriptionChargeable = prescription.getPrescriptionChargeable();
        PrescriptionMedicineText prescriptionMedicineText = prescription.getPrescriptionMedicineText();
        if (feeEntityId == null)
            feeEntityId = prescription.getEntityId();

        Integer executedFeeCount = 0;
        //Todo 建立一个INTERFACE来提取PRESCRIPTION CHARGEABEL 和 文字药品的公用属性
        if (!feeDate.isBefore(prescriptionStartDate)) {
            if (feeDate.toLocalDate().equals(prescriptionStartDate.toLocalDate())) {
                if (prescriptionChargeable != null && prescriptionChargeable.executeOnPrescriptionStartDate())   //检查第一天是否要执行
                    allowedExecutionCount = prescriptionChargeable.getFirstDayQuantity().intValue();
                if (prescriptionMedicineText != null && prescriptionMedicineText.executeOnPrescriptionStartDate())
                    allowedExecutionCount = Integer.valueOf(prescription.getPrescriptionMedicineText().getFirstDayQuantity());

                LocalDateTime startDate = LocalDateTime.of(feeDate.toLocalDate(), LocalTime.MIN);
                LocalDateTime endDate = LocalDateTime.of(feeDate.toLocalDate(), LocalTime.MAX);
                int todayFeeCount = this.accountService.getPrescriptionFeeCount(prescription, startDate, endDate, feeEntityId);
                if (allowedExecutionCount > 0) {
                    allowedExecutionCount = allowedExecutionCount - todayFeeCount;
                }
                executedFeeCount = todayFeeCount;


            } else {
                //如不是首日，根据频次检查是否当天需要执行
                Frequency frequency = null;
                if (prescriptionChargeable != null)
                    frequency = prescriptionChargeable.getFrequency().getFrequencyInfo();
                else if (prescriptionMedicineText != null)
                    frequency = prescriptionMedicineText.getFrequency().getFrequencyInfo();

                if (frequency == null)
                    return 0;
                //获取当天和第一天需执行的天数
                int daysBetween = Math.abs((int) ((feeDate.toLocalDate().toEpochDay() - prescriptionStartDate.toLocalDate().toEpochDay())));

                long differenceInMinutes = Duration.between(prescriptionStartDate.atZone(ZoneId.systemDefault()).toInstant(), feeDate.atZone(ZoneId.systemDefault()).toInstant()).toMinutes();
                if (differenceInMinutes > 1440 && prescription.isOneOff()) //如果是临时医嘱并且超过一天，不允许执行
                    return 0;
                if (frequency.getDayInterval() != null) { //按时间间隔来计算是否需要执行
                    if (prescriptionChargeable != null && !prescriptionChargeable.executeOnPrescriptionStartDate())
                        daysBetween--;//如果注明了首日，则开始日为开嘱日的后一天
                    if (prescriptionMedicineText != null && !prescriptionMedicineText.executeOnPrescriptionStartDate())
                        daysBetween--;//如果注明了首日，则开始日为开嘱日的后一天
                    if (daysBetween % frequency.getDayInterval() == 0) {
                        allowedExecutionCount = frequency.getDayQuantity();
                        LocalDateTime startDate = LocalDateTime.of(feeDate.toLocalDate(), LocalTime.MIN);
                        LocalDateTime endDate = LocalDateTime.of(feeDate.toLocalDate(), LocalTime.MAX);
                        int todayFeeCount = this.accountService.getPrescriptionFeeCount(prescription, startDate, endDate, feeEntityId);
                        allowedExecutionCount = allowedExecutionCount - todayFeeCount;
                        executedFeeCount = todayFeeCount;
                    }
                } else if (frequency.getDayRange() != null) //根据周期性来计算允许执行次数
                {
                    int rangeIndex = daysBetween / frequency.getDayRange();
                    LocalDateTime rangeStartDate = prescriptionStartDate.plusDays(rangeIndex * frequency.getDayRange()).toLocalDate().atTime(feeDate.toLocalTime());
                    LocalDateTime rangeEndDate = rangeStartDate.plusDays(frequency.getDayRange());
                    if (feeDate.toLocalDate().equals(rangeStartDate.toLocalDate())) {
                        if ((prescriptionChargeable != null && !prescriptionChargeable.executeOnPrescriptionStartDate())
                                || (prescriptionMedicineText != null && !prescriptionMedicineText.executeOnPrescriptionStartDate())
                        ) {
                            return 0;
                        }
                    }
                    int feeCount = this.accountService.getPrescriptionFeeCount(prescription, rangeStartDate, rangeEndDate, feeEntityId);
                    if (frequency.getRangeLimit() - feeCount > 0)
                        return frequency.getDayQuantity();
                    executedFeeCount = feeCount;
                }
            }
        }
        prescription.setExecutedCount(executedFeeCount);
        return allowedExecutionCount;
    }

    private ExpressionList<Prescription> buildPrescriptionListQuery(PrescriptionFilterDto filterDto) {
        return this.buildPrescriptionListQuery(filterDto, null);
    }

    private ExpressionList<Prescription> buildPrescriptionListQuery(PrescriptionFilterDto filterDto, List<UUID> treatmentIdList) {
        String orderByColumn = "startDate, whenCreated";
        if (filterDto.getOrderByDesc())
            orderByColumn = "startDate desc, whenCreated desc";
        ExpressionList<Prescription> el = ebeanServer.find(Prescription.class).orderBy(orderByColumn)
                .where();
        if (filterDto.getUuid() != null)
            el = el.eq("uuid", filterDto.getUuid());
        else {
            if (treatmentIdList != null)
                el = el.in("prescriptionChargeable.prescriptionTreatment.treatment.uuid", treatmentIdList);

            if (filterDto.getPatientSignInIdList() != null)
                el = el.in("patientSignIn.uuid", filterDto.getPatientSignInIdList());

            if (filterDto.getOneOff() != null)
                el = el.eq("oneOff", filterDto.getOneOff());

            if (filterDto.getPrescriptionStatusList() != null)
                el = el.in("status", filterDto.getPrescriptionStatusList());

            if (filterDto.getPrescriptionTypeList() != null)
                el = el.in("prescriptionType", filterDto.getPrescriptionTypeList());

            if (filterDto.getDepartmentId() != null)
                el = el.eq("department.uuid", filterDto.getDepartmentId());

            if (filterDto.getDepartmentIdList() != null)
                el = el.in("department.uuid", filterDto.getDepartmentIdList());

            if (filterDto.getChangedStartDate() != null && filterDto.getChangedEndDate() != null)
                el = el.between("whenModified", filterDto.getChangedStartDate(), filterDto.getChangedEndDate());

            if (filterDto.getUseMethodList() != null)
                el = el.or()
                        .in("prescriptionMedicineText.useMethod.code", filterDto.getUseMethodList())
                        .in("prescriptionChargeable.prescriptionMedicine.useMethod.code", filterDto.getUseMethodList())
                        .endOr();

            if (filterDto.getMedicineTypeId() != null)
                el = el.eq("prescriptionChargeable.prescriptionMedicine.medicine.type.id", filterDto.getMedicineTypeId());

            if (filterDto.getTreatmentCardFilter() != null)
                el = el.eq("department.type", DepartmentTreatmentType.ward)
                        .eq("prescriptionChargeable.prescriptionTreatment.treatment.showInCard", true);

            if (filterDto.getLabBottleCardFilter() != null)
                el = el.eq("department.type", DepartmentTreatmentType.jianyan);

            if (filterDto.getDescription() != null)
                el = el.like("description", "%" + filterDto.getDescription() + "%");

            if (filterDto.getStartDate() != null && filterDto.getEndDate() != null)
                el = el.between("startDate", filterDto.getStartDate(), filterDto.getEndDate());

        }
        return el;
    }


    //Todo 应把ACTION设计成表格并记录ACTION前所处的状态和ACTION后所处的状态
    private List<Prescription> getValidatedPrescriptionListByStatus(PrescriptionChangeStatusReqDto reqDto, PrescriptionStatus validStatus) {
        List<Prescription> prescriptionList = ebeanServer.find(Prescription.class)
                .where()
                .eq("status", validStatus)
                .and()
                .in("uuid", reqDto.getPrescriptionIdList())
                .orderBy("startDate desc")
                .findList();

        //存在组套中的医嘱必须同时操作
        List<Prescription> sameGroupPrescriptionList = new ArrayList<>();
        for (Prescription prescription : prescriptionList) {
            PrescriptionGroup group = prescription.getPrescriptionGroup();
            if (group == null)
                continue;
            List<Prescription> groupPrescriptionList = group.getPrescriptionList();
            if (groupPrescriptionList.stream().anyMatch(gp -> !prescriptionList.contains(gp)))
                sameGroupPrescriptionList.addAll(groupPrescriptionList.stream().filter(gp -> !prescriptionList.contains(gp)).collect(Collectors.toList()));
        }
        prescriptionList.addAll(sameGroupPrescriptionList);
        return prescriptionList;
    }

    @Transactional
    public List<Prescription> checkPendingCancelPrescriptionFee(PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.getValidatedPrescriptionListByStatus(reqDto, PrescriptionStatus.approved);
        prescriptionList.addAll(this.getValidatedPrescriptionListByStatus(reqDto, PrescriptionStatus.disabled));

        //作废费用
        for (Prescription prescription : prescriptionList) {
            List<Fee> feeListToCancel = prescription.getFeeList().stream().filter(f -> f.getFeeStatus() == FeeStatus.confirmed).collect(Collectors.toList());
            this.accountService.cancelFee(feeListToCancel, "Prescription Cancel");
            this.doPrescriptionUpdate(prescription, PrescriptionStatus.canceled, PrescriptionChangeAction.cancel);
        }
        return prescriptionList;
    }

    @Transactional
    public List<Prescription> updatePrescriptionStatus(PrescriptionStatus fromStatus, PrescriptionStatus toStatus, PrescriptionChangeAction action, PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.getValidatedPrescriptionListByStatus(reqDto, fromStatus);

        for (Prescription prescription : prescriptionList) {
            this.doPrescriptionUpdate(prescription, toStatus, action);
        }
        return prescriptionList;
    }

    public void doPrescriptionUpdate(Prescription prescription, PrescriptionStatus toStatus, PrescriptionChangeAction action) {
        prescription.setStatus(toStatus);
        if (action == PrescriptionChangeAction.submit)
            prescription.setStartDate(new Date());
        else if (action == PrescriptionChangeAction.reject)
            prescription.setStartDate(null);
        else if ((action == PrescriptionChangeAction.disable
                || action == PrescriptionChangeAction.signOut
                || action == PrescriptionChangeAction.cancel
        ) && prescription.getPrescriptionType() == Medicine) {
            //检查是否有处理中的发药单
            Optional<PrescriptionMedicineOrderLine> optionalOpenOrderLine =
                    prescription.getPrescriptionChargeable().getPrescriptionMedicine().getOrderLineList().stream()
                            .filter(ol -> ol.getOrder().getStatus() != PrescriptionMedicineOrderStatus.confirmed).findFirst();
            if (optionalOpenOrderLine.isPresent())
                throw new ApiValidationException("有已经提交但还未处理的发药单，此发药单处理完毕前无法关停或者作废医嘱，发药单号：" + optionalOpenOrderLine.get().getOrder().getOrderNumberCode());
        }
        ebeanServer.update(prescription);

        PrescriptionChangeLog newLog = new PrescriptionChangeLog();
        newLog.setPrescription(prescription);
        newLog.setAction(action);
        if (action == PrescriptionChangeAction.approve) {
            Date manualDate = this.generateLogDate(prescription.getStartDate());
            Date currentDate = new Date();
            if (currentDate.before(manualDate))
                manualDate = currentDate;
            newLog.setManualDate(manualDate);
        }
        ebeanServer.save(newLog);
    }

    private Date generateLogDate(Date lastLogDate) {
        Instant instant = lastLogDate.toInstant();
        long randomMinutesToPlus = 5 + (int) (Math.random() * 10);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime afterRandomMinutes = instant.atZone(zoneId).toLocalDateTime().plusMinutes(randomMinutesToPlus);
        return Date.from(afterRandomMinutes.atZone(ZoneId.systemDefault()).toInstant());
    }


    public void restoreDisabledPrescription(UUID prescriptionId) {
        Prescription prescription = ebeanServer.find(Prescription.class, prescriptionId);
        if (prescription.getStatus() != PrescriptionStatus.disabled)
            throw new ApiValidationException("医嘱没有被停用，尝试刷新");
        PrescriptionChangeLog confirmDisableLog = prescription.findConfirmDisableLog();
        if (confirmDisableLog != null)
            ebeanServer.delete(confirmDisableLog);
        PrescriptionChangeLog disableLog = prescription.findDisableLog();
        if (disableLog != null)
            ebeanServer.delete(disableLog);
        prescription.setStatus(PrescriptionStatus.approved);
        ebeanServer.save(prescription);
    }


    @Transactional
    public void groupPrescription(PrescriptionGroupReqDto groupReqDto) {
        List<Prescription> prescriptionList = ebeanServer.find(Prescription.class)
                .where()
                .in("uuid", groupReqDto.getPrescriptionIdList()).findList();
        this.validateGroupPrescription(prescriptionList, groupReqDto.isGroup());

        PrescriptionGroup newGroup = null;
        if (groupReqDto.isGroup()) {
            newGroup = new PrescriptionGroup();
            ebeanServer.save(newGroup);
        }

        List<PrescriptionGroup> oldGroupList = new ArrayList<>();
        for (Prescription prescription : prescriptionList) {
            if (prescription.getPrescriptionGroup() != null)
                oldGroupList.add(prescription.getPrescriptionGroup());
            prescription.setPrescriptionGroup(newGroup);
            ebeanServer.update(prescription);
        }
        //删除没有医嘱的GROUP
        for (PrescriptionGroup group : oldGroupList) {
            if (group.getPrescriptionList().size() <= 1) {
                if (group.getPrescriptionList().size() == 1) {
                    group.getPrescriptionList().get(0).setPrescriptionGroup(null);
                    ebeanServer.update(group.getPrescriptionList().get(0));
                }
                ebeanServer.delete(group);
            }
        }
    }

    private void validateGroupPrescription(List<Prescription> prescriptionList, boolean isGroup) {
        if (prescriptionList.stream().anyMatch(p -> p.getStatus() != PrescriptionStatus.created))
            throw new ApiValidationException("prescription.group.invalid.status");

        if (isGroup) {
            if (prescriptionList.size() <= 1)
                throw new ApiValidationException("prescription.group.invalid.number");

            if (prescriptionList.stream().anyMatch(p -> p.getPrescriptionGroup() != null))
                throw new ApiValidationException("prescription.group.invalid.existingGroup");

            List<PrescriptionMedicine> prescriptionMedicineList = prescriptionList.stream().filter(p -> p.getPrescriptionType() == Medicine).map(p -> p.getPrescriptionChargeable().getPrescriptionMedicine()).collect(Collectors.toList());
            long medicineCount = prescriptionMedicineList.stream().map(PrescriptionMedicine::getMedicine).distinct().count();
            if (medicineCount != prescriptionMedicineList.size())
                throw new ApiValidationException("prescription.group.invalid.sameMedicine");

            List<PrescriptionTreatment> prescriptionTreatmentList = prescriptionList.stream().filter(p -> p.getPrescriptionType() == PrescriptionType.Treatment).map(p -> p.getPrescriptionChargeable().getPrescriptionTreatment()).collect(Collectors.toList());
            long treatmentCount = prescriptionTreatmentList.stream().map(PrescriptionTreatment::getTreatment).distinct().count();
            if (treatmentCount != prescriptionTreatmentList.size())
                throw new ApiValidationException("prescription.group.invalid.sameTreatment");

            List<PrescriptionMedicineText> prescriptionMedicineTextList = prescriptionList.stream().filter(p -> p.getPrescriptionType() == PrescriptionType.TextMedicine).map(p -> p.getPrescriptionMedicineText()).collect(Collectors.toList());

            List<Dictionary> medicineUseMethodList = prescriptionMedicineList.stream().map(PrescriptionMedicine::getUseMethod).distinct().collect(Collectors.toList());
            if (medicineUseMethodList.size() > 1)
                throw new ApiValidationException("prescription.group.invalid.multiUseMethod");

            List<Dictionary> medicineTextUseMethodList = prescriptionMedicineTextList.stream().map(PrescriptionMedicineText::getUseMethod).distinct().collect(Collectors.toList());
            if (medicineTextUseMethodList.size() > 1)
                throw new ApiValidationException("prescription.group.invalid.multiUseMethod");

            if (medicineUseMethodList.size() > 0 && medicineTextUseMethodList.size() > 0 && medicineUseMethodList.get(0).getId() != medicineTextUseMethodList.get(0).getId())
                throw new ApiValidationException("prescription.group.invalid.multiUseMethod");

            List<Dictionary> chargeAbleFrequencyList = prescriptionList.stream().filter(p -> p.getPrescriptionChargeable() != null).map(pm -> pm.getPrescriptionChargeable().getFrequency()).distinct().collect(Collectors.toList());
            if (chargeAbleFrequencyList.size() > 1)
                throw new ApiValidationException("prescription.group.invalid.multiFrequency");

            List<Dictionary> medicineTextFrequencyList = prescriptionMedicineTextList.stream().map(mt -> mt.getFrequency()).distinct().collect(Collectors.toList());
            if (medicineTextFrequencyList.size() > 1)
                throw new ApiValidationException("prescription.group.invalid.multiFrequency");

            if (chargeAbleFrequencyList.size() > 0 && medicineTextFrequencyList.size() > 0 && chargeAbleFrequencyList.get(0).getId() != medicineTextFrequencyList.get(0).getId())
                throw new ApiValidationException("prescription.group.invalid.multiFrequency");
        }
    }

    @Transactional
    public List<Prescription> clonePrescription(PrescriptionCloneReqDto prescriptionCloneReqDto) {
        Map<UUID, PrescriptionGroup> groupMap = new HashMap<>();
        List<Prescription> prescriptionList = new ArrayList<>();
        for (UUID prescriptionId : prescriptionCloneReqDto.getPrescriptionIdList()) {
            Prescription prescription = this.findById(Prescription.class, prescriptionId);
            for (UUID patientSignInId : prescriptionCloneReqDto.getToPatientIdList()) {
                Prescription clonedPrescription = prescription.clone(patientSignInId);
                if (prescriptionCloneReqDto.getToPatientIdList().size() == 1 && prescription.getPrescriptionGroup() != null) {
                    PrescriptionGroup newGroup = null;
                    if (groupMap.containsKey(prescription.getPrescriptionGroup().getUuid()))
                        newGroup = groupMap.get(prescription.getPrescriptionGroup().getUuid());
                    else {
                        newGroup = new PrescriptionGroup();
                        groupMap.put(prescription.getPrescriptionGroup().getUuid(), newGroup);
                        ebeanServer.save(newGroup);
                    }
                    clonedPrescription.setPrescriptionGroup(newGroup);
                }
                prescriptionList.add(clonedPrescription);
                ebeanServer.save(clonedPrescription);
                this.doPrescriptionUpdate(clonedPrescription, PrescriptionStatus.submitted, PrescriptionChangeAction.submit);
            }
        }
        return prescriptionList;
    }


    @Transactional
    public List<Prescription> executePrescriptionList(PrescriptionExecutionListReqDto executionListReqDto) {
        //Fee newFee;
        List<Prescription> executedPrescriptionList = new ArrayList<>();

        Employee supervisor = null;
        if (executionListReqDto.getClass() == PrescriptionExecutionListReqMiniDto.class) {
            PrescriptionExecutionListReqMiniDto miniReq = ((PrescriptionExecutionListReqMiniDto) executionListReqDto);
            if (miniReq.getSupervisorId() != null)
                supervisor = new Employee(miniReq.getSupervisorId());
        }

        for (PrescriptionExecutionReqDto prescriptionExecution : executionListReqDto.getPrescriptionExecutionList()) {
            Prescription prescription = this.findById(Prescription.class, prescriptionExecution.getUuid());
            if (prescription.getStatus() != PrescriptionStatus.approved)
                throw new ApiValidationException("非法医嘱状态，尝试刷新并再次执行");

            Integer allowedFeeCount = this.getAllowedExecutionCount(prescription, executionListReqDto.getFeeDate(), prescription.getEntityId());
            if (allowedFeeCount < prescriptionExecution.getExecutionQuantity())
                throw new ApiValidationException(prescription.getDescription() + "可执行次数错误，尝试刷新并再次执行,剩余次数：" + allowedFeeCount.toString());

            if (prescription.getPatientSignIn().getStatus() != PatientSignInStatus.signedIn)
                continue;

            // Frequency frequency = prescription.getPrescriptionChargeable().getFrequency().getFrequencyInfo();
            for (int i = 0; i < prescriptionExecution.getExecutionQuantity(); i++) {
                List<Fee> newFeeList = prescription.generateNewFee(executionListReqDto.getFeeDate(), prescriptionExecution.getChildTreatmentIdList());
                for (Fee newFee : newFeeList) {
                    newFee.setFeeRecordMethod(executionListReqDto.getFeeRecordMethod());
                    newFee.setSupervisor(supervisor);
                }
                ebeanServer.saveAll(newFeeList);
            }
            prescription.getPrescriptionChargeable().getPrescriptionTreatment().setLastExecutionTime(new Date());

            PrescriptionExecutionLog log = prescription.generateLog();
            log.setQuantity(new BigDecimal(prescriptionExecution.getExecutionQuantity()));
            log.setExecuteBy(this.userService.getCurrentUser());
            ebeanServer.save(log);

            //如果为临时医嘱，更新医嘱信息
            if (prescription.isOneOff())
                this.doPrescriptionUpdate(prescription, PrescriptionStatus.disabled, PrescriptionChangeAction.executed);
            else
                ebeanServer.save(prescription); //如果为临时医嘱，更新状态时已经储存

            executedPrescriptionList.add(prescription);
        }
        return executedPrescriptionList;
    }

    @Transactional
    public List<BaseWardPatientListRespDto> getPendingMedicineOrderList(PrescriptionFilterDto filterDto) {
        PrescriptionStatus[] statusArray = new PrescriptionStatus[]{PrescriptionStatus.approved};
        filterDto.setPrescriptionStatusList(Arrays.asList(statusArray));
        PrescriptionType[] prescriptionTypeArray = new PrescriptionType[]{Medicine};
        filterDto.setPrescriptionTypeList(Arrays.asList(prescriptionTypeArray));

        List<Prescription> allPatientPrescriptionList = this.buildPrescriptionListQuery(filterDto).query().findList();

        List<BaseWardPatientListRespDto> filteredPrescriptionList = this.filterOnOrderHistory(allPatientPrescriptionList, filterDto.getMockFutureDate());
        return filteredPrescriptionList;
    }

    private List<BaseWardPatientListRespDto> filterOnOrderHistory(List<Prescription> allPatientPrescriptionList, Date mockFutureDate) {
        List<BaseWardPatientListRespDto> filteredPrescriptionList = new ArrayList<>();
        for (Prescription prescription : allPatientPrescriptionList) {
            PrescriptionMedicine prescriptionMedicine = prescription.getPrescriptionChargeable().getPrescriptionMedicine();
            //得到应该发出多少药物，药房尺寸

            Integer totalNeedIssueQuantity = null;
            if (prescriptionMedicine.getFixedQuantity() != null)
                totalNeedIssueQuantity = prescriptionMedicine.getFixedQuantity().intValue();
                //.multiply(prescriptionMedicine.getPrescriptionChargeable().getQuantity()).intValue();
            else
                totalNeedIssueQuantity = prescriptionMedicine.totalNeedIssueQuantity(mockFutureDate);

            //查找药房领药单，包括已经发药的和提交的发药申请
            Integer totalIssuedQuantity = prescriptionMedicine.getOrderLineList().stream()
                    .filter(ol -> ol.getOriginOrderLine() == null) //所有人工提交过的的发药单
                    .map(PrescriptionMedicineOrderLine::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0).intValue();

//            Integer totalReturnedQuantity = prescriptionMedicine.getOrderLineList().stream()
//                    .filter(ol -> ol.getReturnOrderLine() != null)
//                    .map(ol -> ol.getReturnOrderLine().getQuantity())
//                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0).intValue();

            Integer pendingIssueQuantity = totalNeedIssueQuantity - totalIssuedQuantity;
            if (pendingIssueQuantity > 0) {
                PrescriptionMedicineOrderLineRespDto dto = prescription.toPendingOrderLineRespDto(pendingIssueQuantity);
                filteredPrescriptionList.add(dto);
            }

            //Todo 需要扣除退药数量

        }
        return filteredPrescriptionList;
    }

    @Transactional
    public PrescriptionMedicineOrder createPrescriptionMedicineOrder(PrescriptionMedicineOrderCreateDto prescriptionMedicineOrderCreateDto) {
        if (prescriptionMedicineOrderCreateDto.getOrderLineList().size() == 0)
            throw new ApiValidationException("prescription.error.noPrescriptionSelected");

        PrescriptionMedicineOrder order = prescriptionMedicineOrderCreateDto.toEntity();
        for (PrescriptionMedicineOrderLine orderLine : order.getLineList()) {
            this.patientSignInService.validatePatientSignInStatus(orderLine.getPatientSignIn().getUuid(), PatientSignInStatus.signedIn);
            if (!orderLine.getMedicine().isEnabled()) {
                Medicine medicine = orderLine.getMedicine();
                String validationMessage = medicine.getName() + " 规格：" + medicine.getDepartmentModel() + " 产地：" + medicine.getManufacturerMedicine().getName() + "已被作废";
                validationMessage += "请通知医生停止相关医嘱后再继续提交";
                throw new ApiValidationException(validationMessage);
            }
        }
        ebeanServer.save(order);
        //更新数据库生成的ORDER NUMBER
        order = this.findById(PrescriptionMedicineOrder.class, order.getUuid());
        order.setOrderNumberCode(Utils.buildDisplayCode(order.getOrderNumber()));
        ebeanServer.update(order);
        return order;
    }


    public List<Prescription> getNursingCardList(PrescriptionFilterDto filterDto, EntityType entityType) {
        PrescriptionStatus[] statusArray = new PrescriptionStatus[]{PrescriptionStatus.approved};
        filterDto.setPrescriptionStatusList(Arrays.asList(statusArray));
        PrescriptionType[] prescriptionTypeArray = null;
        if (entityType == EntityType.medicine)
            prescriptionTypeArray = new PrescriptionType[]{Medicine, PrescriptionType.TextMedicine};
        else {
            prescriptionTypeArray = new PrescriptionType[]{PrescriptionType.Treatment};
        }
        filterDto.setPrescriptionTypeList(Arrays.asList(prescriptionTypeArray));
        //filterDto.setOneOff(false);

        List<Prescription> allPatientPrescriptionList = this.buildPrescriptionListQuery(filterDto).query().findList();

        List<Prescription> filteredPrescriptionList = new ArrayList<>();
        for (Prescription prescription : allPatientPrescriptionList) {
            Frequency frequencyInfo = prescription.getFrequency().getFrequencyInfo();
            if (frequencyInfo != null && frequencyInfo.getDayRange() == null) {
                int daysFromStart = prescription.daysFromPrescriptionStart(filterDto.getCardDate());
                if (daysFromStart <= 0)
                    continue;

                if ((daysFromStart - 1) % prescription.getFrequency().getFrequencyInfo().getDayInterval() == 0)
                    filteredPrescriptionList.add(prescription);
            } else
                filteredPrescriptionList.add(prescription); //周期性卡片始终显示
        }
        return filteredPrescriptionList;
    }

    @Transactional
    public void validateRecoveryTreatment(PrescriptionExecutionListReqMiniDto executionListReqDto) {
        //执行时间检查
        //Todo set value in config or DB
        LocalTime now = LocalTime.now();
        LocalTime morningStart = LocalTime.of(7, 20);
        LocalTime morningEnd = LocalTime.of(11, 30);
        LocalTime afternoonStart = LocalTime.of(11, 30);
        LocalTime afternoonEnd = LocalTime.of(17, 30);
        boolean validTime = false;
        if (now.compareTo(morningStart) > 0 && now.compareTo(morningEnd) < 0)
            validTime = true;
        else if (now.compareTo(afternoonStart) > 0 && now.compareTo(afternoonEnd) < 0)
            validTime = true;

        if (!validTime)
            throw new ApiValidationException("非有效计费时间");

        List<Treatment> treatmentList = new ArrayList<>();
        //大于一个执行项目，必须所有项目都允许重复
        for (PrescriptionExecutionReqDto reqDto : executionListReqDto.getPrescriptionExecutionList()) {
            Prescription prescription = this.findById(Prescription.class, reqDto.getUuid());
            Treatment treatment = prescription.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment();
            if (executionListReqDto.getPrescriptionExecutionList().size() > 1 && (treatment.getAllowMultiExecution() == null || !treatment.getAllowMultiExecution()))
                throw new ApiValidationException(treatment.getName() + "不能与其他项目同时执行");
            treatmentList.add(treatment);
        }

        List<Fee> onGoingFeeList = this.getOnExecutingPrescriptionList(executionListReqDto.getPatientSignInId());

        if (onGoingFeeList.size() > 0) {
            boolean invalid = false;
            //正在执行的费用必须允许同时执行
            if (onGoingFeeList.stream().anyMatch(f -> f.getTreatmentSnapshot().getTreatment().getAllowMultiExecution() == null
                    || !f.getTreatmentSnapshot().getTreatment().getAllowMultiExecution()))
                invalid = true;

            if (treatmentList.size() == 1) {
                Treatment newTreatment = treatmentList.get(0);
                if (newTreatment.getAllowMultiExecution() == null || !newTreatment.getAllowMultiExecution())
                    invalid = true;
            }

            if (invalid)
                throw new ApiValidationException("有未结束的项目正在执行");

            List<UUID> executingTreatmentIdList = onGoingFeeList.stream().map(f -> f.getTreatmentSnapshot().getTreatment().getUuid()).collect(Collectors.toList());
            Optional<Treatment> sameTreatment = treatmentList.stream().filter(t -> executingTreatmentIdList.contains(t.getUuid())).findAny();
            if (sameTreatment.isPresent())
                throw new ApiValidationException(sameTreatment.get().getName() + "正在执行中");
        }
    }

    public List<Fee> getOnExecutingPrescriptionList(UUID patientSignInId) {
        LocalDateTime localNow = LocalDateTime.now();
        LocalDateTime lastHour = localNow.plusHours(-1);

        List<Fee> lastHourFeeList =
                //获取一小时之内的扫码费用
                ebeanServer.find(Fee.class).where()
                        .eq("feeStatus", FeeStatus.confirmed)
                        .eq("patientSignIn.uuid", patientSignInId)
                        .eq("entityType", EntityType.treatment)
                        .eq("treatmentSnapshot.treatment.executeDepartmentType", DepartmentTreatmentType.recover)
                        .ge("feeDate", lastHour)
                        .findList();

        List<Fee> onGoingFeeList = new ArrayList<>();
        Date now = new Date();
        for (Fee fee : lastHourFeeList) {
            Integer duration = fee.getTreatmentSnapshot().getTreatment().getDuration();
            if (duration != null) {
                LocalDateTime expectedEndTime = fee.getExpectedFinishLocalTime();
                if (expectedEndTime.compareTo(LocalDateTime.now()) > 0)
                    onGoingFeeList.add(fee);
            }
        }
        return onGoingFeeList;
    }

    @Transactional
    public void updateStartDateInBatch(List<PrescriptionChangeLogReqDto> reqDtoList) {
        for (PrescriptionChangeLogReqDto reqDto : reqDtoList) {
            Prescription prescription = ebeanServer.find(Prescription.class, reqDto.getUuid());
            prescription.setStartDate(reqDto.getManualDate());
            ebeanServer.save(prescription);

            PrescriptionChangeLog lasApproveLog = this.findLastLogByAction(PrescriptionChangeAction.approve, prescription.getUuid());
            if (lasApproveLog != null) {
                lasApproveLog.setManualDate(this.generateLogDate(reqDto.getManualDate()));
                ebeanServer.save(lasApproveLog);
            }

            PrescriptionChangeLog lasSubmitLog = this.findLastLogByAction(PrescriptionChangeAction.submit, prescription.getUuid());
            if (lasSubmitLog != null) {
                lasSubmitLog.setManualDate(reqDto.getManualDate());
                ebeanServer.save(lasSubmitLog);
            }

        }
    }

    @Transactional
    public PrescriptionChangeLog updateLogTime(PrescriptionChangeLogReqDto reqDto) {

        PrescriptionChangeLog log = ebeanServer.find(PrescriptionChangeLog.class, reqDto.getUuid());
        if (log.getAction() == PrescriptionChangeAction.submit) {
            if (reqDto.getManualDate().before(log.getPrescription().getPatientSignIn().getSignInDate()))
                throw new ApiValidationException("开嘱日不能早于入院日期");
            Prescription prescription = log.getPrescription();
            prescription.setStartDate(reqDto.getManualDate());
            ebeanServer.save(prescription);

            PrescriptionChangeLog lasApproveLog = this.findLastLogByAction(PrescriptionChangeAction.approve, prescription.getUuid());

            if (lasApproveLog != null) {
                lasApproveLog.setManualDate(this.generateLogDate(reqDto.getManualDate()));
                ebeanServer.save(lasApproveLog);
            }
        }

        log.setManualDate(reqDto.getManualDate());
        ebeanServer.save(log);
        return log;
    }

    private PrescriptionChangeLog findLastLogByAction(PrescriptionChangeAction action, UUID prescriptionId) {
        Optional<PrescriptionChangeLog> optionalChangeLog = ebeanServer.find(PrescriptionChangeLog.class)
                .orderBy("whenCreated desc")
                .where()
                .eq("prescription.uuid", prescriptionId)
                .eq("action", action)
                .setMaxRows(1)
                .findOneOrEmpty();
        return optionalChangeLog.orElse(null);
    }

    @Transactional
    public PrescriptionSignatureRespDto signPrescriptionCard(PrescriptionCardSignSaveDto signSaveDto) {
        if (signSaveDto.getCardSignatureType() == PrescriptionCardSignatureType.prescriptionLevel)
            if (signSaveDto.getSequence() == null)
                throw new ApiValidationException("Missing sequence");

        Optional<PrescriptionCardSignature> optionalSignature = ebeanServer.find(PrescriptionCardSignature.class)
                .where()
                .eq("cardDate", signSaveDto.getCardDate())
                .eq("patientSignIn.uuid", signSaveDto.getPatientSignInId())
                .eq("cardType", signSaveDto.getCardType())
                .eq("cardSignatureType", signSaveDto.getCardSignatureType())
                .eq("sequence", signSaveDto.getSequence())
                .or()
                .eq("prescription", null)
                .eq("prescription.uuid", signSaveDto.getPrescriptionId())
                .endOr()
                .findOneOrEmpty();
        if (optionalSignature.isPresent())
            throw new ApiValidationException("signature already exists");


        PrescriptionCardSignature signature = new PrescriptionCardSignature();
        signature.setCardType(signSaveDto.getCardType());
        signature.setCardSignatureType(signSaveDto.getCardSignatureType());
        signature.setPatientSignIn(new PatientSignIn(signSaveDto.getPatientSignInId()));
        signature.setCardDate(signSaveDto.getCardDate());
        if (signSaveDto.getPrescriptionId() != null) {
            Prescription prescription = this.findById(Prescription.class, signSaveDto.getPrescriptionId());
            signature.setPrescription(prescription);
        }
        signature.setSequence(signSaveDto.getSequence());
        ebeanServer.save(signature);
        return signature.toDto();
    }

    public List<PrescriptionCardSignature> getPrescriptionCardSignatureList(PrescriptionCardType cardType, List<Prescription> prescriptionList, Date cardDate) {
        //List<Date> dateBoundary = this.getDateBoundary(cardDate);
        List<UUID> patientSignInIdList = prescriptionList.stream().map(p -> p.getPatientSignIn().getUuid()).collect(Collectors.toList());
        return ebeanServer.find(PrescriptionCardSignature.class)
                .where()
                .eq("cardDate", cardDate)
                .in("patientSignIn.uuid", patientSignInIdList)
                .eq("cardType", cardType)
                .findList();
    }
//
//    private List<Date> getDateBoundary(Date date)
//    {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date startDate = calendar.getTime();
//
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        Date endDate = calendar.getTime();
//
//        List<Date> dateBoundaryList = new ArrayList<>();
//        dateBoundaryList.add(startDate);
//        dateBoundaryList.add(endDate);
//        return dateBoundaryList;
//    }

    public void cancelPrescriptionCardSignature(UUID signId) {
        PrescriptionCardSignature signature = this.findById(PrescriptionCardSignature.class, signId);
        ebeanServer.delete(signature);
    }

    @Transactional
    public void setTreatmentPrescriptionAdjustQuantity(TreatmentPrescriptionQuantityAdjust adjustReqDto) {
        BigDecimal adjustQuantity = adjustReqDto.getAdjustQuantity();
        if (adjustQuantity.compareTo(BigDecimal.ZERO) == 0)
            throw new ApiValidationException("调整次数不能为0");

        PrescriptionTreatment prescriptionTreatment = this.findById(PrescriptionTreatment.class, adjustReqDto.getPrescriptionTreatmentId());
        if (adjustQuantity.compareTo(prescriptionTreatment.getPrescriptionChargeable().getQuantity()) > 0)
            throw new ApiValidationException("调整次数大于医嘱执行次数");
        prescriptionTreatment.setAdjustQuantity(adjustQuantity);
        ebeanServer.save(prescriptionTreatment);
    }

    @Transactional
    public PreDefinedPrescription findOrCreatePredefinedPrescription(PreDefinedPrescriptionSaveDto preDefinedPrescriptionSaveDto) {
        Optional<PreDefinedPrescription> optionalPreDefinedPrescription =
                this.basicService.findExistingEntity(ebeanServer.find(PreDefinedPrescription.class).where()
                        .eq("type", preDefinedPrescriptionSaveDto.getType())
                        , preDefinedPrescriptionSaveDto);
        if (optionalPreDefinedPrescription.isPresent())
            return optionalPreDefinedPrescription.get();
        else {
            PreDefinedPrescription newPreDefinedPrescription = preDefinedPrescriptionSaveDto.toEntity();
            ebeanServer.save(newPreDefinedPrescription);
            return newPreDefinedPrescription;
        }
    }

    @Transactional
    public PagedList<PreDefinedPrescription> getPreDefinedPrescriptionList(PredefinePrescriptionFilterDto filter, Integer pageNum, Integer pageSize) {
        ExpressionList<PreDefinedPrescription> el = ebeanServer.find(PreDefinedPrescription.class)
                .where();

        el = el.eq("type", filter.getType())
                .eq("oneOff", filter.getOneOff());

        if (filter.getEnabled() != null)
            el = el.eq("enabled", filter.getEnabled());

        if (filter.getSearchCode() != null)
            el = Utils.addSearchExpression(el, filter.getSearchCode());
        return this.findPagedList(el, pageNum, pageSize);
    }

    @Transactional
    public void savePredefinedPrescriptionLineList(PreDefinedPrescriptionSaveDto saveDto) {
        PreDefinedPrescription preDefinedPrescription = this.findById(PreDefinedPrescription.class, saveDto.getUuid());
        //List<PreDefinedPrescriptionTreatment> currentList = ;
        if (preDefinedPrescription.getType() == PreDefinedPrescriptionType.treatment) {
            ebeanServer.deleteAll(preDefinedPrescription.getTreatmentList());
            List<PreDefinedPrescriptionTreatment> lineList = new ArrayList<>();
            for (PreDefinedPrescriptionEntitySaveDto lineDto : saveDto.getLineList()) {
                PreDefinedPrescriptionTreatment treatmentLine = lineDto.toTreatmentEntity();
                lineList.add(treatmentLine);
            }
            preDefinedPrescription.setTreatmentList(lineList);
        } else {
            ebeanServer.deleteAll(preDefinedPrescription.getMedicineList());
            List<PreDefinedPrescriptionMedicine> lineList = new ArrayList<>();
            for (PreDefinedPrescriptionEntitySaveDto lineDto : saveDto.getLineList()) {
                PreDefinedPrescriptionMedicine medicineLine = lineDto.toMedicineEntity();
                lineList.add(medicineLine);
            }
            preDefinedPrescription.setMedicineList(lineList);
        }

        ebeanServer.save(preDefinedPrescription);
    }

    public List<PreDefinedPrescriptionTreatment> getTreatmentPreDefinedPrescriptionLineList(UUID groupId) {
        return ebeanServer.find(PreDefinedPrescriptionTreatment.class).where()
                .eq("preDefinedGroup.uuid", groupId)
                .orderBy("whenModified ")
                .findList();
    }

    public List<PreDefinedPrescriptionMedicine> getMedicinePreDefinedPrescriptionLineList(UUID groupId) {
        return ebeanServer.find(PreDefinedPrescriptionMedicine.class).where()
                .eq("preDefinedGroup.uuid", groupId)
                .orderBy("whenModified ")
                .findList();
    }

    @Transactional
    public void generatePreDefinedPrescription(UUID groupId, UUID patientSignInID) {
        PreDefinedPrescription preDefinedPrescription = this.findById(PreDefinedPrescription.class, groupId);
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInID);
        if (preDefinedPrescription.getTreatmentList().size() < 1)
            throw new ApiValidationException("没有预定义诊疗项目");

        this.patientSignInService.validatePatientSignInStatus(patientSignIn.getUuid(), PatientSignInStatus.signedIn);

        for (PreDefinedPrescriptionTreatment preDefinedPrescriptionTreatment : preDefinedPrescription.getTreatmentList()) {
            if(!preDefinedPrescriptionTreatment.getTreatment().isEnabled())
                continue;
            Prescription prescription = preDefinedPrescriptionTreatment.toNewPrescription(patientSignIn);
            ebeanServer.save(prescription);
        }
    }

    @Transactional
    public void generateMedicinePreDefinedPrescription(UUID groupId, UUID patientSignInID, Integer totalNumber) {
        PreDefinedPrescription preDefinedPrescription = this.findById(PreDefinedPrescription.class, groupId);
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInID);
        if (preDefinedPrescription.getMedicineList().size() < 1)
            throw new ApiValidationException("没有预定义药品");

        this.patientSignInService.validatePatientSignInStatus(patientSignIn.getUuid(), PatientSignInStatus.signedIn);

        List<UUID> idList = new ArrayList<>();
        for (PreDefinedPrescriptionMedicine preDefinedPrescriptionMedicine : preDefinedPrescription.getMedicineList()) {
            if(!preDefinedPrescriptionMedicine.getMedicine().isEnabled())
                continue;
            Prescription prescription = preDefinedPrescriptionMedicine.toNewPrescription(patientSignIn, totalNumber);
            ebeanServer.save(prescription);
            idList.add(prescription.getUuid());
        }

        //设立组套
        PrescriptionGroupReqDto groupReqDto = new PrescriptionGroupReqDto();
        groupReqDto.setGroup(true);
        groupReqDto.setPrescriptionIdList(idList);
        this.groupPrescription(groupReqDto);
    }

    public Integer getPrescriptionFeeCount(UUID prescriptionId) {
        return ebeanServer.find(Fee.class).where()
                .eq("feeStatus", FeeStatus.confirmed)
                .eq("prescription.uuid", prescriptionId)
                .findCount();
    }


    public void updateLogUser(PrescriptionChangeLogReqDto changeLog) {
        PrescriptionChangeLog log = ebeanServer.find(PrescriptionChangeLog.class, changeLog.getUuid());
        Employee changedToUser = ebeanServer.find(Employee.class, changeLog.getManualUserId());
        log.setManualChangedUser(changedToUser);
        ebeanServer.update(log);
    }

    public List<PrescriptionMedicine> getSameGroupPrescriptionList(UUID prescriptionId) {
        Prescription prescription = ebeanServer.find(Prescription.class, prescriptionId);
        if (prescription.getPrescriptionType() != Medicine)
            throw new ApiValidationException("非药品医嘱");

        if (prescription.getPrescriptionGroup() != null) {
            return
                    prescription.getPrescriptionGroup().getPrescriptionList().stream()
                            .filter(p -> p.getPrescriptionType() == Medicine)
                            .map(p -> p.getPrescriptionChargeable().getPrescriptionMedicine())
                            .collect(Collectors.toList());
        } else {
            List<PrescriptionMedicine> singleList = new ArrayList<>();
            singleList.add(prescription.getPrescriptionChargeable().getPrescriptionMedicine());
            return singleList;
        }
    }
}
