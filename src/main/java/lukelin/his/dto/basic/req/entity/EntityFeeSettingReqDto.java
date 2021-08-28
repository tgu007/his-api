package lukelin.his.dto.basic.req.entity;

import java.util.UUID;

public class EntityFeeSettingReqDto {
    private UUID uuid;

    private Boolean allowManualFee;

    private Boolean allowAutoFee;

    private Boolean prescriptionRequired;

    private Boolean showInCard;

    public Boolean getShowInCard() {
        return showInCard;
    }

    public void setShowInCard(Boolean showInCard) {
        this.showInCard = showInCard;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getAllowManualFee() {
        return allowManualFee;
    }

    public void setAllowManualFee(Boolean allowManualFee) {
        this.allowManualFee = allowManualFee;
    }

    public Boolean getAllowAutoFee() {
        return allowAutoFee;
    }

    public void setAllowAutoFee(Boolean allowAutoFee) {
        this.allowAutoFee = allowAutoFee;
    }

    public Boolean getPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }
}
