package lukelin.his.domain.entity.basic.entity;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerItem;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.yb.CenterMedicine;
import lukelin.his.domain.entity.yb.CenterTreatment;
import lukelin.his.dto.basic.resp.entity.ItemRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BaseItem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "warehouse_type_id")
    private Dictionary warehouseType;

    @ManyToOne
    @JoinColumn(name = "storage_type_id")
    private Dictionary storageType;

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
    private ManufacturerItem manufacturerItem;

    @ManyToOne
    @JoinColumn(name = "yb_center_id")
    private CenterTreatment centerTreatment;

    @Column(name = "self_pay")
    private Boolean selfPay;

    public CenterTreatment getCenterTreatment() {
        return centerTreatment;
    }

    public void setCenterTreatment(CenterTreatment centerTreatment) {
        this.centerTreatment = centerTreatment;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public ManufacturerItem getManufacturerItem() {
        return manufacturerItem;
    }

    public void setManufacturerItem(ManufacturerItem manufacturerItem) {
        this.manufacturerItem = manufacturerItem;
    }

    public Dictionary getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(Dictionary warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Dictionary getStorageType() {
        return storageType;
    }

    public void setStorageType(Dictionary storageType) {
        this.storageType = storageType;
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

    protected ItemRespDto copyDtoProperty(ItemRespDto responseDto) {
        responseDto.setWarehouseType(this.getWarehouseType().toDto());
        responseDto.setFeeType(this.getFeeType().toDto());
        if (this.getStorageType() != null)
            responseDto.setStorageType(this.getStorageType().toDto());

        responseDto.setMinSizeUom(this.getMinUom().toDto());

        if (this.getWarehouseUom() != null)
            responseDto.setWarehouseUom(this.getWarehouseUom().toDto());

        if (this.getDepartmentUom() != null)
            responseDto.setDepartmentUom(this.getDepartmentUom().toDto());

        responseDto.setDepartmentConversionRate(this.getDepartmentToMinRate());
        responseDto.setWarehouseConversionRate(this.getWarehouseToMinRate());
        responseDto.setListPriceMinUom(this.getListPrice());
        return responseDto;
    }
}
