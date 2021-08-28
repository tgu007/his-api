package lukelin.his.domain.entity.account;

import com.fasterxml.jackson.databind.util.BeanUtil;
import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.*;
import lukelin.his.domain.Interfaces.SnapshotSignInInfoInterface;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.entity.*;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.inventory.item.CachedItemTransaction;
import lukelin.his.domain.entity.inventory.item.FeeItemTransaction;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.inventory.medicine.*;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionOrderTransactionStatus;
import lukelin.his.domain.enums.Inventory.StockMovementRule;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.domain.enums.YB.PharmacyOrderUploadStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.account.response.BaseFeeListDto;
import lukelin.his.dto.account.response.FeeListDto;
import lukelin.his.dto.yb.inventory.req.PharmacyOrder;
import lukelin.his.dto.yb.inventory.req.PharmacyOrderLine;
import lukelin.his.dto.yb.resp.FeeUploadLineReq;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "account.fee")
public class Fee extends BaseFee implements FeeInterface, SnapshotSignInInfoInterface {

    public Fee() {
    }

    public Fee(UUID uuid) {
        this.setUuid(uuid);
    }

    @ManyToOne
    @JoinColumn(name = "item_snapshot_id")
    private ItemSnapshot itemSnapshot;

    @ManyToOne
    @JoinColumn(name = "treatment_snapshot_id")
    private TreatmentSnapshot treatmentSnapshot;

    @ManyToOne
    @JoinColumn(name = "medicine_snapshot_id")
    private MedicineSnapshot medicineSnapshot;

    @Column(name = "status", nullable = false)
    private FeeStatus feeStatus;

    @Column(name = "unit_amount", nullable = false)
    private BigDecimal unitAmount;

    @Column(name = "search_code")
    private String searchCode;

    @Column(name = "uom_name", nullable = false)
    private String uomName;

    @Column(name = "fee_type_name", nullable = false)
    private String feeTypeName;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @OneToOne
    @JoinColumn(name = "medicine_order_line_id")
    private PrescriptionMedicineOrderLine medicineOrderLine;


    @Column(name = "manual_recorded", nullable = false)
    private boolean manualRecorded = false;

    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL)
    private List<FeeMedicineTransaction> medicineTransactionList;

    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL)
    private List<FeeItemTransaction> itemTransactionList;

    @Column(name = "unit_amount_info", nullable = false)
    private String unitAmountInfo;

    @Column(name = "quantity_info", nullable = false)
    private String quantityInfo;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fee")
    private FeeUploadResult feeUploadResult;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fee")
    private FeeUploadError feeUploadError;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fee")
    private FeeInventoryUploadResult feeInventoryUploadResult;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fee")
    private FeeInventoryUploadError feeInventoryUploadError;

    @Column(name = "batch")
    private String batch;

    @ManyToOne
    @JoinColumn(name = "item_origin_line_id")
    private ItemOrderLine originItemPurchaseLine;

    @ManyToOne
    @JoinColumn(name = "medicine_origin_line_id")
    private MedicineOrderLine originMedicinePurchaseLine;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private DepartmentWarehouse warehouse;

    @Column(name = "fee_record_type")
    private FeeRecordMethod feeRecordMethod = FeeRecordMethod.def;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @ManyToOne
    @JoinColumn(name = "auto_fee_id")
    private AutoFee autoFee;

    @Column(name = "self_pay")
    private Boolean selfPay;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fee")
    private PreSettlementFee preSettlementFee;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fee")
    private FeeDownloadLine centerDownloadedFee;

    @OneToOne
    @JoinColumn(name = "origin_fee_id")
    private Fee originFullFee;

    @ManyToOne
    @JoinColumn(name = "parent_fee_id")
    private Fee parentFee;

    public FeeInventoryUploadResult getFeeInventoryUploadResult() {
        return feeInventoryUploadResult;
    }

    public void setFeeInventoryUploadResult(FeeInventoryUploadResult feeInventoryUploadResult) {
        this.feeInventoryUploadResult = feeInventoryUploadResult;
    }

    public FeeInventoryUploadError getFeeInventoryUploadError() {
        return feeInventoryUploadError;
    }

    public void setFeeInventoryUploadError(FeeInventoryUploadError feeInventoryUploadError) {
        this.feeInventoryUploadError = feeInventoryUploadError;
    }

    public FeeDownloadLine getCenterDownloadedFee() {
        return centerDownloadedFee;
    }

    public void setCenterDownloadedFee(FeeDownloadLine centerDownloadedFee) {
        this.centerDownloadedFee = centerDownloadedFee;
    }

    public Fee getParentFee() {
        return parentFee;
    }

    public void setParentFee(Fee parentFee) {
        this.parentFee = parentFee;
    }

    public Fee getOriginFullFee() {
        return originFullFee;
    }

    public void setOriginFullFee(Fee originFullFee) {
        this.originFullFee = originFullFee;
    }

    public PreSettlementFee getPreSettlementFee() {
        return preSettlementFee;
    }

    public void setPreSettlementFee(PreSettlementFee preSettlementFee) {
        this.preSettlementFee = preSettlementFee;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public AutoFee getAutoFee() {
        return autoFee;
    }

    public void setAutoFee(AutoFee autoFee) {
        this.autoFee = autoFee;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public FeeRecordMethod getFeeRecordMethod() {
        return feeRecordMethod;
    }

    public void setFeeRecordMethod(FeeRecordMethod feeRecordMethod) {
        this.feeRecordMethod = feeRecordMethod;
    }

    public ItemOrderLine getOriginItemPurchaseLine() {
        return originItemPurchaseLine;
    }

    public void setOriginItemPurchaseLine(ItemOrderLine originItemPurchaseLine) {
        this.originItemPurchaseLine = originItemPurchaseLine;
    }

    public MedicineOrderLine getOriginMedicinePurchaseLine() {
        return originMedicinePurchaseLine;
    }

    public void setOriginMedicinePurchaseLine(MedicineOrderLine originMedicinePurchaseLine) {
        this.originMedicinePurchaseLine = originMedicinePurchaseLine;
    }

    public DepartmentWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(DepartmentWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public FeeUploadResult getFeeUploadResult() {
        return feeUploadResult;
    }

    public void setFeeUploadResult(FeeUploadResult feeUploadResult) {
        this.feeUploadResult = feeUploadResult;
    }

    public FeeUploadError getFeeUploadError() {
        return feeUploadError;
    }

    public void setFeeUploadError(FeeUploadError feeUploadError) {
        this.feeUploadError = feeUploadError;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        super.setPatientSignIn(patientSignIn);
        this.snapshotSignIn(patientSignIn.getUuid());
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

    public ItemSnapshot getItemSnapshot() {
        return itemSnapshot;
    }

    public void setItemSnapshot(ItemSnapshot itemSnapshot) {
        this.itemSnapshot = itemSnapshot;
    }

    public TreatmentSnapshot getTreatmentSnapshot() {
        return treatmentSnapshot;
    }

    public void setTreatmentSnapshot(TreatmentSnapshot treatmentSnapshot) {
        this.treatmentSnapshot = treatmentSnapshot;
    }

    public MedicineSnapshot getMedicineSnapshot() {
        return medicineSnapshot;
    }

    public void setMedicineSnapshot(MedicineSnapshot medicineSnapshot) {
        this.medicineSnapshot = medicineSnapshot;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getFeeTypeName() {
        return feeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    public PrescriptionMedicineOrderLine getMedicineOrderLine() {
        return medicineOrderLine;
    }

    public void setMedicineOrderLine(PrescriptionMedicineOrderLine medicineOrderLine) {
        this.medicineOrderLine = medicineOrderLine;
    }

    public boolean isManualRecorded() {
        return manualRecorded;
    }

    public void setManualRecorded(boolean manualRecorded) {
        this.manualRecorded = manualRecorded;
    }

    public List<FeeMedicineTransaction> getMedicineTransactionList() {
        return medicineTransactionList;
    }

    public void setMedicineTransactionList(List<FeeMedicineTransaction> medicineTransactionList) {
        this.medicineTransactionList = medicineTransactionList;
    }

    public List<FeeItemTransaction> getItemTransactionList() {
        return itemTransactionList;
    }

    public void setItemTransactionList(List<FeeItemTransaction> itemTransactionList) {
        this.itemTransactionList = itemTransactionList;
    }

    public String getUnitAmountInfo() {
        return unitAmountInfo;
    }

    public void setUnitAmountInfo(String unitAmountInfo) {
        this.unitAmountInfo = unitAmountInfo;
    }

    public String getQuantityInfo() {
        return quantityInfo;
    }

    public void setQuantityInfo(String quantityInfo) {
        this.quantityInfo = quantityInfo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public FeeListDto toListDto() {
        FeeListDto dto = new FeeListDto();
        dto.setUuid(this.getUuid());
        dto.setWhoCreated(this.getWhoCreatedName());
        dto.setFeeStatus(this.getFeeStatus());
        dto.setFeeDate(this.getFeeDate());
        this.setBaseFeeListDtoValue(dto);
//        if (!this.getPatientSignIn().selfPay()) {
//
//        } else {
//            dto.setUploadStatus(YBUploadStatus.selfPay);
//            dto.setSelfRatio("100%");
//            dto.setSelfFeeAmount(this.getTotalAmount());
//            dto.setInsuranceAmount(BigDecimal.ZERO);
//        }

        if (this.getFeeUploadResult() != null) {
            dto.setUploadStatus(YBUploadStatus.uploaded);
            dto.setSelfRatio(this.getFeeUploadResult().getSelfRatio().multiply(new BigDecimal("100")).toString() + "%");
            dto.setInsuranceAmount(this.getFeeUploadResult().getInsuranceAmount());
            dto.setSelfFeeAmount(this.getTotalAmount().subtract(dto.getInsuranceAmount()));
        } else if (this.getFeeUploadError() != null) {
            dto.setUploadStatus(YBUploadStatus.error);
            dto.setUploadError(this.getFeeUploadError().getError());
        } else if (this.getSelfPay() != null && this.getSelfPay()) {
            dto.setUploadStatus(YBUploadStatus.selfPay);
            dto.setSelfRatio("100%");
            dto.setSelfFeeAmount(this.getTotalAmount());
            dto.setInsuranceAmount(BigDecimal.ZERO);
        } else
            dto.setUploadStatus(YBUploadStatus.notUploaded);
        if (this.getEntityType() == EntityType.treatment) {
            dto.setDuration(this.getTreatmentSnapshot().getTreatment().getDuration());
            dto.setAllowMultiExecution(this.getTreatmentSnapshot().getTreatment().getAllowMultiExecution());
        }

        dto.setOverlapping(false);
        if (dto.getDuration() != null && this.getEntityType() == EntityType.treatment) {
            Integer overlapCount = Ebean.find(Fee.class).where()
                    .ne("uuid", this.getUuid())
                    .eq("feeStatus", FeeStatus.confirmed)
                    .eq("patientSignIn.uuid", this.getPatientSignIn().getUuid())
                    .eq("entityType", EntityType.treatment)
                    .eq("treatmentSnapshot.treatment.executeDepartmentType", DepartmentTreatmentType.recover)
                    .ne("treatmentSnapshot.treatment.duration", null)
                    .between("feeDate", this.getFeeDate(), this.getExpectedFinishTime())
                    .findCount();
            if (overlapCount > 0)
                dto.setOverlapping(true);
        }
        dto.setFeeRecordMethod(this.feeRecordMethod);
        dto.setDepartmentId(this.getDepartment().getUuid());
        if (this.getSupervisor() != null)
            dto.setSupervisorName(this.getSupervisor().getName());
        dto.setSelfPay(this.getSelfPay());

        dto.setMissingSupervisor(false);
        if (this.getFeeRecordMethod() == FeeRecordMethod.def || this.getFeeRecordMethod() == FeeRecordMethod.manual) {
            if (this.getEntityType() == EntityType.treatment) {
                UUID createdById = this.getWhoCreatedId();
                Employee createdBy = Ebean.find(Employee.class).where().eq("accountId", createdById).findOne();
                if (StringUtils.isBlank(createdBy.getExtraInfo()) && this.getSupervisor() == null)
                    dto.setMissingSupervisor(true);
            }
        }
        return dto;
    }

    public void setBaseFeeListDtoValue(BaseFeeListDto baseFeeListDto) {
        baseFeeListDto.setName(this.getDescription());
        baseFeeListDto.setUomName(this.getUomName());
        baseFeeListDto.setFeeType(this.getFeeTypeName());
        baseFeeListDto.setQuantity(this.getQuantity());
        baseFeeListDto.setUnitAmount(this.getUnitAmount().setScale(4));
        baseFeeListDto.setDisplayUnitAmount(this.getUnitAmountInfo());
        baseFeeListDto.setDisplayQuantityInfo(this.getQuantityInfo());
        baseFeeListDto.setTotalAmount(this.getTotalAmount());
    }


    public List<CachedTransactionInterface> createTransactionList(DepartmentWarehouse warehouse) {
        BigDecimal totalQuantityToUpdate = this.getQuantity();
        List<? extends CachedEntityStockInterface> currentStockList = null;
        if (this.getEntityType() == EntityType.medicine)
            currentStockList = this.getCurrentStockList(totalQuantityToUpdate, warehouse, StockMovementRule.expireDateFirst);
        else
            currentStockList = this.getCurrentStockList(totalQuantityToUpdate, warehouse, StockMovementRule.inFirstOutLast);

        List<CachedTransactionInterface> transactionInterfaceList = this.createStockListTransaction(currentStockList, totalQuantityToUpdate, warehouse);
        return transactionInterfaceList;
    }

    public List<CachedMedicineTransaction> createMedicineReverseTransactionList() {
        List<CachedMedicineTransaction> originTransactionList = this.getMedicineTransactionList().stream().map(t -> t.getMedicineTransaction()).collect(Collectors.toList());
        List<CachedMedicineTransaction> reverseTransactionList = new ArrayList<>();
        for (CachedMedicineTransaction originTransaction : originTransactionList) {
            CachedMedicineTransaction reverseTransaction = originTransaction.createReverseTransaction();
            reverseTransaction.setType(TransactionType.cancelFee);
            reverseTransactionList.add(reverseTransaction);
        }
        return reverseTransactionList;
    }

    public List<CachedItemTransaction> createItemReverseTransactionList() {
        List<CachedItemTransaction> originTransactionList = this.getItemTransactionList().stream().map(FeeItemTransaction::getItemTransaction).collect(Collectors.toList());
        List<CachedItemTransaction> reverseTransactionList = new ArrayList<>();
        for (CachedItemTransaction originTransaction : originTransactionList) {
            CachedItemTransaction reverseTransaction = originTransaction.createReverseTransaction();
            reverseTransaction.setType(TransactionType.cancelFee);
            reverseTransactionList.add(reverseTransaction);
        }
        return reverseTransactionList;
    }

    @Override
    public List<CachedTransactionInterface> createStockTransaction(BigDecimal newTransactionQuantity, UUID reasonId, InventoryEntityInterface inventoryEntity, OrderLineInterface originPurchaseLine, DepartmentWarehouse warehouse) {
        List<CachedTransactionInterface> newStockTransactionList = new ArrayList<>();
        CachedTransactionInterface newTransaction = this.createNewTransactionInstance();
        TransactionType transactionType;
        if (this.getMedicineOrderLine() != null)
            transactionType = TransactionType.medicineOrder;
        else
            transactionType = TransactionType.manualFee;

        newTransaction.setPropertyValue(transactionType, newTransactionQuantity.multiply(new BigDecimal(-1)), this.getUuid(), warehouse, this.getInventoryEntity(), originPurchaseLine);
        newStockTransactionList.add(newTransaction);
        return newStockTransactionList;
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        if (this.getEntityType() == EntityType.medicine)
            return new CachedMedicineTransaction();
        else
            return new CachedItemTransaction();
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        if (this.getEntityType() == EntityType.medicine)
            return this.getMedicineSnapshot().getMedicine();
        else
            return this.getItemSnapshot().getItem();
    }

    public FeeUploadLineReq toUploadDto() {
        FeeUploadLineReq lineReq = new FeeUploadLineReq();
        lineReq.setCfybh(this.getUuid().toString());
        BigDecimal unitLimitAmount = null;
        if (this.getEntityType() == EntityType.medicine) {
            Medicine medicine = this.getMedicineSnapshot().getMedicine();
            lineReq.setFydm(medicine.getUploadResult().getServerCode());
            lineReq.setFylb("0");
            lineReq.setGg(medicine.getDepartmentModel());
            lineReq.setDw(medicine.getMinUom().getName());
            if (medicine.getFfSign())
                lineReq.setFfbz("1");
            else
                lineReq.setFfbz("0");
            if (medicine.chineseMedicine()) {
                PrescriptionMedicine prescriptionMedicine = this.getPrescription().getPrescriptionChargeable().getPrescriptionMedicine();
                lineReq.setTs(prescriptionMedicine.getFixedChineseMedicineQuantity());
                lineReq.setMtsl(prescriptionMedicine.getServeQuantity());
            }

            lineReq.setYf(this.getPrescription().getPrescriptionChargeable().getPrescriptionMedicine().getUseMethod().getName());
            if (medicine.getDoseType() != null)
                lineReq.setJx(medicine.getDoseType().getName());
            if (medicine.getDepartmentToMinRate().compareTo(BigDecimal.ONE) != 0) {
                lineReq.setKccl("1");
            } else {
                lineReq.setKccl("0");
            }
            lineReq.setZxjl(medicine.getServeToMinRate().toString());
            lineReq.setZxjldw(medicine.getServeUom().getName());
            lineReq.setXmzjl(this.getQuantity().multiply(medicine.getServeToMinRate()).setScale(3).toString());
            if (medicine.getCenterMedicine() != null)
                lineReq.setZlbl(medicine.getCenterMedicine().getPTZFBL());
            else
                lineReq.setZlbl(BigDecimal.ONE);//自费药
            lineReq.setKpxm(medicine.getFeeType().getExtraInfo());
        } else if (this.getEntityType() == EntityType.item) {
            Item item = this.getItemSnapshot().getItem();
            lineReq.setFydm(item.getUploadResult().getServerCode());
            lineReq.setFylb("0");
            lineReq.setGg(item.getDepartmentModel());
            lineReq.setDw(item.getMinUom().getName());
            lineReq.setFfbz("0");
            if (item.getDepartmentToMinRate().compareTo(BigDecimal.ONE) != 0) {
                lineReq.setKccl("1");
            } else {
                lineReq.setKccl("0");
            }
            lineReq.setZlbl(item.getCenterTreatment().getPTZFBL());
            lineReq.setKpxm(item.getFeeType().getExtraInfo());
            //unitLimitAmount = item.getCenterTreatment().getZYXE();
        } else if (this.getEntityType() == EntityType.treatment) {
            Treatment treatment = this.getTreatmentSnapshot().getTreatment();
            lineReq.setFydm(treatment.getUploadResult().getServerCode());
            lineReq.setFylb("1");
            lineReq.setGg(treatment.getMinUom().getName());
            lineReq.setDw(treatment.getMinUom().getName());
            lineReq.setFfbz("0");
            lineReq.setZlbl(treatment.getCenterTreatment().getPTZFBL());
            lineReq.setKpxm(treatment.getFeeType().getExtraInfo());
            //unitLimitAmount = treatment.getCenterTreatment().getZYXE();
        }
        if ((this.getSelfPay() != null && this.getSelfPay()) || this.getPatientSignIn().selfPay()) {
            lineReq.setZfje(this.getTotalAmount());
            lineReq.setZlbl(BigDecimal.ONE);
        } else {
            if (lineReq.getZlbl() != null && lineReq.getZlbl().compareTo(BigDecimal.ZERO) > 1) {
                BigDecimal unitSelfRatioPayAmount = lineReq.getZlbl().multiply(this.getUnitAmount());
                lineReq.setZlje(unitSelfRatioPayAmount.multiply(this.getQuantity()));
            }
        }

        //lineReq.setKpxm(this.getFeeTypeName());
        lineReq.setPch(this.getBatch());
        if (this.getWarehouse() != null)
            lineReq.setKfbh(this.getWarehouse().getWarehouseUploaded().getServerCode());
        lineReq.setMc(this.getDescription());
        lineReq.setSl(this.getQuantity());
        lineReq.setDj(this.getUnitAmount());
        lineReq.setJe(this.getTotalAmount());
        if (this.getPrescription() != null) {
            if (this.getPrescription().getPrescriptionType() != PrescriptionType.Text)
                lineReq.setPc(this.getPrescription().getFrequency().getName());
            else
                lineReq.setPc("纯文字医嘱");
        } else
            lineReq.setPc("无医嘱");

        lineReq.setZrdj(this.getUnitAmount());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        lineReq.setFsrq(df.format(this.getFeeDate()));
//        if (this.getPrescription() != null) {
//            lineReq.setKsbm(this.getPrescription().getDepartment().getDepartment().getSequence().toString());
//            lineReq.setKsmc(this.getPrescription().getDepartment().getDepartment().getName());
//        }
        //if (this.getDepartment() != null)

        lineReq.setKsbm(this.getDepartment().getYbDepartment().getCode());
        lineReq.setKsmc(this.getDepartmentName());
        return lineReq;
    }

    public Date getExpectedFinishTime() {
        LocalDateTime localExpectFinish = this.getExpectedFinishLocalTime();
        return Date.from(localExpectFinish.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDateTime getExpectedFinishLocalTime() {
        if (this.getTreatmentSnapshot() == null)
            return null;
        if (this.getTreatmentSnapshot().getTreatment().getDuration() == null)
            return null;
        Integer duration = this.getTreatmentSnapshot().getTreatment().getDuration();
        Instant instant = this.getFeeDate().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime().plusMinutes(duration);
    }

    public Fee createLeftPartialFee(BigDecimal quantityLeft) {
        Fee partialFeeLeft = new Fee();
        BeanUtils.copyPropertiesIgnoreNull(this, partialFeeLeft);
        partialFeeLeft.setUuid(null);
        partialFeeLeft.setQuantity(quantityLeft);
        partialFeeLeft.setFeeStatus(FeeStatus.confirmed);
        partialFeeLeft.setOriginFullFee(this);

        partialFeeLeft.setPatientSignIn(this.getPatientSignIn());
        partialFeeLeft.setPrescription(this.getPrescription());
        partialFeeLeft.setDepartment(this.getDepartment());
        partialFeeLeft.setItemSnapshot(this.getItemSnapshot());
        partialFeeLeft.setMedicineSnapshot(this.getMedicineSnapshot());
        partialFeeLeft.setTreatmentSnapshot(this.getTreatmentSnapshot());
        partialFeeLeft.setBed(this.getBed());
        partialFeeLeft.setWardDepartment(this.getWardDepartment());
        partialFeeLeft.setOriginItemPurchaseLine(this.getOriginItemPurchaseLine());
        partialFeeLeft.setOriginMedicinePurchaseLine(this.getOriginMedicinePurchaseLine());
        partialFeeLeft.setSupervisor(this.getSupervisor());
        partialFeeLeft.setWarehouse(this.getWarehouse());
        partialFeeLeft.setAutoFee(this.autoFee);


        if (this.getEntityType() == EntityType.item)
            partialFeeLeft.setQuantityInfo(this.getItemSnapshot().getItem().getDisplayQuantity(WarehouseType.wardWarehouse, partialFeeLeft.getQuantity()));
        else if (this.getEntityType() == EntityType.medicine)
            partialFeeLeft.setQuantityInfo(this.getMedicineSnapshot().getMedicine().getDisplayQuantity(WarehouseType.wardWarehouse, partialFeeLeft.getQuantity()));
        else if (this.getEntityType() == EntityType.treatment)
            partialFeeLeft.setQuantityInfo(partialFeeLeft.getQuantity() + partialFeeLeft.getUomName());
        partialFeeLeft.setTotalAmount(partialFeeLeft.getUnitAmount().multiply(partialFeeLeft.getQuantity()).setScale(2, RoundingMode.HALF_UP));
        return partialFeeLeft;
    }

    public PharmacyOrder toYbPharmacyOrderDto() {
        PharmacyOrder newOrder = new PharmacyOrder();
        newOrder.setFybh(this.getFeeUploadResult().getServerId());
        newOrder.setJzxh(this.getPatientSignIn().getYbSignIn().getId());
        newOrder.setSfglkf("0"); //暂时不关联库房

        List<PharmacyOrderLine> lineList = new ArrayList<>();
        if (this.getEntityType() == EntityType.medicine) {
            List<FeeMedicineTransaction> transactionList = this.getMedicineTransactionList();
            for (FeeMedicineTransaction transaction : transactionList) {
                CachedMedicineTransaction cachedMedicineTransaction = transaction.getMedicineTransaction();
                PharmacyOrderLine pharmacyOrderLine = new PharmacyOrderLine();
                pharmacyOrderLine.setKfbh(cachedMedicineTransaction.getWarehouse().getWarehouseUploaded().getServerCode());
                pharmacyOrderLine.setPch(cachedMedicineTransaction.getOriginPurchaseLine().getBatchNumber());
                pharmacyOrderLine.setFysl(cachedMedicineTransaction.getMinUomQuantity().toString());
                lineList.add(pharmacyOrderLine);
            }
            newOrder.setYpmx(lineList);
        }

        if (this.getEntityType() == EntityType.item) {
            List<FeeItemTransaction> transactionList = this.getItemTransactionList();
            for (FeeItemTransaction transaction : transactionList) {
                CachedItemTransaction cachedItemTransaction = transaction.getItemTransaction();
                PharmacyOrderLine pharmacyOrderLine = new PharmacyOrderLine();
                pharmacyOrderLine.setKfbh(cachedItemTransaction.getWarehouse().getWarehouseUploaded().getServerCode());
                pharmacyOrderLine.setPch(cachedItemTransaction.getOriginPurchaseLine().getBatchNumber());
                if (pharmacyOrderLine.getPch() == null)
                    pharmacyOrderLine.setPch("耗材无批次");
                pharmacyOrderLine.setFysl(cachedItemTransaction.getMinUomQuantity().toString());
                lineList.add(pharmacyOrderLine);
            }
            newOrder.setYpmx(lineList);
        }

        return newOrder;
    }


    //暂时只适用Treatment
//    public Fee cloneFee(Date feeDate) {
//        Fee newFee = new Fee();
//        BeanUtils.copyPropertiesIgnoreNull(this, newFee);
//        newFee.setFeeRecordMethod(FeeRecordMethod.def);
//        newFee.setUuid(null);
//        newFee.setPatientSignIn(this.getPatientSignIn());
//        newFee.setDepartment(this.getDepartment());
//        if (this.getDescription() != null)
//            newFee.setPrescription(this.getPrescription());
//        newFee.setFeeStatus(FeeStatus.confirmed);
//        newFee.setManualRecorded(true);
//        if (this.getEntityType() == EntityType.treatment)
//            newFee.setTreatmentSnapshot(this.getTreatmentSnapshot());
//        return newFee;
//    }
}
