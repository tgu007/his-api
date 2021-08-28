package lukelin.his.service;

import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.Query;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.inventory.item.*;
import lukelin.his.domain.entity.inventory.medicine.CachedMedicineStock;
import lukelin.his.domain.enums.Inventory.*;
import lukelin.his.dto.Inventory.req.NZTransferSourceLineDto;
import lukelin.his.dto.Inventory.req.filter.OrderFilterDto;
import lukelin.his.dto.Inventory.req.filter.OrderRequestFilterDto;
import lukelin.his.dto.Inventory.req.filter.TransferFilterDto;
import lukelin.his.dto.Inventory.req.item.*;
import lukelin.his.dto.Inventory.req.filter.ItemStockFilterDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemPartialOrderLineRespDto;
import lukelin.his.system.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InventoryItemService extends BaseHisService {
    public PagedList<ItemOrder> getPagedItemOrderList(OrderFilterDto orderFilter, int pageNum) {
        return this.getPagedItemOrderList(orderFilter, pageNum, 0);
    }

    public PagedList<ItemOrder> getPagedItemOrderList(OrderFilterDto orderFilter, int pageNum, int pageSize) {
        ExpressionList<ItemOrder> el = ebeanServer.find(ItemOrder.class).alias("o").where()
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
                    .and()
                    .like("orderRequest.orderRequestNumberCode", "%" + orderFilter.getSearchNumber() + "%")
                    .ne("orderRequest.orderRequestNumberCode", null)
                    .endAnd()
                    .endOr();
        Query<ItemOrder> query = el.query().orderBy("o.when_created desc");
        return findPagedList(query, pageNum, pageSize);
    }

    @Transactional
    public ItemOrder saveItemOrder(ItemOrderSaveDto itemOrderSaveDto) {
        ItemOrder itemOrder = itemOrderSaveDto.toEntity();
        if (itemOrder.getUuid() != null)
            ebeanServer.update(itemOrder);
        else
            ebeanServer.save(itemOrder);

        itemOrder = this.findById(ItemOrder.class, itemOrder.getUuid());
        if (itemOrderSaveDto.getUuid() == null) {
            itemOrder.setOrderNumberCode(Utils.buildDisplayCode(itemOrder.getOrderNumber()));
            ebeanServer.update(itemOrder);
        }
        return itemOrder;

    }

    public ItemOrder getItemOrder(UUID orderId) {
        return findById(ItemOrder.class, orderId);
    }

    @Transactional
    public void deleteItemOrder(UUID orderId) {
        ItemOrder itemOrder = this.getItemOrder(orderId);
        if (itemOrder.getOrderStatus() != OrderStatus.created)
            throw new ApiValidationException("Invalid order status to delete");
        ebeanServer.delete(itemOrder);
    }

    @Transactional
    public void updateItemOrderStatus(ItemOrderSaveDto itemOrderSaveDto, OrderStatus originStatus) {
        ItemOrder itemOrder = this.getItemOrder(itemOrderSaveDto.getUuid());
        if (itemOrder.getOrderStatus() != originStatus)
            throw new ApiValidationException("Invalid order status to update");
        itemOrder.setOrderStatus(itemOrderSaveDto.getOrderStatus());
        ebeanServer.save(itemOrder);
    }

    @Transactional
    public void approveItemOrder(ItemOrderSaveDto itemOrderSaveDto) {
        ItemOrder itemOrder = this.findById(ItemOrder.class, itemOrderSaveDto.getUuid());
        if (itemOrder.getOrderStatus() != OrderStatus.submitted)
            throw new ApiValidationException("Invalid order status to approve");
        itemOrder.setOrderStatus(OrderStatus.approved);
        itemOrder.setApprovedDate(new Date());
        itemOrder.setApprovedBy(new Employee(itemOrderSaveDto.getApproveById()));
        ebeanServer.save(itemOrder);
    }

    @Transactional
    public void approveItemReturnOrder(ItemOrderSaveDto itemOrderSaveDto) {
        ItemOrder itemOrder = this.findById(ItemOrder.class, itemOrderSaveDto.getUuid());
        if (itemOrder.getOrderStatus() != OrderStatus.pendingReturn)
            throw new ApiValidationException("Invalid order status to return");
        itemOrder.setOrderStatus(OrderStatus.returned);
        itemOrder.setApprovedDate(new Date());
        itemOrder.setApprovedBy(new Employee(itemOrderSaveDto.getApproveById()));
        //更新库存信息
        for (ItemOrderLine orderLine : itemOrder.getLineList()) {
            CachedItemTransaction newTransaction = (CachedItemTransaction) orderLine.createOrderLineTransaction();
            ebeanServer.save(newTransaction);
            this.updateItemStockCache(newTransaction);
        }
        ebeanServer.save(itemOrder);
    }

    public PagedList<ItemTransfer> getPagedItemTransferList(TransferFilterDto itemTransferFilter, int pageNum) {
        ExpressionList<ItemTransfer> el = ebeanServer.find(ItemTransfer.class).where();
        if (itemTransferFilter.getFromWarehouseIdList() != null)
            el = el.in("fromWarehouse.uuid", itemTransferFilter.getFromWarehouseIdList());
        if (itemTransferFilter.getToWarehouseIdList() != null)
            el = el.in("toWarehouse.uuid", itemTransferFilter.getToWarehouseIdList());
        if ((itemTransferFilter.getStartDate() != null) && (itemTransferFilter.getEndDate() != null))
            el = el.between("confirmedDate", itemTransferFilter.getStartDate(), itemTransferFilter.getEndDate());

        Query<ItemTransfer> query = el.query().orderBy("when_created desc");
        return findPagedList(query, pageNum);
    }

    @Transactional
    public ItemTransfer saveItemTransfer(ItemTransferSaveDto itemTransferSaveDto) {
        ItemTransfer itemTransfer = itemTransferSaveDto.toEntity();
        if (itemTransfer.getUuid() != null)
            ebeanServer.update(itemTransfer);
        else
            ebeanServer.save(itemTransfer);
        itemTransfer = this.findById(ItemTransfer.class, itemTransfer.getUuid());
        if (itemTransferSaveDto.getUuid() == null) {
            itemTransfer.setTransferNumberCode(Utils.buildDisplayCode(itemTransfer.getTransferNumber()));
            ebeanServer.update(itemTransfer);
        }
        return itemTransfer;

    }

    public ItemTransfer getItemTransfer(UUID transferId) {
        return findById(ItemTransfer.class, transferId);
    }

//    @Transactional
//    public ItemTransfer updateItemTransferStatus(ItemTransferSaveDto itemTransferSaveDto, TransferStatus originStatus) {
//        ItemTransfer itemTransfer = this.findById(ItemTransfer.class, itemTransferSaveDto.getUuid());
//        if (itemTransfer.getTransferStatus() != originStatus)
//            throw new ApiValidationException("Invalid transfer status to update");
//        itemTransfer.setTransferStatus(itemTransferSaveDto.getTransferStatus());
//        ebeanServer.save(itemTransfer);
//        return itemTransfer;
//    }

    @Transactional
    public void deleteItemTransfer(UUID transferId) {
        ItemTransfer itemTransfer = this.getItemTransfer(transferId);
        if (itemTransfer.getTransferStatus() != TransferStatus.created)
            throw new ApiValidationException("Invalid transfer status to delete");
        ebeanServer.delete(itemTransfer);
    }

    @Transactional
    public ItemTransfer confirmItemTransfer(UUID transferId) {
        ItemTransfer itemTransfer = this.getItemTransfer(transferId);
        if (itemTransfer.getTransferStatus() != TransferStatus.pendingConfirm)
            throw new ApiValidationException("Invalid transfer status to confirm");
        itemTransfer.setTransferStatus(TransferStatus.confirmed);
        itemTransfer.setConfirmedDate(new Date());
        for (ItemTransferLine transferLine : itemTransfer.getLineList()) {
            List<CachedTransactionInterface> newTransactionList = transferLine.generateTransferTransactionList(itemTransfer.getToWarehouse(), TransactionType.transferIn);
            transferLine.createTransferTransactionList(newTransactionList, TransferTransactionStatus.confirmed, TransactionType.transferIn);
            for (CachedTransactionInterface transferItemTransaction : newTransactionList)
                this.updateItemStockCache((CachedItemTransaction) transferItemTransaction);
        }
        ebeanServer.save(itemTransfer);
        return itemTransfer;

    }

    @Transactional
    public ItemTransfer rejectItemTransfer(UUID transferId) {
        ItemTransfer itemTransfer = this.getItemTransfer(transferId);
        if (itemTransfer.getTransferStatus() != TransferStatus.pendingConfirm)
            throw new ApiValidationException("Invalid transfer status to confirm");
        itemTransfer.setTransferStatus(TransferStatus.created);
        for (ItemTransferLine transferLine : itemTransfer.getLineList()) {
            List<CachedTransactionInterface> newTransactionList = transferLine.generateTransferTransactionList(itemTransfer.getFromWarehouse(), TransactionType.rejectedTransferOut);
            transferLine.createTransferTransactionList(newTransactionList, TransferTransactionStatus.confirmed, TransactionType.rejectedTransferOut);
            for (CachedTransactionInterface transferItemTransaction : newTransactionList)
                this.updateItemStockCache((CachedItemTransaction) transferItemTransaction);
        }
        ebeanServer.save(itemTransfer);
        return itemTransfer;

    }

    @Transactional
    public ItemTransfer transferItemTransfer(UUID transferId) {
        ItemTransfer itemTransfer = this.findById(ItemTransfer.class, transferId);
        if (itemTransfer.getTransferStatus() != TransferStatus.created)
            throw new ApiValidationException("Invalid transfer status to update");
        itemTransfer.setTransferStatus(TransferStatus.pendingConfirm);
        itemTransfer.setTransferDate(new Date());
        for (ItemTransferLine transferLine : itemTransfer.getLineList()) {
            List<CachedTransactionInterface> newTransactionList = transferLine.createTransferTransactionList();
            //To do need to refactor into interface
            transferLine.createTransferTransactionList(newTransactionList, TransferTransactionStatus.pendingConfirm, TransactionType.transferOut);
            //ebeanServer.saveAll(transferItemTransactionList);
            for (CachedTransactionInterface transferItemTransaction : newTransactionList)
                this.updateItemStockCache((CachedItemTransaction) transferItemTransaction);
        }
        ebeanServer.save(itemTransfer);
        return itemTransfer;
    }


    public void updateItemStockCache(CachedItemTransaction itemTransaction) {
        Optional<CachedItemStock> optionalItemStock = ebeanServer.find(CachedItemStock.class)
                .where()
                .eq("warehouse.uuid", itemTransaction.getWarehouse().getUuid())
                .eq("item.uuid", itemTransaction.getItem().getUuid())
                .eq("originPurchaseLine.uuid", itemTransaction.getOriginPurchaseLine().getUuid())
                .findOneOrEmpty();
        CachedItemStock itemStock;
        if (optionalItemStock.isPresent())
            itemStock = optionalItemStock.get();
        else {
            itemStock = new CachedItemStock();
        }
        itemStock.updateFromTransaction(itemTransaction);
        if (itemStock.getMinUomQuantity().compareTo(BigDecimal.ZERO) == 0)
            ebeanServer.delete(itemStock);
        else
            ebeanServer.save(itemStock);
    }


    //返回物品再仓库中的详细信息,包括同一种物品的分批进货记录
    public List<CachedItemStock> getDetailItemStockList(ItemStockFilterDto filterDto) {
        ExpressionList<CachedItemStock> el = this.buildItemStockQuery(filterDto);
        return el.findList();
    }

    public PagedList<CachedItemStock> getPagedDetailItemStockList(ItemStockFilterDto filterDto, Integer pageNum) {
        return this.getPagedDetailItemStockList(filterDto, pageNum, 0);
    }

    public PagedList<CachedItemStock> getPagedDetailItemStockList(ItemStockFilterDto filterDto, Integer pageNum, Integer pageSize) {
        ExpressionList<CachedItemStock> el = this.buildItemStockQuery(filterDto);
        Query<CachedItemStock> query = el.orderBy("whenCreated desc");
        return findPagedList(query, pageNum, pageSize);
    }


    private ExpressionList<CachedItemStock> buildItemStockQuery(ItemStockFilterDto filterDto) {
        ExpressionList<CachedItemStock> el = ebeanServer.find(CachedItemStock.class)
                .where();

        if (filterDto.getWarehouseIdList() != null)
            el = el.in("warehouse.uuid", filterDto.getWarehouseIdList());

        if (filterDto.getItemId() != null)
            el = el.eq("item.uuid", filterDto.getItemId());

        if (filterDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, filterDto.getSearchCode(), "name", "item.searchCode");
        return el;
    }

    public void createAdjustment(ItemStockAdjustSaveDto itemStockAdjustSaveDto) {
        if (itemStockAdjustSaveDto.quantityChanged()) {
            ItemStockAdjustment newAdjustment = itemStockAdjustSaveDto.toEntity();
            ebeanServer.save(newAdjustment);
            newAdjustment = this.findById(ItemStockAdjustment.class, newAdjustment.getUuid());
            List<CachedTransactionInterface> adjustmentTransactionList = newAdjustment.createAdjustmentTransaction();
            ebeanServer.saveAll(adjustmentTransactionList);
            for (CachedTransactionInterface adjustmentItemTransaction : adjustmentTransactionList)
                this.updateItemStockCache((CachedItemTransaction) adjustmentItemTransaction);
        }
    }


    public PagedList<ItemOrderRequest> getPagedItemOrderRequestList(OrderRequestFilterDto requestFilter, int pageNum) {
        return getPagedItemOrderRequestList(requestFilter, pageNum, 0);
    }


    public PagedList<ItemOrderRequest> getPagedItemOrderRequestList(OrderRequestFilterDto requestFilter, int pageNum, int pageSize) {
        ExpressionList<ItemOrderRequest> el = ebeanServer.find(ItemOrderRequest.class).where()
                .in("status", requestFilter.getOrderStatusList());

        if ((requestFilter.getStartDate() != null) && (requestFilter.getEndDate() != null)) {
            Date endDate = this.addDays(requestFilter.getEndDate(), 1);
            el = el.between("when_created", requestFilter.getStartDate(), endDate);
        }

        if (requestFilter.getOrderNumber() != null) {
            el = el.like("orderRequestNumberCode", "%" + requestFilter.getOrderNumber() + "%");
        }

        Query<ItemOrderRequest> query = el.query().orderBy("when_created desc");
        return findPagedList(query, pageNum);
    }

    public ItemOrderRequest saveItemRequestOrder(ItemOrderRequestSaveDto itemOrderRequestSaveDto) {
        ItemOrderRequest itemOrderRequest = itemOrderRequestSaveDto.toEntity();
        if (itemOrderRequest.getUuid() != null)
            ebeanServer.update(itemOrderRequest);
        else
            ebeanServer.save(itemOrderRequest);
        itemOrderRequest = this.findById(ItemOrderRequest.class, itemOrderRequest.getUuid());
        if (itemOrderRequestSaveDto.getUuid() == null) {
            itemOrderRequest.setOrderRequestNumberCode(Utils.buildDisplayCode(itemOrderRequest.getOrderRequestNumber()));
            ebeanServer.update(itemOrderRequest);
        }
        return itemOrderRequest;
    }

    public void deleteItemOrderRequest(UUID orderRequestId) {
        ItemOrderRequest itemOrderRequest = this.findById(ItemOrderRequest.class, orderRequestId);
        if (itemOrderRequest.getStatus() != OrderRequestStatus.created)
            throw new ApiValidationException("Invalid order request status to delete");
        ebeanServer.delete(itemOrderRequest);
    }

    public void updateItemOrderRequestStatus(ItemOrderRequestSaveDto itemOrderRequestSaveDto, OrderRequestStatus originStatus) {
        ItemOrderRequest itemOrderRequest = this.findById(ItemOrderRequest.class, itemOrderRequestSaveDto.getUuid());
        if (itemOrderRequest.getStatus() != originStatus)
            throw new ApiValidationException("Invalid order request status to update");
        itemOrderRequest.setStatus(itemOrderRequestSaveDto.getStatus());
        ebeanServer.save(itemOrderRequest);
    }

    public void approveItemOrderRequest(ItemOrderRequestSaveDto itemOrderRequestSaveDto) {
        ItemOrderRequest itemOrderRequest = this.findById(ItemOrderRequest.class, itemOrderRequestSaveDto.getUuid());
        if (itemOrderRequest.getStatus() != OrderRequestStatus.submitted)
            throw new ApiValidationException("Invalid order request status to approve");
        itemOrderRequest.setStatus(OrderRequestStatus.approved);
        itemOrderRequest.setApprovedDate(new Date());
        itemOrderRequest.setApprovedBy(new Employee(itemOrderRequestSaveDto.getApproveById()));
        ebeanServer.save(itemOrderRequest);
    }


    public List<ItemOrderLineRespDto> toItemOrderLineFromRequest(NZTransferSourceLineDto requestLineDto) {
        List<ItemOrderLineRespDto> orderLineList = new ArrayList<>();
        for (UUID requestLineId : requestLineDto.getSourceLineIdList()) {
            ItemOrderRequestLine requestLine = this.findById(ItemOrderRequestLine.class, requestLineId);
            orderLineList.add(requestLine.toOrderLine().toDto());
        }
        return orderLineList;
    }

    public PagedList<ItemPartialOrder> getPagedItemPartialOrderList(OrderFilterDto orderFilter, int pageNum) {
        return getPagedItemPartialOrderList(orderFilter, pageNum, 0);
    }

    public PagedList<ItemPartialOrder> getPagedItemPartialOrderList(OrderFilterDto orderFilter, int pageNum, int pageSize) {
        ExpressionList<ItemPartialOrder> el = ebeanServer.find(ItemPartialOrder.class).alias("po").where();
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

        Query<ItemPartialOrder> query = el.query().orderBy("po.when_created desc");
        return findPagedList(query, pageNum, pageSize);
    }

    @Transactional
    public ItemPartialOrder saveItemPartialOrder(ItemPartialOrderSaveDto itemOrderSaveDto) {
        ItemPartialOrder itemOrder = itemOrderSaveDto.toEntity();
        if (itemOrder.getUuid() != null)
            ebeanServer.update(itemOrder);
        else
            ebeanServer.save(itemOrder);
        itemOrder = this.findById(ItemPartialOrder.class, itemOrder.getUuid());
        if (itemOrderSaveDto.getUuid() == null) {
            itemOrder.setOrderNumberCode(Utils.buildDisplayCode(itemOrder.getOrderNumber()));
            ebeanServer.update(itemOrder);
        }
        return itemOrder;

    }


    @Transactional
    public void deleteItemPartialOrder(UUID orderId) {
        ItemPartialOrder itemOrder = this.findById(ItemPartialOrder.class, orderId);
        if (itemOrder.getOrderStatus() != OrderStatus.created)
            throw new ApiValidationException("Invalid order status to delete");
        ebeanServer.delete(itemOrder);
    }

    @Transactional
    public void updateItemPartialOrderStatus(ItemPartialOrderSaveDto itemOrderSaveDto, OrderStatus originStatus) {
        ItemPartialOrder itemOrder = this.findById(ItemPartialOrder.class, itemOrderSaveDto.getUuid());
        if (itemOrder.getOrderStatus() != originStatus)
            throw new ApiValidationException("Invalid order status to update");
        itemOrder.setOrderStatus(itemOrderSaveDto.getOrderStatus());
        ebeanServer.save(itemOrder);
    }

    @Transactional
    public void approveItemPartialOrder(ItemPartialOrderSaveDto itemOrderSaveDto) {
        ItemPartialOrder itemOrder = this.findById(ItemPartialOrder.class, itemOrderSaveDto.getUuid());
        if (itemOrder.getOrderStatus() != OrderStatus.submitted)
            throw new ApiValidationException("Invalid order status to approve");
        itemOrder.setOrderStatus(OrderStatus.approved);
        itemOrder.setApprovedDate(new Date());
        itemOrder.setApprovedBy(new Employee(itemOrderSaveDto.getApproveById()));
        //更新库存信息
        for (ItemPartialOrderLine orderLine : itemOrder.getLineList()) {
            CachedItemTransaction newTransaction = (CachedItemTransaction) orderLine.createOrderLineTransaction();
            ebeanServer.save(newTransaction);
            this.updateItemStockCache(newTransaction);
        }
        ebeanServer.save(itemOrder);
    }

    public List<ItemPartialOrderLineRespDto> toItemPartialOrderLineFromMaster(NZTransferSourceLineDto orderLineDto) {
        List<ItemPartialOrderLineRespDto> orderLineList = new ArrayList<>();
        for (UUID lineId : orderLineDto.getSourceLineIdList()) {
            ItemOrderLine orderLine = this.findById(ItemOrderLine.class, lineId);
            orderLineList.add(orderLine.toPartialOrderLine().toDto());
        }
        return orderLineList;
    }


    public List<CachedItemStock> getStockOriginOrderLineList(List<UUID> stockIdList) {
        return ebeanServer.find(CachedItemStock.class)
                .where()
                .in("uuid", stockIdList)
                .findList();
    }
}
