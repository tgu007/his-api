package lukelin.his.dto.Inventory.resp;

import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.basic.resp.setup.DepartmentWarehouseDto;
import lukelin.his.dto.basic.resp.setup.SupplierDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class BaseOrderRespDto {
    private UUID uuid;

    private String orderNumber;

    private Date orderDate;

    private SupplierDto supplier;

    private String reference;

    private OrderStatus orderStatus;

    private DepartmentWarehouseDto toWarehouse;

    private List<BaseOrderLineRespDto> lineList;

    private Date approvedDate;

    private Boolean paid;

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public DepartmentWarehouseDto getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(DepartmentWarehouseDto toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public List<BaseOrderLineRespDto> getLineList() {
        return lineList;
    }

    public void setLineList(List<BaseOrderLineRespDto> lineList) {
        this.lineList = lineList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
