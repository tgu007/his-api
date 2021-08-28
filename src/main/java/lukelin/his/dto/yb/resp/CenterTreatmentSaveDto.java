package lukelin.his.dto.yb.resp;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.yb.CenterMedicine;
import lukelin.his.domain.entity.yb.CenterTreatment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CenterTreatmentSaveDto {
    //流水号
    private String LSH;

    //中心项目内码
    private String ZXNM;

    //标准编码
    private String BZBM;

    //收费项目名称
    private String SFXMMC;

    //开始日期
    private String STARTDATE;

    //结束日期
    private String ENDDATE;

    //甲乙分类
    private String JYFL;

    //门诊限额
    private BigDecimal MZXE;

    //住院限额
    private BigDecimal ZYXE;

    //普通自付比例
    private BigDecimal PTZFBL;

    //拼音码
    private String PYM;

    //限制范围
    private String XZFW;

    //收费项目单价
    private String SFXMDJ;


    public void copyProperty(CenterTreatment centerTreatment) {
        BeanUtils.copyPropertiesIgnoreNull(this, centerTreatment);
        if (this.getSTARTDATE() != null)
            centerTreatment.setSTARTDATE(LocalDate.parse(this.getSTARTDATE().trim()));
        if (this.getENDDATE() != null)
            centerTreatment.setENDDATE(LocalDate.parse(this.getENDDATE().trim()));
    }

    public String getSFXMDJ() {
        return SFXMDJ;
    }

    public void setSFXMDJ(String SFXMDJ) {
        this.SFXMDJ = SFXMDJ;
    }

    public String getLSH() {
        return LSH;
    }

    public void setLSH(String LSH) {
        this.LSH = LSH;
    }

    public String getZXNM() {
        return ZXNM;
    }

    public void setZXNM(String ZXNM) {
        this.ZXNM = ZXNM;
    }

    public String getBZBM() {
        return BZBM;
    }

    public void setBZBM(String BZBM) {
        this.BZBM = BZBM;
    }

    public String getSFXMMC() {
        return SFXMMC;
    }

    public void setSFXMMC(String SFXMMC) {
        this.SFXMMC = SFXMMC;
    }

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getENDDATE() {
        return ENDDATE;
    }

    public void setENDDATE(String ENDDATE) {
        this.ENDDATE = ENDDATE;
    }

    public String getJYFL() {
        return JYFL;
    }

    public void setJYFL(String JYFL) {
        this.JYFL = JYFL;
    }

    public BigDecimal getMZXE() {
        return MZXE;
    }

    public void setMZXE(BigDecimal MZXE) {
        this.MZXE = MZXE;
    }

    public BigDecimal getZYXE() {
        return ZYXE;
    }

    public void setZYXE(BigDecimal ZYXE) {
        this.ZYXE = ZYXE;
    }

    public BigDecimal getPTZFBL() {
        return PTZFBL;
    }

    public void setPTZFBL(BigDecimal PTZFBL) {
        this.PTZFBL = PTZFBL;
    }

    public String getPYM() {
        return PYM;
    }

    public void setPYM(String PYM) {
        this.PYM = PYM;
    }

    public String getXZFW() {
        return XZFW;
    }

    public void setXZFW(String XZFW) {
        this.XZFW = XZFW;
    }
}
