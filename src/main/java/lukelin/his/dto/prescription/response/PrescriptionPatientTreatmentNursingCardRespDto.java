package lukelin.his.dto.prescription.response;

import java.util.UUID;

public class PrescriptionPatientTreatmentNursingCardRespDto {

    private String patientInfo;

    private String quantityInfo;

    private String frequency;

    private String reference;

    private UUID prescriptionId;

    private Boolean firstDay;

    private String firstDayQuantityInfo;

    private UUID patientSignInId;

    private PrescriptionSignatureRespDto signatureOne;

    private PrescriptionSignatureRespDto signatureTwo;

    private PrescriptionSignatureRespDto signatureThree;

    private PrescriptionSignatureRespDto signatureFour;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
    }

    public String getQuantityInfo() {
        return quantityInfo;
    }

    public void setQuantityInfo(String quantityInfo) {
        this.quantityInfo = quantityInfo;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Boolean getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Boolean firstDay) {
        this.firstDay = firstDay;
    }

    public String getFirstDayQuantityInfo() {
        return firstDayQuantityInfo;
    }

    public void setFirstDayQuantityInfo(String firstDayQuantityInfo) {
        this.firstDayQuantityInfo = firstDayQuantityInfo;
    }

    public PrescriptionSignatureRespDto getSignatureOne() {
        return signatureOne;
    }

    public void setSignatureOne(PrescriptionSignatureRespDto signatureOne) {
        this.signatureOne = signatureOne;
    }

    public PrescriptionSignatureRespDto getSignatureTwo() {
        return signatureTwo;
    }

    public void setSignatureTwo(PrescriptionSignatureRespDto signatureTwo) {
        this.signatureTwo = signatureTwo;
    }

    public PrescriptionSignatureRespDto getSignatureThree() {
        return signatureThree;
    }

    public void setSignatureThree(PrescriptionSignatureRespDto signatureThree) {
        this.signatureThree = signatureThree;
    }

    public PrescriptionSignatureRespDto getSignatureFour() {
        return signatureFour;
    }

    public void setSignatureFour(PrescriptionSignatureRespDto signatureFour) {
        this.signatureFour = signatureFour;
    }
}
