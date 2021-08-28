package lukelin.his.dto.Inventory.resp;

import lukelin.his.domain.enums.Inventory.OrderRequestStatus;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.EmployeeListDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class BaseOrderRequestRespDto {
    private UUID uuid;

    private String name;

    private OrderRequestStatus status;

    private EmployeeListDto byEmployee;

    private DepartmentTreatmentDto byDepartment;

    private Boolean urgent;

    private List<BaseOrderRequestLineRespDto> lineList;

    private String orderRequestNumber;

    private Date approvedDate;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getOrderRequestNumber() {
        return orderRequestNumber;
    }

    public void setOrderRequestNumber(String orderRequestNumber) {
        this.orderRequestNumber = orderRequestNumber;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
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

    public EmployeeListDto getByEmployee() {
        return byEmployee;
    }

    public void setByEmployee(EmployeeListDto byEmployee) {
        this.byEmployee = byEmployee;
    }

    public DepartmentTreatmentDto getByDepartment() {
        return byDepartment;
    }

    public void setByDepartment(DepartmentTreatmentDto byDepartment) {
        this.byDepartment = byDepartment;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public List<BaseOrderRequestLineRespDto> getLineList() {
        return lineList;
    }

    public void setLineList(List<BaseOrderRequestLineRespDto> lineList) {
        this.lineList = lineList;
    }
}
