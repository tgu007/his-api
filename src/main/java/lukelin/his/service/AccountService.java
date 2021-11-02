package lukelin.his.service;

import io.ebean.*;

import lukelin.common.security.jwt.JwtUser;
import lukelin.common.springboot.ebean.DefaultCurrentUserProvider;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.entity.Internal_account.AutoFeeTemp;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;
import lukelin.his.domain.entity.Internal_account.FeeTemp;
import lukelin.his.domain.entity.account.*;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.Frequency;
import lukelin.his.domain.entity.basic.Organization;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.inventory.item.CachedItemTransaction;
import lukelin.his.domain.entity.inventory.item.FeeItemTransaction;
import lukelin.his.domain.entity.inventory.medicine.CachedMedicineTransaction;
import lukelin.his.domain.entity.inventory.medicine.FeeMedicineTransaction;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrder;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrderLine;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionChangeLog;
import lukelin.his.domain.entity.yb.Settlement;
import lukelin.his.domain.entity.yb.hy.SettlementHY;
import lukelin.his.domain.enums.*;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Fee.FeeStatusAction;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.dto.account.filter.AccountSummaryFilter;
import lukelin.his.dto.account.filter.AutoFeeFilterDto;
import lukelin.his.dto.account.filter.FeeFilterDto;
import lukelin.his.dto.account.filter.PaymentListFilter;
import lukelin.his.dto.account.request.*;
import lukelin.his.dto.account.response.*;
import lukelin.his.dto.prescription.response.PrescriptionListRespDto;
import lukelin.his.system.NoStockException;
import lukelin.his.system.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService extends BaseHisService {
    @Autowired
    private EbeanServer ebeanServer;

    @Autowired
    private InventoryMedicineService inventoryMedicineService;

    @Autowired
    private InventoryItemService inventoryItemService;

    @Autowired
    private PatientSignInService patientSignInService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private NotificationService notificationService;


    @Autowired
    private YBInventoryService ybInventoryService;

    @Autowired
    private DefaultCurrentUserProvider currentUserProvider;

    @Value("${uploadYBPatient}")
    private Boolean enableYBService;

    @Value("${enableHYYB}")
    private Boolean enableHYYBService;

    @Autowired
    private YBService ybService;


    @Autowired
    private YBServiceHY ybServiceHy;


    public void createPayment(PaymentSaveDto paymentSaveDto) {
        Payment newPayment = paymentSaveDto.toEntity();
        ebeanServer.save(newPayment);
    }

    public void updatePaymentStatus(UUID paymentId, PaymentStatus paymentStatus) {
        Payment existingPayment = new Payment();
        existingPayment.setUuid(paymentId);
        existingPayment.setStatus(paymentStatus);
        ebeanServer.update(existingPayment);
    }


    public PagedList<Payment> getPatientPaymentList(UUID patientSignInId, int pageNum) {
        ExpressionList<Payment> el = ebeanServer.find(Payment.class)
                .orderBy("whenCreated desc")
                .where()
                .eq("patientSignIn.uuid", patientSignInId)
                .ne("status", PaymentStatus.canceled);
        return findPagedList(el, pageNum);
    }

    public PagedList<Payment> getAllPaymentList(PaymentListFilter filter, int pageNum) {
        ExpressionList<Payment> el = ebeanServer.find(Payment.class)
                .where()
                .or()
                .eq("status", PaymentStatus.paid)
                .eq("status", PaymentStatus.refunded)
                .endOr()
                .between("whenCreated", filter.getStartDate(), filter.getEndDate());
        return findPagedList(el, pageNum);
    }

    public List<Payment> getPaymentSummaryList(AccountSummaryFilter accountSummaryFilter) {
        //Date endDate = this.addDays(accountSummaryFilter.getEndDate(), 1);
        return ebeanServer.find(Payment.class).where()
                .or()
                .eq("status", PaymentStatus.paid)
                .eq("status", PaymentStatus.refunded)
                .endOr()
                .between("whenCreated", accountSummaryFilter.getStartDate(), accountSummaryFilter.getEndDate()).findList();
    }

//    public int getCurrentDayFeeCount(UUID prescriptionId, LocalDate feeDate) {
//
//        ZoneId zoneId = ZoneId.systemDefault();
//        Date startDate = Date.from(LocalDateTime.of(feeDate, LocalTime.MIN).atZone(zoneId).toInstant());
//        Date endDate = Date.from(LocalDateTime.of(feeDate, LocalTime.MAX).atZone(zoneId).toInstant());
//
//        ExpressionList<Fee> el = ebeanServer.find(Fee.class).where()
//                .eq("prescription.uuid", prescriptionId)
//                .eq("feeStatus", FeeStatus.confirmed)
//                .between("feeDate", startDate, endDate);
//
//        Prescription prescription = this.findById(Prescription.class, prescriptionId);
//        UUID feeEntityId = prescription.getEntityId();
//        if (feeEntityId != null)
//            el = el.eq("feeEntityId", feeEntityId);
//
//        return el.findCount();
//    }

    public int getPrescriptionFeeCount(Prescription prescription, LocalDateTime startDate, LocalDateTime endDate, UUID feeEntityId) {

        List<FeeStatus> validFeeStatus = new ArrayList<>();
        validFeeStatus.add(FeeStatus.confirmed);
        validFeeStatus.add(FeeStatus.calculated);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = startDate.atZone(zoneId);
        Date transformedStartDate = Date.from(zdt.toInstant());
        zdt = endDate.atZone(zoneId);
        Date transformedEndDate = Date.from(zdt.toInstant());

        ExpressionList<Fee> el = ebeanServer.find(Fee.class).where()
                .and()
                .in("feeStatus", validFeeStatus)
                .between("feeDate", transformedStartDate, transformedEndDate)
                .eq("feeEntityId", feeEntityId)
                .endAnd();

        if (prescription.getPrescriptionGroup() != null) {
            List<UUID> prescriptionIdList = prescription.getPrescriptionGroup().getPrescriptionList().stream().map(Prescription::getUuid).collect(Collectors.toList());
            el = el.in("prescription.uuid", prescriptionIdList);
        } else
            el = el.eq("prescription.uuid", prescription.getUuid());
        List<Fee> feeList = el.findList();
        return el.findCount();
    }

    public int getAutoFeeGeneratedCount(UUID autoFeeId, LocalDateTime startDate, LocalDateTime endDate, UUID feeEntityId) {
        ExpressionList<Fee> el = ebeanServer.find(Fee.class).where()
                .eq("autoFee.uuid", autoFeeId)
                .eq("feeStatus", FeeStatus.confirmed)
                .between("feeDate", startDate, endDate)
                .eq("feeEntityId", feeEntityId);
        return el.findCount();
    }


    private Query getFeeListQuery(UUID patientSignInId, FeeFilterDto feeFilterDto) {
        ExpressionList<Fee> el = ebeanServer.find(Fee.class).alias("f").where();

        if (patientSignInId != null)
            el = el.eq("patientSignIn.uuid", patientSignInId);

        if (feeFilterDto.getWhoCreated() != null)
            el = el.eq("whoCreated", feeFilterDto.getWhoCreated());

        if (feeFilterDto.getStartDate() != null && feeFilterDto.getEndDate() != null) {
            Date endDate = this.addDays(feeFilterDto.getEndDate(), 1);
            el = el.between("feeDate", feeFilterDto.getStartDate(), endDate);
        }

        if (feeFilterDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, feeFilterDto.getSearchCode().toUpperCase(), "searchCode", "description");

        if (feeFilterDto.getFeeStatusList() != null)
            el = el.in("f.status", feeFilterDto.getFeeStatusList());
        if (feeFilterDto.getFeeTypeList() != null)
            el = el.in("feeTypeName", feeFilterDto.getFeeTypeList());

        if (feeFilterDto.getPendingUpload() != null) {
            el = el.eq("feeUploadResult", null)
                    .eq("feeStatus", FeeStatus.confirmed)
                    .or()
                    .eq("selfPay", null)
                    .eq("selfPay", false)
                    .endOr();
        }

        if (feeFilterDto.getDepartmentIdList() != null)
            el = el.in("department.uuid", feeFilterDto.getDepartmentIdList());

        if (feeFilterDto.getPrescriptionDescription() != null)
            el = el.like("prescription.description", "%" + feeFilterDto.getPrescriptionDescription() + "%");

        if (feeFilterDto.getPrescriptionId() != null)
            el = el.eq("prescription.uuid", feeFilterDto.getPrescriptionId());

        if (feeFilterDto.getOrderByDesc())
            return el.orderBy("fee_date desc");
        else
            return el.orderBy("fee_date");


    }

    public List<String> getPatientFeeTypeList(UUID patientSignInId) {
        List<Fee> feeList = ebeanServer.find(Fee.class)
                .select("feeTypeName")
                .setDistinct(true)
                .where()
                .eq("patientSignIn.uuid", patientSignInId)
                .findList();
        List<String> feeTypeList = feeList.stream().map(Fee::getFeeTypeName).collect(Collectors.toList());
        return feeTypeList;
    }

    public List<String> getDepartmentFeeTypeList(FeeFilterDto feeFilter) {
        Date endDate = this.addDays(feeFilter.getEndDate(), 1);

        List<Fee> feeList = ebeanServer.find(Fee.class)
                .select("feeTypeName")
                .setDistinct(true)
                .where()
                .in("department.uuid", feeFilter.getDepartmentIdList())
                .eq("feeStatus", FeeStatus.confirmed)
                .between("feeDate", feeFilter.getStartDate(), endDate)
                .findList();
        List<String> feeTypeList = feeList.stream().map(Fee::getFeeTypeName).collect(Collectors.toList());
        return feeTypeList;
    }

    public List<Fee> getFeeList(FeeFilterDto feeFilterDto) {
        return this.getFeeList(null, feeFilterDto);
    }


    public List<Fee> getFeeList(UUID patientSignInId, FeeFilterDto feeFilterDto) {
        Query query = this.getFeeListQuery(patientSignInId, feeFilterDto);
        return query.findList();
    }

    public PagedList<Fee> getFeePagedList(UUID patientSignInId, Integer pageNum, FeeFilterDto feeFilterDto) {
        Query query = this.getFeeListQuery(patientSignInId, feeFilterDto);
        return findPagedList(query, pageNum);
    }


    public List<Fee> getValidatedStatusFeeList(List<UUID> feeIdList, List<FeeStatus> validStatusList) {
        List<Fee> feeList = ebeanServer.find(Fee.class)
                .where()
                .in("status", validStatusList)
                .and()
                .in("uuid", feeIdList)
                .findList();
        return feeList;
    }

    public void updateFeeStatus(Fee fee, FeeStatus feeStatus, FeeStatusAction action, String reason) {
        List<Fee> feeList = new ArrayList<>();
        feeList.add(fee);
        this.updateFeeStatus(feeList, feeStatus, action, reason);
    }

    @Transactional
    public void updateFeeStatus(List<Fee> feeList, FeeStatus feeStatus, FeeStatusAction action, String reason) {
        for (Fee fee : feeList) {
            fee.setFeeStatus(feeStatus);
            ebeanServer.update(fee);

            FeeChangeLog newLog = new FeeChangeLog();
            newLog.setFee(fee);
            newLog.setAction(action);
            newLog.setReason(reason);
            //Todo hardcode for now
            newLog.setWhoCreated("admin");
            ebeanServer.save(newLog);

        }
    }

    @Transactional
    public Fee saveFee(FeeSaveDto feeSaveDto) {
        this.patientSignInService.validatePatientSignInStatus(feeSaveDto.getPatientSignInId(), PatientSignInStatus.signedIn);

        Fee newFee = feeSaveDto.toFeeEntity();
        String errorMessage = this.validateFeeToSave(newFee);
        if (errorMessage != null)
            throw new ApiValidationException(errorMessage);
        ebeanServer.save(newFee);

        //更新库存信息
        if (feeSaveDto.getEntityType() == EntityType.item || feeSaveDto.getEntityType() == EntityType.medicine) {
            DepartmentWarehouse warehouse = this.findById(DepartmentWarehouse.class, feeSaveDto.getWarehouseId());
            this.updateFeeTransaction(newFee, warehouse);
        }
        return newFee;
    }

    private String validateFeeToSave(BaseFee newFee) {
        return this.validateFeeToSave(newFee, true);
    }

    public String validateFeeToSave(BaseFee newFee, Boolean checkFeeHistoryCount) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class, newFee.getPatientSignIn().getUuid());
        if (newFee.getFeeDate().before(patientSignIn.getSignInDate())) {
            return "计费日期不能早于入院日期";
        }

        if (patientSignIn.getSingOutRequest() != null && newFee.getFeeDate().after(patientSignIn.getSingOutRequest().getSignOutDate()))
            return "计费日期不能晚于出院日期";

        if (newFee.getPrescription() != null) {
            Prescription prescription = this.findById(Prescription.class, newFee.getPrescription().getUuid());

            if (newFee.getFeeDate().before(prescription.getStartDate()))
                return "费用日期不可在医嘱开始日期之前";

            if (!newFee.getPrescription().isOneOff()) {
                PrescriptionChangeLog disableLog = prescription.findDisableLog();
                if (disableLog != null && disableLog.getDate().before(newFee.getFeeDate()))
                    return "计费日期不能晚于医嘱结束之后";


                if (newFee.getClass() == Fee.class && checkFeeHistoryCount && prescription.getPrescriptionType() != PrescriptionType.Text) {
                    int allowedExecutionCount = this.prescriptionService.getAllowedExecutionCount(prescription, newFee.getFeeDate(), newFee.getFeeEntityId());

                    if (allowedExecutionCount < newFee.getQuantity().intValue())
                        return "费用次数错误，剩余可计费次数：" + allowedExecutionCount + "尝试计费次数：" + newFee.getQuantity().intValue();

                }
            }
        }
        return null;
    }

    private void updateFeeTransaction(Fee fee, DepartmentWarehouse warehouse) {
        List<CachedTransactionInterface> cachedTransactionList = fee.createTransactionList(warehouse);
        //getLastCreated transaction;
        CachedTransactionInterface lastTransaction = cachedTransactionList.get(0);
        if (fee.getEntityType() == EntityType.medicine) {
            this.createMedicineFeeTransactionList(fee, cachedTransactionList);
            fee.setBatch(((CachedMedicineTransaction) lastTransaction).getOriginPurchaseLine().getBatchNumber());
            fee.setOriginMedicinePurchaseLine(((CachedMedicineTransaction) lastTransaction).getOriginPurchaseLine());
        } else {
            this.createItemFeeTransactionList(fee, cachedTransactionList);
            fee.setBatch(((CachedItemTransaction) lastTransaction).getOriginPurchaseLine().getBatchNumber());
            fee.setOriginItemPurchaseLine(((CachedItemTransaction) lastTransaction).getOriginPurchaseLine());
        }
        fee.setWarehouse(warehouse);
        ebeanServer.save(fee);
    }

    private void createItemFeeTransactionList(Fee newFee, List<CachedTransactionInterface> cachedTransactionList) {
        List<FeeItemTransaction> feeItemTransactionList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : cachedTransactionList) {
            this.inventoryItemService.updateItemStockCache((CachedItemTransaction) cachedTransactionInterface);
            FeeItemTransaction feeItemTransaction = new FeeItemTransaction();
            feeItemTransaction.setItemTransaction((CachedItemTransaction) cachedTransactionInterface);
            feeItemTransactionList.add(feeItemTransaction);
        }
        newFee.setItemTransactionList(feeItemTransactionList);
    }

    public void createMedicineFeeTransactionList(Fee newFee, List<CachedTransactionInterface> cachedTransactionList) {
        List<FeeMedicineTransaction> feeMedicineTransactionList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : cachedTransactionList) {
            this.inventoryMedicineService.updateMedicineStockCache((CachedMedicineTransaction) cachedTransactionInterface);
            FeeMedicineTransaction feeMedicineTransaction = new FeeMedicineTransaction();
            feeMedicineTransaction.setMedicineTransaction((CachedMedicineTransaction) cachedTransactionInterface);
            feeMedicineTransactionList.add(feeMedicineTransaction);
        }
        newFee.setMedicineTransactionList(feeMedicineTransactionList);
    }

    @Transactional
    public void cancelPartialFee(PartialFeeCancelReq reqDto) {
        Fee feeToCancel = this.findById(Fee.class, reqDto.getFeeId());
        BigDecimal quantityToCancel = reqDto.getCancelQuantity();
        BigDecimal quantityLeft = feeToCancel.getQuantity().subtract(quantityToCancel);
        if (quantityLeft.compareTo(BigDecimal.ZERO) <= 0)
            throw new ApiValidationException("非法的退费数量");
        if (feeToCancel.getFeeStatus() != FeeStatus.confirmed)
            throw new ApiValidationException("非法的费用状态");
        this.patientSignInService.validatePatientSignInStatus(feeToCancel.getPatientSignIn(), PatientSignInStatus.signedIn);


        if (feeToCancel.getMedicineOrderLine() != null) {
            PrescriptionMedicineReturnOrderLine returnOrderLine = feeToCancel.getMedicineOrderLine().createReturnOrderLine();
            returnOrderLine.setQuantity(quantityToCancel);
            this.updateFeeStatus(feeToCancel, FeeStatus.pendingOnMedicineReturn, FeeStatusAction.medicineReturn, "manual partial");
            List<PrescriptionMedicineReturnOrderLine> lineList = new ArrayList<>();
            lineList.add(returnOrderLine);
            this.generateReturnOrder(lineList);
        } else {
            //删除医保端费用
            if (enableYBService)
                this.ybService.cancelFee(feeToCancel);
            else if (enableHYYBService)
                this.ybServiceHy.cancelFee(feeToCancel);

            if (feeToCancel.getEntityType() == EntityType.item) {
                List<CachedItemTransaction> reverseTransactionList = feeToCancel.createItemReverseTransactionList();
                ebeanServer.saveAll(reverseTransactionList);
                for (CachedItemTransaction itemTransaction : reverseTransactionList)
                    this.inventoryItemService.updateItemStockCache(itemTransaction);
            } else if (feeToCancel.getEntityType() == EntityType.medicine) {
                List<CachedMedicineTransaction> reverseTransactionList = feeToCancel.createMedicineReverseTransactionList();
                ebeanServer.saveAll(reverseTransactionList);
                for (CachedMedicineTransaction medicineTransaction : reverseTransactionList)
                    this.inventoryMedicineService.updateMedicineStockCache(medicineTransaction);
            }

            Fee leftPartialFee = feeToCancel.createLeftPartialFee(quantityLeft);
            ebeanServer.save(leftPartialFee);
            if (feeToCancel.getEntityType() != EntityType.treatment)
                this.updateFeeTransaction(leftPartialFee, leftPartialFee.getWarehouse());
            this.updateFeeStatus(feeToCancel, FeeStatus.canceled, FeeStatusAction.cancel, "manual partial");
        }
    }

    @Transactional
    public void cancelFee(List<Fee> feeListToCancel, String cancelReason) {
        if (feeListToCancel.size() > 0)
            this.patientSignInService.validatePatientSignInStatus(feeListToCancel.get(0).getPatientSignIn(), PatientSignInStatus.signedIn);

        List<PrescriptionMedicineReturnOrderLine> newReturnOrderLineList = new ArrayList<>();

        for (Fee fee : feeListToCancel) {
            if (fee.getMedicineOrderLine() != null) {//药房领药，需要生成退药单
                newReturnOrderLineList.add(fee.getMedicineOrderLine().createReturnOrderLine());
                this.updateFeeStatus(fee, FeeStatus.pendingOnMedicineReturn, FeeStatusAction.medicineReturn, cancelReason);
            } else {
                if (fee.getEntityType() == EntityType.item) {
                    List<CachedItemTransaction> reverseTransactionList = fee.createItemReverseTransactionList();
                    ebeanServer.saveAll(reverseTransactionList);
                    for (CachedItemTransaction itemTransaction : reverseTransactionList)
                        this.inventoryItemService.updateItemStockCache(itemTransaction);
                } else if (fee.getEntityType() == EntityType.medicine) {
                    List<CachedMedicineTransaction> reverseTransactionList = fee.createMedicineReverseTransactionList();
                    ebeanServer.saveAll(reverseTransactionList);
                    for (CachedMedicineTransaction medicineTransaction : reverseTransactionList)
                        this.inventoryMedicineService.updateMedicineStockCache(medicineTransaction);
                }

                if (enableYBService)
                    this.ybService.cancelFee(fee);
                else if (enableHYYBService)
                    this.ybServiceHy.cancelFee(fee);
                this.updateFeeStatus(fee, FeeStatus.canceled, FeeStatusAction.cancel, cancelReason);
            }
        }

        if (newReturnOrderLineList.size() > 0)
            this.generateReturnOrder(newReturnOrderLineList);

    }

    private void generateReturnOrder(List<PrescriptionMedicineReturnOrderLine> returnLineList) {
        PrescriptionMedicineReturnOrder returnOrder = new PrescriptionMedicineReturnOrder();
        returnOrder.setWard(returnLineList.get(0).getOriginOrderLine().getOrder().getWard());
        returnOrder.setLineList(returnLineList);
        returnOrder.setStatus(PrescriptionMedicineOrderStatus.submitted);
        returnOrder = this.inventoryMedicineService.savePrescriptionMedicineReturnOrder(returnOrder);
        this.notificationService.returnMedicineOrderSubmitted(returnOrder);
    }

    public PagedList<AutoFee> getAutoFeeList(UUID patientSignInId, Integer pageNum, AutoFeeFilterDto filterDto) {
        ExpressionList<AutoFee> el = ebeanServer.find(AutoFee.class).where()
                .eq("patientSignIn.uuid", patientSignInId);
        if (filterDto.getEnabled() != null)
            el = el.and().eq("enabled", filterDto.getEnabled());
        Query<AutoFee> query = el.orderBy("whenCreated desc");
        return findPagedList(query, pageNum);
    }

    public AutoFee saveAutoFee(FeeSaveDto feeSaveDto) {
        AutoFee newAutoFee = feeSaveDto.toAutoFeeEntity();
//        String errorMessage = this.validateFeeToSave(newAutoFee);
//        if (errorMessage != null)
//            throw new ApiValidationException(errorMessage);
        ebeanServer.save(newAutoFee);
        return newAutoFee;
    }

    @Transactional
    public void enableAutoFee(UUID autoFeeId, boolean enabled) {
        AutoFee autoFee = ebeanServer.find(AutoFee.class)
                .where()
                .eq("uuid", autoFeeId)
                .findOne();

        if (enabled)
            this.patientSignInService.validatePatientSignInStatus(autoFee.getPatientSignIn(), PatientSignInStatus.signedIn);

        autoFee.setEnabled(enabled);
        ebeanServer.update(autoFee);

        AutoFeeChangeLog newLog = new AutoFeeChangeLog();
        newLog.setAutoFee(autoFee);
        if (enabled)
            newLog.setAction("启用");
        else
            newLog.setAction("停用");
        ebeanServer.save(newLog);
    }


    public void feeAutoRun() {
        //后台运行，没有TOKEN，模拟一个用户
        String whoCreated = "00000000-0000-0000-0000-000000000000 (系统)";
        User user = new User(whoCreated, "", new ArrayList<>());
        JwtUser jwtUser = new JwtUser(user);
        currentUserProvider.setJwtUser(jwtUser);

        Date feeDate = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            feeDate = sdf.parse("2021-05-19 20:30:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //非上传项目收费滚费
        this.internalFeeAutoRun(feeDate);
        this.hisInternalFeeAutoRun(feeDate);
        //床位费
        this.generateSystemFee(feeDate);
        //病区自动滚费
        this.generateAutoFee();

        //上传费用
        if (this.enableYBService) {
            this.ybService.uploadAllFee();
            this.ybInventoryService.uploadPatientPendingFeeInventory(null);
        }
    }


    public void autoFeeManualRun(UUID autoFeeId) {
        AutoFee autoFee = ebeanServer.find(AutoFee.class, autoFeeId);
        if (!autoFee.isEnabled())
            throw new ApiValidationException("自动计费未启用");
        Date currentDate = new Date();
        Date feeDate = autoFee.getFeeDate();
        while (feeDate.before(currentDate)) {
            this.generateSingleAutoFee(autoFee, feeDate);
            feeDate = this.addDays(feeDate, 1);
        }

    }

    public void generateAutoFee() {
        List<AutoFee> feeList = ebeanServer.find(AutoFee.class).where()
                .and()
                .eq("enabled", true)
                .findList();
        for (AutoFee autoFee : feeList) {
            if (autoFee.getPrescription() != null) //检查医嘱状态是否正常
            {
                Prescription prescription = autoFee.getPrescription();
                if (prescription.getStatus() != PrescriptionStatus.approved) {
                    autoFee.setEnabled(false);
                    ebeanServer.save(autoFee);
                    continue;
                }
            }
            this.generateSingleAutoFee(autoFee, new Date());
        }
    }


    @Transactional
    public void generateSingleAutoFee(AutoFee autoFee, Date feeDate) {
        try {
            ebeanServer.beginTransaction();
            Integer allowedFeeCount = this.calculateAllowedAutoFeeCount(autoFee, feeDate);
            for (int i = 0; i < allowedFeeCount; i++) {
                Fee newFee = autoFee.generateFee(feeDate);
                if (this.validateFeeToSave(newFee, false) == null) //无需再检查历时费用次数，计算可计费次数时已经检查过
                {
                    ebeanServer.save(newFee);
                    //更新库存信息
                    if (newFee.getEntityType() == EntityType.item) {
                        DepartmentWarehouse warehouse = newFee.getPatientSignIn().getDepartmentTreatment().getWardWarehouse();
                        this.updateFeeTransaction(newFee, warehouse);
                    }
                }
            }
            ebeanServer.commitTransaction();
        } catch (NoStockException ex) {

            //记录没有库存的自动费用
            this.notificationService.createAutoFeeNoStockNotification(autoFee);
            ebeanServer.rollbackTransaction();

        } catch (Exception ex) {
            ebeanServer.rollbackTransaction();
            //
            String exMessage = ex.getMessage();
            String test = null;
            //Todo log the message
        }
    }

    private Integer calculateAllowedAutoFeeCount(AutoFee autoFee, Date feeDate) {
        Integer allowedFeeCount = 0;
        //Date feeDate = new Date();
        Prescription relatedPrescription = autoFee.getPrescription();

        if (relatedPrescription != null && relatedPrescription.getPrescriptionType() != PrescriptionType.Text) {
            if (autoFee.getFrequency() != null) {
                LocalDateTime feeStartDate = LocalDateTime.ofInstant(autoFee.getPrescription().getStartDate().toInstant(), ZoneId.systemDefault());
                allowedFeeCount = this.getNoPrescriptionAllowedAutoFeeCount(autoFee, feeDate, feeStartDate);
            } else
                allowedFeeCount = this.prescriptionService.getAllowedExecutionCount(relatedPrescription, feeDate, autoFee.getFeeEntityId());


        } else {
            LocalDateTime autoFeeStartDate = LocalDateTime.ofInstant(autoFee.getFeeDate().toInstant(), ZoneId.systemDefault());
            allowedFeeCount = this.getNoPrescriptionAllowedAutoFeeCount(autoFee, feeDate, autoFeeStartDate);
        }
        return allowedFeeCount;
    }

    private Integer getNoPrescriptionAllowedAutoFeeCount(AutoFee autoFee, Date feeDate, LocalDateTime feeStartDate) {
        Integer allowedFeeCount = 0;
        Frequency frequency = autoFee.getFrequency().getFrequencyInfo();
        if (frequency == null)
            return 0;
        LocalDateTime feeDateMin = LocalDateTime.ofInstant(feeDate.toInstant(), ZoneId.systemDefault());
        int daysBetween = Math.abs((int) ((feeDateMin.toLocalDate().toEpochDay() - feeStartDate.toLocalDate().toEpochDay())));
        if (frequency.getDayInterval() != null && daysBetween % frequency.getDayInterval() == 0) {
            //allowedFeeCount = frequency.getDayQuantity();
            feeDateMin = LocalDateTime.of(feeDateMin.toLocalDate(), LocalTime.MIN);
            LocalDateTime feeDateMax = LocalDateTime.of(feeDateMin.toLocalDate(), LocalTime.MAX);
            int feeCount = this.getAutoFeeGeneratedCount(autoFee.getUuid(), feeDateMin, feeDateMax, autoFee.getFeeEntityId());
            if (frequency.getDayQuantity() - feeCount > 0)
                allowedFeeCount = frequency.getDayQuantity() - feeCount;
        } else if (frequency.getDayRange() != null) {
            int rangeIndex = daysBetween / frequency.getDayRange();
            LocalDateTime rangeStartDate = feeStartDate.plusDays(rangeIndex * frequency.getDayRange());
            LocalDateTime rangeEndDate = rangeStartDate.plusDays(frequency.getDayRange());
            int feeCount = this.getAutoFeeGeneratedCount(autoFee.getUuid(), rangeStartDate, rangeEndDate, autoFee.getFeeEntityId());
            if (frequency.getRangeLimit() - feeCount > 0)
                allowedFeeCount = frequency.getDayQuantity();
        }
        return allowedFeeCount;
    }

    private void hisInternalFeeAutoRun(Date feeDate) {
        List<lukelin.his.domain.entity.Internal_account.AutoFee> feeList = ebeanServer.find(lukelin.his.domain.entity.Internal_account.AutoFee.class).where()
                .and()
                .eq("enabled", true)
                .findList();

        for (lukelin.his.domain.entity.Internal_account.AutoFee autoFee : feeList) {
            if (autoFee.getPatientSignIn().getStatus() == PatientSignInStatus.signedOut) {
                autoFee.setEnabled(false);
                ebeanServer.save(autoFee);
            } else {
                lukelin.his.domain.entity.Internal_account.Fee newFee = autoFee.toFee();
                if (feeDate != null)
                    newFee.setFeeDate(feeDate);
                ebeanServer.save(newFee);
            }
        }
    }

    private void internalFeeAutoRun(Date feeDate) {
        List<AutoFeeTemp> feeList = ebeanServer.find(AutoFeeTemp.class).where()
                .and()
                .eq("enabled", true)
                .findList();

        for (AutoFeeTemp autoFeeTemp : feeList) {
            FeeTemp newFeeTemp = autoFeeTemp.toFee();
            if (feeDate != null)
                newFeeTemp.setFeeDate(feeDate);

            ebeanServer.save(newFeeTemp);
        }

    }

    private void generateSystemFee(Date feeDate) {
        List<PatientSignIn> patientSignInList = ebeanServer.find(PatientSignIn.class)
                .where()
                .eq("status", PatientSignInStatus.signedIn)
                .findList();
        ChargeableItem extraBedFeeItem = null;

        for (PatientSignIn patientSignIn : patientSignInList) {
            WardRoomBed bed = patientSignIn.getCurrentBed();
            if (bed != null && bed.getTreatment() != null) {
                Fee newBedFee = new Fee();
                newBedFee.setFeeRecordMethod(FeeRecordMethod.auto);
                newBedFee.setFeeDate(new Date());
                if (feeDate != null)
                    newBedFee.setFeeDate(feeDate);

                newBedFee.setPatientSignIn(patientSignIn);
                //Todo, 这里需改为创建用户所属的科室ID
                newBedFee.setDepartment(patientSignIn.getDepartmentTreatment());
                newBedFee.setQuantity(BigDecimal.ONE); //最小单位
                newBedFee.setFeeEntityId(bed.getTreatment().getUuid());
                newBedFee.setEntityType(EntityType.treatment);
                newBedFee.setFeeStatus(FeeStatus.confirmed);
                newBedFee.setDepartmentName(newBedFee.getDepartment().getDepartment().getName());
                //TreatmentSnapshot latestSnapShot = bed.getTreatment().findLatestSnapshot();
                BigDecimal bedFeeLimit = null;
                if (!patientSignIn.selfPay())
                    bedFeeLimit = new BigDecimal("50.00");
                bed.getTreatment().setFeeValue(newBedFee, bedFeeLimit);
                if (bedFeeLimit != null && bed.getTreatment().getListPrice().compareTo(bedFeeLimit) > 0) {
                    if (extraBedFeeItem == null)
                        extraBedFeeItem = ebeanServer.find(ChargeableItem.class).where().eq("name", "床位调价").findOne();
                    this.createInternalExtraBedFee(patientSignIn, extraBedFeeItem, bed.getTreatment().getListPrice().subtract(bedFeeLimit));

                }
                ebeanServer.save(newBedFee);
            }
        }
    }

    private void createInternalExtraBedFee(PatientSignIn patientSignIn, ChargeableItem extraBedFeeItem, BigDecimal extraBedFeeAmount) {
        try {
            lukelin.his.domain.entity.Internal_account.Fee extraBedFee = new lukelin.his.domain.entity.Internal_account.Fee();
            extraBedFee.setPatientSignIn(patientSignIn);
            extraBedFee.setQuantity(1);
            extraBedFee.setUnitAmount(extraBedFeeAmount);
            extraBedFee.setFeeDate(new Date());
            extraBedFee.setFeeStatus(FeeStatus.confirmed);
            extraBedFee.setItem(extraBedFeeItem);
            ebeanServer.save(extraBedFee);
        } catch (Exception ex) {
            //Todo write log;
        }
    }

    @Transactional
    public void timeAdjustment(FeeTimeAdjust reqDto) {
        Fee existingFee = this.findById(Fee.class, reqDto.getFeeId());
        if (existingFee.getFeeStatus() != FeeStatus.confirmed)
            throw new ApiValidationException("费用为非确认状态");
        if (existingFee.getFeeUploadResult() != null)
            throw new ApiValidationException("费用已上传至医保");
        existingFee.setFeeDate(reqDto.getNewTime());
        ebeanServer.save(existingFee);
    }

    @Transactional
    public void supervisorAdjustment(SupervisorAdjust reqDto) {
        Fee existingFee = this.findById(Fee.class, reqDto.getFeeId());
        if (existingFee.getFeeStatus() != FeeStatus.confirmed)
            throw new ApiValidationException("费用为非确认状态");
        if (existingFee.getFeeUploadResult() != null)
            throw new ApiValidationException("费用已上传至医保");
        Employee supervisor = ebeanServer.find(Employee.class, reqDto.getSupervisorId());
        existingFee.setSupervisor(supervisor);
        ebeanServer.save(existingFee);
    }

    @Transactional
    public List<FeeCheckPrescriptionDto> patientFeeCheck(UUID patientSignInId, FeeFilterDto filter) {
        //获取病人所有费用
        List<FeeStatus> statusList = new ArrayList<>();
        statusList.add(FeeStatus.confirmed);
        statusList.add(FeeStatus.pendingOnMedicineReturn);
        filter.setFeeStatusList(statusList);
        List<Fee> allFeeList = this.getFeeList(patientSignInId, filter);


        List<FeeCheckPrescriptionDto> feeCheckPrescriptionDtoList = new ArrayList<>();

        //处理有医嘱的费用
        List<Fee> feeHasPrescriptionList = allFeeList.stream().filter(f -> f.getPrescription() != null).collect(Collectors.toList());
        List<Prescription> prescriptionList = feeHasPrescriptionList.stream().map(BaseFee::getPrescription).distinct().collect(Collectors.toList());
        for (Prescription prescription : prescriptionList) {
            List<Fee> prescriptionFeeList = feeHasPrescriptionList.stream().filter(f -> f.getPrescription() == prescription).collect(Collectors.toList());
            FeeCheckPrescriptionDto prescriptionDto = this.createFeeCheckPrescriptionDto(prescriptionFeeList, prescription);
            feeCheckPrescriptionDtoList.add(prescriptionDto);
        }
        feeCheckPrescriptionDtoList.sort(Comparator.comparing(f -> f.getPrescriptionListDto().getStartDate()));

        //无医嘱费用
        List<Fee> feeNoPrescriptionList = allFeeList.stream().filter(f -> f.getPrescription() == null).collect(Collectors.toList());
        if (feeNoPrescriptionList.size() > 0) {
            FeeCheckPrescriptionDto prescriptionDto = this.createFeeCheckPrescriptionDto(feeNoPrescriptionList, null);
            feeCheckPrescriptionDtoList.add(prescriptionDto);
        }


        return feeCheckPrescriptionDtoList;

    }

    private FeeCheckPrescriptionDto createFeeCheckPrescriptionDto(List<Fee> prescriptionFeeList, Prescription prescription) {

        FeeCheckPrescriptionDto prescriptionDto = new FeeCheckPrescriptionDto();
        if (prescription != null) {
            prescriptionDto.setPrescriptionListDto(prescription.toListRespDto());
            prescriptionDto.setDuration(prescription.getDuration());
            prescriptionDto.setUuid(prescription.getUuid());
        } else {
            //无医嘱，手动建一个DTO
            PrescriptionListRespDto listRespDto = new PrescriptionListRespDto();
            prescriptionDto.setPrescriptionListDto(listRespDto);
            listRespDto.setDescription("无医嘱");
            prescriptionDto.setUuid(UUID.randomUUID());
        }
        List<FeeCheckEntityDto> entityDtoList = new ArrayList<>();
        prescriptionDto.setEntityList(entityDtoList);


        List<UUID> feeEntityIdList = prescriptionFeeList.stream().map(BaseFee::getFeeEntityId).distinct().collect(Collectors.toList());
        for (UUID feeEntityId : feeEntityIdList) {
            List<Fee> entityPrescriptionFeeList = prescriptionFeeList.stream().filter(f -> f.getFeeEntityId().equals(feeEntityId)).collect(Collectors.toList());
            FeeCheckEntityDto entityDto = this.createFeeCheckEntityDto(entityPrescriptionFeeList);
            entityDto.setId(feeEntityId.toString() + prescriptionDto.getUuid());
            entityDtoList.add(entityDto);

        }
        return prescriptionDto;
    }

    private FeeCheckEntityDto createFeeCheckEntityDto(List<Fee> entityPrescriptionFeeList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        FeeCheckEntityDto entityDto = new FeeCheckEntityDto();
        BigDecimal totalEntityFeeAmount = entityPrescriptionFeeList.stream().map(Fee::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        Fee firstRecordFee = entityPrescriptionFeeList.stream().min(Comparator.comparing(Fee::getFeeDate)).get();
        Fee lastRecordFee = entityPrescriptionFeeList.stream().max(Comparator.comparing(Fee::getFeeDate)).get();
        Integer totalFeeCount = entityPrescriptionFeeList.size();
        Integer totalDayCount = (int) entityPrescriptionFeeList.stream().map(f -> sdf.format(f.getFeeDate())).distinct().count();
        entityDto.setDescription(firstRecordFee.getDescription());
        entityDto.setEntityType(firstRecordFee.getEntityType());
        if (entityDto.getEntityType() == EntityType.item) {
            entityDto.setDepartmentModel(firstRecordFee.getItemSnapshot().getItem().getDepartmentModel());
            entityDto.setManufacturer(firstRecordFee.getItemSnapshot().getItem().getManufacturerItem().getName());
        } else if (entityDto.getEntityType() == EntityType.medicine) {
            entityDto.setDepartmentModel(firstRecordFee.getMedicineSnapshot().getMedicine().getDepartmentModel());
            entityDto.setManufacturer(firstRecordFee.getMedicineSnapshot().getMedicine().getManufacturerMedicine().getName());
        }
        entityDto.setFeeTypeName(firstRecordFee.getFeeTypeName());
        entityDto.setFeeEntityId(firstRecordFee.getFeeEntityId());
        entityDto.setFirstRecordDate(firstRecordFee.getFeeDate());
        entityDto.setLastRecordDate(lastRecordFee.getFeeDate());
        entityDto.setRecordCount(totalFeeCount);
        entityDto.setRecordDayCount(totalDayCount);
        entityDto.setTotalAmount(totalEntityFeeAmount);

        entityPrescriptionFeeList.sort(Comparator.comparing(Fee::getFeeDate));
        List<FeeListDto> feeList = new ArrayList<>();
        entityDto.setFeeList(feeList);
        for (Fee fee : entityPrescriptionFeeList)
            feeList.add(fee.toListDto());
        return entityDto;
    }

    public String returnCurrentInvoiceNumber() {
        Organization organization = ebeanServer.find(Organization.class).findOne();
        String invoiceNumber = organization.getInvoiceNumber();
        if (invoiceNumber == null)
            invoiceNumber = "1";
        return invoiceNumber;
    }

    @Transactional
    public SettlementHYSummaryResp getSettlementHYSummary(UUID patientSignInId) {
        SettlementHYSummaryResp resp = new SettlementHYSummaryResp();
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        ViewFeeSummaryByType feeSummaryByType = ebeanServer.find(ViewFeeSummaryByType.class).where()
                .eq("patientSignInId", patientSignInId).findOne();
        BeanUtils.copyProperties(feeSummaryByType, resp);
        return resp;
    }


    @Transactional
    public InvoiceDto generateInvoice(UUID patientSignInId, String invoiceNumber) {
        //更新系统发票号
        this.updateSystemInvoiceNumber(invoiceNumber);

        //
        Date dateNow = new Date();
        LocalDate localDateNow = LocalDate.now();
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        //Settlement settlement = patientSignIn.getSettlement();
        SettlementHY settlementHY = patientSignIn.getSettlementHY();

        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setSelfPay(patientSignIn.selfPay());
        invoiceDto.setInvoiceNumber(invoiceNumber);
        if (settlementHY != null && settlementHY.getSetl_id() != null)
            invoiceDto.setYbjsh(settlementHY.getSetl_id());

        WardRoomBed bed = patientSignIn.findLastOrCurrentBed();
        if (bed != null)
            invoiceDto.setWardInfo(bed.getWardRoom().getWard().getName());
        invoiceDto.setGender(patientSignIn.getPatient().getGender().getName());
        invoiceDto.setDepartmentName(patientSignIn.getDepartmentTreatment().getDepartment().getName());
        Integer signInDays = patientSignIn.getPatientSignInDays();
        invoiceDto.setTotalSignInDays(signInDays.toString());


        invoiceDto.setYbType(patientSignIn.getInsuranceType().getName());
        invoiceDto.setInvoiceYear(String.valueOf(localDateNow.getYear()));
        invoiceDto.setInvoiceMonth(String.valueOf(localDateNow.getMonth().getValue()));
        invoiceDto.setInvoiceDay(String.valueOf(localDateNow.getDayOfMonth()));
        invoiceDto.setPatientName(patientSignIn.getPatient().getName());
        invoiceDto.setWorkUnit(patientSignIn.getPatient().getPlaceOfWork());
        invoiceDto.setSignInNumber(patientSignIn.getSignInNumberCode());
        Date signInDate = patientSignIn.getSignInDate();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        invoiceDto.setSignInDate(sdf.format(signInDate));
        Date signOutDate = patientSignIn.getSingOutRequest().getSignOutDate();
        invoiceDto.setSignOutDate(sdf.format(signOutDate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(signInDate);
        invoiceDto.setSignInYear(calendar.get(Calendar.YEAR));
        invoiceDto.setSignInMonth(calendar.get(Calendar.MONTH) + 1);
        invoiceDto.setSignInDay(calendar.get(Calendar.DAY_OF_MONTH));
        calendar.setTime(signOutDate);
        invoiceDto.setSignOutYear(calendar.get(Calendar.YEAR));
        invoiceDto.setSignOutMonth(calendar.get(Calendar.MONTH) + 1);
        invoiceDto.setSignOutDay(calendar.get(Calendar.DAY_OF_MONTH));


        //获取费用信息
        ViewFeeSummaryByType feeSummaryByType = ebeanServer.find(ViewFeeSummaryByType.class).where()
                .eq("patientSignInId", patientSignInId).findOne();
        BeanUtils.copyProperties(feeSummaryByType, invoiceDto);
        invoiceDto.setTotalAmountChinese(Utils.numberToChinese(invoiceDto.getFeeTotalSelf().toString()));


        //结算信息
        if (!patientSignIn.selfPay()) {
            invoiceDto.setCashAmount(settlementHY.getPsn_part_amt());
            invoiceDto.setFeeTotalSelf(settlementHY.getPsn_part_amt());
//            invoiceDto.setFromBalanceThisYear(settlement.getDNZHZF());
//            invoiceDto.setFromBalancePreviousYear(settlement.getLNZHZF());
//            invoiceDto.setCivilSubsidy(settlement.getGWYBZZF());
//            invoiceDto.setSeriousDiseaseSubsidy(settlement.getDBJE());
//            invoiceDto.setOverallPayment(settlement.getTCJE());
//            invoiceDto.setYbSelfAmount(settlement.getGRZF());
//            invoiceDto.setYbSelfAmountAll(settlement.getZfje());
//            invoiceDto.setYbSelfAmountRatio(settlement.getZlje());
//            invoiceDto.setCardBalance(settlement.getJSHDNGZYE());
//            invoiceDto.setYbAccessAmount(settlement.getQFX());
//            invoiceDto.setRetirementFund(settlement.getLXJJZF());
//            invoiceDto.setMedicalSubsidy(settlement.getYLJZ());
//            invoiceDto.setSpecialCareSubsidy(settlement.getYFBZ());
//            invoiceDto.setSupplementInsurance(settlement.getXJJE());
//            invoiceDto.setFamilyFund(settlement.getJTGJJJ());
//            invoiceDto.setFundInfo1("自负金额:" + invoiceDto.getYbSelfAmount().toString() + "自理:" + invoiceDto.getYbSelfAmountRatio().toString() + "自费:" + invoiceDto.getYbSelfAmountAll().toString() + "卡余额:" + invoiceDto.getCardBalance().toString());
//            invoiceDto.setFundInfo2("起付标准:" + invoiceDto.getYbAccessAmount().toString() + "离休基金:" + invoiceDto.getRetirementFund().toString() + "医疗补助:" + invoiceDto.getMedicalSubsidy().toString() + "优抚补助:" + invoiceDto.getSpecialCareSubsidy().toString());
//            invoiceDto.setFundInfo3("补充医疗保险:" + invoiceDto.getSupplementInsurance().toString() + "家庭共济基金:" + invoiceDto.getFamilyFund().toString());
        }


        //付款信息
        Optional<ViewPaymentSummary> optionalPaymentInfo = Ebean.find(ViewPaymentSummary.class).where().eq("patientSignInId", patientSignInId).findOneOrEmpty();
        if (optionalPaymentInfo.isPresent())
            invoiceDto.setTotalPayment(optionalPaymentInfo.get().getTotalPayment());
        else
            invoiceDto.setTotalPayment(BigDecimal.ZERO);

        BigDecimal totalSelfCash = null;
        if (patientSignIn.selfPay())
            totalSelfCash = invoiceDto.getFeeTotalSelf();
        else
            totalSelfCash = invoiceDto.getCashAmount(); //医保现金支付数目
        if (invoiceDto.getTotalPayment().compareTo(totalSelfCash) > 0)
            invoiceDto.setRefund(invoiceDto.getTotalPayment().subtract(totalSelfCash));
        else if (invoiceDto.getTotalPayment().compareTo(totalSelfCash) < 0)
            invoiceDto.setRemainingPayment(totalSelfCash.subtract(invoiceDto.getTotalPayment()));
        //保存发票信息
        Invoice invoice = new Invoice();
        invoice.setPatientSignIn(patientSignIn);
        BeanUtils.copyProperties(invoiceDto, invoice);
        ebeanServer.save(invoice);
        return invoiceDto;
    }

    private void updateSystemInvoiceNumber(String invoiceNumber) {
        Organization organization = ebeanServer.find(Organization.class).findOne();
        //if(organization.getInvoiceNumber() ==null)
        String strNumber = "";
        for (int i = 0; i < invoiceNumber.length(); i++) {
            if (invoiceNumber.charAt(i) != '0') {
                strNumber = invoiceNumber.substring(i);
                break;
            }
        }
        Integer number = Integer.parseInt(strNumber);
        number++;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8 - number.toString().length(); i++)
            sb.append("0");
        String newInvoiceNumber = sb.toString() + number.toString();
        organization.setInvoiceNumber(newInvoiceNumber);
        ebeanServer.save(organization);
    }

}
