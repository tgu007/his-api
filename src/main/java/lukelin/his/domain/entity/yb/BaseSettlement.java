package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@MappedSuperclass
public class BaseSettlement extends BaseEntity {
    @OneToOne()
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    private String jsbh;

    private BigDecimal zje = BigDecimal.ZERO;

    private BigDecimal zfje = BigDecimal.ZERO;

    private BigDecimal zlje = BigDecimal.ZERO;

    private BigDecimal ybfy = BigDecimal.ZERO;

    private BigDecimal zyzf = BigDecimal.ZERO;

    private BigDecimal QFX = BigDecimal.ZERO;

    private BigDecimal GRZFXJ = BigDecimal.ZERO;

    private BigDecimal ZFXJ = BigDecimal.ZERO;

    private BigDecimal XJJE = BigDecimal.ZERO;

    private BigDecimal BXJE = BigDecimal.ZERO;

    private BigDecimal LNZHZF = BigDecimal.ZERO;

    private BigDecimal DNZHZF = BigDecimal.ZERO;

    private BigDecimal TCJE = BigDecimal.ZERO;

    private BigDecimal DBJE = BigDecimal.ZERO;

    private BigDecimal GWYBZZF = BigDecimal.ZERO;

    private BigDecimal LXJJZF = BigDecimal.ZERO;

    private BigDecimal EYJJZF = BigDecimal.ZERO;

    private BigDecimal BCYLBX = BigDecimal.ZERO;

    private BigDecimal DEZFBZ = BigDecimal.ZERO;

    private BigDecimal MZJZZF = BigDecimal.ZERO;

    private BigDecimal GSJJZF = BigDecimal.ZERO;

    private BigDecimal SYJJZF = BigDecimal.ZERO;

    private BigDecimal JSHDNGZYE = BigDecimal.ZERO;

    private BigDecimal JSHLNGZYE = BigDecimal.ZERO;

    private BigDecimal MZTCJJZF = BigDecimal.ZERO;

    private BigDecimal MZTCYBFY = BigDecimal.ZERO;

    private BigDecimal MZTCQFX = BigDecimal.ZERO;

    private BigDecimal YFBZ = BigDecimal.ZERO;

    private BigDecimal GRZF = BigDecimal.ZERO;

    private BigDecimal JTGJJJ = BigDecimal.ZERO;

    private BigDecimal YLJZ = BigDecimal.ZERO;

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }


    public String getJsbh() {
        return jsbh;
    }

    public void setJsbh(String jsbh) {
        this.jsbh = jsbh;
    }

    public BigDecimal getZje() {
        return zje;
    }

    public void setZje(BigDecimal zje) {
        this.zje = zje;
    }

    public BigDecimal getZfje() {
        return zfje;
    }

    public void setZfje(BigDecimal zfje) {
        this.zfje = zfje;
    }

    public BigDecimal getZlje() {
        return zlje;
    }

    public void setZlje(BigDecimal zlje) {
        this.zlje = zlje;
    }

    public BigDecimal getYbfy() {
        return ybfy;
    }

    public void setYbfy(BigDecimal ybfy) {
        this.ybfy = ybfy;
    }

    public BigDecimal getZyzf() {
        return zyzf;
    }

    public void setZyzf(BigDecimal zyzf) {
        this.zyzf = zyzf;
    }

    public BigDecimal getQFX() {
        return QFX;
    }

    public void setQFX(BigDecimal QFX) {
        this.QFX = QFX;
    }

    public BigDecimal getGRZFXJ() {
        return GRZFXJ;
    }

    public void setGRZFXJ(BigDecimal GRZFXJ) {
        this.GRZFXJ = GRZFXJ;
    }

    public BigDecimal getZFXJ() {
        return ZFXJ;
    }

    public void setZFXJ(BigDecimal ZFXJ) {
        this.ZFXJ = ZFXJ;
    }

    public BigDecimal getXJJE() {
        return XJJE;
    }

    public void setXJJE(BigDecimal XJJE) {
        this.XJJE = XJJE;
    }

    public BigDecimal getBXJE() {
        return BXJE;
    }

    public void setBXJE(BigDecimal BXJE) {
        this.BXJE = BXJE;
    }

    public BigDecimal getLNZHZF() {
        return LNZHZF;
    }

    public void setLNZHZF(BigDecimal LNZHZF) {
        this.LNZHZF = LNZHZF;
    }

    public BigDecimal getDNZHZF() {
        return DNZHZF;
    }

    public void setDNZHZF(BigDecimal DNZHZF) {
        this.DNZHZF = DNZHZF;
    }

    public BigDecimal getTCJE() {
        return TCJE;
    }

    public void setTCJE(BigDecimal TCJE) {
        this.TCJE = TCJE;
    }

    public BigDecimal getDBJE() {
        return DBJE;
    }

    public void setDBJE(BigDecimal DBJE) {
        this.DBJE = DBJE;
    }

    public BigDecimal getGWYBZZF() {
        return GWYBZZF;
    }

    public void setGWYBZZF(BigDecimal GWYBZZF) {
        this.GWYBZZF = GWYBZZF;
    }

    public BigDecimal getLXJJZF() {
        return LXJJZF;
    }

    public void setLXJJZF(BigDecimal LXJJZF) {
        this.LXJJZF = LXJJZF;
    }

    public BigDecimal getEYJJZF() {
        return EYJJZF;
    }

    public void setEYJJZF(BigDecimal EYJJZF) {
        this.EYJJZF = EYJJZF;
    }

    public BigDecimal getBCYLBX() {
        return BCYLBX;
    }

    public void setBCYLBX(BigDecimal BCYLBX) {
        this.BCYLBX = BCYLBX;
    }

    public BigDecimal getDEZFBZ() {
        return DEZFBZ;
    }

    public void setDEZFBZ(BigDecimal DEZFBZ) {
        this.DEZFBZ = DEZFBZ;
    }

    public BigDecimal getMZJZZF() {
        return MZJZZF;
    }

    public void setMZJZZF(BigDecimal MZJZZF) {
        this.MZJZZF = MZJZZF;
    }

    public BigDecimal getGSJJZF() {
        return GSJJZF;
    }

    public void setGSJJZF(BigDecimal GSJJZF) {
        this.GSJJZF = GSJJZF;
    }

    public BigDecimal getSYJJZF() {
        return SYJJZF;
    }

    public void setSYJJZF(BigDecimal SYJJZF) {
        this.SYJJZF = SYJJZF;
    }

    public BigDecimal getJSHDNGZYE() {
        return JSHDNGZYE;
    }

    public void setJSHDNGZYE(BigDecimal JSHDNGZYE) {
        this.JSHDNGZYE = JSHDNGZYE;
    }

    public BigDecimal getJSHLNGZYE() {
        return JSHLNGZYE;
    }

    public void setJSHLNGZYE(BigDecimal JSHLNGZYE) {
        this.JSHLNGZYE = JSHLNGZYE;
    }

    public BigDecimal getMZTCJJZF() {
        return MZTCJJZF;
    }

    public void setMZTCJJZF(BigDecimal MZTCJJZF) {
        this.MZTCJJZF = MZTCJJZF;
    }

    public BigDecimal getMZTCYBFY() {
        return MZTCYBFY;
    }

    public void setMZTCYBFY(BigDecimal MZTCYBFY) {
        this.MZTCYBFY = MZTCYBFY;
    }

    public BigDecimal getMZTCQFX() {
        return MZTCQFX;
    }

    public void setMZTCQFX(BigDecimal MZTCQFX) {
        this.MZTCQFX = MZTCQFX;
    }

    public BigDecimal getYFBZ() {
        return YFBZ;
    }

    public void setYFBZ(BigDecimal YFBZ) {
        this.YFBZ = YFBZ;
    }

    public BigDecimal getGRZF() {
        return GRZF;
    }

    public void setGRZF(BigDecimal GRZF) {
        this.GRZF = GRZF;
    }

    public BigDecimal getJTGJJJ() {
        return JTGJJJ;
    }

    public void setJTGJJJ(BigDecimal JTGJJJ) {
        this.JTGJJJ = JTGJJJ;
    }

    public BigDecimal getYLJZ() {
        return YLJZ;
    }

    public void setYLJZ(BigDecimal YLJZ) {
        this.YLJZ = YLJZ;
    }
}

