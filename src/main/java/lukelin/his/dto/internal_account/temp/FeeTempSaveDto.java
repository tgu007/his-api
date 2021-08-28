package lukelin.his.dto.internal_account.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;
import lukelin.his.domain.entity.Internal_account.FeeTemp;
import lukelin.his.domain.enums.Fee.FeeStatus;

import java.util.Date;
import java.util.UUID;

public class FeeTempSaveDto {
    public UUID uuid;

    private UUID itemId;

    private Integer quantity;

    private String patientInfo;

    private String singInNumber;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date feeDate;

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

    public FeeTemp toEntity() {
        FeeTemp feeTemp;
        if (this.getUuid() == null)
            feeTemp = new FeeTemp();
        else
            feeTemp = Ebean.find(FeeTemp.class, this.getUuid());
        BeanUtils.copyPropertiesIgnoreNull(this, feeTemp);
        ChargeableItem item = Ebean.find(ChargeableItem.class, this.getItemId());
        feeTemp.setItem(item);
        feeTemp.setFeeStatus(FeeStatus.confirmed);
        feeTemp.setUnitAmount(item.getListPrice());
        return feeTemp;
    }
}
