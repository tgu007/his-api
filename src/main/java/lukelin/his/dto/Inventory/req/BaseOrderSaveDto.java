package lukelin.his.dto.Inventory.req;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.inventory.BaseOrder;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.Supplier;
import lukelin.his.domain.enums.Inventory.OrderStatus;

import java.util.Date;
import java.util.UUID;

public abstract class BaseOrderSaveDto {
    private UUID uuid;

    private String reference;

    private OrderStatus orderStatus;

    private UUID supplierId;

    private UUID toWarehouseId;

    private Date orderDate;

    private boolean returnOrder;

    private Boolean paid;

    private UUID approveById;

    private UUID orderRequestId;

    public UUID getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(UUID orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public UUID getApproveById() {
        return approveById;
    }

    public void setApproveById(UUID approveById) {
        this.approveById = approveById;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

    public UUID getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(UUID toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(boolean returnOrder) {
        this.returnOrder = returnOrder;
    }

    protected void setOrderProperty(BaseOrder order) {
        BeanUtils.copyPropertiesIgnoreNull(this, order);
        order.setToWarehouse(new DepartmentWarehouse(this.toWarehouseId));
        order.setSupplier(new Supplier(this.supplierId));
    }
}
