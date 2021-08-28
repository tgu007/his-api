package lukelin.his.domain.entity.Internal_account;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.internal_account.AutoFeeRespDto;
import lukelin.his.dto.internal_account.temp.AutoFeeTempRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "internal_account.auto_fee")
public class AutoFee extends BaseEntity implements DtoConvertible<AutoFeeRespDto> {
    public AutoFee() {
    }

    public AutoFee(UUID uuid) {
        this.setUuid(uuid);
    }

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ChargeableItem item;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    public ChargeableItem getItem() {
        return item;
    }

    public void setItem(ChargeableItem item) {
        this.item = item;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    @Override
    public AutoFeeRespDto toDto() {
        AutoFeeRespDto responseDto = DtoUtils.convertRawDto(this);
        responseDto.setItem(this.getItem().toDto());
        responseDto.setPatient(this.getPatientSignIn().toDto());
        responseDto.setCreatedBy(this.getWhoCreatedName());
        return responseDto;
    }

    public Fee toFee() {
        Fee newFee = new Fee();
        newFee.setFeeStatus(FeeStatus.confirmed);
        newFee.setUnitAmount(this.getItem().getListPrice());
        newFee.setAutoFee(this);
        newFee.setItem(this.getItem());
        newFee.setPatientSignIn(this.getPatientSignIn());
        newFee.setQuantity(this.getQuantity());
        newFee.setFeeDate(new Date());
        return newFee;
    }
}
