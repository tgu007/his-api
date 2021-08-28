package lukelin.his.dto.Inventory.resp;

import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.basic.resp.setup.DepartmentWarehouseDto;

import java.util.Date;
import java.util.UUID;

public abstract class BaseOrderListRespDto {
    private UUID uuid;

    private String orderNumber;

    private Date orderDate;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date approvedDate;

    private String supplier;

    private String reference;

    private String whoCreated;

    private OrderStatus orderStatus;

    private DepartmentWarehouseDto toWarehouse;

    private String approvedBy;

    private Boolean paid;

    private BaseOrderRequestListRespDto orderRequest;

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public BaseOrderRequestListRespDto getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(BaseOrderRequestListRespDto orderRequest) {
        this.orderRequest = orderRequest;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public DepartmentWarehouseDto getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(DepartmentWarehouseDto toWarehouse) {
        this.toWarehouse = toWarehouse;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
