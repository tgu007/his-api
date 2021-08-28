package lukelin.his.domain.entity.basic.entity;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.yb.CenterMedicine;
import lukelin.his.domain.enums.Basic.MedicineChargeLevel;
import lukelin.his.dto.basic.resp.entity.MedicineRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.math.RoundingMode;

@MappedSuperclass
public class BaseMedicine extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Dictionary type;

    @ManyToOne
    @JoinColumn(name = "type_function_id")
    private Dictionary functionType;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Dictionary level;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Dictionary attribute;

    @ManyToOne
    @JoinColumn(name = "dose_type_id")
    private Dictionary doseType;

    @ManyToOne
    @JoinColumn(name = "storage_type_id")
    private Dictionary storageType;

    @Column(name = "can_split")
    private boolean canSplit;

    @Column(name = "skin_test")
    private boolean skinTest;

    @Column(name = "need_prescription")
    private boolean needPrescription;


    @Column(name = "serve_conversion_rate")
    private BigDecimal serveToMinRate;

    @ManyToOne
    @JoinColumn(name = "serve_uom_id", nullable = false)
    private UnitOfMeasure serveUom;

    @Column(nullable = false)
    private String name;

    private boolean enabled;

    @Column(name = "search_code", nullable = false, length = 50)
    private String searchCode;

    @ManyToOne
    @JoinColumn(name = "min_uom_id", nullable = false)
    private UnitOfMeasure minUom;

    @ManyToOne
    @JoinColumn(name = "fee_type_id")
    private Dictionary feeType;

    @Column(name = "list_price")
    private BigDecimal listPrice = BigDecimal.ZERO;

    @Column(name = "warehouse_conversion_rate")
    private BigDecimal warehouseToMinRate;

    @Column(name = "department_conversion_rate")
    private BigDecimal departmentToMinRate;

    @Column(name = "warehouse_model")
    private String warehouseModel;

    @Column(name = "department_model")
    private String departmentModel;

    @ManyToOne
    @JoinColumn(name = "warehouse_uom_id", nullable = false)
    private UnitOfMeasure warehouseUom;

    @ManyToOne
    @JoinColumn(name = "department_uom_id", nullable = false)
    private UnitOfMeasure departmentUom;

    @ManyToOne
    @JoinColumn(name = "manufacture_id", nullable = false)
    private ManufacturerMedicine manufacturerMedicine;

    @ManyToOne
    @JoinColumn(name = "yb_center_id", nullable = false)
    private CenterMedicine centerMedicine;

    @Column(name = "self_pay")
    private Boolean selfPay;


    public Boolean getSlipTypeOne() {
        if (this.getAttribute() != null) {
            if (this.getAttribute().getName().equals("一类精神") || this.getAttribute().getName().equals("麻醉"))
                return true;
        }
        return false;
    }

    public Boolean getSlipTypeTwo() {
        if (this.getAttribute() != null) {
            if (this.getAttribute().getName().equals("二类精神"))
                return true;
        }
        return false;
    }

    public Boolean chineseMedicine() {
        return this.getType().getName().equals("中草药");
    }

    public ManufacturerMedicine getManufacturerMedicine() {
        return manufacturerMedicine;
    }

    public void setManufacturerMedicine(ManufacturerMedicine manufacturerMedicine) {
        this.manufacturerMedicine = manufacturerMedicine;
    }

    public CenterMedicine getCenterMedicine() {
        return centerMedicine;
    }

    public void setCenterMedicine(CenterMedicine centerMedicine) {
        this.centerMedicine = centerMedicine;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public Dictionary getType() {
        return type;
    }

    public void setType(Dictionary type) {
        this.type = type;
    }

    public Dictionary getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Dictionary functionType) {
        this.functionType = functionType;
    }

    public Dictionary getLevel() {
        return level;
    }

    public void setLevel(Dictionary level) {
        this.level = level;
    }

    public Dictionary getAttribute() {
        return attribute;
    }

    public void setAttribute(Dictionary attribute) {
        this.attribute = attribute;
    }

    public Dictionary getDoseType() {
        return doseType;
    }

    public void setDoseType(Dictionary doseType) {
        this.doseType = doseType;
    }

    public Dictionary getStorageType() {
        return storageType;
    }

    public void setStorageType(Dictionary storageType) {
        this.storageType = storageType;
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

    public UnitOfMeasure getServeUom() {
        return serveUom;
    }

    public void setServeUom(UnitOfMeasure serveUom) {
        this.serveUom = serveUom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public UnitOfMeasure getMinUom() {
        return minUom;
    }

    public void setMinUom(UnitOfMeasure minUom) {
        this.minUom = minUom;
    }

    public Dictionary getFeeType() {
        return feeType;
    }

    public void setFeeType(Dictionary feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
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

    public UnitOfMeasure getWarehouseUom() {
        return warehouseUom;
    }

    public void setWarehouseUom(UnitOfMeasure warehouseUom) {
        this.warehouseUom = warehouseUom;
    }

    public UnitOfMeasure getDepartmentUom() {
        return departmentUom;
    }

    public void setDepartmentUom(UnitOfMeasure departmentUom) {
        this.departmentUom = departmentUom;
    }

    protected void copyDtoProperty(MedicineRespDto responseDto) {
        responseDto.setMinSizeUom(this.getMinUom().toDto());
        responseDto.setServeSizeUom(this.getServeUom().toDto());
        responseDto.setDepartmentUom(this.getDepartmentUom().toDto());
        responseDto.setWarehouseUom(this.getWarehouseUom().toDto());
        responseDto.setDepartmentModel(this.getDepartmentModel());
        responseDto.setWarehouseModel(this.getWarehouseModel());
        responseDto.setTypeDto(this.getType().toDto());
        responseDto.setFeeType(this.getFeeType().toDto());
        responseDto.setServeToMinRate(this.getServeToMinRate());
        responseDto.setDepartmentConversionRate(this.getDepartmentToMinRate());
        responseDto.setWarehouseConversionRate(this.getWarehouseToMinRate());
        responseDto.setListPriceMinUom(this.listPrice.divide(this.departmentToMinRate, 4, RoundingMode.HALF_UP));

        if (this.getStorageType() != null)
            responseDto.setStorageType(this.getStorageType().toDto());
        if (this.getFunctionType() != null)
            responseDto.setFunctionType(this.getFunctionType().toDto());
        if (this.getLevel() != null)
            responseDto.setLevel(this.getLevel().toDto());
        if (this.getAttribute() != null)
            responseDto.setAttribute(this.getAttribute().toDto());
        if (this.getDoseType() != null)
            responseDto.setDoseType(this.getDoseType().toDto());
    }


}
