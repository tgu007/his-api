package lukelin.his.dto.prescription.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.Prescription.PrescriptionCardSignatureType;
import lukelin.his.domain.enums.Prescription.PrescriptionCardType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PrescriptionCardSignSaveDto {
    private UUID patientSignInId;

    private PrescriptionCardType cardType;

    private PrescriptionCardSignatureType cardSignatureType;

    private Integer sequence;

    private UUID prescriptionId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cardDate; //护理卡片日期

    public Date getCardDate() {
        return cardDate;
    }

    public void setCardDate(Date cardDate) {
        this.cardDate = cardDate;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
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
}
