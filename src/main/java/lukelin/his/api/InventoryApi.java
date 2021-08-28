package lukelin.his.api;

import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.inventory.item.*;
import lukelin.his.domain.entity.inventory.medicine.*;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Inventory.OrderRequestStatus;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.Inventory.req.NZTransferSourceLineDto;
import lukelin.his.dto.Inventory.req.filter.*;
import lukelin.his.dto.Inventory.req.item.*;
import lukelin.his.dto.Inventory.req.medicine.*;
import lukelin.his.dto.Inventory.resp.NZTransferRespDto;
import lukelin.his.dto.Inventory.resp.medicine.*;
import lukelin.his.dto.conveter.InventoryDtoConverter;
import lukelin.his.dto.Inventory.resp.item.*;
import lukelin.his.dto.conveter.PatientSignInDtoConverter;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;
import lukelin.his.service.InventoryItemService;
import lukelin.his.service.InventoryMedicineService;
import lukelin.his.service.NotificationService;
import lukelin.his.service.PatientSignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/inventory")
public class InventoryApi extends BaseController {
    @Autowired
    private InventoryItemService inventoryItemService;

    @Autowired
    private InventoryMedicineService inventoryMedicineService;

    @Autowired
    private PatientSignInService patientSignInService;

    @Autowired
    private NotificationService notificationService;


    @PostMapping("item/order/list/{pageNum}")
    public PagedDTO<ItemOrderListRespDto> getItemOrderList(@RequestBody OrderFilterDto orderFilter, @PathVariable int pageNum) {
        PagedList<ItemOrder> orderList = inventoryItemService.getPagedItemOrderList(orderFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toItemOrderListRespDto(orderList.getList()), orderList);
    }

    @PostMapping("item/order/list/{pageNum}/{pageSize}")
    public PagedDTO<ItemOrderListRespDto> getItemOrderSelectionList(@RequestBody OrderFilterDto orderFilter, @PathVariable int pageNum, @PathVariable int pageSize) {
        PagedList<ItemOrder> orderList = inventoryItemService.getPagedItemOrderList(orderFilter, pageNum, pageSize);
        return pagedResponse(InventoryDtoConverter.toItemOrderListRespDto(orderList.getList()), orderList);
    }

    @PostMapping("item/order/save")
    public DecoratedDTO<ItemOrderRespDto> saveItemOrder(@RequestBody ItemOrderSaveDto itemOrderSaveDto) {
        return decoratedResponse(inventoryItemService.saveItemOrder(itemOrderSaveDto).toDto());
    }

    @PostMapping("item/order/{orderId}")
    public DecoratedDTO<ItemOrderRespDto> getOrderDetail(@PathVariable UUID orderId) {
        return decoratedResponse(this.inventoryItemService.getItemOrder(orderId).toDto());
    }

//    @PostMapping("item/order/confirm/{uuid}")
//    public DecoratedDTO<ItemOrderRespDto> confirmItemOrder(@PathVariable UUID uuid) {
//        ItemOrder itemOrder = this.inventoryItemService.confirmItemOrder(uuid);
//        return decoratedResponse(itemOrder.toDto());
//
//    }

    @PostMapping("item/order/delete/{uuid}")
    public void deleteItemOrder(@PathVariable UUID uuid) {
        this.inventoryItemService.deleteItemOrder(uuid);
    }


    @PostMapping("item/order/submit")
    public void submitItemOrder(@RequestBody ItemOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.updateItemOrderStatus(itemOrderSaveDto, OrderStatus.created);
    }

    @PostMapping("item/order/reject")
    public void approveItemOrder(@RequestBody ItemOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.updateItemOrderStatus(itemOrderSaveDto, OrderStatus.submitted);
    }

    @PostMapping("item/order/approve")
    public void rejectItemOrderRequest(@RequestBody ItemOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.approveItemOrder(itemOrderSaveDto);
    }

    @PostMapping("item/order/submit_return")
    public void submitItemReturnOrder(@RequestBody ItemOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.updateItemOrderStatus(itemOrderSaveDto, OrderStatus.created);
    }

    @PostMapping("item/order/reject_return")
    public void rejectItemReturnOrder(@RequestBody ItemOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.updateItemOrderStatus(itemOrderSaveDto, OrderStatus.pendingReturn);
    }

    @PostMapping("item/order/approve_return")
    public void approveItemReturnOrder(@RequestBody ItemOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.approveItemReturnOrder(itemOrderSaveDto);
    }

    @PostMapping("item/stock/origin_order/list/{pageNum}/{pageSize}")
    public PagedDTO<ItemOrderStockRespDto> getItemOriginOrderList(@RequestBody ItemStockFilterDto stockFilter, @PathVariable int pageNum, @PathVariable int pageSize) {
        PagedList<CachedItemStock> stockList = inventoryItemService.getPagedDetailItemStockList(stockFilter, pageNum, pageSize);
        return pagedResponse(DtoUtils.toDtoList(stockList.getList()), stockList);
    }

    @PostMapping("item/transfer/list/{pageNum}")
    public PagedDTO<ItemTransferListRespDto> getItemTransferList(@RequestBody TransferFilterDto itemTransferFilter, @PathVariable int pageNum) {
        PagedList<ItemTransfer> transferList = inventoryItemService.getPagedItemTransferList(itemTransferFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toItemTransferListRespDto(transferList.getList()), transferList);
    }


    @PostMapping("item/transfer/save")
    public DecoratedDTO<ItemTransferRespDto> saveItemTransfer(@RequestBody ItemTransferSaveDto itemTransferSaveDto) {
        ItemTransfer transfer = inventoryItemService.saveItemTransfer(itemTransferSaveDto);
        if (transfer.getFromWarehouse().getWarehouseType() == WarehouseType.levelOneWarehouse)
            this.notificationService.itemTransferRequest(transfer);
        return decoratedResponse(transfer.toDto());
    }

    @PostMapping("item/transfer/{transferId}")
    public DecoratedDTO<ItemTransferRespDto> getItemTransferDetail(@PathVariable UUID transferId) {
        return decoratedResponse(this.inventoryItemService.getItemTransfer(transferId).toDto());
    }

    @PostMapping("item/transfer/confirm/{uuid}")
    public DecoratedDTO<ItemTransferRespDto> confirmItemTransfer(@PathVariable UUID uuid) {
        ItemTransfer itemTransfer = this.inventoryItemService.confirmItemTransfer(uuid);
        this.notificationService.removePendingConfirmItemTransfer(itemTransfer.getUuid());
        return decoratedResponse(itemTransfer.toDto());
    }

    @PostMapping("item/transfer/transfer")
    public void transferItemTransfer(@RequestBody ItemTransferSaveDto itemTransferSaveDto) {
        ItemTransfer transfer = inventoryItemService.transferItemTransfer(itemTransferSaveDto.getUuid());
        this.notificationService.removeItemTransferRequest(transfer.getUuid());
        this.notificationService.pendingConfirmItemTransfer(transfer);

    }

    @PostMapping("item/transfer/cancel")
    public void cancelItemTransfer(@RequestBody ItemTransferSaveDto itemTransferSaveDto) {
        ItemTransfer transfer = inventoryItemService.rejectItemTransfer(itemTransferSaveDto.getUuid());
        this.notificationService.removePendingConfirmItemTransfer(transfer.getUuid());
        if (transfer.getFromWarehouse().getWarehouseType() == WarehouseType.levelOneWarehouse)
            this.notificationService.itemTransferRequest(transfer);
    }

    @PostMapping("item/transfer/delete/{uuid}")
    public void deleteItemTransfer(@PathVariable UUID uuid) {
        this.inventoryItemService.deleteItemTransfer(uuid);
        this.notificationService.removeItemTransferRequest(uuid);
    }


    @PostMapping("medicine/order/list/{pageNum}")
    public PagedDTO<MedicineOrderListRespDto> getMedicineOrderList(@RequestBody OrderFilterDto orderFilter, @PathVariable int pageNum) {
        PagedList<MedicineOrder> pagedOrderList = inventoryMedicineService.getPagedMedicineOrderList(orderFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toMedicineOrderListRespDto(pagedOrderList.getList()), pagedOrderList);
    }

    @PostMapping("medicine/order/list/{pageNum}/{pageSize}")
    public PagedDTO<MedicineOrderListRespDto> getMedicineOrderSelectionList(@RequestBody OrderFilterDto orderFilter, @PathVariable int pageNum, @PathVariable int pageSize) {
        PagedList<MedicineOrder> orderList = inventoryMedicineService.getPagedMedicineOrderList(orderFilter, pageNum, pageSize);
        return pagedResponse(InventoryDtoConverter.toMedicineOrderListRespDto(orderList.getList()), orderList);
    }


    @PostMapping("medicine/order/save")
    public DecoratedDTO<MedicineOrderRespDto> saveMedicineOrder(@RequestBody MedicineOrderSaveDto medicineOrderSaveDto) {
        return decoratedResponse(inventoryMedicineService.saveMedicineOrder(medicineOrderSaveDto).toDto());
    }

    @PostMapping("medicine/order/{orderId}")
    public DecoratedDTO<MedicineOrderRespDto> getMedicineOrderDetail(@PathVariable UUID orderId) {
        return decoratedResponse(this.inventoryMedicineService.getMedicineOrder(orderId).toDto());
    }

//    @PostMapping("medicine/order/confirm/{uuid}")
//    public DecoratedDTO<MedicineOrderRespDto> confirmMedicineOrder(@PathVariable UUID uuid) {
//        MedicineOrder medicineOrder = this.inventoryMedicineService.confirmMedicineOrder(uuid);
//        return decoratedResponse(medicineOrder.toDto());
//
//    }

//    @PostMapping("medicine/order/generate_transfer/{orderId}")
//    public void generateMedicineTransfer(@PathVariable UUID orderId) {
//        this.inventoryMedicineService.generateMedicineTransfer(orderId);
//    }

    @PostMapping("medicine/order/delete/{uuid}")
    public void deleteMedicineOrder(@PathVariable UUID uuid) {
        this.inventoryMedicineService.deleteMedicineOrder(uuid);
    }


    @PostMapping("medicine/order/submit")
    public void submitMedicineOrder(@RequestBody MedicineOrderSaveDto medicineOrderSaveDto) {
        inventoryMedicineService.updateMedicineOrderStatus(medicineOrderSaveDto, OrderStatus.created);
    }

    @PostMapping("medicine/order/approve")
    public void approveMedicineOrder(@RequestBody MedicineOrderSaveDto medicineOrderSaveDto) {
        inventoryMedicineService.approveMedicineOrder(medicineOrderSaveDto);
    }

    @PostMapping("medicine/order/reject")
    public void rejectMedicineOrderRequest(@RequestBody MedicineOrderSaveDto medicineOrderSaveDto) {
        inventoryMedicineService.updateMedicineOrderStatus(medicineOrderSaveDto, OrderStatus.submitted);
    }

    @PostMapping("medicine/order/submit_return")
    public void submitMedicineReturnOrder(@RequestBody MedicineOrderSaveDto medicineOrderSaveDto) {
        inventoryMedicineService.submitMedicineReturnOrder(medicineOrderSaveDto);
    }

    @PostMapping("medicine/order/reject_return")
    public void rejectMedicineReturnOrder(@RequestBody MedicineOrderSaveDto medicineOrderSaveDto) {
        inventoryMedicineService.rejectMedicineReturnOrder(medicineOrderSaveDto);
    }

    @PostMapping("medicine/order/approve_return")
    public void approveMedicineReturnOrder(@RequestBody MedicineOrderSaveDto medicineOrderSaveDto) {
        inventoryMedicineService.approveMedicineReturnOrder(medicineOrderSaveDto);
    }


    @PostMapping("medicine/stock/origin_order/list/{pageNum}/{pageSize}")
    public PagedDTO<MedicineOrderStockRespDto> getMedicineOriginOrderList(@RequestBody MedicineStockFilterDto stockFilter, @PathVariable int pageNum, @PathVariable int pageSize) {
        PagedList<CachedMedicineStock> stockList = inventoryMedicineService.getPagedDetailMedicineStockList(stockFilter, pageNum, pageSize);
        return pagedResponse(DtoUtils.toDtoList(stockList.getList()), stockList);
    }

    @PostMapping("medicine/transfer/list/{pageNum}")
    public PagedDTO<MedicineTransferListRespDto> getTransferList(@RequestBody TransferFilterDto medicineTransferFilter, @PathVariable int pageNum) {
        PagedList<MedicineTransfer> transferList = inventoryMedicineService.getPagedMedicineTransferList(medicineTransferFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toMedicineTransferListRespDto(transferList.getList()), transferList);
    }


    @PostMapping("medicine/transfer/save")
    public DecoratedDTO<MedicineTransferRespDto> saveMedicineTransfer(@RequestBody MedicineTransferSaveDto medicineTransferSaveDto) {
        MedicineTransfer transfer = inventoryMedicineService.saveMedicineTransfer(medicineTransferSaveDto);
        if (transfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy)
            this.notificationService.medicineTransferRequest(transfer);
        return decoratedResponse(transfer.toDto());
    }

    @PostMapping("medicine/transfer/transfer")
    public void transferMedicineTransfer(@RequestBody MedicineTransferSaveDto medicineTransferSaveDto) {
        MedicineTransfer transfer = inventoryMedicineService.transferMedicineTransfer(medicineTransferSaveDto.getUuid());
        this.notificationService.removeMedicineTransferRequest(medicineTransferSaveDto.getUuid());
        this.notificationService.pendingConfirmMedicineTransfer(transfer);
    }

    @PostMapping("medicine/transfer/cancel")
    public void cancelMedicineTransfer(@RequestBody MedicineTransferSaveDto medicineTransferSaveDto) {
        MedicineTransfer transfer = inventoryMedicineService.rejectMedicineTransfer(medicineTransferSaveDto.getUuid());
        this.notificationService.removePendingConfirmMedicineTransfer(transfer.getUuid());
        if (transfer.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy)
            this.notificationService.medicineTransferRequest(transfer);
    }

    @PostMapping("medicine/transfer/{transferId}")
    public DecoratedDTO<MedicineTransferRespDto> getTransferDetail(@PathVariable UUID transferId) {
        return decoratedResponse(this.inventoryMedicineService.getMedicineTransfer(transferId).toDto());
    }

    @PostMapping("medicine/transfer/confirm/{uuid}")
    public DecoratedDTO<MedicineTransferRespDto> confirmMedicineTransfer(@PathVariable UUID uuid) {
        MedicineTransfer medicineTransfer = this.inventoryMedicineService.confirmMedicineTransfer(uuid);
        this.notificationService.removePendingConfirmMedicineTransfer(medicineTransfer.getUuid());
        return decoratedResponse(medicineTransfer.toDto());

    }

    @PostMapping("medicine/transfer/delete/{uuid}")
    public void deleteMedicineTransfer(@PathVariable UUID uuid) {
        this.inventoryMedicineService.deleteMedicineTransfer(uuid);
        this.notificationService.removeMedicineTransferRequest(uuid);
    }


    @PostMapping("stock/medicine/adjust")
    public void adjustMedicineStock(@RequestBody MedicineStockAdjustSaveDto medicineStockAdjustSaveDto) {
        this.inventoryMedicineService.createAdjustment(medicineStockAdjustSaveDto);
    }

    @PostMapping("stock/item/adjust")
    public void adjustItemStock(@RequestBody ItemStockAdjustSaveDto itemStockAdjustSaveDto) {
        this.inventoryItemService.createAdjustment(itemStockAdjustSaveDto);
    }

    @PostMapping("medicine/prescription/order/list/pending")
    public DecoratedDTO<List<PrescriptionMedicineOrderRespDto>> getPendingPrescriptionMedicineOrderList(@RequestBody MedicineOrderFilterDto orderFilter) {
        List<PrescriptionMedicineOrder> orderList = this.inventoryMedicineService.getPendingPrescriptionMedicineOrderList(orderFilter);
        return decoratedResponse(DtoUtils.toDtoList(orderList));

    }

    @PostMapping("medicine/prescription/order/list/pending_confirm")
    public DecoratedDTO<List<PrescriptionMedicineOrderRespDto>> getPendingConfirmPrescriptionMedicineOrderList(@RequestBody MedicineOrderFilterDto orderFilter) {
        List<PrescriptionMedicineOrder> orderList = this.inventoryMedicineService.getPendingConfirmPrescriptionMedicineOrderList(orderFilter);
        return decoratedResponse(DtoUtils.toDtoList(orderList));

    }

    @PostMapping("medicine/prescription/order/list/recent_processed")
    public DecoratedDTO<List<PrescriptionMedicineOrderRespDto>> getRecentPrescriptionMedicineOrderList(@RequestBody MedicineOrderFilterDto orderFilter) {
        List<PrescriptionMedicineOrder> orderList = this.inventoryMedicineService.getRecentPrescriptionMedicineOrderList(orderFilter);
        return decoratedResponse(DtoUtils.toDtoList(orderList));

    }

    @PostMapping("medicine/prescription/order/{orderId}/line/list")
    public DecoratedDTO<List<BaseWardPatientListRespDto>> getPrescriptionMedicineOrderLineList(@PathVariable UUID orderId) {
        List<PrescriptionMedicineOrderLine> orderLineList = this.inventoryMedicineService.getPrescriptionMedicineOrderLineList(orderId);
        return decoratedResponse(InventoryDtoConverter.toPrescriptionMedicineOrderLineListRespDto(orderLineList));
    }


    @PostMapping("medicine/prescription/order/{orderId}/line/list/summary")
    public DecoratedDTO<List<PrescriptionMedicineOrderMedicineSummaryRespDto>> getPrescriptionMedicineOrderLineSummaryList(@PathVariable UUID orderId) {
        List<PrescriptionMedicineOrderLine> orderLineList = this.inventoryMedicineService.getPrescriptionMedicineOrderLineList(orderId);
        List<PrescriptionMedicineOrderMedicineSummaryRespDto> orderLineSummaryDtoList = InventoryDtoConverter.toPrescriptionMedicineOrderLineMedicineSummaryListRespDto(orderLineList);
        return decoratedResponse(orderLineSummaryDtoList);
    }

    @PostMapping("medicine/prescription/order/{orderId}/process")
    public void processPrescriptionMedicineOrder(@PathVariable UUID orderId, @RequestBody PrescriptionMedicineOrderProcessDto orderProcessDto) {
        PrescriptionMedicineOrder order = inventoryMedicineService.processPrescriptionMedicineOrder(orderId, orderProcessDto);
        this.notificationService.medicineOrderProcessed(order);
    }

    @PostMapping("medicine/prescription/order/line/{orderLineId}/delete")
    public void deletePrescriptionMedicineOrderLine(@PathVariable UUID orderLineId) {
        this.inventoryMedicineService.deleteMedicineOrderLine(orderLineId);
    }

    @PostMapping("medicine/prescription/order/{orderId}/reject")
    public void rejectPrescriptionMedicineOrder(@PathVariable UUID orderId) {
        PrescriptionMedicineOrder order = inventoryMedicineService.rejectPrescriptionMedicineOrder(orderId);
        this.notificationService.removePendingConfirmPrescriptionMedicineOrder(order.getUuid());
        this.notificationService.medicineOrderSubmitted(order);
    }

    @PostMapping("medicine/prescription/order/{orderId}/confirm")
    public void confirmPrescriptionMedicineOrder(@PathVariable UUID orderId) {
        PrescriptionMedicineOrder order = this.inventoryMedicineService.confirmPrescriptionMedicineOrder(orderId);
        this.notificationService.removePendingConfirmPrescriptionMedicineOrder(order.getUuid());
    }


    //Medicine Summary
    @PostMapping("medicine/prescription/order/usage/list/{warehouseId}")
    public DecoratedDTO<List<PrescriptionMedicineUsageRespDto>> getPrescriptionMedicineUsageList(@PathVariable UUID warehouseId, @RequestBody PrescriptionMedicineOrderFilterDto filter) {
        List<PrescriptionMedicineOrderLine> orderLineList = this.inventoryMedicineService.getProcessedPrescriptionMedicineOrderList(filter);
        List<PrescriptionMedicineUsageRespDto> usageRespDtoList = InventoryDtoConverter.toPrescriptionMedicineUsageRespDto(orderLineList, warehouseId);
        return decoratedResponse(usageRespDtoList);
    }

    @PostMapping("medicine/prescription/order/processed/list/{pageNum}")
    public PagedDTO<PrescriptionMedicineOrderLineRespDto> getPagedProcessedPrescriptionMedicineOrderLineList(@RequestBody PrescriptionMedicineOrderFilterDto filter, @PathVariable int pageNum) {
        PagedList<PrescriptionMedicineOrderLine> pagedOrderLineList = this.inventoryMedicineService.getProcessedPrescriptionMedicineOrderList(filter, pageNum);
        return pagedResponse(DtoUtils.toDtoList(pagedOrderLineList.getList()), pagedOrderLineList);
    }

    //patient summary
    @PostMapping("medicine/prescription/order/processed/summary/list")
    public DecoratedDTO<List<BaseWardPatientListRespDto>> getProcessedPrescriptionMedicineOrderLineSummaryList(@RequestBody PrescriptionMedicineOrderFilterDto filter) {
        List<PrescriptionMedicineOrderLine> orderLineList = this.inventoryMedicineService.getProcessedPrescriptionMedicineOrderList(filter);
        List<BaseWardPatientListRespDto> orderLineSummaryDtoList = InventoryDtoConverter.toPrescriptionMedicineOrderLinePatientSummaryListRespDto(orderLineList);
        List<Ward> currentPatientList = this.patientSignInService.getCurrentPatientWardList();
        return decoratedResponse(PatientSignInDtoConverter.toWardPatientList(currentPatientList, orderLineSummaryDtoList));
    }

    @PostMapping("medicine/prescription/order/return")
    public void createPrescriptionMedicineReturnOrder(@RequestBody PrescriptionMedicineReturnOrderCreateDto returnOrderCreateDto) {
        PrescriptionMedicineReturnOrder returnOrder = this.inventoryMedicineService.createPrescriptionMedicineReturnOrder(returnOrderCreateDto);
        this.notificationService.returnMedicineOrderSubmitted(returnOrder);
    }

    @PostMapping("medicine/prescription/order/return/list/pending")
    public DecoratedDTO<List<PrescriptionMedicineReturnOrderRespDto>> getPendingPrescriptionMedicineReturnOrderList(@RequestBody MedicineOrderFilterDto orderFilter) {
        List<PrescriptionMedicineReturnOrder> orderList = this.inventoryMedicineService.getPendingPrescriptionMedicineReturnOrderList(orderFilter);
        return decoratedResponse(DtoUtils.toDtoList(orderList));

    }

    @PostMapping("medicine/prescription/order/return/list/recent_processed")
    public DecoratedDTO<List<PrescriptionMedicineReturnOrderRespDto>> getRecentPrescriptionMedicineReturnOrderList(@RequestBody MedicineOrderFilterDto orderFilter) {
        List<PrescriptionMedicineReturnOrder> orderList = this.inventoryMedicineService.getRecentPrescriptionMedicineReturnOrderList(orderFilter);
        return decoratedResponse(DtoUtils.toDtoList(orderList));
    }

    @PostMapping("medicine/prescription/order/return/{orderId}/line/list/summary")
    public DecoratedDTO<List<PrescriptionMedicineReturnOrderMedicineSummaryRespDto>> getPrescriptionMedicineReturnOrderLineList(@PathVariable UUID orderId) {
        List<PrescriptionMedicineReturnOrderLine> returnOrderLineList = this.inventoryMedicineService.getPrescriptionMedicineReturnOrderLineList(orderId);
        List<PrescriptionMedicineReturnOrderMedicineSummaryRespDto> orderLineDtoList = InventoryDtoConverter.toPrescriptionMedicineReturnOrderLineSummaryListRespDto(returnOrderLineList);
        return decoratedResponse(orderLineDtoList);
    }

    @PostMapping("medicine/prescription/order/return/{orderId}/process")
    public void processPrescriptionMedicineOrder(@PathVariable UUID orderId, @RequestBody PrescriptionMedicineReturnOrderProcessDto orderProcessDto) {
        PrescriptionMedicineReturnOrder returnOrder = this.inventoryMedicineService.processPrescriptionMedicineReturnOrder(orderId, orderProcessDto);
        this.notificationService.medicineReturnOrderProcessed(returnOrder);
    }

    @PostMapping("item/order/request/list/{pageNum}")
    public PagedDTO<ItemOrderRequestListRespDto> getItemOrderRequestList(@RequestBody OrderRequestFilterDto requestFilter, @PathVariable int pageNum) {
        PagedList<ItemOrderRequest> orderRequestList = inventoryItemService.getPagedItemOrderRequestList(requestFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toItemOrderRequestListRespDto(orderRequestList.getList()), orderRequestList);
    }

    @PostMapping("item/order/request/list/{pageNum}/{pageSize}")
    public PagedDTO<ItemOrderRequestListRespDto> getItemOrderRequestSelectionList(@RequestBody OrderRequestFilterDto requestFilter, @PathVariable int pageNum, @PathVariable int pageSize) {
        PagedList<ItemOrderRequest> orderRequestList = inventoryItemService.getPagedItemOrderRequestList(requestFilter, pageNum, pageSize);
        return pagedResponse(InventoryDtoConverter.toItemOrderRequestListRespDto(orderRequestList.getList()), orderRequestList);
    }

    @PostMapping("item/order/request/save")
    public DecoratedDTO<ItemOrderRequestRespDto> saveItemRequestOrder(@RequestBody ItemOrderRequestSaveDto itemOrderRequestSaveDto) {
        return decoratedResponse(inventoryItemService.saveItemRequestOrder(itemOrderRequestSaveDto).toDto());
    }

    @PostMapping("item/order/request/{orderRequestId}")
    public DecoratedDTO<ItemOrderRequestRespDto> getItemOrderRequestDetail(@PathVariable UUID orderRequestId) {
        return decoratedResponse(this.inventoryItemService.findById(ItemOrderRequest.class, orderRequestId).toDto());
    }


    @PostMapping("item/order/request/delete/{orderRequestId}")
    public void deleteItemOrderRequest(@PathVariable UUID orderRequestId) {
        this.inventoryItemService.deleteItemOrderRequest(orderRequestId);
    }

    @PostMapping("item/order/request/submit")
    public void submitItemOrderRequest(@RequestBody ItemOrderRequestSaveDto itemOrderRequestSaveDto) {
        inventoryItemService.updateItemOrderRequestStatus(itemOrderRequestSaveDto, OrderRequestStatus.created);
    }

    @PostMapping("item/order/request/reject")
    public void rejectItemOrderRequest(@RequestBody ItemOrderRequestSaveDto itemOrderRequestSaveDto) {
        inventoryItemService.updateItemOrderRequestStatus(itemOrderRequestSaveDto, OrderRequestStatus.submitted);
    }

    @PostMapping("item/order/request/approve")
    public void approveItemOrderRequest(@RequestBody ItemOrderRequestSaveDto itemOrderRequestSaveDto) {
        inventoryItemService.approveItemOrderRequest(itemOrderRequestSaveDto);
    }


    @PostMapping("medicine/order/request/list/{pageNum}")
    public PagedDTO<MedicineOrderRequestListRespDto> getMedicineOrderRequestList(@RequestBody OrderRequestFilterDto requestFilter, @PathVariable int pageNum) {
        PagedList<MedicineOrderRequest> orderRequestList = inventoryMedicineService.getPagedMedicineOrderRequestList(requestFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toMedicineOrderRequestListRespDto(orderRequestList.getList()), orderRequestList);
    }

    @PostMapping("medicine/order/request/list/{pageNum}/{pageSize}")
    public PagedDTO<MedicineOrderRequestListRespDto> getMedicineOrderRequestSelectionList(@RequestBody OrderRequestFilterDto requestFilter, @PathVariable int pageNum, @PathVariable int pageSize) {
        PagedList<MedicineOrderRequest> orderRequestList = inventoryMedicineService.getPagedMedicineOrderRequestList(requestFilter, pageNum, pageSize);
        return pagedResponse(InventoryDtoConverter.toMedicineOrderRequestListRespDto(orderRequestList.getList()), orderRequestList);
    }


    @PostMapping("medicine/order/request/save")
    public DecoratedDTO<MedicineOrderRequestRespDto> saveMedicineRequestOrder(@RequestBody MedicineOrderRequestSaveDto medicineOrderRequestSaveDto) {
        return decoratedResponse(inventoryMedicineService.saveMedicineRequestOrder(medicineOrderRequestSaveDto).toDto());
    }

    @PostMapping("medicine/order/request/{orderRequestId}")
    public DecoratedDTO<MedicineOrderRequestRespDto> getMedicineOrderRequestDetail(@PathVariable UUID orderRequestId) {
        return decoratedResponse(this.inventoryMedicineService.findById(MedicineOrderRequest.class, orderRequestId).toDto());
    }


    @PostMapping("medicine/order/request/delete/{orderRequestId}")
    public void deleteMedicineOrderRequest(@PathVariable UUID orderRequestId) {
        this.inventoryMedicineService.deleteMedicineOrderRequest(orderRequestId);
    }

    @PostMapping("medicine/order/request/submit")
    public void submitMedicineOrderRequest(@RequestBody MedicineOrderRequestSaveDto medicineOrderRequestSaveDto) {
        inventoryMedicineService.updateMedicineOrderRequestStatus(medicineOrderRequestSaveDto, OrderRequestStatus.created);
    }

    @PostMapping("medicine/order/request/reject")
    public void rejectMedicineOrderRequest(@RequestBody MedicineOrderRequestSaveDto medicineOrderRequestSaveDto) {
        inventoryMedicineService.updateMedicineOrderRequestStatus(medicineOrderRequestSaveDto, OrderRequestStatus.submitted);
    }

    @PostMapping("medicine/order/request/approve")
    public void approveMedicineOrderRequest(@RequestBody MedicineOrderRequestSaveDto medicineOrderRequestSaveDto) {
        inventoryMedicineService.approveMedicineOrderRequest(medicineOrderRequestSaveDto);
    }

    @PostMapping("medicine/order/request/{requestId}/line/list")
    public DecoratedDTO<List<NZTransferRespDto>> getMedicineOrderRequestLineList(@PathVariable UUID requestId) {
        MedicineOrderRequest orderRequest = this.inventoryMedicineService.findById(MedicineOrderRequest.class, requestId);
        return decoratedResponse(InventoryDtoConverter.toMedicineNZTransferListDto(orderRequest.getLineList()));
    }

    @PostMapping("item/order/request/{requestId}/line/list")
    public DecoratedDTO<List<NZTransferRespDto>> getItemOrderRequestLineList(@PathVariable UUID requestId) {
        ItemOrderRequest orderRequest = this.inventoryItemService.findById(ItemOrderRequest.class, requestId);
        return decoratedResponse(InventoryDtoConverter.toItemNZTransferListFromRequestLine(orderRequest.getLineList()));
    }

    @PostMapping("item/order/master/{orderId}/line/list")
    public DecoratedDTO<List<NZTransferRespDto>> getItemOrderLineList(@PathVariable UUID orderId) {
        ItemOrder order = this.inventoryItemService.findById(ItemOrder.class, orderId);
        return decoratedResponse(InventoryDtoConverter.toItemNZTransferListFromOrderLine(order.getLineList()));
    }

    @PostMapping("medicine/order/master/{orderId}/line/list")
    public DecoratedDTO<List<NZTransferRespDto>> getMedicineOrderLineList(@PathVariable UUID orderId) {
        MedicineOrder order = this.inventoryMedicineService.findById(MedicineOrder.class, orderId);
        return decoratedResponse(InventoryDtoConverter.toMedicineNZTransferListFromOrderLine(order.getLineList()));
    }

    @PostMapping("item/order/request/line/copy")
    public DecoratedDTO<List<ItemOrderLineRespDto>> generateItemOrderLineList(@RequestBody NZTransferSourceLineDto sourceLineListDto) {
        return decoratedResponse(this.inventoryItemService.toItemOrderLineFromRequest(sourceLineListDto));
    }

    @PostMapping("medicine/order/request/line/copy")
    public DecoratedDTO<List<MedicineOrderLineRespDto>> generateMedicineOrderLine(@RequestBody NZTransferSourceLineDto sourceLineListDto) {
        return decoratedResponse(this.inventoryMedicineService.toMedicineOrderLineFromRequest(sourceLineListDto));
    }

    @PostMapping("item/order/master/line/copy")
    public DecoratedDTO<List<ItemPartialOrderLineRespDto>> generateItemPartialOrderLineList(@RequestBody NZTransferSourceLineDto sourceLineListDto) {
        return decoratedResponse(this.inventoryItemService.toItemPartialOrderLineFromMaster(sourceLineListDto));
    }

    @PostMapping("medicine/order/master/line/copy")
    public DecoratedDTO<List<MedicinePartialOrderLineRespDto>> generateMedicinePartialOrderLineList(@RequestBody NZTransferSourceLineDto sourceLineListDto) {
        return decoratedResponse(this.inventoryMedicineService.toMedicinePartialOrderLineFromMaster(sourceLineListDto));
    }

    @PostMapping("medicine/order/partial/list/{pageNum}")
    public PagedDTO<MedicinePartialOrderListRespDto> getMedicinePartialOrderList(@RequestBody OrderFilterDto orderFilter, @PathVariable int pageNum) {
        PagedList<MedicinePartialOrder> orderList = inventoryMedicineService.getPagedMedicinePartialOrderList(orderFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toMedicinePartialOrderListRespDto(orderList.getList()), orderList);
    }


    @PostMapping("medicine/order/partial/save")
    public DecoratedDTO<MedicinePartialOrderRespDto> saveMedicinePartialOrder(@RequestBody MedicinePartialOrderSaveDto medicineOrderSaveDto) {
        return decoratedResponse(inventoryMedicineService.saveMedicinePartialOrder(medicineOrderSaveDto).toDto());
    }

    @PostMapping("medicine/order/partial/{orderId}")
    public DecoratedDTO<MedicinePartialOrderRespDto> getMedicinePartialOrderDetail(@PathVariable UUID orderId) {
        return decoratedResponse(this.inventoryMedicineService.findById(MedicinePartialOrder.class, orderId).toDto());
    }


    @PostMapping("medicine/order/partial/delete/{uuid}")
    public void deleteMedicinePartialOrder(@PathVariable UUID uuid) {
        this.inventoryMedicineService.deleteMedicinePartialOrder(uuid);
    }


    @PostMapping("medicine/order/partial/submit")
    public void submitMedicinePartialOrder(@RequestBody MedicinePartialOrderSaveDto medicineOrderSaveDto) {
        this.inventoryMedicineService.submitMedicinePartialOrder(medicineOrderSaveDto);
    }

    @PostMapping("medicine/order/partial/approve")
    public void approveMedicinePartialOrder(@RequestBody MedicinePartialOrderSaveDto medicineOrderSaveDto) {
        inventoryMedicineService.approveMedicinePartialOrder(medicineOrderSaveDto);
    }

    @PostMapping("medicine/order/partial/reject")
    public void rejectMedicinePartialOrderRequest(@RequestBody MedicinePartialOrderSaveDto medicineOrderSaveDto) {
        this.inventoryMedicineService.rejectMedicinePartialOrderRequest(medicineOrderSaveDto);
    }

    @PostMapping("item/order/partial/list/{pageNum}")
    public PagedDTO<ItemPartialOrderListRespDto> getItemPartialOrderList(@RequestBody OrderFilterDto orderFilter, @PathVariable int pageNum) {
        PagedList<ItemPartialOrder> orderList = inventoryItemService.getPagedItemPartialOrderList(orderFilter, pageNum);
        return pagedResponse(InventoryDtoConverter.toItemPartialOrderListRespDto(orderList.getList()), orderList);
    }

    @PostMapping("item/order/partial/save")
    public DecoratedDTO<ItemPartialOrderRespDto> saveItemPartialOrder(@RequestBody ItemPartialOrderSaveDto itemOrderSaveDto) {
        return decoratedResponse(inventoryItemService.saveItemPartialOrder(itemOrderSaveDto).toDto());
    }

    @PostMapping("item/order/partial/{orderId}")
    public DecoratedDTO<ItemPartialOrderRespDto> getItemPartialOrderDetail(@PathVariable UUID orderId) {
        return decoratedResponse(this.inventoryItemService.findById(ItemPartialOrder.class, orderId).toDto());
    }


    @PostMapping("item/order/partial/delete/{uuid}")
    public void deleteItemPartialOrder(@PathVariable UUID uuid) {
        this.inventoryItemService.deleteItemPartialOrder(uuid);
    }


    @PostMapping("item/order/partial/submit")
    public void submitItemPartialOrder(@RequestBody ItemPartialOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.updateItemPartialOrderStatus(itemOrderSaveDto, OrderStatus.created);
    }

    @PostMapping("item/order/partial/approve")
    public void approveItemPartialOrder(@RequestBody ItemPartialOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.approveItemPartialOrder(itemOrderSaveDto);
    }

    @PostMapping("item/order/partial/reject")
    public void rejectItemPartialOrderRequest(@RequestBody ItemPartialOrderSaveDto itemOrderSaveDto) {
        inventoryItemService.updateItemPartialOrderStatus(itemOrderSaveDto, OrderStatus.submitted);
    }

    @PostMapping("medicine/stock/origin_order_line/list")
    public DecoratedDTO<List<MedicineOrderStockRespDto>> getStockOriginOrderLineList(@RequestBody List<UUID> stockIdList) {
        List<CachedMedicineStock> stockList = this.inventoryMedicineService.getStockOriginOrderLineList(stockIdList);
        return decoratedResponse(DtoUtils.toDtoList(stockList));
    }

    @PostMapping("item/stock/origin_order_line/list")
    public DecoratedDTO<List<ItemOrderStockRespDto>> getItemStockOriginOrderLineList( @RequestBody List<UUID> stockIdList) {
        List<CachedItemStock> stockList = this.inventoryItemService.getStockOriginOrderLineList(stockIdList);
        return decoratedResponse(DtoUtils.toDtoList(stockList));
    }
}

