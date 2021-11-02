package lukelin.his.domain.entity.yb;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.dto.yb.inventory.resp.CenterMedicineRespDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@javax.persistence.Entity
@Table(name = "yb.center_medicine")
public class CenterMedicine extends BaseEntity implements DtoConvertible<CenterMedicineRespDto> {
    //流水号
    private String LSH;

    //中心项目内码
    private String ZXNM;

    //标准编码
    private String BZBM;

    //收费项目名称
    private String SFXMMC;

    //通用名
    private String TYM;

    //英文名（化学名）
    private String YWM;

    //开始日期
    private LocalDate KSRQ;

    //结束日期
    private LocalDate JSRQ;

    //甲乙分类
    private String JYFL;

    //门诊限额
    private BigDecimal MZXE;

    //住院限额
    private BigDecimal ZYXE;

    //标准价格
    private BigDecimal BZJG;

    //产地属性
    private String CDSX;

    //剂型
    private String JX;

    //规格
    private String GG;

    //单位
    private String DW;

    //产地
    private String CD;

    //普通自付比例
    private BigDecimal PTZFBL;

    //拼音码
    private String PYM;

    //五笔码
    private String WBM;

    //限制范围
    private String XZFW;

    //转换比
    private BigDecimal ZHB;

    @Column(name = "fullInfo")
    private String fullInfo;

    public String getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(String fullInfo) {
        this.fullInfo = fullInfo;
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

    public String getTYM() {
        return TYM;
    }

    public void setTYM(String TYM) {
        this.TYM = TYM;
    }

    public String getYWM() {
        return YWM;
    }

    public void setYWM(String YWM) {
        this.YWM = YWM;
    }

    public LocalDate getKSRQ() {
        return KSRQ;
    }

    public void setKSRQ(LocalDate KSRQ) {
        this.KSRQ = KSRQ;
    }

    public LocalDate getJSRQ() {
        return JSRQ;
    }

    public void setJSRQ(LocalDate JSRQ) {
        this.JSRQ = JSRQ;
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

    public BigDecimal getBZJG() {
        return BZJG;
    }

    public void setBZJG(BigDecimal BZJG) {
        this.BZJG = BZJG;
    }

    public String getCDSX() {
        return CDSX;
    }

    public void setCDSX(String CDSX) {
        this.CDSX = CDSX;
    }

    public String getJX() {
        return JX;
    }

    public void setJX(String JX) {
        this.JX = JX;
    }

    public String getGG() {
        return GG;
    }

    public void setGG(String GG) {
        this.GG = GG;
    }

    public String getDW() {
        return DW;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }

    public String getCD() {
        return CD;
    }

    public void setCD(String CD) {
        this.CD = CD;
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

    public String getWBM() {
        return WBM;
    }

    public void setWBM(String WBM) {
        this.WBM = WBM;
    }

    public String getXZFW() {
        return XZFW;
    }

    public void setXZFW(String XZFW) {
        this.XZFW = XZFW;
    }

    public BigDecimal getZHB() {
        return ZHB;
    }

    public void setZHB(BigDecimal ZHB) {
        this.ZHB = ZHB;
    }

    @Override
    public CenterMedicineRespDto toDto() {
        CenterMedicineRespDto resp = new CenterMedicineRespDto();
        resp.setUuid(this.getUuid());
        resp.setCode1(this.getZXNM());
        resp.setCode2(this.getBZBM());
        resp.setStartDate(this.getKSRQ());
        resp.setEndDate(this.getJSRQ());
        resp.setListPrice(this.getBZJG());
        switch (this.getJYFL()) {
            case "1":
                resp.setChargeLevel("甲");
                break;
            case "2":
                resp.setChargeLevel("乙");
                break;
            case "3":
                resp.setChargeLevel("丙");
                break;
        }
        resp.setDoseType(this.getJX());
        resp.setManufacture(this.getCD());
        resp.setName(this.getSFXMMC());
        resp.setModel(this.getGG());
        resp.setPayRatio(this.getPTZFBL());
        return resp;
    }
}
