package lukelin.his.dto.basic.resp.entity;

import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.util.List;

public class MedicineDetailPramDto {
    private List<DictionaryDto> typeDtoList;

    private List<DictionaryDto> functionTypeDtoList;

    private List<DictionaryDto> storageTypeDtoList;

    private List<DictionaryDto> levelDtoList;

    private List<DictionaryDto> attributeDtoList;

    private List<DictionaryDto> formDtoList;

    private List<UnitOfMeasureDto> pharmacyUomDtoList;

    private List<UnitOfMeasureDto> minUomDtoList;

    private List<UnitOfMeasureDto> serveUomDtoList;

    private List<UnitOfMeasureDto> warehouseUomDtoList;

    private List<DictionaryDto> feeTypeList;

    public List<UnitOfMeasureDto> getMinUomDtoList() {
        return minUomDtoList;
    }

    public void setMinUomDtoList(List<UnitOfMeasureDto> minUomDtoList) {
        this.minUomDtoList = minUomDtoList;
    }

    public List<DictionaryDto> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<DictionaryDto> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }

    public List<DictionaryDto> getTypeDtoList() {
        return typeDtoList;
    }

    public void setTypeDtoList(List<DictionaryDto> typeDtoList) {
        this.typeDtoList = typeDtoList;
    }

    public List<DictionaryDto> getFunctionTypeDtoList() {
        return functionTypeDtoList;
    }

    public void setFunctionTypeDtoList(List<DictionaryDto> functionTypeDtoList) {
        this.functionTypeDtoList = functionTypeDtoList;
    }

    public List<DictionaryDto> getStorageTypeDtoList() {
        return storageTypeDtoList;
    }

    public void setStorageTypeDtoList(List<DictionaryDto> storageTypeDtoList) {
        this.storageTypeDtoList = storageTypeDtoList;
    }

    public List<DictionaryDto> getLevelDtoList() {
        return levelDtoList;
    }

    public void setLevelDtoList(List<DictionaryDto> levelDtoList) {
        this.levelDtoList = levelDtoList;
    }

    public List<DictionaryDto> getAttributeDtoList() {
        return attributeDtoList;
    }

    public void setAttributeDtoList(List<DictionaryDto> attributeDtoList) {
        this.attributeDtoList = attributeDtoList;
    }

    public List<DictionaryDto> getFormDtoList() {
        return formDtoList;
    }

    public void setFormDtoList(List<DictionaryDto> formDtoList) {
        this.formDtoList = formDtoList;
    }

    public List<UnitOfMeasureDto> getPharmacyUomDtoList() {
        return pharmacyUomDtoList;
    }

    public void setPharmacyUomDtoList(List<UnitOfMeasureDto> pharmacyUomDtoList) {
        this.pharmacyUomDtoList = pharmacyUomDtoList;
    }

    public List<UnitOfMeasureDto> getServeUomDtoList() {
        return serveUomDtoList;
    }

    public void setServeUomDtoList(List<UnitOfMeasureDto> serveUomDtoList) {
        this.serveUomDtoList = serveUomDtoList;
    }

    public List<UnitOfMeasureDto> getWarehouseUomDtoList() {
        return warehouseUomDtoList;
    }

    public void setWarehouseUomDtoList(List<UnitOfMeasureDto> warehouseUomDtoList) {
        this.warehouseUomDtoList = warehouseUomDtoList;
    }
}
