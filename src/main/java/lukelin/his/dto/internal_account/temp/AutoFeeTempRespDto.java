package lukelin.his.dto.internal_account.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.dto.internal_account.ChargeableItemRespDto;

import java.util.Date;
import java.util.UUID;

public class AutoFeeTempRespDto {
    private UUID uuid;

    private ChargeableItemRespDto item;

    private Boolean enabled;

    private Integer quantity;

    private String patientInfo;

    private String singInNumber;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date whenCreated;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
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
}
