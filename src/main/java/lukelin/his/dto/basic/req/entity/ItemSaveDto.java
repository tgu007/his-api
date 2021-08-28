package lukelin.his.dto.basic.req.entity;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.basic.entity.Item;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

public class ItemSaveDto extends InventoryEntitySaveDto {
    private Integer warehouseTypeId;

    private Integer storageTypeId;

    private UUID centerTreatmentId;

    private boolean selfPay;

    public boolean isSelfPay() {
        return selfPay;
    }

    public void setSelfPay(boolean selfPay) {
        this.selfPay = selfPay;
    }

    public UUID getCenterTreatmentId() {
        return centerTreatmentId;
    }

    public void setCenterTreatmentId(UUID centerTreatmentId) {
        this.centerTreatmentId = centerTreatmentId;
    }

    public Integer getWarehouseTypeId() {
        return warehouseTypeId;
    }

    public void setWarehouseTypeId(Integer warehouseTypeId) {
        this.warehouseTypeId = warehouseTypeId;
    }

    public Integer getStorageTypeId() {
        return storageTypeId;
    }

    public void setStorageTypeId(Integer storageTypeId) {
        this.storageTypeId = storageTypeId;
    }


    public void copyPropertiesToEntity(Item item) {
        //Todo DTO名字调整
        item.setName(this.getName());
        item.setSelfPay(this.isSelfPay());
        if (!StringUtils.isEmpty(this.getSearchCode()))
            item.setSearchCode(this.getSearchCode().toUpperCase());
        item.setWarehouseType(Ebean.find(Dictionary.class, this.getWarehouseTypeId()));
        item.setFeeType(new Dictionary(this.getFeeTypeId()));
        if (this.getStorageTypeId() != null)
            item.setStorageType(new Dictionary(this.getStorageTypeId()));
        item.setMinUom(Ebean.find(UnitOfMeasure.class, this.getMinSizeUomId()));
        item.setWarehouseUom(Ebean.find(UnitOfMeasure.class, this.getWarehouseUomId()));
        item.setDepartmentUom(Ebean.find(UnitOfMeasure.class, this.getDepartmentUomId()));
        item.setWarehouseModel(this.getWarehouseModel());
        item.setDepartmentModel(this.getDepartmentModel());
        item.setWarehouseToMinRate(this.getWarehouseToMinRate());
        item.setDepartmentToMinRate(this.getDepartmentToMinRate());
        item.setEnabled(this.isEnabled());
    }

}
