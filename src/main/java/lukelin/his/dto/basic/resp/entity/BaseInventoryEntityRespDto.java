package lukelin.his.dto.basic.resp.entity;

import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.math.BigDecimal;

public class BaseInventoryEntityRespDto extends BaseEntityRespDto {
    private String departmentModel;

    private String warehouseModel;

    private UnitOfMeasureDto warehouseUom;

    private UnitOfMeasureDto departmentUom;

    private BigDecimal warehouseConversionRate;

    private BigDecimal departmentConversionRate;


    public String getDepartmentModel() {
        return departmentModel;
    }

    public void setDepartmentModel(String departmentModel) {
        this.departmentModel = departmentModel;
    }

    public String getWarehouseModel() {
        return warehouseModel;
    }

    public void setWarehouseModel(String warehouseModel) {
        this.warehouseModel = warehouseModel;
    }

    public UnitOfMeasureDto getWarehouseUom() {
        return warehouseUom;
    }

    public void setWarehouseUom(UnitOfMeasureDto warehouseUom) {
        this.warehouseUom = warehouseUom;
    }

    public UnitOfMeasureDto getDepartmentUom() {
        return departmentUom;
    }

    public void setDepartmentUom(UnitOfMeasureDto departmentUom) {
        this.departmentUom = departmentUom;
    }

    public BigDecimal getWarehouseConversionRate() {
        return warehouseConversionRate;
    }

    public void setWarehouseConversionRate(BigDecimal warehouseConversionRate) {
        this.warehouseConversionRate = warehouseConversionRate;
    }

    public BigDecimal getDepartmentConversionRate() {
        return departmentConversionRate;
    }

    public void setDepartmentConversionRate(BigDecimal departmentConversionRate) {
        this.departmentConversionRate = departmentConversionRate;
    }
}
