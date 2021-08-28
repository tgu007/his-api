package lukelin.his.dto.account.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.EntityType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class AutoFeeListDto {
    private UUID uuid;

    private BigDecimal quantity;

    private String quantityInfo;

    private String unitAmountInfo;

    private UUID patientSignInId;

    private String description;

    private String prescriptionDescription;

    private UUID feeEntityId;

    private EntityType entityType;

    private boolean enabled;

    private String frequencyName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date feeDate;

    private String departmentModel;

    private String manufacturer;

    private BigDecimal unitAmount;

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getDepartmentModel() {
        return departmentModel;
    }

    public void setDepartmentModel(String departmentModel) {
        this.departmentModel = departmentModel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public String getQuantityInfo() {
        return quantityInfo;
    }

    public void setQuantityInfo(String quantityInfo) {
        this.quantityInfo = quantityInfo;
    }

    public String getUnitAmountInfo() {
        return unitAmountInfo;
    }

    public void setUnitAmountInfo(String unitAmountInfo) {
        this.unitAmountInfo = unitAmountInfo;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrescriptionDescription() {
        return prescriptionDescription;
    }

    public void setPrescriptionDescription(String prescriptionDescription) {
        this.prescriptionDescription = prescriptionDescription;
    }

    public UUID getFeeEntityId() {
        return feeEntityId;
    }

    public void setFeeEntityId(UUID feeEntityId) {
        this.feeEntityId = feeEntityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }
}
