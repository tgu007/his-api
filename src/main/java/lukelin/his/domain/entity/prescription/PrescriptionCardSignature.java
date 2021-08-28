package lukelin.his.domain.entity.prescription;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Prescription.PrescriptionCardSignatureType;
import lukelin.his.domain.enums.Prescription.PrescriptionCardType;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.dto.prescription.response.PrescriptionSignatureRespDto;
import lukelin.his.dto.prescription.response.PrescriptionTreatmentRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "prescription.prescription_card_signature")
public class PrescriptionCardSignature extends BaseEntity  implements DtoConvertible<PrescriptionSignatureRespDto> {
    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "card_type", nullable = false)
    private PrescriptionCardType cardType;

    @Column(name = "signature_type", nullable = false)
    private PrescriptionCardSignatureType cardSignatureType;

    @Column(name = "signature_sequence")
    private Integer sequence;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @Column(name = "card_date")
    private Date cardDate;

    public Date getCardDate() {
        return cardDate;
    }

    public void setCardDate(Date cardDate) {
        this.cardDate = cardDate;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
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

    @Override
    public PrescriptionSignatureRespDto toDto() {
        PrescriptionSignatureRespDto dto = new PrescriptionSignatureRespDto();
        dto.setCardSignatureType(this.getCardSignatureType());
        dto.setCardType(this.getCardType());
        dto.setPatientSignInId(this.getPatientSignIn().getUuid());
        dto.setUuid(this.getUuid());
        dto.setSequence(this.getSequence());
        dto.setSignedBy(this.getWhoCreatedName());
        dto.setSignedTime(this.getWhenCreated());
        dto.setSignedById(this.getWhoCreatedId());
        if(this.getPrescription() !=null)
            dto.setPrescriptionId(this.getPrescription().getUuid());
        return dto;
    }
}
