package lukelin.his.dto.basic.resp.entity;

import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.util.List;

public class ItemDetailPramDto {
    private List<DictionaryDto> warehouseList;

    private List<DictionaryDto> storageTypeList;

    private List<UnitOfMeasureDto> minUomList;

    private List<UnitOfMeasureDto> warehouseUomList;

    private List<DictionaryDto> feeTypeList;

    private List<UnitOfMeasureDto> departmentUomList;

    public List<UnitOfMeasureDto> getDepartmentUomList() {
        return departmentUomList;
    }

    public void setDepartmentUomList(List<UnitOfMeasureDto> departmentUomList) {
        this.departmentUomList = departmentUomList;
    }

    public List<DictionaryDto> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<DictionaryDto> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }

    public List<DictionaryDto> getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(List<DictionaryDto> warehouseList) {
        this.warehouseList = warehouseList;
    }

    public List<DictionaryDto> getStorageTypeList() {
        return storageTypeList;
    }

    public void setStorageTypeList(List<DictionaryDto> storageTypeList) {
        this.storageTypeList = storageTypeList;
    }

    public List<UnitOfMeasureDto> getMinUomList() {
        return minUomList;
    }

    public void setMinUomList(List<UnitOfMeasureDto> minUomList) {
        this.minUomList = minUomList;
    }

    public List<UnitOfMeasureDto> getWarehouseUomList() {
        return warehouseUomList;
    }

    public void setWarehouseUomList(List<UnitOfMeasureDto> warehouseUomList) {
        this.warehouseUomList = warehouseUomList;
    }
}
