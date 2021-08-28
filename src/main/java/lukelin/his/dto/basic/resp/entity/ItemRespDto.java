package lukelin.his.dto.basic.resp.entity;

import lukelin.his.domain.enums.YB.YBMatchStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.ManufacturerItemRespDto;
import lukelin.his.dto.yb.resp.CenterTreatmentRespDto;

import java.math.BigDecimal;

public class ItemRespDto extends BaseInventoryEntityRespDto {

    private DictionaryDto warehouseType;

    private DictionaryDto storageType;

    private ManufacturerItemRespDto manufacturer;

    private Boolean selfPay;

    private CenterTreatmentRespDto centerTreatment;

    private YBUploadStatus ybUploadStatus;

    private String ybUploadError;

    private YBMatchStatus ybMatchStatus;

    private String ybMatchError;

    private Boolean allowAutoFee;

    private Boolean prescriptionRequired;

    public Boolean getAllowAutoFee() {
        return allowAutoFee;
    }

    public void setAllowAutoFee(Boolean allowAutoFee) {
        this.allowAutoFee = allowAutoFee;
    }

    public Boolean getPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public YBMatchStatus getYbMatchStatus() {
        return ybMatchStatus;
    }

    public void setYbMatchStatus(YBMatchStatus ybMatchStatus) {
        this.ybMatchStatus = ybMatchStatus;
    }

    public String getYbMatchError() {
        return ybMatchError;
    }

    public void setYbMatchError(String ybMatchError) {
        this.ybMatchError = ybMatchError;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public CenterTreatmentRespDto getCenterTreatment() {
        return centerTreatment;
    }

    public void setCenterTreatment(CenterTreatmentRespDto centerTreatment) {
        this.centerTreatment = centerTreatment;
    }

    public YBUploadStatus getYbUploadStatus() {
        return ybUploadStatus;
    }

    public void setYbUploadStatus(YBUploadStatus ybUploadStatus) {
        this.ybUploadStatus = ybUploadStatus;
    }

    public String getYbUploadError() {
        return ybUploadError;
    }

    public void setYbUploadError(String ybUploadError) {
        this.ybUploadError = ybUploadError;
    }

    public ManufacturerItemRespDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerItemRespDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public DictionaryDto getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(DictionaryDto warehouseType) {
        this.warehouseType = warehouseType;
    }

    public DictionaryDto getStorageType() {
        return storageType;
    }

    public void setStorageType(DictionaryDto storageType) {
        this.storageType = storageType;
    }
}

