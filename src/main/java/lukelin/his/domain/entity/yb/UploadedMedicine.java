package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "yb.medicine_uploaded")
public class UploadedMedicine extends BaseEntity {
    @Column(name = "server_Id")
    private String serverId;

    @Column(name = "hospital_Id")
    private String hospitalId;

    //规格名称
    private String model;

    //商品名称
    private String name;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "friendly_code")
    private String friendlyCode;

    @Column(name = "other_code")
    private String otherCode;

    @Column(name = "scan_code")
    private String scanCode;

    @Column(name = "serve_uom")
    private String serveUom;

    @Column(name = "common_serve_volume")
    private String commonServeVolume;

    @Column(name = "common_frequency")
    private String commonFrequency;

    @Column(name = "common_use_method")
    private String commonUseMethod;

    @Column(name = "department_uom")
    private String departmentUom;

    @Column(name = "list_price")
    private BigDecimal listPrice;

    //OTC
    private String OTC;

    @Column(name = "does_type")
    private String doesType;

    @Column(name = "ff_sign")
    private String ffSign;

    //开票项目 01：西药费 02：中成药 03：中草药 17 材料 99 未知
    @Column(name = "fee_type")
    private String feeType;

    //生产商编号
    @Column(name = "manufacture_Id")
    private String manufactureId;

    //产地
    private String manufacture;

    //物资类别
    //0 药品，1 材料 2 器械，3 保健品
    @Column(name = "entity_type")
    private String entityType;

    //药品分类
    @Column(name = "medicine_type")
    private String medicineType;

    //优惠方式
    @Column(name = "discount_type")
    private String discountType;

    //是否基药
    @Column(name = "basic_medicine")
    private Boolean basicMedicine;

    //是否支持库存 拆零
    @Column(name = "can_split")
    private Boolean canSplit;

    //拆零单位
    @Column(name = "split_uom")
    private String splitUom;

    //拆零比例
    @Column(name = "split_conversion_rate")
    private BigDecimal splitConversionRate;

    //拆零单价
    @Column(name = "split_price")
    private BigDecimal splitPrice;

    //物资包装
    @Column(name = "warehouse_uom")
    private String warehouseUom;

    //注册商标
    @Column(name = "trade_mark")
    private String tradeMark;

    //批准文号
    @Column(name = "batch_number")
    private String batchNumber;

    //用药天数
    @Column(name = "use_period")
    private String usePeriod;

    //注销标志
    @Column(name = "cancel_sign")
    private Boolean cancelSign;

    //ybmc
    @Column(name = "center_name")
    private String centerName;

    //bzbm
    @Column(name = "code_one")
    private String codeOne;

    @Column(name = "pay_ratio")
    private BigDecimal payRatio;

    @Column(name = "code_two")
    private String codeTwo;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getFriendlyCode() {
        return friendlyCode;
    }

    public void setFriendlyCode(String friendlyCode) {
        this.friendlyCode = friendlyCode;
    }

    public String getOtherCode() {
        return otherCode;
    }

    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public String getServeUom() {
        return serveUom;
    }

    public void setServeUom(String serveUom) {
        this.serveUom = serveUom;
    }

    public String getCommonServeVolume() {
        return commonServeVolume;
    }

    public void setCommonServeVolume(String commonServeVolume) {
        this.commonServeVolume = commonServeVolume;
    }

    public String getCommonFrequency() {
        return commonFrequency;
    }

    public void setCommonFrequency(String commonFrequency) {
        this.commonFrequency = commonFrequency;
    }

    public String getCommonUseMethod() {
        return commonUseMethod;
    }

    public void setCommonUseMethod(String commonUseMethod) {
        this.commonUseMethod = commonUseMethod;
    }

    public String getDepartmentUom() {
        return departmentUom;
    }

    public void setDepartmentUom(String departmentUom) {
        this.departmentUom = departmentUom;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public String getOTC() {
        return OTC;
    }

    public void setOTC(String OTC) {
        this.OTC = OTC;
    }

    public String getDoesType() {
        return doesType;
    }

    public void setDoesType(String doesType) {
        this.doesType = doesType;
    }

    public String getFfSign() {
        return ffSign;
    }

    public void setFfSign(String ffSign) {
        this.ffSign = ffSign;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(String manufactureId) {
        this.manufactureId = manufactureId;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Boolean getBasicMedicine() {
        return basicMedicine;
    }

    public void setBasicMedicine(Boolean basicMedicine) {
        this.basicMedicine = basicMedicine;
    }

    public Boolean getCanSplit() {
        return canSplit;
    }

    public void setCanSplit(Boolean canSplit) {
        this.canSplit = canSplit;
    }

    public String getSplitUom() {
        return splitUom;
    }

    public void setSplitUom(String splitUom) {
        this.splitUom = splitUom;
    }

    public BigDecimal getSplitConversionRate() {
        return splitConversionRate;
    }

    public void setSplitConversionRate(BigDecimal splitConversionRate) {
        this.splitConversionRate = splitConversionRate;
    }

    public BigDecimal getSplitPrice() {
        return splitPrice;
    }

    public void setSplitPrice(BigDecimal splitPrice) {
        this.splitPrice = splitPrice;
    }

    public String getWarehouseUom() {
        return warehouseUom;
    }

    public void setWarehouseUom(String warehouseUom) {
        this.warehouseUom = warehouseUom;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getUsePeriod() {
        return usePeriod;
    }

    public void setUsePeriod(String usePeriod) {
        this.usePeriod = usePeriod;
    }

    public Boolean getCancelSign() {
        return cancelSign;
    }

    public void setCancelSign(Boolean cancelSign) {
        this.cancelSign = cancelSign;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCodeOne() {
        return codeOne;
    }

    public void setCodeOne(String codeOne) {
        this.codeOne = codeOne;
    }

    public BigDecimal getPayRatio() {
        return payRatio;
    }

    public void setPayRatio(BigDecimal payRatio) {
        this.payRatio = payRatio;
    }

    public String getCodeTwo() {
        return codeTwo;
    }

    public void setCodeTwo(String codeTwo) {
        this.codeTwo = codeTwo;
    }
}
