package lukelin.his.dto.signin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.PatientSignIn.Consciousness;
import lukelin.his.domain.enums.PatientSignIn.PipingCondition;
import lukelin.his.domain.enums.PatientSignIn.PupilCondition;

import javax.persistence.Column;
import java.util.Date;
import java.util.UUID;

public class NursingRecordRespDto {
    private UUID uuid;

    private String patientName;

    private UUID patientSignInId;

    private Double bodyTemp;

    private Double pulse;

    private Double breath;

    private Double bloodPressureHigh;

    private Double bloodPressureLow;

    private Double spo2;

    private Double oxygen;

    private Consciousness consciousness;

    private PupilCondition leftPupil;

    private PupilCondition rightPupil;

    private String inName;

    private Double inVolume;

    private String outName;

    private Double outVolume;

    private String outCondition;

    private PipingCondition pipingCondition;

    private String skinCondition;

    private Double pressureSoreScore;

    private Double fallScore;

    private Double careOneselfScore;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date recordDate;

    private String whoCreatedName;

    private String leftPupilSize;

    private String rightPupilSize;

    public String getLeftPupilDescription() {
        String description = "";
        if(this.leftPupil != null)
            description+=this.leftPupil.toString();
        if(this.leftPupilSize !=null)
            description+=this.leftPupilSize.toString();
        return description;
    }

    public String getRightPupilDescription() {
        String description = "";
        if(this.rightPupil != null)
            description+=this.rightPupil.toString();
        if(this.rightPupilSize !=null)
            description+=this.rightPupilSize.toString();
        return description;
    }

    public String getLeftPupilSize() {
        return leftPupilSize;
    }

    public void setLeftPupilSize(String leftPupilSize) {
        this.leftPupilSize = leftPupilSize;
    }

    public String getRightPupilSize() {
        return rightPupilSize;
    }

    public void setRightPupilSize(String rightPupilSize) {
        this.rightPupilSize = rightPupilSize;
    }

    public String getWhoCreatedName() {
        return whoCreatedName;
    }

    public void setWhoCreatedName(String whoCreatedName) {
        this.whoCreatedName = whoCreatedName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public Double getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(Double bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public Double getPulse() {
        return pulse;
    }

    public void setPulse(Double pulse) {
        this.pulse = pulse;
    }

    public Double getBreath() {
        return breath;
    }

    public void setBreath(Double breath) {
        this.breath = breath;
    }

    public Double getBloodPressureHigh() {
        return bloodPressureHigh;
    }

    public void setBloodPressureHigh(Double bloodPressureHigh) {
        this.bloodPressureHigh = bloodPressureHigh;
    }

    public Double getBloodPressureLow() {
        return bloodPressureLow;
    }

    public void setBloodPressureLow(Double bloodPressureLow) {
        this.bloodPressureLow = bloodPressureLow;
    }

    public Double getSpo2() {
        return spo2;
    }

    public void setSpo2(Double spo2) {
        this.spo2 = spo2;
    }

    public Double getOxygen() {
        return oxygen;
    }

    public void setOxygen(Double oxygen) {
        this.oxygen = oxygen;
    }

    public Consciousness getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(Consciousness consciousness) {
        this.consciousness = consciousness;
    }

    public PupilCondition getLeftPupil() {
        return leftPupil;
    }

    public void setLeftPupil(PupilCondition leftPupil) {
        this.leftPupil = leftPupil;
    }

    public PupilCondition getRightPupil() {
        return rightPupil;
    }

    public void setRightPupil(PupilCondition rightPupil) {
        this.rightPupil = rightPupil;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public Double getInVolume() {
        return inVolume;
    }

    public void setInVolume(Double inVolume) {
        this.inVolume = inVolume;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public Double getOutVolume() {
        return outVolume;
    }

    public void setOutVolume(Double outVolume) {
        this.outVolume = outVolume;
    }

    public String getOutCondition() {
        return outCondition;
    }

    public void setOutCondition(String outCondition) {
        this.outCondition = outCondition;
    }

    public PipingCondition getPipingCondition() {
        return pipingCondition;
    }

    public void setPipingCondition(PipingCondition pipingCondition) {
        this.pipingCondition = pipingCondition;
    }

    public String getSkinCondition() {
        return skinCondition;
    }

    public void setSkinCondition(String skinCondition) {
        this.skinCondition = skinCondition;
    }

    public Double getPressureSoreScore() {
        return pressureSoreScore;
    }

    public void setPressureSoreScore(Double pressureSoreScore) {
        this.pressureSoreScore = pressureSoreScore;
    }

    public Double getFallScore() {
        return fallScore;
    }

    public void setFallScore(Double fallScore) {
        this.fallScore = fallScore;
    }

    public Double getCareOneselfScore() {
        return careOneselfScore;
    }

    public void setCareOneselfScore(Double careOneselfScore) {
        this.careOneselfScore = careOneselfScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
