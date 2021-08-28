package lukelin.his.dto.yb.inventory.resp;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.yb.CenterMedicine;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class CenterMedicineSaveDto {
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
    private String KSRQ;

    //结束日期
    private String JSRQ;

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

    public void copyProperty(CenterMedicine centerMedicine) {
        BeanUtils.copyPropertiesIgnoreNull(this, centerMedicine);
        if (this.getKSRQ() != null)
            centerMedicine.setKSRQ(LocalDate.parse(this.getKSRQ().trim()));
        if (this.getJSRQ() != null)
            centerMedicine.setJSRQ(LocalDate.parse(this.getJSRQ().trim()));
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

    public String getKSRQ() {
        return KSRQ;
    }

    public void setKSRQ(String KSRQ) {
        this.KSRQ = KSRQ;
    }

    public String getJSRQ() {
        return JSRQ;
    }

    public void setJSRQ(String JSRQ) {
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


}
