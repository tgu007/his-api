package lukelin.his.dto.internal_account.temp;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.Internal_account.AutoFeeTemp;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;

import java.util.UUID;

public class AutoFeeTempSaveDto {
    private UUID uuid;

    private UUID itemId;

    private Boolean enabled;

    private Integer quantity;

    private String patientInfo;

    private String singInNumber;

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

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
    }

    public String getSingInNumber() {
        return singInNumber;
    }

    public void setSingInNumber(String singInNumber) {
        this.singInNumber = singInNumber;
    }

    public AutoFeeTemp toEntity() {
        AutoFeeTemp autoFeeTemp;
        if (this.getUuid() != null)
            autoFeeTemp = Ebean.find(AutoFeeTemp.class, this.getUuid());
        else
            autoFeeTemp = new AutoFeeTemp();
        BeanUtils.copyPropertiesIgnoreNull(this, autoFeeTemp);
        ChargeableItem item = Ebean.find(ChargeableItem.class, this.getItemId());
        autoFeeTemp.setItem(item);
        return autoFeeTemp;
    }
}
