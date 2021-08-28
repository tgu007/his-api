package lukelin.his.dto.basic.req.entity;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.enums.Basic.MedicineChargeLevel;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MedicineSaveDto extends InventoryEntitySaveDto {
    private Integer typeId;

    private Integer storageTypeId;

    private Integer functionTypeId;

    private Integer levelId;

    private Integer attributeId;

    private Integer doseTypeId;

    private boolean canSplit;

    private boolean skinTest;

    private boolean needPrescription;

    private boolean selfPay;

    private UUID serveSizeUomId;

    private BigDecimal serveToMinRate;

    private Boolean ffSign;

    private UUID centerMedicineId;

    public Boolean getFfSign() {
        return ffSign;
    }

    public void setFfSign(Boolean ffSign) {
        this.ffSign = ffSign;
    }

    public boolean isSelfPay() {
        return selfPay;
    }

    public void setSelfPay(boolean selfPay) {
        this.selfPay = selfPay;
    }

    public UUID getCenterMedicineId() {
        return centerMedicineId;
    }

    public void setCenterMedicineId(UUID centerMedicineId) {
        this.centerMedicineId = centerMedicineId;
    }

    public BigDecimal getServeToMinRate() {
        return serveToMinRate;
    }

    public void setServeToMinRate(BigDecimal serveToMinRate) {
        this.serveToMinRate = serveToMinRate;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStorageTypeId() {
        return storageTypeId;
    }

    public void setStorageTypeId(Integer storageTypeId) {
        this.storageTypeId = storageTypeId;
    }

    public Integer getFunctionTypeId() {
        return functionTypeId;
    }

    public void setFunctionTypeId(Integer functionTypeId) {
        this.functionTypeId = functionTypeId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public Integer getDoseTypeId() {
        return doseTypeId;
    }

    public void setDoseTypeId(Integer doseTypeId) {
        this.doseTypeId = doseTypeId;
    }

    public boolean isCanSplit() {
        return canSplit;
    }

    public void setCanSplit(boolean canSplit) {
        this.canSplit = canSplit;
    }

    public boolean isSkinTest() {
        return skinTest;
    }

    public void setSkinTest(boolean skinTest) {
        this.skinTest = skinTest;
    }

    public boolean isNeedPrescription() {
        return needPrescription;
    }

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    public UUID getServeSizeUomId() {
        return serveSizeUomId;
    }

    public void setServeSizeUomId(UUID serveSizeUomId) {
        this.serveSizeUomId = serveSizeUomId;
    }

    public void copyPropertiesToEntity(Medicine medicine) {
        BeanUtils.copyPropertiesIgnoreNull(this, medicine);
        medicine.setType(Ebean.find(Dictionary.class, this.getTypeId()));
        if (this.getFunctionTypeId() != null)
            medicine.setFunctionType(Ebean.find(Dictionary.class, this.getFunctionTypeId()));
        if (this.getStorageTypeId() != null)
            medicine.setStorageType(Ebean.find(Dictionary.class, this.getStorageTypeId()));
        if (this.getLevelId() != null)
            medicine.setLevel(Ebean.find(Dictionary.class, this.getLevelId()));
        if (this.getAttributeId() != null)
            medicine.setAttribute(Ebean.find(Dictionary.class, this.getAttributeId()));
        if (this.getDoseTypeId() != null)
            medicine.setDoseType(Ebean.find(Dictionary.class, this.getDoseTypeId()));
        medicine.setServeUom(Ebean.find(UnitOfMeasure.class, this.getServeSizeUomId()));

        medicine.setWarehouseUom(Ebean.find(UnitOfMeasure.class, this.getWarehouseUomId()));
        medicine.setWarehouseToMinRate(this.getWarehouseToMinRate());
        medicine.setDepartmentUom(Ebean.find(UnitOfMeasure.class, this.getDepartmentUomId()));
        medicine.setDepartmentToMinRate(this.getDepartmentToMinRate());
        if (this.getSearchCode() != null)
            medicine.setSearchCode(this.getSearchCode().toUpperCase());
        medicine.setFeeType(Ebean.find(Dictionary.class, this.getFeeTypeId()));
        medicine.setMinUom(Ebean.find(UnitOfMeasure.class, this.getMinSizeUomId()));
    }
}
