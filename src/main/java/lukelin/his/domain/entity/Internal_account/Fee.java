package lukelin.his.domain.entity.Internal_account;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.internal_account.FeeRespDto;
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
@Table(name = "internal_account.fee")
public class Fee extends BaseEntity implements DtoConvertible<FeeRespDto> {
    public Fee() {
    }

    public Fee(UUID uuid) {
        this.setUuid(uuid);
    }

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ChargeableItem item;

    @ManyToOne
    @JoinColumn(name = "auto_fee_id")
    private AutoFee autoFee;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "status", nullable = false)
    private FeeStatus feeStatus;

    @Column(name = "unit_amount", nullable = false)
    private BigDecimal unitAmount;

    @Column(name = "fee_date", nullable = false)
    private Date feeDate;

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
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

    public AutoFee getAutoFee() {
        return autoFee;
    }

    public void setAutoFee(AutoFee autoFee) {
        this.autoFee = autoFee;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
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
    public FeeRespDto toDto() {
        FeeRespDto responseDto = DtoUtils.convertRawDto(this);
        responseDto.setItem(this.getItem().toDto());
        responseDto.setCreatedBy(this.getWhoCreatedName());
        responseDto.setPatient(this.getPatientSignIn().toDto());
        responseDto.setTotalAmount(this.getUnitAmount().multiply(new BigDecimal(this.getQuantity())).setScale(2, RoundingMode.HALF_UP));
        return responseDto;
    }

    public BigDecimal getTotalAmount() {
        return this.getUnitAmount().multiply(new BigDecimal(this.quantity)).setScale(2,RoundingMode.HALF_UP);
    }
}
