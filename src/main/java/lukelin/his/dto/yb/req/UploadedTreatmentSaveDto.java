package lukelin.his.dto.yb.req;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.yb.UploadedTreatment;

import java.math.BigDecimal;

public class UploadedTreatmentSaveDto {
    //服务代码
    private String code;

    //服务名称
    private String name;

    //单位
    private String unit;

    //单价
    private BigDecimal listPrice;

    //住院开票项目
    private String inHospitalName;

    //门诊开票项目
    private String clinicName;

    //变动医保代码
    private String changedInsuranceCode;

    //变动医保名称
    private String changedInsuranceName;

    //变动医保属性
    private String changedInsuranceAttribute;

    //变动医保比例
    private BigDecimal changedInsuranceRatio;

    //变动医保限价
    private BigDecimal changedInsurancePriceLimit;

    //修改人
    private String modifiedBy;

    //修改人 ip
    private String modifiedIp;

    // 修时间
    private String modifiedWhen;

    //医保上传状态
    private Boolean matchedWithCenter;

    //审批结果
    private String matchResult;

    private String centerName;

    private String centerCodeOne;

    private String centerCodeTwo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public String getInHospitalName() {
        return inHospitalName;
    }

    public void setInHospitalName(String inHospitalName) {
        this.inHospitalName = inHospitalName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getChangedInsuranceCode() {
        return changedInsuranceCode;
    }

    public void setChangedInsuranceCode(String changedInsuranceCode) {
        this.changedInsuranceCode = changedInsuranceCode;
    }

    public String getChangedInsuranceName() {
        return changedInsuranceName;
    }

    public void setChangedInsuranceName(String changedInsuranceName) {
        this.changedInsuranceName = changedInsuranceName;
    }

    public String getChangedInsuranceAttribute() {
        return changedInsuranceAttribute;
    }

    public void setChangedInsuranceAttribute(String changedInsuranceAttribute) {
        this.changedInsuranceAttribute = changedInsuranceAttribute;
    }

    public BigDecimal getChangedInsuranceRatio() {
        return changedInsuranceRatio;
    }

    public void setChangedInsuranceRatio(BigDecimal changedInsuranceRatio) {
        this.changedInsuranceRatio = changedInsuranceRatio;
    }

    public BigDecimal getChangedInsurancePriceLimit() {
        return changedInsurancePriceLimit;
    }

    public void setChangedInsurancePriceLimit(BigDecimal changedInsurancePriceLimit) {
        this.changedInsurancePriceLimit = changedInsurancePriceLimit;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedIp() {
        return modifiedIp;
    }

    public void setModifiedIp(String modifiedIp) {
        this.modifiedIp = modifiedIp;
    }

    public String getModifiedWhen() {
        return modifiedWhen;
    }

    public void setModifiedWhen(String modifiedWhen) {
        this.modifiedWhen = modifiedWhen;
    }

    public Boolean getMatchedWithCenter() {
        return matchedWithCenter;
    }

    public void setMatchedWithCenter(Boolean matchedWithCenter) {
        this.matchedWithCenter = matchedWithCenter;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterCodeOne() {
        return centerCodeOne;
    }

    public void setCenterCodeOne(String centerCodeOne) {
        this.centerCodeOne = centerCodeOne;
    }

    public String getCenterCodeTwo() {
        return centerCodeTwo;
    }

    public void setCenterCodeTwo(String centerCodeTwo) {
        this.centerCodeTwo = centerCodeTwo;
    }

    public UploadedTreatment toEntity() {
        UploadedTreatment treatment = new UploadedTreatment();
        BeanUtils.copyPropertiesIgnoreNull(this, treatment);
        return treatment;
    }
}
