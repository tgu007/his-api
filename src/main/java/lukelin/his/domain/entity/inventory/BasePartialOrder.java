package lukelin.his.domain.entity.inventory;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.Supplier;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.Inventory.resp.BasePartialOrderRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class BasePartialOrder extends BaseEntity implements DtoConvertible<BasePartialOrderRespDto> {
    @Column(nullable = false, name = "status")
    private OrderStatus orderStatus;

    @Column(name = "order_number", insertable = false, updatable = false)
    private Integer orderNumber;

    @Column(name = "approved_date")
    private Date approvedDate;

    @ManyToOne
    @JoinColumn(name = "approved_by_id")
    private Employee approvedBy;

    @Column(name = "order_number_code")
    private String orderNumberCode;

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

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getOrderNumberCode() {
        return orderNumberCode;
    }

    public void setOrderNumberCode(String orderNumberCode) {
        this.orderNumberCode = orderNumberCode;
    }
}
