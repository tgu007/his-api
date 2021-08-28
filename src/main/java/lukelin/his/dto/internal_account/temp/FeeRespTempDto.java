package lukelin.his.dto.internal_account.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.internal_account.ChargeableItemRespDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class FeeRespTempDto {
    private UUID uuid;

    private ChargeableItemRespDto item;

    private Integer quantity;

    private String patientInfo;

    private FeeStatus feeStatus;

    private BigDecimal unitAmount;

    private BigDecimal totalAmount;

    private String singInNumber;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date feeDate;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ChargeableItemRespDto getItem() {
        return item;
    }

    public void setItem(ChargeableItemRespDto item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getSingInNumber() {
        return singInNumber;
    }

    public void setSingInNumber(String singInNumber) {
        this.singInNumber = singInNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }
}
