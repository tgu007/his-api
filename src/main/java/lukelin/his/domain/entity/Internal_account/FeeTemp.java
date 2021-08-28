package lukelin.his.domain.entity.Internal_account;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.internal_account.temp.FeeRespTempDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "internal_account.fee_temp")
public class FeeTemp extends BaseEntity implements DtoConvertible<FeeRespTempDto> {
    public FeeTemp() {
    }

    public FeeTemp(UUID uuid) {
        this.setUuid(uuid);
    }

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ChargeableItem item;

    @ManyToOne
    @JoinColumn(name = "auto_fee_id")
    private AutoFeeTemp autoFeeTemp;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "patient_info")
    private String patientInfo;

    @Column(name = "status", nullable = false)
    private FeeStatus feeStatus;

    @Column(name = "unit_amount", nullable = false)
    private BigDecimal unitAmount;

    @Column(name = "sign_in_number")
    private String singInNumber;

    @Column(name = "fee_date", nullable = false)
    private Date feeDate;

    public AutoFeeTemp getAutoFeeTemp() {
        return autoFeeTemp;
    }

    public void setAutoFeeTemp(AutoFeeTemp autoFeeTemp) {
        this.autoFeeTemp = autoFeeTemp;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public String getSingInNumber() {
        return singInNumber;
    }

    public void setSingInNumber(String singInNumber) {
        this.singInNumber = singInNumber;
    }

    public AutoFeeTemp getAutoFee() {
        return autoFeeTemp;
    }

    public void setAutoFee(AutoFeeTemp autoFeeTemp) {
        this.autoFeeTemp = autoFeeTemp;
    }

    public ChargeableItem getItem() {
        return item;
    }

    public void setItem(ChargeableItem item) {
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

    @Override
    public FeeRespTempDto toDto() {
        FeeRespTempDto responseDto = DtoUtils.convertRawDto(this);
        responseDto.setItem(this.getItem().toDto());
        responseDto.setCreatedBy(this.getWhoCreatedName());
        responseDto.setTotalAmount(this.getUnitAmount().multiply(new BigDecimal(this.getQuantity())).setScale(2, RoundingMode.HALF_UP));
        return responseDto;
    }

    public BigDecimal getTotalAmount() {
        return this.getUnitAmount().multiply(new BigDecimal(this.quantity)).setScale(2,RoundingMode.HALF_UP);
    }
}
