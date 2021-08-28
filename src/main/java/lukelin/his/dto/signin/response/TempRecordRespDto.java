package lukelin.his.dto.signin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.PatientSignIn.UrineVolume;

import java.util.Date;
import java.util.UUID;

public class TempRecordRespDto {
    private UUID uuid;

    private String patientName;

    private UUID patientSignInId;

    private Double pulse;

    private Double breath;

    private Double bloodPressureHigh;

    private Double bloodPressureLow;

    private Double heartBeat;

    private Double bowels;

    private Double armpitTemp;

    private Double mouthTemp;

    private Double earTemp;

    private Double rectalTemp;

    private Double adjustTemp;

    private Double inVolume;

    private Double outVolume;

    private Double urineVolume;

    private Double height;

    private Double weight;

    private String allergy;

    private String reference;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordDate;

    private Boolean canNotStand;

    private UrineVolume urineVolumeSelection;

    public UrineVolume getUrineVolumeSelection() {
        return urineVolumeSelection;
    }

    public void setUrineVolumeSelection(UrineVolume urineVolumeSelection) {
        this.urineVolumeSelection = urineVolumeSelection;
    }

    public Boolean getCanNotStand() {
        return canNotStand;
    }

    public void setCanNotStand(Boolean canNotStand) {
        this.canNotStand = canNotStand;
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

    public Double getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(Double heartBeat) {
        this.heartBeat = heartBeat;
    }

    public Double getBowels() {
        return bowels;
    }

    public void setBowels(Double bowels) {
        this.bowels = bowels;
    }

    public Double getArmpitTemp() {
        return armpitTemp;
    }

    public void setArmpitTemp(Double armpitTemp) {
        this.armpitTemp = armpitTemp;
    }

    public Double getMouthTemp() {
        return mouthTemp;
    }

    public void setMouthTemp(Double mouthTemp) {
        this.mouthTemp = mouthTemp;
    }

    public Double getEarTemp() {
        return earTemp;
    }

    public void setEarTemp(Double earTemp) {
        this.earTemp = earTemp;
    }

    public Double getRectalTemp() {
        return rectalTemp;
    }

    public void setRectalTemp(Double rectalTemp) {
        this.rectalTemp = rectalTemp;
    }

    public Double getAdjustTemp() {
        return adjustTemp;
    }

    public void setAdjustTemp(Double adjustTemp) {
        this.adjustTemp = adjustTemp;
    }

    public Double getInVolume() {
        return inVolume;
    }

    public void setInVolume(Double inVolume) {
        this.inVolume = inVolume;
    }

    public Double getOutVolume() {
        return outVolume;
    }

    public void setOutVolume(Double outVolume) {
        this.outVolume = outVolume;
    }

    public Double getUrineVolume() {
        return urineVolume;
    }

    public void setUrineVolume(Double urineVolume) {
        this.urineVolume = urineVolume;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
