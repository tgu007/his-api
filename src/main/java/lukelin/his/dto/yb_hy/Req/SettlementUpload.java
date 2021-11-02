package lukelin.his.dto.yb_hy.Req;

import java.math.BigDecimal;
import java.util.Date;

public class SettlementUpload {
    private String mdtrt_id;

    private String setl_id;

    private String fixmedins_name;

    private String fixmedins_code;

    private String medcasno;

    private String psn_name;

    private String gend;

    private String brdy;

    private String ntly;

    private String naty;

    private String patn_cert_type;

    private String certno;

    private String prfs;

    private String coner_name;

    private String patn_rlts;

    private String coner_addr;

    private String coner_tel;

    private String hi_type;

    private String insuplc;

    private String ipt_med_type;

    private String adm_caty;

    private String dscg_caty;

    private String maindiag_flag;

    private String bill_code;

    private String bill_no;

    private String biz_sn;

    private Date setl_begn_date;

    private Date setl_end_date;

    private BigDecimal psn_selfpay;

    private BigDecimal psn_ownpay;

    private BigDecimal acct_pay;

    private BigDecimal psn_cashpay;

    private String hi_paymtd;

    private String hsorg;

    private String hsorg_opter;

    private String medins_fill_dept;

    private String medins_fill_psn;

    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getConer_name() {
        return coner_name;
    }

    public void setConer_name(String coner_name) {
        this.coner_name = coner_name;
    }

    public String getMdtrt_id() {
        return mdtrt_id;
    }

    public void setMdtrt_id(String mdtrt_id) {
        this.mdtrt_id = mdtrt_id;
    }

    public String getSetl_id() {
        return setl_id;
    }

    public void setSetl_id(String setl_id) {
        this.setl_id = setl_id;
    }

    public String getFixmedins_name() {
        return fixmedins_name;
    }

    public void setFixmedins_name(String fixmedins_name) {
        this.fixmedins_name = fixmedins_name;
    }

    public String getFixmedins_code() {
        return fixmedins_code;
    }

    public void setFixmedins_code(String fixmedins_code) {
        this.fixmedins_code = fixmedins_code;
    }

    public String getMedcasno() {
        return medcasno;
    }

    public void setMedcasno(String medcasno) {
        this.medcasno = medcasno;
    }

    public String getPsn_name() {
        return psn_name;
    }

    public void setPsn_name(String psn_name) {
        this.psn_name = psn_name;
    }

    public String getGend() {
        return gend;
    }

    public void setGend(String gend) {
        this.gend = gend;
    }

    public String getBrdy() {
        return brdy;
    }

    public void setBrdy(String brdy) {
        this.brdy = brdy;
    }

    public String getNtly() {
        return ntly;
    }

    public void setNtly(String ntly) {
        this.ntly = ntly;
    }

    public String getNaty() {
        return naty;
    }

    public void setNaty(String naty) {
        this.naty = naty;
    }

    public String getPatn_cert_type() {
        return patn_cert_type;
    }

    public void setPatn_cert_type(String patn_cert_type) {
        this.patn_cert_type = patn_cert_type;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getPrfs() {
        return prfs;
    }

    public void setPrfs(String prfs) {
        this.prfs = prfs;
    }

    public String getPatn_rlts() {
        return patn_rlts;
    }

    public void setPatn_rlts(String patn_rlts) {
        this.patn_rlts = patn_rlts;
    }

    public String getConer_addr() {
        return coner_addr;
    }

    public void setConer_addr(String coner_addr) {
        this.coner_addr = coner_addr;
    }

    public String getConer_tel() {
        return coner_tel;
    }

    public void setConer_tel(String coner_tel) {
        this.coner_tel = coner_tel;
    }

    public String getHi_type() {
        return hi_type;
    }

    public void setHi_type(String hi_type) {
        this.hi_type = hi_type;
    }

    public String getInsuplc() {
        return insuplc;
    }

    public void setInsuplc(String insuplc) {
        this.insuplc = insuplc;
    }

    public String getIpt_med_type() {
        return ipt_med_type;
    }

    public void setIpt_med_type(String ipt_med_type) {
        this.ipt_med_type = ipt_med_type;
    }

    public String getAdm_caty() {
        return adm_caty;
    }

    public void setAdm_caty(String adm_caty) {
        this.adm_caty = adm_caty;
    }

    public String getDscg_caty() {
        return dscg_caty;
    }

    public void setDscg_caty(String dscg_caty) {
        this.dscg_caty = dscg_caty;
    }

    public String getMaindiag_flag() {
        return maindiag_flag;
    }

    public void setMaindiag_flag(String maindiag_flag) {
        this.maindiag_flag = maindiag_flag;
    }

    public String getBill_code() {
        return bill_code;
    }

    public void setBill_code(String bill_code) {
        this.bill_code = bill_code;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getBiz_sn() {
        return biz_sn;
    }

    public void setBiz_sn(String biz_sn) {
        this.biz_sn = biz_sn;
    }

    public Date getSetl_begn_date() {
        return setl_begn_date;
    }

    public void setSetl_begn_date(Date setl_begn_date) {
        this.setl_begn_date = setl_begn_date;
    }

    public Date getSetl_end_date() {
        return setl_end_date;
    }

    public void setSetl_end_date(Date setl_end_date) {
        this.setl_end_date = setl_end_date;
    }

    public BigDecimal getPsn_selfpay() {
        return psn_selfpay;
    }

    public void setPsn_selfpay(BigDecimal psn_selfpay) {
        this.psn_selfpay = psn_selfpay;
    }

    public BigDecimal getPsn_ownpay() {
        return psn_ownpay;
    }

    public void setPsn_ownpay(BigDecimal psn_ownpay) {
        this.psn_ownpay = psn_ownpay;
    }

    public BigDecimal getAcct_pay() {
        return acct_pay;
    }

    public void setAcct_pay(BigDecimal acct_pay) {
        this.acct_pay = acct_pay;
    }

    public BigDecimal getPsn_cashpay() {
        return psn_cashpay;
    }

    public void setPsn_cashpay(BigDecimal psn_cashpay) {
        this.psn_cashpay = psn_cashpay;
    }

    public String getHi_paymtd() {
        return hi_paymtd;
    }

    public void setHi_paymtd(String hi_paymtd) {
        this.hi_paymtd = hi_paymtd;
    }

    public String getHsorg() {
        return hsorg;
    }

    public void setHsorg(String hsorg) {
        this.hsorg = hsorg;
    }

    public String getHsorg_opter() {
        return hsorg_opter;
    }

    public void setHsorg_opter(String hsorg_opter) {
        this.hsorg_opter = hsorg_opter;
    }

    public String getMedins_fill_dept() {
        return medins_fill_dept;
    }

    public void setMedins_fill_dept(String medins_fill_dept) {
        this.medins_fill_dept = medins_fill_dept;
    }

    public String getMedins_fill_psn() {
        return medins_fill_psn;
    }

    public void setMedins_fill_psn(String medins_fill_psn) {
        this.medins_fill_psn = medins_fill_psn;
    }
}
