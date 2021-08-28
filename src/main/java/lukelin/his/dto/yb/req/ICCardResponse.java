package lukelin.his.dto.yb.req;

import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.CardInfo;

import java.util.UUID;

public class ICCardResponse {
    private UUID uuid;

    private String fullInfo;

    private String xm;

    private String zjbh;

    private UUID patientSignInId;

    private String kxxy;

    private String ybszdbh;

    private String ylrylb;

    private String grbh;

    private String xb;

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getGrbh() {
        return grbh;
    }

    public void setGrbh(String grbh) {
        this.grbh = grbh;
    }

    public String getYlrylb() {
        return ylrylb;
    }

    public void setYlrylb(String ylrylb) {
        this.ylrylb = ylrylb;
    }

    public String getYbszdbh() {
        return ybszdbh;
    }

    public void setYbszdbh(String ybszdbh) {
        this.ybszdbh = ybszdbh;
    }

    public String getKxxy() {
        return kxxy;
    }

    public void setKxxy(String kxxy) {
        this.kxxy = kxxy;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(String fullInfo) {
        this.fullInfo = fullInfo;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjbh() {
        return zjbh;
    }

    public void setZjbh(String zjbh) {
        this.zjbh = zjbh;
    }

    public CardInfo toEntity(PatientSignIn patientSignIn)
    {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setUuid(this.getUuid());
        cardInfo.setFullInfo(this.getFullInfo());
        cardInfo.setIdNumber(this.getZjbh());
        cardInfo.setName(this.getXm());
        cardInfo.setCardId(this.getKxxy());
        cardInfo.setPatientSignIn(patientSignIn);
        cardInfo.setAreaCode(this.getYbszdbh());
        cardInfo.setPatientType(this.getYlrylb());
        cardInfo.setPatientNumber(this.getGrbh());
        return cardInfo;
    }
}
