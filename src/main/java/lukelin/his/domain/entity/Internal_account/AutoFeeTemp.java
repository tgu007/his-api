package lukelin.his.domain.entity.Internal_account;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.internal_account.temp.AutoFeeTempRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "internal_account.auto_fee_temp")
public class AutoFeeTemp extends BaseEntity implements DtoConvertible<AutoFeeTempRespDto> {
    public AutoFeeTemp() {
    }

    public AutoFeeTemp(UUID uuid) {
        this.setUuid(uuid);
    }

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ChargeableItem item;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "patient_info")
    private String patientInfo;


    @Column(name = "sign_in_number")
    private String singInNumber;

    public String getSingInNumber() {
        return singInNumber;
    }

    public void setSingInNumber(String singInNumber) {
        this.singInNumber = singInNumber;
    }

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

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
    }

    @Override
    public AutoFeeTempRespDto toDto() {
        AutoFeeTempRespDto responseDto = DtoUtils.convertRawDto(this);
        responseDto.setItem(this.getItem().toDto());
        responseDto.setCreatedBy(this.getWhoCreatedName());
        return responseDto;
    }

    public FeeTemp toFee() {
        FeeTemp newFeeTemp = new FeeTemp();
        newFeeTemp.setFeeStatus(FeeStatus.confirmed);
        newFeeTemp.setUnitAmount(this.getItem().getListPrice());
        newFeeTemp.setAutoFee(this);
        newFeeTemp.setItem(this.getItem());
        newFeeTemp.setPatientInfo(this.getPatientInfo());
        newFeeTemp.setSingInNumber(this.getSingInNumber());
        newFeeTemp.setQuantity(this.getQuantity());
        newFeeTemp.setFeeDate(new Date());
        return newFeeTemp;
    }
}
