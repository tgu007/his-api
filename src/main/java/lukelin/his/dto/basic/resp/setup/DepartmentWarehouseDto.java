package lukelin.his.dto.basic.resp.setup;

import lukelin.his.domain.enums.Basic.WarehouseType;

import java.util.UUID;

public class DepartmentWarehouseDto {
    private UUID uuid;

    private DepartmentDto department;

    private WarehouseType warehouseType;

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public WarehouseType getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseType warehouseType) {
        this.warehouseType = warehouseType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
