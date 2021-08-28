package lukelin.his.dto.prescription.response;

import lukelin.his.domain.enums.Prescription.PrescriptionCardType;

import java.util.List;
import java.util.UUID;

public class PrescriptionPatientNursingCardRespDto {
    private String cardTypeName;

    private UUID patientSignInId;

    private String patientName;

    private Integer patientAge;

    private String bedInfo;

    private Boolean hideQuantityInfo;

    private Boolean hideDropSpeed;

    private Boolean hideUseMethod;

    private Boolean hideSelfMedicine;

    private Boolean hideServeInfo;

    private PrescriptionSignatureRespDto signatureOne;

    private PrescriptionSignatureRespDto signatureTwo;

    private String bottleColour;

    private String sampleType;

    private String signInCode;

    public String getSignInCode() {
        return signInCode;
    }

    public void setSignInCode(String signInCode) {
        this.signInCode = signInCode;
    }

    private List<PrescriptionNursingCardRespDto> nursingCardRespDtoList;

    public String getBottleColour() {
        return bottleColour;
    }

    public void setBottleColour(String bottleColour) {
        this.bottleColour = bottleColour;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public void setHideSelfMedicine(Boolean hideSelfMedicine) {
        this.hideSelfMedicine = hideSelfMedicine;
    }

    public void setHideServeInfo(Boolean hideServeInfo) {
        this.hideServeInfo = hideServeInfo;
    }

    public void setHideQuantityInfo(Boolean hideQuantityInfo) {
        this.hideQuantityInfo = hideQuantityInfo;
    }

    public void setHideDropSpeed(Boolean hideDropSpeed) {
        this.hideDropSpeed = hideDropSpeed;
    }

    public void setHideUseMethod(Boolean hideUseMethod) {
        this.hideUseMethod = hideUseMethod;
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



    public Boolean getHideUseMethod() {
        if (this.getCardTypeName().equals(PrescriptionCardType.injection.getDescription()))
            return false;
        else
            return true;
    }

    public Boolean getHideDropSpeed() {
        if (this.getCardTypeName().equals(PrescriptionCardType.drop.getDescription()) || this.getCardTypeName().equals(PrescriptionCardType.dropBottle.getDescription()))
            return false;
        else
            return true;
    }


    public Boolean getHideQuantityInfo() {
//        if (this.getCardTypeName().equals(PrescriptionCardType.oral.getDescription()))
//            return false;
//        else
            return false;
    }

    public Boolean getHideSelfMedicine() {
        if (this.getCardTypeName().equals(PrescriptionCardType.treatment.getDescription()))
            return true;
        else
            return false;
    }

    public Boolean getHideServeInfo() {
        if (this.getCardTypeName().equals(PrescriptionCardType.treatment.getDescription()))
            return true;
        else
            return false;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getBedInfo() {
        return bedInfo;
    }

    public void setBedInfo(String bedInfo) {
        this.bedInfo = bedInfo;
    }

    public List<PrescriptionNursingCardRespDto> getNursingCardRespDtoList() {
        return nursingCardRespDtoList;
    }

    public void setNursingCardRespDtoList(List<PrescriptionNursingCardRespDto> nursingCardRespDtoList) {
        this.nursingCardRespDtoList = nursingCardRespDtoList;
    }
}
