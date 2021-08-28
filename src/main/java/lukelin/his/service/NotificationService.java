package lukelin.his.service;

import lukelin.common.springboot.service.BaseService;
import lukelin.his.domain.entity.account.AutoFee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.inventory.item.ItemTransfer;
import lukelin.his.domain.entity.inventory.medicine.MedicineTransfer;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrder;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.patient_sign_in.PatientSignOutRequest;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.Basic.UserRoleType;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;
import lukelin.his.domain.enums.Inventory.TransferStatus;
import lukelin.his.domain.enums.NotificationMessageType;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionChangeAction;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.dto.notification.req.NotificationFilterDto;
import lukelin.his.dto.notification.resp.NotificationDto;
import lukelin.his.dto.prescription.request.filter.PrescriptionFilterDto;
import lukelin.his.dto.signin.response.PatientSignInMessageDto;
import lukelin.his.system.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService extends BaseService {
    @Autowired
    private PrescriptionService prescriptionService;

    private LocalDateTime lastExecutionTime;
    private List<NotificationDto> notificationList = new ArrayList<>();
    private List<Prescription> pendingPrescriptionList;
    private List<Prescription> rejectedPrescriptionList;
    private List<Prescription> pendingDisablePrescriptionList;


    @PostConstruct
    public synchronized void initNotificationList() {
        //Todo 精简代码
        lastExecutionTime = null;
        this.notificationList.clear();
        List<PrescriptionMedicineOrder> submittedOrderList = ebeanServer.find(PrescriptionMedicineOrder.class)
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.submitted)
                .findList();
        this.initMedicineOrderSubmitted(submittedOrderList);

        List<PrescriptionMedicineReturnOrder> submittedReturnOrderList = ebeanServer.find(PrescriptionMedicineReturnOrder.class)
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.submitted)
                .findList();
        this.initReturnMedicineOrderSubmitted(submittedReturnOrderList);

        List<PrescriptionStatus> statusList = new ArrayList<>();
        statusList.add(PrescriptionStatus.approved);
        statusList.add(PrescriptionStatus.created);
        statusList.add(PrescriptionStatus.submitted);
        statusList.add(PrescriptionStatus.pendingDisable);
        List<PatientSignIn> currentSignInList = ebeanServer.find(PatientSignIn.class).where().eq("status", PatientSignInStatus.signedIn).findList();
        List<Prescription> allPrescriptionList = currentSignInList.stream().map(PatientSignIn::getPrescriptionList).flatMap(Collection::stream).filter(pre -> statusList.contains(pre.getStatus())).collect(Collectors.toList());
        rejectedPrescriptionList = allPrescriptionList.stream()
                .filter(p -> p.getStatus() == PrescriptionStatus.created
                        && p.getChangeLogList().stream().anyMatch(c -> c.getAction() == PrescriptionChangeAction.reject))
                .collect(Collectors.toList());
        pendingPrescriptionList = allPrescriptionList.stream()
                .filter(p -> p.getStatus() == PrescriptionStatus.submitted)
                .collect(Collectors.toList());
        pendingDisablePrescriptionList = allPrescriptionList.stream()
                .filter(p -> p.getStatus() == PrescriptionStatus.pendingDisable)
                .collect(Collectors.toList());
        this.addPendingDisablePrescriptionNotification(pendingDisablePrescriptionList);
        this.addPendingPrescriptionNotification(pendingPrescriptionList);
        this.addRejectPrescriptionNotification(rejectedPrescriptionList);
        List<Prescription> approvedPrescriptionList = allPrescriptionList.stream()
                .filter(p -> p.getStatus() == PrescriptionStatus.approved)
                .collect(Collectors.toList());
        this.approvedPrescriptionChanged(approvedPrescriptionList);

        List<PatientSignIn> pendingSignInList =
                ebeanServer.find(PatientSignIn.class).where()
                        .eq("status", PatientSignInStatus.pendingSignIn)
                        .findList();
        for (PatientSignIn patientSignIn : pendingSignInList)
            this.patientSignInRequest(patientSignIn);

        List<PatientSignIn> pendingSignOutList =
                ebeanServer.find(PatientSignIn.class).where()
                        .eq("status", PatientSignInStatus.pendingSignOut)
                        .findList();
        for (PatientSignIn patientSignIn : pendingSignOutList)
            this.patientSignOutRequest(patientSignIn);

        List<MedicineTransfer> medicineTransferList =
                ebeanServer.find(MedicineTransfer.class).where()
                        .eq("transferStatus", TransferStatus.created)
                        .eq("fromWarehouse.warehouseType", WarehouseType.pharmacy)
                        .findList();
        for (MedicineTransfer medicineTransfer : medicineTransferList)
            this.medicineTransferRequest(medicineTransfer);

        List<ItemTransfer> itemTransferList =
                ebeanServer.find(ItemTransfer.class).where()
                        .eq("transferStatus", TransferStatus.created)
                        .eq("fromWarehouse.warehouseType", WarehouseType.levelOneWarehouse)
                        .findList();
        for (ItemTransfer itemTransfer : itemTransferList)
            this.itemTransferRequest(itemTransfer);

        List<MedicineTransfer> pendingConfirmMedicineTransferList =
                ebeanServer.find(MedicineTransfer.class).where()
                        .eq("transferStatus", TransferStatus.pendingConfirm)
                        .eq("toWarehouse.warehouseType", WarehouseType.wardWarehouse)
                        .findList();
        for (MedicineTransfer medicineTransfer : pendingConfirmMedicineTransferList)
            this.pendingConfirmMedicineTransfer(medicineTransfer);

        List<ItemTransfer> pendingConfirmItemTransferList =
                ebeanServer.find(ItemTransfer.class).where()
                        .eq("transferStatus", TransferStatus.pendingConfirm)
                        .eq("fromWarehouse.warehouseType", WarehouseType.wardWarehouse)
                        .findList();
        for (ItemTransfer itemTransfer : pendingConfirmItemTransferList)
            this.pendingConfirmItemTransfer(itemTransfer);

        List<PrescriptionMedicineOrder> processedOrderList = ebeanServer.find(PrescriptionMedicineOrder.class)
                .where()
                .eq("status", PrescriptionMedicineOrderStatus.processed)
                .findList();
        for (PrescriptionMedicineOrder order : processedOrderList)
            this.medicineOrderProcessed(order);
    }

    public List<NotificationDto> getNotificationList(NotificationFilterDto messageFilter) {
        LocalDateTime currentTime = LocalDateTime.now();
        //第一次或者隔天从数据库更新
        if (lastExecutionTime == null || !(currentTime.toLocalDate().isEqual(lastExecutionTime.toLocalDate()))) {


            List<DepartmentTreatment> allWardDepartmentList =
                    ebeanServer.find(DepartmentTreatment.class)
                            .where()
                            .eq("type", DepartmentTreatmentType.ward)
                            .findList();
            this.updatePendingMedicineOrderInfo(allWardDepartmentList);
            this.updatePendingTreatmentInfo(allWardDepartmentList);
        }

        lastExecutionTime = currentTime;
        List<NotificationDto> returnList = new ArrayList<>();

        if (messageFilter.getUserRoleType() == UserRoleType.pharmacy || messageFilter.getUserRoleType() == UserRoleType.cashier || messageFilter.getUserRoleType() == UserRoleType.warehouse || messageFilter.getUserRoleType() == UserRoleType.therapist) {
            returnList.addAll(this.notificationList.stream()
                    .filter(ml ->
                            messageFilter.getUserRoleType().equals(ml.getUserRoleType())
                    ).collect(Collectors.toList()));
        } else if (messageFilter.getUserRoleType() == UserRoleType.nurse) {
            if (messageFilter.getDepartmentIdList() != null)
                returnList.addAll(this.notificationList.stream()
                        .filter(ml ->
                                messageFilter.getUserRoleType().equals(ml.getUserRoleType()) &&
                                        messageFilter.getDepartmentIdList().contains(ml.getDepartmentId()
                                        )
                        ).collect(Collectors.toList()));
        } else if (messageFilter.getUserRoleType() == UserRoleType.doctor) {
            if (messageFilter.getEmployeeId() != null)
                returnList.addAll(this.notificationList.stream()
                        .filter(ml ->
                                messageFilter.getUserRoleType().equals(ml.getUserRoleType()) &&
                                        messageFilter.getEmployeeId().equals(ml.getEmployeeId())
                        ).collect(Collectors.toList()));
        }
        return returnList;
    }

    public void patientSignInRequest(PatientSignIn patientSignIn) {
        //住院部消息
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.cashier);
        newNotification.setNotificationMessageType(NotificationMessageType.newPatientSignIn);
        newNotification.setKey(patientSignIn.getUuid().toString() + NotificationMessageType.newPatientSignIn);
        newNotification.setMessage(patientSignIn.getPatient().getName() + ":" + patientSignIn.getSignInNumberCode());
        notificationList.add(newNotification);
    }


    public synchronized void medicineTransferRequest(MedicineTransfer medicineTransfer) {
        //药物申领
        //检查是否已经又消息，有的话删除
        this.removeMedicineTransferRequest(medicineTransfer.getUuid());
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.pharmacy);
        newNotification.setNotificationMessageType(NotificationMessageType.newMedicineTransferRequest);
        newNotification.setKey(medicineTransfer.getUuid().toString() + NotificationMessageType.newMedicineTransferRequest);
        newNotification.setMessage(medicineTransfer.getWhoCreatedName() + ":" + Utils.buildDisplayCode(medicineTransfer.getTransferNumber()));
        newNotification.setTabName("药品出库");
        newNotification.setTabComponent("pharmacyMedicineTransferOut");
        notificationList.add(newNotification);
    }

    public void removeMedicineTransferRequest(UUID transferId) {
        String messageKey = transferId.toString() + NotificationMessageType.newMedicineTransferRequest;
        this.deleteMessage(messageKey);
    }

    public synchronized void itemTransferRequest(ItemTransfer itemTransfer) {
        //药物申领
        //检查是否已经又消息，有的话删除
        this.removeItemTransferRequest(itemTransfer.getUuid());
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.warehouse);
        newNotification.setNotificationMessageType(NotificationMessageType.newItemTransferRequest);
        newNotification.setKey(itemTransfer.getUuid().toString() + NotificationMessageType.newItemTransferRequest);
        newNotification.setMessage(itemTransfer.getWhoCreatedName() + ":" + Utils.buildDisplayCode(itemTransfer.getTransferNumber()));
        newNotification.setTabName("物品出库");
        newNotification.setTabComponent("itemTransferOut");
        notificationList.add(newNotification);
    }

    public void removeItemTransferRequest(UUID transferId) {
        String messageKey = transferId.toString() + NotificationMessageType.newItemTransferRequest;
        this.deleteMessage(messageKey);
    }

    public synchronized void pendingConfirmItemTransfer(ItemTransfer itemTransfer) {
        //药物申领
        //检查是否已经又消息，有的话删除
        if (itemTransfer.getToWarehouse().getWarehouseType() != WarehouseType.wardWarehouse)
            return;
        this.removePendingConfirmItemTransfer(itemTransfer.getUuid());
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.nurse);
        newNotification.setNotificationMessageType(NotificationMessageType.pendingConfirmItemTransferRequest);
        newNotification.setKey(itemTransfer.getUuid().toString() + NotificationMessageType.pendingConfirmItemTransferRequest);
        newNotification.setMessage(itemTransfer.getWhoCreatedName() + ":" + Utils.buildDisplayCode(itemTransfer.getTransferNumber()));
        newNotification.setTabName("物品申领");
        newNotification.setTabComponent("wardItemTransferRequest");
        newNotification.setDepartmentId(itemTransfer.getToWarehouse().getWardDepartment().getUuid());
        notificationList.add(newNotification);
    }

    public void removePendingConfirmItemTransfer(UUID transferId) {
        String messageKey = transferId.toString() + NotificationMessageType.pendingConfirmItemTransferRequest;
        this.deleteMessage(messageKey);
    }

    public synchronized void pendingConfirmMedicineTransfer(MedicineTransfer medicineTransfer) {
        //药物申领
        //检查是否已经又消息，有的话删除
        if (medicineTransfer.getToWarehouse().getWarehouseType() != WarehouseType.wardWarehouse)
            return;
        this.removePendingConfirmMedicineTransfer(medicineTransfer.getUuid());
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.nurse);
        newNotification.setNotificationMessageType(NotificationMessageType.pendingConfirmMedicineTransfer);
        newNotification.setKey(medicineTransfer.getUuid().toString() + NotificationMessageType.pendingConfirmMedicineTransfer);
        newNotification.setMessage(medicineTransfer.getWhoCreatedName() + ":" + Utils.buildDisplayCode(medicineTransfer.getTransferNumber()));
        newNotification.setTabName("药品申领");
        newNotification.setTabComponent("wardMedicineTransferRequest");
        newNotification.setDepartmentId(medicineTransfer.getToWarehouse().getWardDepartment().getUuid());
        notificationList.add(newNotification);
    }

    public void removePendingConfirmMedicineTransfer(UUID transferId) {
        String messageKey = transferId.toString() + NotificationMessageType.pendingConfirmMedicineTransfer;
        this.deleteMessage(messageKey);
    }


    public void patientSignInRequestChanged(PatientSignIn patientSignIn) {
        String messageKey = patientSignIn.getUuid().toString() + NotificationMessageType.newPatientSignIn;
        this.deleteMessage(messageKey);
    }

    public void patientSignOutRequest(PatientSignIn patientSignIn) {
        //住院部消息
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.cashier);
        newNotification.setNotificationMessageType(NotificationMessageType.newSignOutRequest);
        newNotification.setKey(patientSignIn.getUuid().toString() + NotificationMessageType.newSignOutRequest);
        newNotification.setMessage(patientSignIn.getPatient().getName() + ":" + patientSignIn.getSignInNumberCode());
        notificationList.add(newNotification);
    }

    public void patientPrepareSignOut(PatientSignOutRequest signOutRequest) {
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.therapist);
        newNotification.setNotificationMessageType(NotificationMessageType.prepareSignOut);
        newNotification.setKey(signOutRequest.getPatientSignIn().getUuid().toString() + NotificationMessageType.prepareSignOut);
        newNotification.setMessage(signOutRequest.getPatientSignIn().getPatient().getName() + ":" + signOutRequest.getPatientSignIn().getSignInNumberCode());
        notificationList.add(newNotification);
    }

    public void patientSignedOutRequestChanged(PatientSignIn patientSignIn) {
        String messageKey = patientSignIn.getUuid().toString() + NotificationMessageType.newSignOutRequest;
        this.deleteMessage(messageKey);
    }


    public void prescriptionSubmitted(List<Prescription> prescriptionList) {
        this.removeFromPrescriptionCached(prescriptionList, this.rejectedPrescriptionList, NotificationMessageType.rejectedPrescription);
        this.addPendingPrescriptionNotification(prescriptionList);
    }

    public void prescriptionPendingDisable(List<Prescription> prescriptionList) {
        this.addPendingDisablePrescriptionNotification(prescriptionList);
    }

    public void prescriptionRejected(List<Prescription> prescriptionList) {
        this.removeFromPrescriptionCached(prescriptionList, this.pendingPrescriptionList, NotificationMessageType.pendingPrescription);
        this.addRejectPrescriptionNotification(prescriptionList);
    }

    public void prescriptionDeleted(List<Prescription> prescriptionList) {
        this.removeFromPrescriptionCached(prescriptionList, this.rejectedPrescriptionList, NotificationMessageType.rejectedPrescription);
    }


    public void prescriptionConfirmDisable(List<Prescription> prescriptionList) {
        this.removeFromPrescriptionCached(prescriptionList, this.pendingDisablePrescriptionList, NotificationMessageType.pendingDisablePrescription);
    }

    public void approvedPrescriptionChanged(List<Prescription> prescriptionList) {
        this.removeFromPrescriptionCached(prescriptionList, this.pendingPrescriptionList, NotificationMessageType.pendingPrescription);
        List<DepartmentTreatment> departmentTreatmentList =
                prescriptionList.stream()
                        .filter(p -> p.getPrescriptionType() == PrescriptionType.Treatment && p.getDepartment().getType() == DepartmentTreatmentType.ward)
                        .map(Prescription::getDepartment).distinct().collect(Collectors.toList());
        if (departmentTreatmentList.size() > 0)
            this.updatePendingTreatmentInfo(departmentTreatmentList);

        departmentTreatmentList =
                prescriptionList.stream()
                        .filter(p -> p.getPrescriptionType() == PrescriptionType.Medicine)
                        .map(Prescription::getDepartment).distinct().collect(Collectors.toList());
        if (departmentTreatmentList.size() > 0)
            this.updatePendingMedicineOrderInfo(departmentTreatmentList);
    }

    public void prescriptionExecuted(List<Prescription> prescriptionList) {
        List<DepartmentTreatment> departmentTreatmentList =
                prescriptionList.stream()
                        .filter(p -> p.getPrescriptionType() == PrescriptionType.Treatment && p.getDepartment().getType() == DepartmentTreatmentType.ward)
                        .map(Prescription::getDepartment).distinct().collect(Collectors.toList());
        if (departmentTreatmentList.size() > 0)
            this.updatePendingTreatmentInfo(departmentTreatmentList);
    }

    public void initMedicineOrderSubmitted(List<PrescriptionMedicineOrder> orderList) {
        for (PrescriptionMedicineOrder order : orderList)
            this.medicineOrderSubmitted(order);
    }

    public synchronized void medicineOrderSubmitted(PrescriptionMedicineOrder order) {
        List<DepartmentTreatment> departmentTreatmentList =
                order.getLineList().stream()
                        .filter(l -> l.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription().getPrescriptionType() == PrescriptionType.Medicine)
                        .map(l -> l.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription().getDepartment()).distinct().collect(Collectors.toList());
        if (departmentTreatmentList.size() > 0)
            this.updatePendingMedicineOrderInfo(departmentTreatmentList);

        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.pharmacy);
        newNotification.setNotificationMessageType(NotificationMessageType.pendingProcessMedicineOrder);
        newNotification.setKey(order.getUuid().toString() + NotificationMessageType.pendingProcessMedicineOrder);
        newNotification.setMessage("发药单号:" + order.getOrderNumberCode());
        newNotification.setTabName("医嘱发药");
        newNotification.setTabComponent("prescriptionMedicineOrder");
        notificationList.add(newNotification);
    }

    public synchronized void medicineOrderProcessed(PrescriptionMedicineOrder order) {
        String messageKey = order.getUuid().toString() + NotificationMessageType.pendingProcessMedicineOrder;
        this.deleteMessage(messageKey);

        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.nurse);
        newNotification.setNotificationMessageType(NotificationMessageType.pendingConfirmPrescriptionMedicineOrder);
        newNotification.setKey(order.getUuid().toString() + NotificationMessageType.pendingConfirmPrescriptionMedicineOrder);
        newNotification.setMessage("发药单号:" + order.getOrderNumberCode());
        newNotification.setTabName("已提交发药单");
        newNotification.setTabComponent("prescriptionMedicineOrder");
        //Todo changed department to department list
        if (order.getWard().getDepartmentList().size() > 0) {
            DepartmentTreatment departmentTreatment = (DepartmentTreatment) order.getWard().getDepartmentList().toArray()[0];
            newNotification.setDepartmentId(departmentTreatment.getUuid());
        }
        notificationList.add(newNotification);
    }

    public void removePendingConfirmPrescriptionMedicineOrder(UUID orderId) {
        String messageKey = orderId.toString() + NotificationMessageType.pendingConfirmPrescriptionMedicineOrder;
        this.deleteMessage(messageKey);
    }

    public void initReturnMedicineOrderSubmitted(List<PrescriptionMedicineReturnOrder> returnOrderList) {
        for (PrescriptionMedicineReturnOrder returnOrder : returnOrderList)
            this.returnMedicineOrderSubmitted(returnOrder);
    }


    public synchronized void returnMedicineOrderSubmitted(PrescriptionMedicineReturnOrder returnOrder) {
        //药房消息
        NotificationDto newNotification = new NotificationDto();
        newNotification.setUserRoleType(UserRoleType.pharmacy);
        newNotification.setNotificationMessageType(NotificationMessageType.pendingProcessReturnMedicineOrder);
        newNotification.setKey(returnOrder.getUuid().toString() + NotificationMessageType.pendingProcessReturnMedicineOrder);
        newNotification.setMessage("退药单号:" + returnOrder.getOrderNumberCode());
        newNotification.setTabName("医嘱退药药");
        newNotification.setTabComponent("prescriptionMedicineReturnOrderList");
        notificationList.add(newNotification);

        //护士消息
        List<UUID> departmentIdList = returnOrder.getLineList().stream().map(rl -> rl.getOriginOrderLine().getPrescriptionMedicine().getPrescriptionChargeable().getPrescription().getDepartment().getUuid()).distinct().collect(Collectors.toList());
        for (UUID departmentId : departmentIdList) {
            newNotification = new NotificationDto();
            newNotification.setUserRoleType(UserRoleType.nurse);
            newNotification.setNotificationMessageType(NotificationMessageType.pendingReturnOrderConfirmation);
            newNotification.setKey(returnOrder.getUuid().toString() + NotificationMessageType.pendingReturnOrderConfirmation);
            newNotification.setMessage("退药单号:" + returnOrder.getOrderNumberCode());
            newNotification.setDepartmentId(departmentId);
        }
        notificationList.add(newNotification);

    }

    public synchronized void medicineReturnOrderProcessed(PrescriptionMedicineReturnOrder returnOrder) {
        //删除药房消息
        String messageKey = returnOrder.getUuid().toString() + NotificationMessageType.pendingProcessReturnMedicineOrder;
        this.deleteMessage(messageKey);

        //删除护士消息
        String nurseMessageKey = returnOrder.getUuid().toString() + NotificationMessageType.pendingReturnOrderConfirmation;
        this.deleteMessage(nurseMessageKey);
    }

    public synchronized void deleteMessage(String messageKey) {
        Optional<NotificationDto> existingNotification = this.notificationList.stream().filter(n -> n.getKey().equals(messageKey)).findFirst();
        existingNotification.ifPresent(notificationDto -> this.notificationList.remove(notificationDto));
    }


    public synchronized void removeFromPrescriptionCached(List<Prescription> prescriptionList, List<Prescription> cachedPrescriptionList, NotificationMessageType messageType) {
        for (Prescription prescription : prescriptionList) {
            int index = cachedPrescriptionList.indexOf(prescription);
            if (index >= 0)
                cachedPrescriptionList.remove(index);

            Optional<NotificationDto> notificationToRemove;
            if (cachedPrescriptionList.stream().noneMatch(p -> p.getPatientSignIn().equals(prescription.getPatientSignIn()))) {
                String messageKey = prescription.getPatientSignIn().getUuid().toString() + messageType;
                this.deleteMessage(messageKey);
            }
        }
    }


    public synchronized void addPendingDisablePrescriptionNotification(List<Prescription> prescriptionList) {
        for (Prescription prescription : prescriptionList) {
            NotificationDto newNotification = this.createPrescriptionNotification(prescription, UserRoleType.nurse, NotificationMessageType.pendingDisablePrescription);
            if (newNotification != null) {
                newNotification.setDepartmentId(prescription.getPatientSignIn().getDepartmentTreatment().getUuid());
                notificationList.add(newNotification);
            }
            if (!this.pendingDisablePrescriptionList.contains(prescription))
                this.pendingDisablePrescriptionList.add(prescription);
        }
    }

    public synchronized void addPendingPrescriptionNotification(List<Prescription> prescriptionList) {
        for (Prescription prescription : prescriptionList) {
            NotificationDto newNotification = this.createPrescriptionNotification(prescription, UserRoleType.nurse, NotificationMessageType.pendingPrescription);
            if (newNotification != null) {
                newNotification.setDepartmentId(prescription.getPatientSignIn().getDepartmentTreatment().getUuid());
                notificationList.add(newNotification);
            }
            if (!this.pendingPrescriptionList.contains(prescription))
                this.pendingPrescriptionList.add(prescription);

        }
    }

    private synchronized void addRejectPrescriptionNotification(List<Prescription> prescriptionList) {
        for (Prescription prescription : prescriptionList) {
            NotificationDto newNotification = this.createPrescriptionNotification(prescription, UserRoleType.doctor, NotificationMessageType.rejectedPrescription);
            if (newNotification != null) {
                newNotification.setEmployeeId(prescription.getWhoCreatedId());
                notificationList.add(newNotification);
            }
            if (!this.rejectedPrescriptionList.contains(prescription))
                this.rejectedPrescriptionList.add(prescription);
        }
    }

    private synchronized void updatePendingMedicineOrderInfo(List<DepartmentTreatment> wardDepartmentList) {
        this.updateBatchProcessedPrescriptionInfo(wardDepartmentList, NotificationMessageType.pendingSubmitMedicineOrder, "药品医嘱提交", "prescriptionMedicineOrderList");
    }

    private synchronized void updatePendingTreatmentInfo(List<DepartmentTreatment> wardDepartmentList) {
        this.updateBatchProcessedPrescriptionInfo(wardDepartmentList, NotificationMessageType.pendingExecutionPrescription, "医嘱执行", "executePrescriptionList");
    }

    private void updateBatchProcessedPrescriptionInfo(List<DepartmentTreatment> wardDepartmentList, NotificationMessageType messageType, String tabName, String tabComponent) {
        PrescriptionFilterDto filterDto = new PrescriptionFilterDto();
        for (DepartmentTreatment departmentTreatment : wardDepartmentList) {
            filterDto.setDepartmentId(departmentTreatment.getUuid());
            Integer prescriptionListSize = null;
            if (messageType == NotificationMessageType.pendingSubmitMedicineOrder)
                prescriptionListSize = this.prescriptionService.getPendingMedicineOrderList(filterDto).size();
            else if (messageType == NotificationMessageType.pendingExecutionPrescription)
                prescriptionListSize = this.prescriptionService.getPendingExecutionPrescriptionList(filterDto, false).size();

            if (prescriptionListSize == null)
                return;

            String messageKey = departmentTreatment.getUuid().toString() + messageType;
            Optional<NotificationDto> existingNotification = this.notificationList.stream().filter(n -> n.getKey().equals(messageKey)).findFirst();
            if (existingNotification.isPresent()) {
                if (prescriptionListSize == 0)
                    this.notificationList.remove(existingNotification.get());
                else
                    existingNotification.get().setMessage(prescriptionListSize.toString());

            } else if (prescriptionListSize > 0) {
                NotificationDto newNotification = new NotificationDto();
                newNotification.setDepartmentId(departmentTreatment.getUuid());
                newNotification.setUserRoleType(UserRoleType.nurse);
                newNotification.setNotificationMessageType(messageType);
                newNotification.setKey(messageKey);
                newNotification.setMessage(prescriptionListSize.toString());
                newNotification.setTabName(tabName);
                newNotification.setTabComponent(tabComponent);
                notificationList.add(newNotification);
            }

        }
    }


    private NotificationDto createPrescriptionNotification(Prescription prescription, UserRoleType userRoleType, NotificationMessageType notificationMessageType) {
        String key = prescription.getPatientSignIn().getUuid().toString() + notificationMessageType;
        Optional<NotificationDto> optionalNotificationDto = this.notificationList.stream().filter(f -> f.getKey().equals(key)).findFirst();
        if (optionalNotificationDto.isPresent()) {
            optionalNotificationDto.get().getDetailInfoList().add(prescription.getDescription());
            return null;
        }
        NotificationDto newNotification = new NotificationDto();
        PatientSignInMessageDto patientSignInMessageDto = prescription.getPatientSignIn().toMessageDto();
        newNotification.setPatientInfo(patientSignInMessageDto);
        newNotification.setPatientSignInId(patientSignInMessageDto.getPatientSignInId());
        newNotification.setUserRoleType(userRoleType);
        newNotification.setNotificationMessageType(notificationMessageType);
        newNotification.setKey(key);
        String message = patientSignInMessageDto.getName();
        if (patientSignInMessageDto.getCurrentBedDto() != null)
            message = patientSignInMessageDto.getCurrentBedDto().getName() + ":" + message;
        newNotification.setMessage(message);
        newNotification.setTabName(newNotification.getPatientInfo().getName() + "：医嘱");
        newNotification.setTabComponent("patientPrescription");
        newNotification.setTabPram(prescription.getPatientSignIn().toDto());
        newNotification.getDetailInfoList().add(prescription.getDescription());
        return newNotification;

    }


    public void createAutoFeeNoStockNotification(AutoFee autoFee) {
        String key = autoFee.getUuid().toString() + NotificationMessageType.noStockAutoFee;
        NotificationDto newNotification = new NotificationDto();
        PatientSignInMessageDto patientSignInMessageDto = autoFee.getPatientSignIn().toMessageDto();
        newNotification.setPatientInfo(patientSignInMessageDto);
        newNotification.setPatientSignInId(patientSignInMessageDto.getPatientSignInId());
        newNotification.setUserRoleType(UserRoleType.nurse);
        newNotification.setNotificationMessageType(NotificationMessageType.noStockAutoFee);
        newNotification.setKey(key);
        newNotification.setDepartmentId(autoFee.getPatientSignIn().getDepartmentTreatment().getUuid());
        String message = patientSignInMessageDto.getName() + autoFee.getDescription();
        if (patientSignInMessageDto.getCurrentBedDto() != null)
            message = patientSignInMessageDto.getCurrentBedDto().getName() + ":" + message;
        newNotification.setMessage(message);
        notificationList.add(newNotification);
    }


}
