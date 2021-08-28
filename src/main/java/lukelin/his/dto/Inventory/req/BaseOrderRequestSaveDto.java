package lukelin.his.dto.Inventory.req;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.Supplier;
import lukelin.his.domain.entity.inventory.BaseOrder;
import lukelin.his.domain.entity.inventory.BaseOrderRequest;
import lukelin.his.domain.enums.Inventory.OrderRequestStatus;
import lukelin.his.domain.enums.Inventory.OrderStatus;

import java.util.Date;
import java.util.UUID;

public abstract class BaseOrderRequestSaveDto {
    private UUID uuid;

    private String name;

    private OrderRequestStatus status;

    private UUID byEmployeeId;

    private UUID byDepartmentId;

    private Boolean urgent;

    private UUID approveById;

    public UUID getApproveById() {
        return approveById;
    }

    public void setApproveById(UUID approveById) {
        this.approveById = approveById;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public UUID getByEmployeeId() {
        return byEmployeeId;
    }

    public void setByEmployeeId(UUID byEmployeeId) {
        this.byEmployeeId = byEmployeeId;
    }

    public UUID getByDepartmentId() {
        return byDepartmentId;
    }

    public void setByDepartmentId(UUID byDepartmentId) {
        this.byDepartmentId = byDepartmentId;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    protected void setOrderRequestProperty(BaseOrderRequest orderRequest) {
        BeanUtils.copyPropertiesIgnoreNull(this, orderRequest);
        if (this.byDepartmentId != null)
            orderRequest.setByDepartment(new DepartmentTreatment(this.byDepartmentId));
        else
            orderRequest.setByDepartment(null);

        if (this.byEmployeeId != null)
            orderRequest.setByEmployee(new Employee(this.byEmployeeId));
        else
            orderRequest.setByEmployee(null);
    }
}
