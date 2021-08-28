package lukelin.his.dto.basic.req.filter;

import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.Basic.DepartmentType;
import lukelin.his.domain.enums.Basic.WarehouseType;

import java.util.List;
import java.util.UUID;

public class DepartmentFilterDto {
    private List<UUID> warehouseIdList;

    private List<UUID> departmentTreatmentIdList;

    private DepartmentType departmentType;

    private List<WarehouseType> warehouseTypeList;

    private DepartmentTreatmentType departmentTreatmentType;

    private  List<DepartmentTreatmentType> departmentTreatmentTypeList;

    public List<DepartmentTreatmentType> getDepartmentTreatmentTypeList() {
        return departmentTreatmentTypeList;
    }

    public void setDepartmentTreatmentTypeList(List<DepartmentTreatmentType> departmentTreatmentTypeList) {
        this.departmentTreatmentTypeList = departmentTreatmentTypeList;
    }

    public List<UUID> getDepartmentTreatmentIdList() {
        return departmentTreatmentIdList;
    }

    public void setDepartmentTreatmentIdList(List<UUID> departmentTreatmentIdList) {
        this.departmentTreatmentIdList = departmentTreatmentIdList;
    }

    public List<UUID> getWarehouseIdList() {
        return warehouseIdList;
    }

    public void setWarehouseIdList(List<UUID> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }

    public DepartmentTreatmentType getDepartmentTreatmentType() {
        return departmentTreatmentType;
    }

    public void setDepartmentTreatmentType(DepartmentTreatmentType departmentTreatmentType) {
        this.departmentTreatmentType = departmentTreatmentType;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public List<WarehouseType> getWarehouseTypeList() {
        return warehouseTypeList;
    }

    public void setWarehouseTypeList(List<WarehouseType> warehouseTypeList) {
        this.warehouseTypeList = warehouseTypeList;
    }
}
