package lukelin.his.dto.notification.req;

import lukelin.his.domain.enums.Basic.UserRoleType;

import java.util.List;
import java.util.UUID;

public class NotificationFilterDto {
    private UserRoleType userRoleType;

    private List<UUID> departmentIdList;

    private List<UUID> wardIdList;

    private List<UUID> warehouseIdList;

    private UUID employeeId;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }

    public List<UUID> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<UUID> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public List<UUID> getWardIdList() {
        return wardIdList;
    }

    public void setWardIdList(List<UUID> wardIdList) {
        this.wardIdList = wardIdList;
    }

    public List<UUID> getWarehouseIdList() {
        return warehouseIdList;
    }

    public void setWarehouseIdList(List<UUID> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }
}
