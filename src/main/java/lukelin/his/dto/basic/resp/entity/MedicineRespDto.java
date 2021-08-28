package lukelin.his.dto.basic.resp.entity;

import lukelin.his.domain.enums.YB.YBMatchStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.ManufacturerMedicineRespDto;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;
import lukelin.his.dto.yb.inventory.resp.CenterMedicineRespDto;

import java.math.BigDecimal;

public class MedicineRespDto extends BaseInventoryEntityRespDto {
    private boolean canSplit;

    private boolean skinTest;

    private boolean needPrescription;

    private BigDecimal serveToMinRate;

    private DictionaryDto typeDto;

    private DictionaryDto functionType;

    private DictionaryDto level;

    private DictionaryDto attribute;

    private DictionaryDto doseType;

    private DictionaryDto storageType;

    private UnitOfMeasureDto serveSizeUom;

    private ManufacturerMedicineRespDto manufacturer;

    private Boolean selfPay;

    private CenterMedicineRespDto centerMedicine;

    private YBUploadStatus ybUploadStatus;

    private String ybUploadError;

    private YBMatchStatus ybMatchStatus;

    private String ybMatchError;

    private Boolean ffSign;

    private Boolean prescriptionRequired;

    public Boolean getPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public Boolean getFfSign() {
        return ffSign;
    }

    public void setFfSign(Boolean ffSign) {
        this.ffSign = ffSign;
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

    public String getYbUploadError() {
        return ybUploadError;
    }

    public void setYbUploadError(String ybUploadError) {
        this.ybUploadError = ybUploadError;
    }

    public YBUploadStatus getYbUploadStatus() {
        return ybUploadStatus;
    }

    public void setYbUploadStatus(YBUploadStatus ybUploadStatus) {
        this.ybUploadStatus = ybUploadStatus;
    }

    public ManufacturerMedicineRespDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerMedicineRespDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public CenterMedicineRespDto getCenterMedicine() {
        return centerMedicine;
    }

    public void setCenterMedicine(CenterMedicineRespDto centerMedicine) {
        this.centerMedicine = centerMedicine;
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

    public BigDecimal getServeToMinRate() {
        return serveToMinRate;
    }

    public void setServeToMinRate(BigDecimal serveToMinRate) {
        this.serveToMinRate = serveToMinRate;
    }

    public DictionaryDto getTypeDto() {
        return typeDto;
    }

    public void setTypeDto(DictionaryDto typeDto) {
        this.typeDto = typeDto;
    }

    public DictionaryDto getFunctionType() {
        return functionType;
    }

    public void setFunctionType(DictionaryDto functionType) {
        this.functionType = functionType;
    }

    public DictionaryDto getLevel() {
        return level;
    }

    public void setLevel(DictionaryDto level) {
        this.level = level;
    }

    public DictionaryDto getAttribute() {
        return attribute;
    }

    public void setAttribute(DictionaryDto attribute) {
        this.attribute = attribute;
    }

    public DictionaryDto getDoseType() {
        return doseType;
    }

    public void setDoseType(DictionaryDto doseType) {
        this.doseType = doseType;
    }

    public DictionaryDto getStorageType() {
        return storageType;
    }

    public void setStorageType(DictionaryDto storageType) {
        this.storageType = storageType;
    }

    public UnitOfMeasureDto getServeSizeUom() {
        return serveSizeUom;
    }

    public void setServeSizeUom(UnitOfMeasureDto serveSizeUom) {
        this.serveSizeUom = serveSizeUom;
    }
}
