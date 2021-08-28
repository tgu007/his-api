package lukelin.his.domain.entity.inventory;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.Supplier;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.Inventory.resp.BaseOrderRespDto;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseOrder extends BaseEntity implements DtoConvertible<BaseOrderRespDto> {
    private String reference;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(nullable = false, name = "status")
    private OrderStatus orderStatus;

    @Column(name = "order_number", insertable = false, updatable = false)
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "to_warehouse_id", nullable = false)
    private DepartmentWarehouse toWarehouse;

    @Column(nullable = false, name = "is_return_order")
    private boolean returnOrder;

    @Column(name = "approved_date")
    private Date approvedDate;

    @ManyToOne
    @JoinColumn(name = "approved_by_id")
    private Employee approvedBy;

    @Column(name = "paid")
    private Boolean paid = false;

    @Column(name = "order_number_code")
    private String orderNumberCode;

    public String getOrderNumberCode() {
        return orderNumberCode;
    }

    public void setOrderNumberCode(String orderNumberCode) {
        this.orderNumberCode = orderNumberCode;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Boolean getPaid() {
        return paid;
    }

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

    public boolean isReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(boolean returnOrder) {
        this.returnOrder = returnOrder;
    }

    public DepartmentWarehouse getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(DepartmentWarehouse toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }


    protected void setOrderDtoValue(BaseOrderRespDto orderDto) {
        BeanUtils.copyPropertiesIgnoreNull(this, orderDto);
        orderDto.setPaid(this.isPaid());
        orderDto.setOrderNumber(Utils.buildDisplayCode(this.getOrderNumber()));
        if (this.getSupplier() != null)
            orderDto.setSupplier(this.getSupplier().toDto());
        orderDto.setToWarehouse(this.getToWarehouse().toDto());
    }
}
