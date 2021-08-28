package lukelin.his.dto.internal_account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.signin.response.PatientSignInRespDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class FeeRespDto {
    private UUID uuid;

    private ChargeableItemRespDto item;

    private Integer quantity;

    private PatientSignInRespDto patient;

    private FeeStatus feeStatus;

    private BigDecimal unitAmount;

    private BigDecimal totalAmount;

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

    public PatientSignInRespDto getPatient() {
        return patient;
    }

    public void setPatient(PatientSignInRespDto patient) {
        this.patient = patient;
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
