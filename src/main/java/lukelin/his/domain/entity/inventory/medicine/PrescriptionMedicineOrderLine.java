package lukelin.his.domain.entity.inventory.medicine;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.*;
import lukelin.his.domain.Interfaces.SnapshotSignInInfoInterface;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;
import lukelin.his.domain.entity.yb.PharmacyOrderUpload;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Inventory.*;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderLineRespDto;
import lukelin.his.dto.yb.inventory.req.OrderHeaderReq;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;
import lukelin.his.dto.yb.inventory.req.OrderLineReq;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "inventory.prescription_medicine_order_line")
public class PrescriptionMedicineOrderLine extends BaseEntity implements DtoConvertible<PrescriptionMedicineOrderLineRespDto>, SnapshotSignInInfoInterface, StockTransactionInterface {
    @OneToOne()
    @JoinColumn(name = "prescription_medicine_id", nullable = false)
    private PrescriptionMedicine prescriptionMedicine;

    @OneToOne()
    @JoinColumn(name = "medicine_snapshot_id", nullable = false)
    private MedicineSnapshot medicineSnapshot;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", nullable = false)
    private PrescriptionMedicineOrder order;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private PrescriptionMedicineOrderLineStatus status;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "sign_in_code", nullable = false)
    private String patientSignInCode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "medicineOrderLine")
    private Fee fee;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "originOrderLine")
    private PrescriptionMedicineReturnOrderLine returnOrderLine;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @Column(name = "medicine_search_code", nullable = false)
    private String medicineSearchCode;

    public String getMedicineSearchCode() {
        return medicineSearchCode;
    }

    public void setMedicineSearchCode(String medicineSearchCode) {
        this.medicineSearchCode = medicineSearchCode;
    }

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

    @OneToMany(mappedBy = "prescriptionMedicineOrderLine", cascade = CascadeType.ALL)
    private List<PrescriptionOrderMedicineTransaction> medicineTransactionList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderLine")
    private PharmacyOrderUpload ybOrderUpload;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "origin_order_line_id")
    private PrescriptionMedicineOrderLine originOrderLine;

    public PrescriptionMedicineOrderLine getOriginOrderLine() {
        return originOrderLine;
    }

    public void setOriginOrderLine(PrescriptionMedicineOrderLine originOrderLine) {
        this.originOrderLine = originOrderLine;
    }

    public PharmacyOrderUpload getYbOrderUpload() {
        return ybOrderUpload;
    }

    public void setYbOrderUpload(PharmacyOrderUpload ybOrderUpload) {
        this.ybOrderUpload = ybOrderUpload;
    }

    public List<PrescriptionOrderMedicineTransaction> getMedicineTransactionList() {
        return medicineTransactionList;
    }

    public void setMedicineTransactionList(List<PrescriptionOrderMedicineTransaction> medicineTransactionList) {
        this.medicineTransactionList = medicineTransactionList;
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

    public WardRoomBed getBed() {
        return bed;
    }

    public void setBed(WardRoomBed bed) {
        this.bed = bed;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public PrescriptionMedicineReturnOrderLine getReturnOrderLine() {
        return returnOrderLine;
    }

    public void setReturnOrderLine(PrescriptionMedicineReturnOrderLine returnOrderLine) {
        this.returnOrderLine = returnOrderLine;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        this.setMedicineName(medicine.getName());
        this.setMedicineSearchCode(medicine.getSearchCode());
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
        this.snapshotSignIn(patientSignIn.getUuid());
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSignInCode() {
        return patientSignInCode;
    }

    public void setPatientSignInCode(String patientSignInCode) {
        this.patientSignInCode = patientSignInCode;
    }

    public PrescriptionMedicineOrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionMedicineOrderLineStatus status) {
        this.status = status;
    }


    public PrescriptionMedicine getPrescriptionMedicine() {
        return prescriptionMedicine;
    }

    public void setPrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
        this.prescriptionMedicine = prescriptionMedicine;
    }

    public MedicineSnapshot getMedicineSnapshot() {
        return medicineSnapshot;
    }

    public void setMedicineSnapshot(MedicineSnapshot medicineSnapshot) {
        this.medicineSnapshot = medicineSnapshot;
    }

    public PrescriptionMedicineOrder getOrder() {
        return order;
    }

    public void setOrder(PrescriptionMedicineOrder order) {
        this.order = order;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public PrescriptionMedicineOrderLineRespDto toDto() {
        PrescriptionMedicineOrderLineRespDto dto = DtoUtils.convertRawDto(this);
        dto.setPatientSignInId(this.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription().getPatientSignIn().getUuid());
        dto.setPrescriptionMedicineId(this.getPrescriptionMedicine().getUuid());
        dto.setOrderNumber(this.getOrder().getOrderNumberCode());
        dto.setProcessedDate(this.getOrder().getProcessedDate());
        dto.setOrderLineCommon(this.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription().toOrderLineCommonDto(this.getMedicine()));
        dto.setOrderQuantity(this.getQuantity().intValue());
        dto.setOrderQuantityInfo(this.getMedicine().getDisplayQuantity(WarehouseType.pharmacy, this.getQuantity()));
        dto.setSlipRequired(this.getMedicine().getSlipTypeOne() || this.getMedicine().getSlipTypeTwo());
        this.getPrescriptionMedicine().setPrescriptionInfo(dto);

        WardRoomBed wardRoomBed = this.getPatientSignIn().getBedByDate(this.getWhenCreated());
        dto.setPatientName(wardRoomBed.getName() + ":" + this.getPatientSignIn().getPatient().getName());
        dto.setWardRoom(wardRoomBed.getWardRoom().getName());
        dto.setWard(wardRoomBed.getWardRoom().getWard().getName());
        dto.setBedOrder(wardRoomBed.getOrder());
        dto.setRoomOrder(wardRoomBed.getWardRoom().getOrder());
        dto.setWardOrder(wardRoomBed.getWardRoom().getWard().getOrder());

        if (this.returnOrderLine != null)
            dto.setReturnOrderProcessedDate(this.returnOrderLine.getOrder().getProcessedDate());

        if (this.getYbOrderUpload() != null) {
            dto.setUploadStatus(this.getYbOrderUpload().getStatus());
            dto.setUploadError(this.getYbOrderUpload().getErrorMessage());
        }
        return dto;
    }

    public Fee generateNewFee() {
        Fee newFee = new Fee();
        newFee.setFeeRecordMethod(FeeRecordMethod.def);
        Prescription prescription = this.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription();
        newFee.setPrescription(prescription);
        newFee.setFeeStatus(FeeStatus.confirmed);
        newFee.setPatientSignIn(prescription.getPatientSignIn());
        newFee.setDepartment(prescription.getDepartment());
        newFee.setDepartmentName(prescription.getDepartment().getDepartment().getName());
        newFee.setFeeDate(new Date());
        newFee.setQuantity(this.getQuantity());
        this.getMedicine().setMedicineFeeValue(newFee);
        newFee.setMedicineOrderLine(this);
        newFee.setFeeEntityId(this.getMedicine().getUuid());
        newFee.setEntityType(EntityType.medicine);

        CachedMedicineTransaction medicineTransaction = this.getMedicineTransactionList().stream()
                .filter(t -> t.getStatus() == PrescriptionOrderTransactionStatus.pendingConfirm && t.getTransactionType() == TransactionType.medicineOrder).findFirst().get().getMedicineTransaction();

        newFee.setBatch(medicineTransaction.getOriginPurchaseLine().getBatchNumber());
        newFee.setOriginMedicinePurchaseLine(medicineTransaction.getOriginPurchaseLine());
        newFee.setWarehouse(medicineTransaction.getWarehouse());

        return newFee;
    }

    public PrescriptionMedicineReturnOrderLine createReturnOrderLine() {
        PrescriptionMedicineReturnOrderLine returnOrderLine = new PrescriptionMedicineReturnOrderLine();
        returnOrderLine.setStatus(PrescriptionMedicineReturnOrderLineStatus.pending);
        returnOrderLine.setOriginOrderLine(this);
        returnOrderLine.setMedicineName(this.getMedicine().getName());
        returnOrderLine.setQuantity(this.getQuantity());
        returnOrderLine.setPatientName(this.getPatientName());
        returnOrderLine.setPatientSignInCode(this.getPatientSignInCode());
        this.setStatus(PrescriptionMedicineOrderLineStatus.pendingReturn);
        return returnOrderLine;
    }

    public List<CachedTransactionInterface> createTransactionList(DepartmentWarehouse defaultPharmacy) {
        BigDecimal totalQuantityToUpdate = this.getQuantity();
        List<? extends CachedEntityStockInterface> currentStockList = this.getCurrentStockList(totalQuantityToUpdate, defaultPharmacy, StockMovementRule.expireDateFirst);
        List<CachedTransactionInterface> transactionInterfaceList = this.createStockListTransaction(currentStockList, totalQuantityToUpdate, defaultPharmacy);
        return transactionInterfaceList;
    }

    public void createPrescriptionOrderTransactionList(List<CachedTransactionInterface> newTransactionList, PrescriptionOrderTransactionStatus status, TransactionType transactionType) {
        List<PrescriptionOrderMedicineTransaction> prescriptionOrderTransactionList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : newTransactionList) {
            PrescriptionOrderMedicineTransaction prescriptionOrderTransaction = new PrescriptionOrderMedicineTransaction();
            prescriptionOrderTransaction.setMedicineTransaction((CachedMedicineTransaction) cachedTransactionInterface);
            prescriptionOrderTransaction.setStatus(status);
            prescriptionOrderTransaction.setTransactionType(transactionType);
            prescriptionOrderTransaction.setPrescriptionMedicineOrderLine(this);
            prescriptionOrderTransactionList.add(prescriptionOrderTransaction);
        }
        this.setMedicineTransactionList(prescriptionOrderTransactionList);
    }

    public List<CachedTransactionInterface> reversePrescriptionOrderTransactionList(PrescriptionOrderTransactionStatus fromStatus, TransactionType newTransactionType, PrescriptionOrderTransactionStatus toStatus) {
        List<PrescriptionOrderMedicineTransaction> prescriptionOrderTransactionList = this.getMedicineTransactionList().stream()
                .filter(t -> t.getStatus() == fromStatus && t.getTransactionType() == TransactionType.medicineOrder).collect(Collectors.toList());
        List<CachedTransactionInterface> newTransactionList = new ArrayList<>();
        for (PrescriptionOrderMedicineTransaction prescriptionOrderMedicineTransaction : prescriptionOrderTransactionList) {
            //入库记录或者退还记录
            CachedMedicineTransaction cachedMedicineTransaction = prescriptionOrderMedicineTransaction.getMedicineTransaction().createReverseTransaction();
            cachedMedicineTransaction.setType(newTransactionType);
            cachedMedicineTransaction.setWarehouse(prescriptionOrderMedicineTransaction.getMedicineTransaction().getWarehouse());
            newTransactionList.add(cachedMedicineTransaction);

            //更新出库记录状态
            prescriptionOrderMedicineTransaction.setStatus(toStatus);
            Ebean.save(prescriptionOrderMedicineTransaction);
        }
        return newTransactionList;
    }

    public void updatePendingPrescriptionOrderTransactionListStatus(Fee fee) {
        List<PrescriptionOrderMedicineTransaction> prescriptionOrderTransactionList = this.getMedicineTransactionList().stream()
                .filter(t -> t.getStatus() == PrescriptionOrderTransactionStatus.pendingConfirm && t.getTransactionType() == TransactionType.medicineOrder).collect(Collectors.toList());
        for (PrescriptionOrderMedicineTransaction prescriptionOrderMedicineTransaction : prescriptionOrderTransactionList) {
            prescriptionOrderMedicineTransaction.setStatus(PrescriptionOrderTransactionStatus.confirmed);
            Ebean.save(prescriptionOrderMedicineTransaction);

            FeeMedicineTransaction newFeeMedicineTransaction = new FeeMedicineTransaction();
            newFeeMedicineTransaction.setFee(fee);
            newFeeMedicineTransaction.setMedicineTransaction(prescriptionOrderMedicineTransaction.getMedicineTransaction());
            Ebean.save(newFeeMedicineTransaction);
        }


    }


    @Override
    public List<CachedTransactionInterface> createStockTransaction(BigDecimal newTransactionQuantity, UUID reasonId, InventoryEntityInterface inventoryEntity, OrderLineInterface originPurchaseLine, DepartmentWarehouse warehouse) {
        List<CachedTransactionInterface> newStockTransactionList = new ArrayList<>();
        CachedTransactionInterface newTransaction = this.createNewTransactionInstance();
        TransactionType transactionType = TransactionType.medicineOrder;
        newTransaction.setPropertyValue(transactionType, newTransactionQuantity.multiply(new BigDecimal(-1)), this.getUuid(), warehouse, this.getInventoryEntity(), originPurchaseLine);
        newStockTransactionList.add(newTransaction);
        return newStockTransactionList;
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedMedicineTransaction();
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getMedicine();
    }


    public OrderHeaderReq toYBOrderHeader(InventoryOrderType orderType) {
        DepartmentWarehouse defaultWareHouse = this.getPatientSignIn().getDepartmentTreatment().getDefaultPharmacy();
        OrderHeaderReq req = new OrderHeaderReq();
        req.setCYWDH(this.getUuid().toString());
        req.setKFBH(defaultWareHouse.getWarehouseUploaded().getServerCode());
        req.setYWDM(orderType.toString());
        req.setYWBM(defaultWareHouse.getDepartment().getName());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        req.setFSRQ(df.format(new Date()));
        req.setGYSMC("不适用");
        return req;

    }

    public OrderLineReq toYBOrderLineDto(List<CachedTransactionInterface> newTransactionList, boolean isOut) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        OrderLineReq req = new OrderLineReq();
        req.setCYWDH(this.getUuid().toString());
        List<OrderLineDetailReq> lineDetailList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : newTransactionList) {
            CachedMedicineTransaction medicineTransaction = (CachedMedicineTransaction) cachedTransactionInterface;
            OrderLineDetailReq detailReq = medicineTransaction.toYBOrderLineDetailReq(df, isOut);
            lineDetailList.add(detailReq);
        }
        req.setYWMXLB(lineDetailList);
        return req;

    }

    public PrescriptionMedicineOrderLine generatePartialLeftLine(BigDecimal lineLeftQuantity) {
        PrescriptionMedicineOrderLine orderLine = new PrescriptionMedicineOrderLine();
        BeanUtils.copyPropertiesIgnoreNull(this, orderLine);
        orderLine.setUuid(null);
        orderLine.setQuantity(lineLeftQuantity);
        orderLine.setBed(this.getBed());
        orderLine.setPrescriptionMedicine(this.getPrescriptionMedicine());
        orderLine.setOrder(this.getOrder());
        orderLine.setStatus(PrescriptionMedicineOrderLineStatus.approved);
        orderLine.setPatientSignIn(this.getPatientSignIn());
        orderLine.setMedicine(this.getMedicine());
        orderLine.setBed(this.getBed());
        orderLine.setWardDepartment(this.getWardDepartment());
        orderLine.setOriginOrderLine(this);
        return orderLine;
    }
}
