package lukelin.his.dto.basic.req.entity;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class InventoryEntitySaveDto extends EntitySaveDto {

    private UUID warehouseUomId;

    private UUID departmentUomId;

    private String warehouseModel;

    private String departmentModel;

    private BigDecimal warehouseToMinRate;

    private BigDecimal departmentToMinRate;

    private UUID manufacturerId;

    public UUID getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(UUID manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public UUID getWarehouseUomId() {
        return warehouseUomId;
    }

    public void setWarehouseUomId(UUID warehouseUomId) {
        this.warehouseUomId = warehouseUomId;
    }

    public UUID getDepartmentUomId() {
        return departmentUomId;
    }

    public void setDepartmentUomId(UUID departmentUomId) {
        this.departmentUomId = departmentUomId;
    }

    public String getWarehouseModel() {
        return warehouseModel;
    }

    public void setWarehouseModel(String warehouseModel) {
        this.warehouseModel = warehouseModel;
    }

    public String getDepartmentModel() {
        return departmentModel;
    }

    public void setDepartmentModel(String departmentModel) {
        this.departmentModel = departmentModel;
    }

    public BigDecimal getWarehouseToMinRate() {
        return warehouseToMinRate;
    }

    public void setWarehouseToMinRate(BigDecimal warehouseToMinRate) {
        this.warehouseToMinRate = warehouseToMinRate;
    }

    public BigDecimal getDepartmentToMinRate() {
        return departmentToMinRate;
    }

    public void setDepartmentToMinRate(BigDecimal departmentToMinRate) {
        this.departmentToMinRate = departmentToMinRate;
    }
}
