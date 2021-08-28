package lukelin.his.dto.internal_account;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.Internal_account.AutoFee;
import lukelin.his.domain.entity.Internal_account.AutoFeeTemp;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import java.util.UUID;

public class AutoFeeSaveDto {
    private UUID uuid;

    private UUID itemId;

    private UUID patientSignInId;

    private Boolean enabled;

    private Integer quantity;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
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

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public AutoFee toEntity() {
        AutoFee autoFee;
        if (this.getUuid() != null)
            autoFee = Ebean.find(AutoFee.class, this.getUuid());
        else
            autoFee = new AutoFee();
        BeanUtils.copyPropertiesIgnoreNull(this, autoFee);
        ChargeableItem item = Ebean.find(ChargeableItem.class, this.getItemId());
        autoFee.setItem(item);
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, this.getPatientSignInId());
        autoFee.setPatientSignIn(patientSignIn);
        return autoFee;
    }
}
