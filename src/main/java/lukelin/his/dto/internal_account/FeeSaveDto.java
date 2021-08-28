package lukelin.his.dto.internal_account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;
import lukelin.his.domain.entity.Internal_account.Fee;
import lukelin.his.domain.entity.Internal_account.FeeTemp;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Fee.FeeStatus;

import java.util.Date;
import java.util.UUID;

public class FeeSaveDto {
    private UUID itemId;

    private Integer quantity;

    private UUID patientSignInId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date feeDate;

    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public Fee toEntity() {
        Fee fee;
        if (this.getUuid() == null)
            fee = new Fee();
        else
            fee = Ebean.find(Fee.class, this.getUuid());
        BeanUtils.copyPropertiesIgnoreNull(this, fee);
        ChargeableItem item = Ebean.find(ChargeableItem.class, this.getItemId());
        fee.setItem(item);
        fee.setFeeStatus(FeeStatus.confirmed);
        fee.setUnitAmount(item.getListPrice());
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, this.getPatientSignInId());
        fee.setPatientSignIn(patientSignIn);
        return fee;
    }
}
