package lukelin.his.dto.basic.req;

import java.util.List;
import java.util.UUID;

public class UserPermissionUpdateDto {
    private UUID uuid;

    private UUID roleId;

    private List<UUID> departmentIdList;

    private List<UUID> warehouseIdList;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public List<UUID> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<UUID> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public List<UUID> getWarehouseIdList() {
        return warehouseIdList;
    }

    public void setWarehouseIdList(List<UUID> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }
}
