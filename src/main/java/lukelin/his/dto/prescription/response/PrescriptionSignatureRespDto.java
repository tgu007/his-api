package lukelin.his.dto.prescription.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Prescription.PrescriptionCardSignatureType;
import lukelin.his.domain.enums.Prescription.PrescriptionCardType;

import java.util.Date;
import java.util.UUID;

public class PrescriptionSignatureRespDto {
    private UUID uuid;

    private PrescriptionCardType cardType;

    private PrescriptionCardSignatureType cardSignatureType;

    private Integer sequence;

    private UUID prescriptionId;

    private UUID patientSignInId;

    private String signedBy;

    private UUID signedById;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date signedTime;

    public UUID getSignedById() {
        return signedById;
    }

    public void setSignedById(UUID signedById) {
        this.signedById = signedById;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PrescriptionCardType getCardType() {
        return cardType;
    }

    public void setCardType(PrescriptionCardType cardType) {
        this.cardType = cardType;
    }

    public PrescriptionCardSignatureType getCardSignatureType() {
        return cardSignatureType;
    }

    public void setCardSignatureType(PrescriptionCardSignatureType cardSignatureType) {
        this.cardSignatureType = cardSignatureType;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }
}
