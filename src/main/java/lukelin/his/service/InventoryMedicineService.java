package lukelin.his.service;

import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.Query;
import lukelin.common.security.jwt.JwtUser;
import lukelin.common.springboot.ebean.DefaultCurrentUserProvider;
import lukelin.common.springboot.exception.ApiClientException;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.inventory.medicine.MedicineStockAdjustment;
import lukelin.his.domain.entity.inventory.medicine.*;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Fee.FeeStatusAction;
import lukelin.his.domain.enums.Inventory.*;
import lukelin.his.domain.enums.NotificationMessageType;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionChangeAction;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.domain.enums.YB.PharmacyOrderUploadStatus;
import lukelin.his.dto.Inventory.req.NZTransferSourceLineDto;
import lukelin.his.dto.Inventory.req.filter.*;
import lukelin.his.dto.Inventory.req.medicine.*;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicinePartialOrderLineRespDto;
import lukelin.his.system.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class InventoryMedicineService extends BaseHisService {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PatientSignInService patientSignInService;

    @Value("${uploadYBPatient}")
    private Boolean enableYBService;

    @Value("${enableHYYB}")
    private Boolean enableHYYBService;

    @Autowired
    private YBService ybService;


    @Autowired
    private YBServiceHY ybServiceHy;

    @Autowired
    private YBInventoryService ybInventoryService;

    @Value("${uploadYBInventory}")
    private Boolean uploadYBInventory;

    @Autowired
    private NotificationService notificationService;


    public PagedList<MedicineOrder> getPagedMedicineOrderList(OrderFilterDto orderFilter, int pageNum) {
        return this.getPagedMedicineOrderList(orderFilter, pageNum, 0);
    }

    public PagedList<MedicineOrder> getPagedMedicineOrderList(OrderFilterDto orderFilter, int pageNum, int pageSize) {
        ExpressionList<MedicineOrder> el = ebeanServer.find(MedicineOrder.class).fetch("orderRequest").alias("o").where()
                .in("orderStatus", orderFilter.getOrderStatusList());
        if (orderFilter.getReturnOrder() != null)
            el = el.eq("returnOrder", orderFilter.getReturnOrder());
        if (orderFilter.getWarehouseId() != null)
            el = el.eq("toWarehouse.uuid", orderFilter.getWarehouseId());
        if ((orderFilter.getStartDate() != null) && (orderFilter.getEndDate() != null)) {
            Date endDate = this.addDays(orderFilter.getEndDate(), 1);
            el = el.between("o.when_created", orderFilter.getStartDate(), endDate);
        }
        if (orderFilter.getOrderNumber() != null)
            el = el.like("orderNumberCode", "%" + orderFilter.getOrderNumber() + "%");

        if (!StringUtils.isBlank(orderFilter.getSearchNumber()))
            el = el.or()
                    .like("orderNumberCode", "%" + orderFilter.getSearchNumber() + "%")
                    .like("orderRequest.orderRequestNumberCode", "%" + orderFilter.getSearchNumber() + "%")
                    .endOr();

        Query<MedicineOrder> query = el.query().orderBy("o.when_created desc");
        return findPagedList(query, pageNum, pageSize);
    }

    @Transactional
    public MedicineOrder saveMedicineOrder(MedicineOrderSaveDto medicineOrderSaveDto) {
        MedicineOrder medicineOrder = medicineOrderSaveDto.toEntity();
        if (medicineOrder.getUuid() != null)
            ebeanServer.update(medicineOrder);
        else
            ebeanServer.save(medicineOrder);
        medicineOrder = this.findById(MedicineOrder.class, medicineOrder.getUuid());
        if (medicineOrderSaveDto.getUuid() == null) {
            medicineOrder.setOrderNumberCode(Utils.buildDisplayCode(medicineOrder.getOrderNumber()));
            ebeanServer.update(medicineOrder);
        }
        return medicineOrder;

    }

    public MedicineOrder getMedicineOrder(UUID orderId) {
        return findById(MedicineOrder.class, orderId);
    }

    @Transactional
    public void deleteMedicineOrder(UUID orderId) {
        MedicineOrder medicineOrder = this.getMedicineOrder(orderId);
        if (medicineOrder.getOrderStatus() != OrderStatus.created)
            throw new ApiValidationException("Invalid order status to delete");
        ebeanServer.delete(medicineOrder);
    }

    @Transactional
    public void submitMedicineReturnOrder(MedicineOrderSaveDto medicineOrderSaveDto) {
        this.updateMedicineOrderStatus(medicineOrderSaveDto, OrderStatus.created);
        if (this.uploadYBInventory) {
            MedicineOrder medicineOrder = this.findById(MedicineOrder.class, medicineOrderSaveDto.getUuid());
            this.ybInventoryService.newYBInventoryOrder(medicineOrder.toYBOrderHeader(), InventoryOrderType.buyReturn, medicineOrder.toYBOrderLineDto());
        }
    }

    public void rejectMedicineReturnOrder(MedicineOrderSaveDto medicineOrderSaveDto) {
        this.updateMedicineOrderStatus(medicineOrderSaveDto, OrderStatus.pendingReturn);
        if (this.uploadYBInventory) {
            this.ybInventoryService.deleteInventoryOrder(InventoryOrderType.buyReturn, medicineOrderSaveDto.getUuid());
        }

    }

    @Transactional
    public void updateMedicineOrderStatus(MedicineOrderSaveDto medicineOrderSaveDto, OrderStatus originStatus) {
        MedicineOrder medicineOrder = this.getMedicineOrder(medicineOrderSaveDto.getUuid());
        if (medicineOrder.getOrderStatus() != originStatus)
            throw new ApiValidationException("Invalid order status to update");
        medicineOrder.setOrderStatus(medicineOrderSaveDto.getOrderStatus());
        ebeanServer.save(medicineOrder);
    }

    @Transactional
    public void approveMedicineOrder(MedicineOrderSaveDto medicineOrderSaveDto) {
        MedicineOrder medicineOrder = this.findById(MedicineOrder.class, medicineOrderSaveDto.getUuid());
        if (medicineOrder.getOrderStatus() != OrderStatus.submitted)
            throw new ApiValidationException("Invalid order status to approve");
        medicineOrder.setOrderStatus(OrderStatus.approved);
        medicineOrder.setApprovedDate(new Date());
        medicineOrder.setApprovedBy(new Employee(medicineOrderSaveDto.getApproveById()));
        ebeanServer.save(medicineOrder);
    }

    @Transactional
    public void approveMedicineReturnOrder(MedicineOrderSaveDto medicineOrderSaveDto) {
        MedicineOrder medicineOrder = this.findById(MedicineOrder.class, medicineOrderSaveDto.getUuid());
        if (medicineOrder.getOrderStatus() != OrderStatus.pendingReturn)
            throw new ApiValidationException("Invalid order status to return");

        medicineOrder.setOrderStatus(OrderStatus.returned);
        medicineOrder.setApprovedDate(new Date());
        medicineOrder.setApprovedBy(new Employee(medicineOrderSaveDto.getApproveById()));
        //更新库存信息
        for (MedicineOrderLine orderLine : medicineOrder.getLineList()) {
            CachedMedicineTransaction newTransaction = (CachedMedicineTransaction) orderLine.createOrderLineTransaction();
            ebeanServer.save(newTransaction);
            this.updateMedicineStockCache(newTransaction);
        }
        ebeanServer.save(medicineOrder);

        if (this.uploadYBInventory) {
            this.ybInventoryService.confirmInventoryOrder(InventoryOrderType.buyReturn, medicineOrder.getUuid());
        }
    }


    public PagedList<MedicineTransfer> getPagedMedicineTransferList(TransferFilterDto medicineTransferFilter, int pageNum) {
        ExpressionList<MedicineTransfer> el = ebeanServer.find(MedicineTransfer.class).where();
        if (medicineTransferFilter.getFromWarehouseIdList() != null)
            el = el.in("fromWarehouse.uuid", medicineTransferFilter.getFromWarehouseIdList());
        if (medicineTransferFilter.getToWarehouseIdList() != null)
            el = el.in("toWarehouse.uuid", medicineTransferFilter.getToWarehouseIdList());
        if ((medicineTransferFilter.getStartDate() != null) && (medicineTransferFilter.getEndDate() != null))
            el = el.between("confirmedDate", medicineTransferFilter.getStartDate(), medicineTransferFilter.getEndDate());

        Query<MedicineTransfer> query = el.query().orderBy("when_created desc");
        return findPagedList(query, pageNum);
    }

    @Transactional
    public MedicineTransfer saveMedicineTransfer(MedicineTransferSaveDto medicineTransferSaveDto) {
        MedicineTransfer medicineTransfer = medicineTransferSaveDto.toEntity();
        if (medicineTransfer.getUuid() != null)
            ebeanServer.update(medicineTransfer);
        else
            ebeanServer.save(medicineTransfer);
        medicineTransfer = this.findById(MedicineTransfer.class, medicineTransfer.getUuid());

        if (medicineTransferSaveDto.getUuid() == null) {
            medicineTransfer.setTransferNumberCode(Utils.buildDisplayCode(medicineTransfer.getTransferNumber()));
            ebeanServer.update(medicineTransfer);
        }
        return medicineTransfer;

    }

    public MedicineTransfer getMedicineTransfer(UUID transferId) {
        return findById(MedicineTransfer.class, transferId);
    }

    @Transactional
    public void deleteMedicineTransfer(UUID transferId) {
        MedicineTransfer medicineTransfer = this.getMedicineTransfer(transferId);
        if (medicineTransfer.getTransferStatus() != TransferStatus.created)
            throw new ApiValidationException("Invalid transfer status to delete");
        ebeanServer.delete(medicineTransfer);
    }

//    @Transactional
//    public MedicineTransfer updateMedicineTransferStatus(MedicineTransferSaveDto medicineTransferSaveDto, TransferStatus originStatus) {
//        MedicineTransfer medicineTransfer = this.findById(MedicineTransfer.class, medicineTransferSaveDto.getUuid());
//        if (medicineTransfer.getTransferStatus() != originStatus)
//            throw new ApiValidationException("Invalid transfer status to update");
//        medicineTransfer.setTransferStatus(medicineTransferSaveDto.getTransferStatus());
//        ebeanServer.save(medicineTransfer);
//        return medicineTransfer;
//    }

    @Transactional
    public MedicineTransfer confirmMedicineTransfer(UUID transferId) {
        MedicineTransfer medicineTransfer = this.getMedicineTransfer(transferId);
        if (medicineTransfer.getTransferStatus() != TransferStatus.pendingConfirm)
            throw new ApiValidationException("Invalid transfer status to confirm");
        medicineTransfer.setTransferStatus(TransferStatus.confirmed);
        medicineTransfer.setConfirmedDate(new Date());
        for (MedicineTransferLine transferLine : medicineTransfer.getLineList()) {
            List<CachedTransactionInterface> newTransactionList = transferLine.generateTransferTransactionList(medicineTransfer.getToWarehouse(), TransactionType.transferIn);
            transferLine.createTransferTransactionList(newTransactionList, TransferTransactionStatus.confirmed, TransactionType.transferIn);
            for (CachedTransactionInterface transferMedicineTransaction : newTransactionList)
                this.updateMedicineStockCache((CachedMedicineTransaction) transferMedicineTransaction);
        }
        ebeanServer.save(medicineTransfer);

        if (this.uploadYBInventory && (medicineTransfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy || medicineTransfer.getToWarehouse().getWarehouseType() == WarehouseType.pharmacy)) {
            InventoryOrderType orderType;
            if (medicineTransfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy)
                orderType = InventoryOrderType.transferOut;
            else
                orderType = InventoryOrderType.transferIn;

            this.ybInventoryService.confirmInventoryOrder(orderType, medicineTransfer.getUuid());
        }
        return medicineTransfer;

    }

    @Transactional
    public MedicineTransfer rejectMedicineTransfer(UUID transferId) {
        MedicineTransfer medicineTransfer = this.getMedicineTransfer(transferId);
        if (medicineTransfer.getTransferStatus() != TransferStatus.pendingConfirm)
            throw new ApiValidationException("Invalid transfer status to confirm");
        medicineTransfer.setTransferStatus(TransferStatus.created);
        for (MedicineTransferLine transferLine : medicineTransfer.getLineList()) {
            List<CachedTransactionInterface> newTransactionList = transferLine.generateTransferTransactionList(medicineTransfer.getFromWarehouse(), TransactionType.rejectedTransferOut);
            transferLine.createTransferTransactionList(newTransactionList, TransferTransactionStatus.confirmed, TransactionType.rejectedTransferOut);
            for (CachedTransactionInterface transferMedicineTransaction : newTransactionList)
                this.updateMedicineStockCache((CachedMedicineTransaction) transferMedicineTransaction);
        }
        ebeanServer.save(medicineTransfer);

        if (this.uploadYBInventory && (medicineTransfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy || medicineTransfer.getToWarehouse().getWarehouseType() == WarehouseType.pharmacy)) {
            InventoryOrderType orderType;
            if (medicineTransfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy)
                orderType = InventoryOrderType.transferOut;
            else
                orderType = InventoryOrderType.transferIn;
            this.ybInventoryService.deleteInventoryOrder(orderType, medicineTransfer.getUuid());
        }
        return medicineTransfer;

    }

    @Transactional
    public MedicineTransfer transferMedicineTransfer(UUID transferId) {
        MedicineTransfer medicineTransfer = this.getMedicineTransfer(transferId);
        if (medicineTransfer.getTransferStatus() != TransferStatus.created)
            throw new ApiValidationException("Invalid transfer status to update");
        medicineTransfer.setTransferStatus(TransferStatus.pendingConfirm);
        medicineTransfer.setTransferDate(new Date());
        for (MedicineTransferLine transferLine : medicineTransfer.getLineList()) {
            List<CachedTransactionInterface> newTransactionList = transferLine.createTransferTransactionList();
            //To do need to refactor into interface
            transferLine.createTransferTransactionList(newTransactionList, TransferTransactionStatus.pendingConfirm, TransactionType.transferOut);
            //ebeanServer.saveAll(transferItemTransactionList);
            for (CachedTransactionInterface transferMedicineTransaction : newTransactionList)
                this.updateMedicineStockCache((CachedMedicineTransaction) transferMedicineTransaction);
        }
        ebeanServer.save(medicineTransfer);

        if (this.uploadYBInventory && (medicineTransfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy || medicineTransfer.getToWarehouse().getWarehouseType() == WarehouseType.pharmacy)) {
            InventoryOrderType orderType;
            if (medicineTransfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy)
                orderType = InventoryOrderType.transferOut;
            else
                orderType = InventoryOrderType.transferIn;
            this.ybInventoryService.newYBInventoryOrder(medicineTransfer.toYBOrderHeader(), orderType, medicineTransfer.toYBOrderLineDto());
        }

        return medicineTransfer;
    }

    public void updateMedicineStockCache(CachedMedicineTransaction medicineTransaction) {
        Optional<CachedMedicineStock> optionalMedicineStock = ebeanServer.find(CachedMedicineStock.class)
                .where()
                .eq("warehouse.uuid", medicineTransaction.getWarehouse().getUuid())
                .eq("medicine.uuid", medicineTransaction.getMedicine().getUuid())
                .eq("originPurchaseLine.uuid", medicineTransaction.getOriginPurchaseLine().getUuid())
                .findOneOrEmpty();
        CachedMedicineStock medicineStock;
        if (optionalMedicineStock.isPresent())
            medicineStock = optionalMedicineStock.get();
        else {
            medicineStock = new CachedMedicineStock();
        }
        medicineStock.updateFromTransaction(medicineTransaction);
        if (medicineStock.getMinUomQuantity().compareTo(BigDecimal.ZERO) == 0)
            ebeanServer.delete(medicineStock);
        else
            ebeanServer.save(medicineStock);
    }

    //返回物品再仓库中的详细信息,包括同一种物品的分批进货记录
    public List<CachedMedicineStock> getDetailMedicineStockList(MedicineStockFilterDto filterDto) {
        ExpressionList<CachedMedicineStock> el = this.buildMedicineStockQuery(filterDto);
        return el.findList();
    }

    public PagedList<CachedMedicineStock> getPagedDetailMedicineStockList(MedicineStockFilterDto filterDto, Integer pageNum) {
        return this.getPagedDetailMedicineStockList(filterDto, pageNum, 0);
    }

    public PagedList<CachedMedicineStock> getPagedDetailMedicineStockList(MedicineStockFilterDto filterDto, Integer pageNum, Integer pageSize) {
        ExpressionList<CachedMedicineStock> el = this.buildMedicineStockQuery(filterDto);
        Query<CachedMedicineStock> query = el.orderBy("whenCreated desc");
        return findPagedList(query, pageNum, pageSize);
    }

    private ExpressionList<CachedMedicineStock> buildMedicineStockQuery(MedicineStockFilterDto filterDto) {
        ExpressionList<CachedMedicineStock> el = ebeanServer.find(CachedMedicineStock.class)
                .where();

        if (filterDto.getWarehouseIdList() != null)
            el = el.in("warehouse.uuid", filterDto.getWarehouseIdList());

        if (filterDto.getMedicineId() != null)
            el = el.eq("medicine.uuid", filterDto.getMedicineId());

        if (filterDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, filterDto.getSearchCode(), "name", "medicine.searchCode");
        return el;
    }

    @Transactional
    public void createAdjustment(MedicineStockAdjustSaveDto stockAdjustSaveDto) {
        if (stockAdjustSaveDto.quantityChanged()) {
            MedicineStockAdjustment newAdjustment = stockAdjustSaveDto.toEntity();
            ebeanServer.save(newAdjustment);
            newAdjustment = this.findById(MedicineStockAdjustment.class, newAdjustment.getUuid());
            List<CachedTransactionInterface> adjustmentTransactionList = newAdjustment.createAdjustmentTransaction();
            ebeanServer.saveAll(adjustmentTransactionList);
            for (CachedTransactionInterface adjustmentMedicineTransaction : adjustmentTransactionList)
                this.updateMedicineStockCache((CachedMedicineTransaction) adjustmentMedicineTransaction);

            if (this.uploadYBInventory && newAdjustment.getWarehouse().getWarehouseType() == WarehouseType.pharmacy && !newAdjustment.getMedicine().getSelfPay()) {
                this.ybInventoryService.newYBInventoryOrder(newAdjustment.toYBOrderHeader(), InventoryOrderType.adjust, newAdjustment.toYBOrderLineDto(adjustmentTransactionList));
                this.ybInventoryService.confirmInventoryOrder(InventoryOrderType.adjust, newAdjustment.getUuid());
            }

        }

    }

    public List<PrescriptionMedicineOrder> getPendingConfirmPrescriptionMedicineOrderList(MedicineOrderFilterDto orderFilter) {
        ExpressionList<PrescriptionMedicineOrder> el = ebeanServer.find(PrescriptionMedicineOrder.class)
                .orderBy("whenCreated")
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.processed);

        if (orderFilter.getWardIdList() != null)
            el = el.in("ward.uuid", orderFilter.getWardIdList());

        return el.findList();
    }

    public List<PrescriptionMedicineOrder> getPendingPrescriptionMedicineOrderList(MedicineOrderFilterDto orderFilter) {
        ExpressionList<PrescriptionMedicineOrder> el = ebeanServer.find(PrescriptionMedicineOrder.class)
                .orderBy("whenCreated")
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.submitted);

        if (orderFilter.getWardIdList() != null)
            el = el.in("ward.uuid", orderFilter.getWardIdList());

        return el.findList();
    }

    public List<PrescriptionMedicineOrder> getRecentPrescriptionMedicineOrderList(MedicineOrderFilterDto orderFilter) {
        ExpressionList<PrescriptionMedicineOrder> el = ebeanServer.find(PrescriptionMedicineOrder.class)
                .orderBy("processedDate desc")
                .setMaxRows(50)
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.confirmed);

        if (orderFilter.getWardIdList() != null)
            el = el.in("ward.uuid", orderFilter.getWardIdList());

        return el.findList();
    }

    public PagedList<PrescriptionMedicineOrderLine> getProcessedPrescriptionMedicineOrderList(PrescriptionMedicineOrderFilterDto filter, int pageNum) {
        Query<PrescriptionMedicineOrderLine> query = this.getPrescriptionMedicineOrderLineQuery(filter);
        return findPagedList(query, pageNum);
    }

    public List<PrescriptionMedicineOrderLine> getProcessedPrescriptionMedicineOrderList(PrescriptionMedicineOrderFilterDto filter) {
        Query<PrescriptionMedicineOrderLine> query = this.getPrescriptionMedicineOrderLineQuery(filter);
        return query.findList();
    }

    private Query<PrescriptionMedicineOrderLine> getPrescriptionMedicineOrderLineQuery(PrescriptionMedicineOrderFilterDto filter) {
        ExpressionList<PrescriptionMedicineOrderLine> el = ebeanServer.find(PrescriptionMedicineOrderLine.class)
                .where()
                .eq("order.status", PrescriptionMedicineOrderStatus.confirmed);

        if (filter.getLineStatus() != null)
            el = el.eq("status", filter.getLineStatus());

        if (!StringUtils.isEmpty(filter.getSearchCode()))
            el = el.where().or()
                    .like("patientName", "%" + filter.getSearchCode() + "%")
                    .like("patientSignInCode", "%" + filter.getSearchCode() + "%")
                    .endOr();

        if ((filter.getStartDate() != null) && (filter.getEndDate() != null)) {
            Date endDate = this.addDays(filter.getEndDate(), 1);
            el = el.between("order.processedDate", filter.getStartDate(), endDate);
        }

        if (filter.getPatientSignInIdList() != null)
            el = el.in("patientSignIn.uuid", filter.getPatientSignInIdList());

        if (!StringUtils.isEmpty(filter.getMedicineSearchCode()))
            el = el.where().or()
                    .like("medicineName", "%" + filter.getMedicineSearchCode() + "%")
                    .like("medicineSearchCode", "%" + filter.getMedicineSearchCode().toUpperCase() + "%")
                    .endOr();

        if (filter.getPendingUpload() != null && filter.getPendingUpload())
            el = el.and()
                    .ne("ybOrderUpload", null)
                    .or()
                    .eq("ybOrderUpload.status", PharmacyOrderUploadStatus.error)
                    .eq("ybOrderUpload.status", PharmacyOrderUploadStatus.pending)
                    .endOr()
                    .endAnd();

        Query<PrescriptionMedicineOrderLine> query = el.query().orderBy("order.processedDate desc");
        return query;
    }

    public List<PrescriptionMedicineOrderLine> getPrescriptionMedicineOrderLineList(UUID orderId) {
        return this.findById(PrescriptionMedicineOrder.class, orderId).getLineList();
    }

    public PrescriptionMedicineOrder updatePrescriptionMedicineOrderStatus(UUID orderId, PrescriptionMedicineOrderStatus originStatus, PrescriptionMedicineOrderStatus toStatus, Date processedDate) {
        PrescriptionMedicineOrder prescriptionMedicineOrder = this.findById(PrescriptionMedicineOrder.class, orderId);
        if (prescriptionMedicineOrder.getStatus() != originStatus)
            throw new ApiValidationException("Invalid order status to update");
        prescriptionMedicineOrder.setProcessedDate(processedDate);
        prescriptionMedicineOrder.setStatus(toStatus);
        for (PrescriptionMedicineOrderLine orderLine : prescriptionMedicineOrder.getLineList()) {
            if (processedDate != null)
                orderLine.setStatus(PrescriptionMedicineOrderLineStatus.pendingConfirm);
            else
                orderLine.setStatus(PrescriptionMedicineOrderLineStatus.pending);
        }
        ebeanServer.save(prescriptionMedicineOrder);
        return prescriptionMedicineOrder;
    }

    @Transactional
    public PrescriptionMedicineOrder confirmPrescriptionMedicineOrder(UUID orderId) {
        PrescriptionMedicineOrder order = this.findById(PrescriptionMedicineOrder.class, orderId);
        if (order.getStatus() != PrescriptionMedicineOrderStatus.processed)
            throw new ApiValidationException("Invalid order status to confirm");
        order.setStatus(PrescriptionMedicineOrderStatus.confirmed);
        for (PrescriptionMedicineOrderLine orderLine : order.getLineList()) {
            if (orderLine.getStatus() == PrescriptionMedicineOrderLineStatus.pendingConfirm) {
                Prescription prescription = orderLine.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription();
                //Todo 需要整体验证
                if (prescription.getStatus() == PrescriptionStatus.canceled)
                    throw new ApiValidationException(orderLine.getPatientName() + ":" + orderLine.getMedicine().getName() + "医嘱已作废");

                if(!orderLine.getMedicine().isEnabled())
                    throw new ApiValidationException(orderLine.getMedicine().getName() + "药品已作废");

                this.patientSignInService.validatePatientSignInStatus(prescription.getPatientSignIn(), PatientSignInStatus.signedIn);
                //建立费用
                Fee newFee = orderLine.generateNewFee();
                String feeValidationMessage = this.accountService.validateFeeToSave(newFee, false);
                if (!StringUtils.isBlank(feeValidationMessage))
                    throw new ApiValidationException(orderLine.getPatientName() + ":" + orderLine.getMedicine().getName() + feeValidationMessage);
                ebeanServer.save(newFee); //需要先存储，因为transaction reason id没有外联
                //如果为临时医嘱，更新医嘱信息
                if (prescription.isOneOff())
                    this.prescriptionService.doPrescriptionUpdate(prescription, PrescriptionStatus.disabled, PrescriptionChangeAction.executed);

                orderLine.updatePendingPrescriptionOrderTransactionListStatus(newFee);
                orderLine.setStatus(PrescriptionMedicineOrderLineStatus.approved);


                if (this.uploadYBInventory) {
                    //非医保药品不用处理
                    if (orderLine.getMedicineSnapshot().getSelfPay())
                        continue;

                    this.ybInventoryService.confirmPrescriptionMedicineOrder(orderLine);

                }
            }
        }
        ebeanServer.save(order);
        return order;
    }

    @Transactional
    public PrescriptionMedicineOrder rejectPrescriptionMedicineOrder(UUID orderId) {
        PrescriptionMedicineOrder order = this.findById(PrescriptionMedicineOrder.class, orderId);
        if (order.getStatus() != PrescriptionMedicineOrderStatus.processed)
            throw new ApiValidationException("Invalid order status to confirm");
        order.setStatus(PrescriptionMedicineOrderStatus.submitted);
        order.setProcessedDate(null);
        for (PrescriptionMedicineOrderLine orderLine : order.getLineList()) {
            if (orderLine.getStatus() == PrescriptionMedicineOrderLineStatus.pendingConfirm) {
                //建立库存信息
                List<CachedTransactionInterface> newTransactionList = orderLine.reversePrescriptionOrderTransactionList(PrescriptionOrderTransactionStatus.pendingConfirm, TransactionType.rejectedMedicineOrder, PrescriptionOrderTransactionStatus.confirmed);
                orderLine.createPrescriptionOrderTransactionList(newTransactionList, PrescriptionOrderTransactionStatus.confirmed, TransactionType.rejectedMedicineOrder);

                for (CachedTransactionInterface transferMedicineTransaction : newTransactionList)
                    this.updateMedicineStockCache((CachedMedicineTransaction) transferMedicineTransaction);


//                if (this.uploadYBInventory) {
//                    //非医保药品不用处理
//                    if (orderLine.getMedicineSnapshot().getSelfPay())
//                        continue;
//                    if (orderLine.getPatientSignIn().selfPay())
//                        //发药时处理自费病人的医保开单，确认收药后自费病人登账，医保病人调用发药借口
//                        this.ybInventoryService.deleteInventoryOrder(InventoryOrderType.sellOut, orderLine.getUuid());
//                }
            }
            orderLine.setStatus(PrescriptionMedicineOrderLineStatus.pending);
        }
        ebeanServer.save(order);
        return order;
    }

    @Transactional
    public PrescriptionMedicineOrder processPrescriptionMedicineOrder(UUID orderId, PrescriptionMedicineOrderProcessDto orderProcessDto) {
        PrescriptionMedicineOrder order = this.findById(PrescriptionMedicineOrder.class, orderId);
        if (order.getStatus() != PrescriptionMedicineOrderStatus.submitted)
            throw new ApiValidationException("Invalid order status to confirm");
        order.setStatus(PrescriptionMedicineOrderStatus.processed);
        order.setProcessedDate(new Date());
        for (PrescriptionMedicineOrderLine orderLine : order.getLineList()) {
            PrescriptionMedicineOrderLineProcessDto lineProcessDto =
                    orderProcessDto.getOrderLineList().stream()
                            .filter(l -> (l.getOrderLineId() != null && l.getOrderLineId().equals(orderLine.getUuid())) || (l.getMedicineId() != null && l.getMedicineId().equals(orderLine.getMedicine().getUuid())))
                            .findFirst().get();

            if (lineProcessDto.getApprove()) {
                Prescription prescription = orderLine.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription();
                //Todo 需要整体验证
                if (prescription.getStatus() == PrescriptionStatus.canceled)
                    throw new ApiValidationException(orderLine.getPatientName() + ":" + orderLine.getMedicine().getName() + "医嘱已作废");

                if(!orderLine.getMedicine().isEnabled())
                    throw new ApiValidationException(orderLine.getMedicine().getName() + "药品已作废");

                this.patientSignInService.validatePatientSignInStatus(prescription.getPatientSignIn(), PatientSignInStatus.signedIn);
                orderLine.setStatus(PrescriptionMedicineOrderLineStatus.pendingConfirm);

                //建立药房出库信息
                this.createPrescriptionMedicineOrderTransaction(orderLine);


//                if (this.uploadYBInventory) {
//                    //非医保药品不用处理
//                    if (orderLine.getMedicineSnapshot().getSelfPay())
//                        continue;
//                    //发药时处理自费病人的医保开单，确认收药后自费病人登账，医保病人调用发药借口
//                    if (prescription.getPatientSignIn().selfPay())
//                        this.ybInventoryService.newYBInventoryOrder(orderLine.toYBOrderHeader(InventoryOrderType.sellOut), InventoryOrderType.sellOut, orderLine.toYBOrderLineDto(newTransactionList, true));
//                }
            } else
                orderLine.setStatus(PrescriptionMedicineOrderLineStatus.rejected);
        }
        ebeanServer.save(order);
        return order;
    }

    private List<CachedTransactionInterface> createPrescriptionMedicineOrderTransaction(PrescriptionMedicineOrderLine orderLine) {
        List<CachedTransactionInterface> newTransactionList = orderLine.createTransactionList(orderLine.getPatientSignIn().getDepartmentTreatment().getDefaultPharmacy());
        orderLine.createPrescriptionOrderTransactionList(newTransactionList, PrescriptionOrderTransactionStatus.pendingConfirm, TransactionType.medicineOrder);

        for (CachedTransactionInterface cachedTransactionInterface : newTransactionList) {
            CachedMedicineTransaction transferMedicineTransaction = (CachedMedicineTransaction) cachedTransactionInterface;
            this.updateMedicineStockCache(transferMedicineTransaction);
        }
        ebeanServer.saveAll(newTransactionList);
        return newTransactionList;
    }


    @Transactional
    public PrescriptionMedicineReturnOrder createPrescriptionMedicineReturnOrder
            (PrescriptionMedicineReturnOrderCreateDto returnOrderCreateDto) {
        PrescriptionMedicineReturnOrder returnOrder = returnOrderCreateDto.toEntity();
        returnOrder = this.savePrescriptionMedicineReturnOrder(returnOrder);
        return returnOrder;
    }

    public PrescriptionMedicineReturnOrder savePrescriptionMedicineReturnOrder(PrescriptionMedicineReturnOrder returnOrder) {
        ebeanServer.save(returnOrder);
        //更新数据库生成的ORDER NUMBER
        returnOrder = this.findById(PrescriptionMedicineReturnOrder.class, returnOrder.getUuid());
        returnOrder.setOrderNumberCode(Utils.buildDisplayCode(returnOrder.getOrderNumber()));
        ebeanServer.update(returnOrder);
        return returnOrder;
    }

    public List<PrescriptionMedicineReturnOrder> getPendingPrescriptionMedicineReturnOrderList
            (MedicineOrderFilterDto orderFilter) {
        ExpressionList<PrescriptionMedicineReturnOrder> el = ebeanServer.find(PrescriptionMedicineReturnOrder.class)
                .orderBy("whenCreated")
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.submitted);

        if (orderFilter.getWardIdList() != null)
            el = el.in("ward.uuid", orderFilter.getWardIdList());

        return el.findList();
    }

    public List<PrescriptionMedicineReturnOrder> getRecentPrescriptionMedicineReturnOrderList
            (MedicineOrderFilterDto orderFilter) {
        ExpressionList<PrescriptionMedicineReturnOrder> el = ebeanServer.find(PrescriptionMedicineReturnOrder.class)
                .orderBy("processedDate desc")
                .setMaxRows(50)
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.processed);

        if (orderFilter.getWardIdList() != null)
            el = el.in("ward.uuid", orderFilter.getWardIdList());
        return el.findList();
    }

    public List<PrescriptionMedicineReturnOrderLine> getPrescriptionMedicineReturnOrderLineList(UUID orderId) {
        return this.findById(PrescriptionMedicineReturnOrder.class, orderId).getLineList();
    }

    @Transactional
    public PrescriptionMedicineReturnOrder processPrescriptionMedicineReturnOrder(UUID orderId, PrescriptionMedicineReturnOrderProcessDto orderProcessDto) {
        PrescriptionMedicineReturnOrder order = this.findById(PrescriptionMedicineReturnOrder.class, orderId);
        order.setStatus(PrescriptionMedicineOrderStatus.processed);
        order.setProcessedDate(new Date());
        List<Prescription> pendingDisablePrescriptionList = new ArrayList<>();
        for (PrescriptionMedicineReturnOrderLine returnOrderLine : order.getLineList()) {
            Fee fee = returnOrderLine.getOriginOrderLine().getFee();
            PrescriptionMedicineOrderLine orderLine = returnOrderLine.getOriginOrderLine();
            if (orderProcessDto.getReturnOrderLineIdList().contains(returnOrderLine.getUuid())) {
                returnOrderLine.setStatus(PrescriptionMedicineReturnOrderLineStatus.approved);
                orderLine.setStatus(PrescriptionMedicineOrderLineStatus.returned);

                if (fee.getFeeStatus() != FeeStatus.canceled) {
                    List<Fee> feeList = new ArrayList<>();
                    feeList.add(fee);
                    //退回库存
                    //List<CachedMedicineTransaction> reverseTransactionList = orderLine.createReverseTransactionList();
                    List<CachedTransactionInterface> reverseTransactionList = orderLine.reversePrescriptionOrderTransactionList(PrescriptionOrderTransactionStatus.confirmed, TransactionType.cancelFee, PrescriptionOrderTransactionStatus.returned);
                    orderLine.createPrescriptionOrderTransactionList(reverseTransactionList, PrescriptionOrderTransactionStatus.returned, TransactionType.cancelFee);
                    for (CachedTransactionInterface reversTransaction : reverseTransactionList) {
                        this.updateMedicineStockCache((CachedMedicineTransaction) reversTransaction);
                    }
                    ebeanServer.saveAll(reverseTransactionList);
                    this.accountService.updateFeeStatus(feeList, FeeStatus.canceled, FeeStatusAction.medicineReturn, "");


                    if (this.uploadYBInventory) {
                        //非医保药品不用处理
                        if (orderLine.getMedicineSnapshot().getSelfPay())
                            continue;
                        this.ybInventoryService.processPrescriptionMedicineReturnOrder(returnOrderLine, reverseTransactionList);
                    }
                    if (enableYBService)
                        this.ybService.cancelFee(fee);
                    else if(enableHYYBService)
                        this.ybServiceHy.cancelFee(fee);
                }

                //如果部分退药，需在原发药单上生成一个新的剩余数量的发药列
                BigDecimal lineLeftQuantity = orderLine.getQuantity().subtract(returnOrderLine.getQuantity());
                if (lineLeftQuantity.compareTo(BigDecimal.ZERO) > 0)
                    this.generatePartialLeftOrderLine(orderLine, lineLeftQuantity);
            } else {
                returnOrderLine.setStatus(PrescriptionMedicineReturnOrderLineStatus.rejected);
                fee.setFeeStatus(FeeStatus.confirmed);
                orderLine.setStatus(PrescriptionMedicineOrderLineStatus.approved);
                if (fee.getPrescription().getStatus() == PrescriptionStatus.canceled) {
                    this.prescriptionService.doPrescriptionUpdate(fee.getPrescription(), PrescriptionStatus.pendingDisable, PrescriptionChangeAction.failedOnMedicineReturn);
                    pendingDisablePrescriptionList.add(fee.getPrescription());
                }
            }

            ebeanServer.save(returnOrderLine.getOriginOrderLine());
        }
//        if(pendingDisablePrescriptionList.size() >0)
//            this.notificationService.addPendingDisablePrescriptionNotification(pendingDisablePrescriptionList);
        ebeanServer.save(order);
        return order;
    }


    @Autowired
    private DefaultCurrentUserProvider currentUserProvider;

    private void generatePartialLeftOrderLine(PrescriptionMedicineOrderLine originOrderLine, BigDecimal lineLeftQuantity) {
        PrescriptionMedicineOrderLine partialLeftLine = originOrderLine.generatePartialLeftLine(lineLeftQuantity);
        ebeanServer.save(partialLeftLine);
        //建立药房出库信息
        this.createPrescriptionMedicineOrderTransaction(partialLeftLine);
        //建立费用信息
        Fee newFee = partialLeftLine.generateNewFee();
        Fee originFee = originOrderLine.getFee();
        newFee.setMedicineSnapshot(originFee.getMedicineSnapshot());
        newFee.setQuantityInfo(originOrderLine.getMedicine().getDisplayQuantity(WarehouseType.wardWarehouse, newFee.getQuantity()));
        newFee.setTotalAmount(newFee.getUnitAmount().multiply(newFee.getQuantity()).setScale(2, RoundingMode.HALF_UP));
        newFee.setFeeDate(originOrderLine.getFee().getFeeDate());
        newFee.setOriginFullFee(originFee);

        JwtUser currentUser = currentUserProvider.getJwtUser();
        String whoCreated = originFee.getWhoCreated();
        User user = new User(whoCreated, "", new ArrayList<>());
        JwtUser jwtUser = new JwtUser(user);
        currentUserProvider.setJwtUser(jwtUser);
        ebeanServer.save(newFee);
        currentUserProvider.setJwtUser(currentUser);
        //建立确认收药库存信息
        partialLeftLine.updatePendingPrescriptionOrderTransactionListStatus(newFee);
        if (this.uploadYBInventory && !partialLeftLine.getMedicineSnapshot().getSelfPay()) {
            this.ybInventoryService.confirmPrescriptionMedicineOrder(partialLeftLine);
        }
    }


//    public void generateMedicineTransfer(UUID orderId) {
//        MedicineOrder order = this.findById(MedicineOrder.class, orderId);
//        if (order.getOrderStatus() != OrderStatus.approved)
//            throw new ApiValidationException("invalid order status");
//
//        List<DepartmentWarehouse> warehouseList = ebeanServer.find(DepartmentWarehouse.class).where().eq("warehouseType", WarehouseType.pharmacy).findList();
//        if (warehouseList.size() != 1)
//            throw new ApiValidationException("warehouse count not equal to 1, not supported");
//
//        MedicineTransfer newTransfer = order.generateTransfer(warehouseList.get(0));
//        ebeanServer.save(newTransfer);
//    }

    public PagedList<MedicineOrderRequest> getPagedMedicineOrderRequestList(OrderRequestFilterDto requestFilter,
                                                                            int pageNum) {
        return getPagedMedicineOrderRequestList(requestFilter, pageNum, 0);
    }

    public PagedList<MedicineOrderRequest> getPagedMedicineOrderRequestList(OrderRequestFilterDto requestFilter,
                                                                            int pageNum, int pageSize) {
        ExpressionList<MedicineOrderRequest> el = ebeanServer.find(MedicineOrderRequest.class).where()
                .in("status", requestFilter.getOrderStatusList());

        if ((requestFilter.getStartDate() != null) && (requestFilter.getEndDate() != null)) {
            Date endDate = this.addDays(requestFilter.getEndDate(), 1);
            el = el.between("when_created", requestFilter.getStartDate(), endDate);
        }

        if (requestFilter.getOrderNumber() != null) {
            el = el.like("orderRequestNumberCode", "%" + requestFilter.getOrderNumber() + "%");
        }

        Query<MedicineOrderRequest> query = el.query().orderBy("when_created desc");
        return findPagedList(query, pageNum, pageSize);
    }

    public MedicineOrderRequest saveMedicineRequestOrder(MedicineOrderRequestSaveDto medicineOrderRequestSaveDto) {
        MedicineOrderRequest medicineOrderRequest = medicineOrderRequestSaveDto.toEntity();
        if (medicineOrderRequest.getUuid() != null)
            ebeanServer.update(medicineOrderRequest);
        else
            ebeanServer.save(medicineOrderRequest);
        medicineOrderRequest = this.findById(MedicineOrderRequest.class, medicineOrderRequest.getUuid());
        if (medicineOrderRequestSaveDto.getUuid() == null) {
            medicineOrderRequest.setOrderRequestNumberCode(Utils.buildDisplayCode(medicineOrderRequest.getOrderRequestNumber()));
            ebeanServer.update(medicineOrderRequest);
        }
        return medicineOrderRequest;
    }

    public void deleteMedicineOrderRequest(UUID orderRequestId) {
        MedicineOrderRequest medicineOrderRequest = this.findById(MedicineOrderRequest.class, orderRequestId);
        if (medicineOrderRequest.getStatus() != OrderRequestStatus.created)
            throw new ApiValidationException("Invalid order request status to delete");
        ebeanServer.delete(medicineOrderRequest);
    }

    public void updateMedicineOrderRequestStatus(MedicineOrderRequestSaveDto
                                                         medicineOrderRequestSaveDto, OrderRequestStatus originStatus) {
        MedicineOrderRequest medicineOrderRequest = this.findById(MedicineOrderRequest.class, medicineOrderRequestSaveDto.getUuid());
        if (medicineOrderRequest.getStatus() != originStatus)
            throw new ApiValidationException("Invalid order request status to update");
        medicineOrderRequest.setStatus(medicineOrderRequestSaveDto.getStatus());
        ebeanServer.save(medicineOrderRequest);
    }

    public void approveMedicineOrderRequest(MedicineOrderRequestSaveDto medicineOrderRequestSaveDto) {
        MedicineOrderRequest medicineOrderRequest = this.findById(MedicineOrderRequest.class, medicineOrderRequestSaveDto.getUuid());
        if (medicineOrderRequest.getStatus() != OrderRequestStatus.submitted)
            throw new ApiValidationException("Invalid order request status to approve");
        medicineOrderRequest.setStatus(OrderRequestStatus.approved);
        medicineOrderRequest.setApprovedDate(new Date());
        medicineOrderRequest.setApprovedBy(new Employee(medicineOrderRequestSaveDto.getApproveById()));
        ebeanServer.save(medicineOrderRequest);
    }

    public List<MedicineOrderLineRespDto> toMedicineOrderLineFromRequest(NZTransferSourceLineDto requestLineDto) {
        List<MedicineOrderLineRespDto> orderLineList = new ArrayList<>();
        for (UUID requestLineId : requestLineDto.getSourceLineIdList()) {
            MedicineOrderRequestLine requestLine = this.findById(MedicineOrderRequestLine.class, requestLineId);
            orderLineList.add(requestLine.toOrderLine().toDto());
        }
        return orderLineList;
    }

    public PagedList<MedicinePartialOrder> getPagedMedicinePartialOrderList(OrderFilterDto orderFilter, int pageNum) {
        ExpressionList<MedicinePartialOrder> el = ebeanServer.find(MedicinePartialOrder.class).alias("po").where();
        if ((orderFilter.getStartDate() != null) && (orderFilter.getEndDate() != null)) {
            Date endDate = this.addDays(orderFilter.getEndDate(), 1);
            el = el.between("po.when_created", orderFilter.getStartDate(), endDate);
        }
        if (orderFilter.getOrderStatusList() != null)
            el = el.in("orderStatus", orderFilter.getOrderStatusList());

        if (orderFilter.getOrderNumber() != null)
            el = el.like("orderNumberCode", "%" + orderFilter.getOrderNumber() + "%");

        if (!StringUtils.isBlank(orderFilter.getSearchNumber()))
            el = el.or()
                    .like("orderNumberCode", "%" + orderFilter.getSearchNumber() + "%")
                    .like("masterOrder.orderNumberCode", "%" + orderFilter.getSearchNumber() + "%")
                    .endOr();

        Query<MedicinePartialOrder> query = el.query().orderBy("po.when_created desc");
        return findPagedList(query, pageNum);
    }

    @Transactional
    public MedicinePartialOrder saveMedicinePartialOrder(MedicinePartialOrderSaveDto medicineOrderSaveDto) {
        MedicinePartialOrder medicineOrder = medicineOrderSaveDto.toEntity();
        if (medicineOrder.getUuid() != null)
            ebeanServer.update(medicineOrder);
        else
            ebeanServer.save(medicineOrder);
        medicineOrder = this.findById(MedicinePartialOrder.class, medicineOrder.getUuid());
        if (medicineOrderSaveDto.getUuid() == null) {
            medicineOrder.setOrderNumberCode(Utils.buildDisplayCode(medicineOrder.getOrderNumber()));
            ebeanServer.update(medicineOrder);
        }
        return medicineOrder;

    }


    @Transactional
    public void deleteMedicinePartialOrder(UUID orderId) {
        MedicinePartialOrder medicineOrder = this.findById(MedicinePartialOrder.class, orderId);
        if (medicineOrder.getOrderStatus() != OrderStatus.created)
            throw new ApiValidationException("Invalid order status to delete");
        ebeanServer.delete(medicineOrder);
    }

    @Transactional
    public void submitMedicinePartialOrder(MedicinePartialOrderSaveDto medicineOrderSaveDto) {
        this.updateMedicinePartialOrderStatus(medicineOrderSaveDto, OrderStatus.created);

        if (this.uploadYBInventory) {
            MedicinePartialOrder medicineOrder = this.findById(MedicinePartialOrder.class, medicineOrderSaveDto.getUuid());
            this.ybInventoryService.newYBInventoryOrder(medicineOrder.toYBOrderHeader(), InventoryOrderType.buyOrder, medicineOrder.toYBOrderLineDto());
        }
    }


    @Transactional
    public void rejectMedicinePartialOrderRequest(MedicinePartialOrderSaveDto medicineOrderSaveDto) {
        this.updateMedicinePartialOrderStatus(medicineOrderSaveDto, OrderStatus.submitted);

        if (this.uploadYBInventory) {
            this.ybInventoryService.deleteInventoryOrder(InventoryOrderType.buyOrder, medicineOrderSaveDto.getUuid());
        }
    }


    @Transactional
    public void updateMedicinePartialOrderStatus(MedicinePartialOrderSaveDto medicineOrderSaveDto, OrderStatus
            originStatus) {
        MedicinePartialOrder medicineOrder = this.findById(MedicinePartialOrder.class, medicineOrderSaveDto.getUuid());
        if (medicineOrder.getOrderStatus() != originStatus)
            throw new ApiValidationException("Invalid order status to update");
        medicineOrder.setOrderStatus(medicineOrderSaveDto.getOrderStatus());
        ebeanServer.save(medicineOrder);
    }

    @Transactional
    public void approveMedicinePartialOrder(MedicinePartialOrderSaveDto medicineOrderSaveDto) {

        MedicinePartialOrder medicineOrder = this.findById(MedicinePartialOrder.class, medicineOrderSaveDto.getUuid());

        if (medicineOrder.getOrderStatus() != OrderStatus.submitted)
            throw new ApiValidationException("Invalid order status to approve");

        if (this.uploadYBInventory) {
            this.ybInventoryService.confirmInventoryOrder(InventoryOrderType.buyOrder, medicineOrder.getUuid());
        }


        medicineOrder.setOrderStatus(OrderStatus.approved);
        medicineOrder.setApprovedDate(new Date());
        medicineOrder.setApprovedBy(new Employee(medicineOrderSaveDto.getApproveById()));
        for (MedicinePartialOrderLine orderLine : medicineOrder.getLineList()) {
            CachedMedicineTransaction newTransaction = (CachedMedicineTransaction) orderLine.createOrderLineTransaction();
            ebeanServer.save(newTransaction);
            this.updateMedicineStockCache(newTransaction);
        }
        ebeanServer.save(medicineOrder);
    }


    public List<MedicinePartialOrderLineRespDto> toMedicinePartialOrderLineFromMaster(NZTransferSourceLineDto
                                                                                              orderLineDto) {
        List<MedicinePartialOrderLineRespDto> orderLineList = new ArrayList<>();
        for (UUID lineId : orderLineDto.getSourceLineIdList()) {
            MedicineOrderLine orderLine = this.findById(MedicineOrderLine.class, lineId);
            orderLineList.add(orderLine.toPartialOrderLine().toDto());
        }
        return orderLineList;
    }

    public List<CachedMedicineStock> getStockOriginOrderLineList(List<UUID> stockIdList) {
        return ebeanServer.find(CachedMedicineStock.class)
                .where()
                .in("uuid", stockIdList)
                .findList();
    }

    public void deleteMedicineOrderLine(UUID orderLineId) {
        PrescriptionMedicineOrderLine prescriptionMedicineOrderLine = this.findById(PrescriptionMedicineOrderLine.class, orderLineId);
//        PrescriptionMedicineOrder order = prescriptionMedicineOrderLine.getOrder();
//        if (order.getLineList().size() == 1)
//            ebeanServer.delete(order);
//        else
        ebeanServer.delete(prescriptionMedicineOrderLine);


    }
}
