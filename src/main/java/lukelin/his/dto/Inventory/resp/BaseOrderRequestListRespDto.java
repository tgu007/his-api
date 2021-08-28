package lukelin.his.dto.Inventory.resp;

import lukelin.his.domain.enums.Inventory.OrderRequestStatus;

import java.util.Date;
import java.util.UUID;

public abstract class BaseOrderRequestListRespDto {
    private UUID uuid;

    private String name;

    private OrderRequestStatus status;

    private String byEmployeeName;

    private String byDepartmentName;

    private Boolean urgent;

    private String orderRequestNumber;

    private Date approvedDate;

    private String whoCreated;

    private String approvedBy;

    private Date whenCreated;

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
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

    public String getByEmployeeName() {
        return byEmployeeName;
    }

    public void setByEmployeeName(String byEmployeeName) {
        this.byEmployeeName = byEmployeeName;
    }

    public String getByDepartmentName() {
        return byDepartmentName;
    }

    public void setByDepartmentName(String byDepartmentName) {
        this.byDepartmentName = byDepartmentName;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
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

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }


    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }
}
