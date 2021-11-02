package lukelin.his.domain.entity.yb;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.dto.yb.resp.CenterTreatmentRespDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@javax.persistence.Entity
@Table(name = "yb.center_treatment")
public class CenterTreatment extends BaseEntity implements DtoConvertible<CenterTreatmentRespDto> {
    //流水号
    private String LSH;

    //中心项目内码
    private String ZXNM;

    //标准编码
    private String BZBM;

    //收费项目名称
    private String SFXMMC;

    //开始日期
    private LocalDate STARTDATE;

    //结束日期
    private LocalDate ENDDATE;

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
    private BigDecimal SFXMDJ;

    @Column(name = "fullInfo")
    private String fullInfo;

    public String getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(String fullInfo) {
        this.fullInfo = fullInfo;
    }

    public BigDecimal getSFXMDJ() {
        return SFXMDJ;
    }

    public void setSFXMDJ(BigDecimal SFXMDJ) {
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

    public LocalDate getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(LocalDate STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public LocalDate getENDDATE() {
        return ENDDATE;
    }

    public void setENDDATE(LocalDate ENDDATE) {
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

    @Override
    public CenterTreatmentRespDto toDto() {
        CenterTreatmentRespDto resp = new CenterTreatmentRespDto();
        resp.setUuid(this.getUuid());
        resp.setCode1(this.getZXNM());
        resp.setCode2(this.getBZBM());
        resp.setStartDate(this.getSTARTDATE());
        resp.setEndDate(this.getENDDATE());
        switch (this.getJYFL()) {
            case "01":
                resp.setChargeLevel("甲");
                break;
            case "02":
                resp.setChargeLevel("乙");
                break;
            case "03":
                resp.setChargeLevel("丙");
                break;
        }
        resp.setName(this.getSFXMMC());
        resp.setPayRatio(this.getPTZFBL());
        return resp;
    }
}
