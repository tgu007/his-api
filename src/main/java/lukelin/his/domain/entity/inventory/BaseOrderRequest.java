package lukelin.his.domain.entity.inventory;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.enums.Inventory.OrderRequestStatus;
import lukelin.his.dto.Inventory.resp.BaseOrderRequestListRespDto;
import lukelin.his.dto.Inventory.resp.BaseOrderRequestRespDto;
import lukelin.his.system.Utils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseOrderRequest extends BaseEntity implements DtoConvertible<BaseOrderRequestRespDto> {


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private OrderRequestStatus status;

    @ManyToOne
    @JoinColumn(name = "request_employee_id")
    private Employee byEmployee;


    @ManyToOne
    @JoinColumn(name = "approved_by_id")
    private Employee approvedBy;

    @ManyToOne
    @JoinColumn(name = "request_department_id")
    private DepartmentTreatment byDepartment;

    @Column(name = "urgent", nullable = false)
    private Boolean urgent;

    @Column(name = "order_request_number", insertable = false, updatable = false)
    private Integer orderRequestNumber;

    @Column(name = "order_request_number_code")
    private String orderRequestNumberCode;

    @Column(name = "approved_date")
    private Date approvedDate;

    public String getOrderRequestNumberCode() {
        return orderRequestNumberCode;
    }

    public void setOrderRequestNumberCode(String orderRequestNumberCode) {
        this.orderRequestNumberCode = orderRequestNumberCode;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getOrderRequestNumber() {
        return orderRequestNumber;
    }

    public void setOrderRequestNumber(Integer orderRequestNumber) {
        this.orderRequestNumber = orderRequestNumber;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderRequestStatus getStatus() {
        return status;
    }

    public void setStatus(OrderRequestStatus status) {
        this.status = status;
    }

    public Employee getByEmployee() {
        return byEmployee;
    }

    public void setByEmployee(Employee byEmployee) {
        this.byEmployee = byEmployee;
    }

    public DepartmentTreatment getByDepartment() {
        return byDepartment;
    }

    public void setByDepartment(DepartmentTreatment byDepartment) {
        this.byDepartment = byDepartment;
    }

    protected void setOrderRequestDtoValue(BaseOrderRequestRespDto requestDto) {
        BeanUtils.copyPropertiesIgnoreNull(this, requestDto);
        requestDto.setOrderRequestNumber(Utils.buildDisplayCode(this.getOrderRequestNumber()));
        if (this.getByDepartment() != null)
            requestDto.setByDepartment(this.getByDepartment().toDto());
        if (this.getByEmployee() != null)
            requestDto.setByEmployee(this.getByEmployee().toListDto());
    }

    protected void copyListDtoValue(BaseOrderRequestListRespDto dto) {
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        dto.setWhoCreated(this.getWhoCreatedName());
        if (this.getApprovedBy() != null)
            dto.setApprovedBy(this.getApprovedBy().getName());
        dto.setOrderRequestNumber(Utils.buildDisplayCode(this.getOrderRequestNumber()));
        if (this.getByEmployee() != null)
            dto.setByEmployeeName(this.getByEmployee().getName());
        if (this.getByDepartment() != null)
            dto.setByDepartmentName(this.getByDepartment().getDepartment().getName());
    }
}
