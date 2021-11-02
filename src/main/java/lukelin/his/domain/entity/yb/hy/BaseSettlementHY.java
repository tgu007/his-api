package lukelin.his.domain.entity.yb.hy;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.dto.yb_hy.Resp.SettlementResp;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@MappedSuperclass
public class BaseSettlementHY extends BaseEntity {
    @Column(name = "full_info")
    private String fullInfo;

    @OneToOne()
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;


    @Column(name = "mdtrt_id")
    private String mdtrt_id;

    @Column(name = "insutype")
    private String insutype;

    @Column(name = "psn_type")
    private String psn_type;

    @Column(name = "cvlserv_flag")
    private String cvlserv_flag;

    @Column(name = "setl_time")
    private Date setl_time;

    @Column(name = "mdtrt_cert_type")
    private String mdtrt_cert_type;

    @Column(name = "med_type")
    private String med_type;

    @Column(name = "medfee_sumamt")
    private BigDecimal medfee_sumamt=BigDecimal.ZERO;

    @Column(name = "fulamt_ownpay_amt")
    private BigDecimal fulamt_ownpay_amt=BigDecimal.ZERO;

    @Column(name = "overlmt_selfpay")
    private BigDecimal overlmt_selfpay=BigDecimal.ZERO;

    @Column(name = "preselfpay_amt")
    private BigDecimal preselfpay_amt=BigDecimal.ZERO;

    @Column(name = "inscp_scp_amt")
    private BigDecimal inscp_scp_amt=BigDecimal.ZERO;

    @Column(name = "act_pay_dedc")
    private BigDecimal act_pay_dedc=BigDecimal.ZERO;

    @Column(name = "pool_prop_selfpay")
    private BigDecimal pool_prop_selfpay = BigDecimal.ZERO;

    @Column(name = "cvlserv_pay")
    private BigDecimal cvlserv_pay=BigDecimal.ZERO;

    @Column(name = "hifmi_pay")
    private BigDecimal hifmi_pay=BigDecimal.ZERO;

    @Column(name = "hifob_pay")
    private BigDecimal hifob_pay=BigDecimal.ZERO;

    @Column(name = "maf_pay")
    private BigDecimal maf_pay=BigDecimal.ZERO;

    @Column(name = "oth_pay")
    private BigDecimal oth_pay=BigDecimal.ZERO;

    @Column(name = "fund_pay_sumamt")
    private BigDecimal fund_pay_sumamt=BigDecimal.ZERO;

    @Column(name = "psn_part_amt")
    private BigDecimal psn_part_amt=BigDecimal.ZERO;

    @Column(name = "acct_pay")
    private BigDecimal acct_pay=BigDecimal.ZERO;

    @Column(name = "psn_cash_pay")
    private BigDecimal psn_cash_pay=BigDecimal.ZERO;

    @Column(name = "hosp_part_amt")
    private BigDecimal hosp_part_amt=BigDecimal.ZERO;

    @Column(name = "balc")
    private BigDecimal balc=BigDecimal.ZERO;

    @Column(name = "acct_mulaid_pay")
    private BigDecimal acct_mulaid_pay=BigDecimal.ZERO;

    @Column(name = "medins_setl_id")
    private String medins_setl_id;

    @Column(name = "clr_optins")
    private String clr_optins;

    @Column(name = "clr_way")
    private String clr_way;

    @Column(name = "clr_type")
    private String clr_type;

    @Column(name = "hifp_pay")
    private BigDecimal hifp_pay;

    public BigDecimal getHifp_pay() {
        return hifp_pay;
    }

    public void setHifp_pay(BigDecimal hifp_pay) {
        this.hifp_pay = hifp_pay;
    }

    public String getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(String fullInfo) {
        this.fullInfo = fullInfo;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public String getMdtrt_id() {
        return mdtrt_id;
    }

    public void setMdtrt_id(String mdtrt_id) {
        this.mdtrt_id = mdtrt_id;
    }

    public String getInsutype() {
        return insutype;
    }

    public void setInsutype(String insutype) {
        this.insutype = insutype;
    }

    public String getPsn_type() {
        return psn_type;
    }

    public void setPsn_type(String psn_type) {
        this.psn_type = psn_type;
    }

    public String getCvlserv_flag() {
        return cvlserv_flag;
    }

    public void setCvlserv_flag(String cvlserv_flag) {
        this.cvlserv_flag = cvlserv_flag;
    }

    public Date getSetl_time() {
        return setl_time;
    }

    public void setSetl_time(Date setl_time) {
        this.setl_time = setl_time;
    }

    public String getMdtrt_cert_type() {
        return mdtrt_cert_type;
    }

    public void setMdtrt_cert_type(String mdtrt_cert_type) {
        this.mdtrt_cert_type = mdtrt_cert_type;
    }

    public String getMed_type() {
        return med_type;
    }

    public void setMed_type(String med_type) {
        this.med_type = med_type;
    }

    public BigDecimal getMedfee_sumamt() {
        return medfee_sumamt;
    }

    public void setMedfee_sumamt(BigDecimal medfee_sumamt) {
        this.medfee_sumamt = medfee_sumamt;
    }

    public BigDecimal getFulamt_ownpay_amt() {
        return fulamt_ownpay_amt;
    }

    public void setFulamt_ownpay_amt(BigDecimal fulamt_ownpay_amt) {
        this.fulamt_ownpay_amt = fulamt_ownpay_amt;
    }

    public BigDecimal getOverlmt_selfpay() {
        return overlmt_selfpay;
    }

    public void setOverlmt_selfpay(BigDecimal overlmt_selfpay) {
        this.overlmt_selfpay = overlmt_selfpay;
    }

    public BigDecimal getPreselfpay_amt() {
        return preselfpay_amt;
    }

    public void setPreselfpay_amt(BigDecimal preselfpay_amt) {
        this.preselfpay_amt = preselfpay_amt;
    }

    public BigDecimal getInscp_scp_amt() {
        return inscp_scp_amt;
    }

    public void setInscp_scp_amt(BigDecimal inscp_scp_amt) {
        this.inscp_scp_amt = inscp_scp_amt;
    }

    public BigDecimal getAct_pay_dedc() {
        return act_pay_dedc;
    }

    public void setAct_pay_dedc(BigDecimal act_pay_dedc) {
        this.act_pay_dedc = act_pay_dedc;
    }

    public BigDecimal getPool_prop_selfpay() {
        return pool_prop_selfpay;
    }

    public void setPool_prop_selfpay(BigDecimal pool_prop_selfpay) {
        this.pool_prop_selfpay = pool_prop_selfpay;
    }

    public BigDecimal getCvlserv_pay() {
        return cvlserv_pay;
    }

    public void setCvlserv_pay(BigDecimal cvlserv_pay) {
        this.cvlserv_pay = cvlserv_pay;
    }

    public BigDecimal getHifmi_pay() {
        return hifmi_pay;
    }

    public void setHifmi_pay(BigDecimal hifmi_pay) {
        this.hifmi_pay = hifmi_pay;
    }

    public BigDecimal getHifob_pay() {
        return hifob_pay;
    }

    public void setHifob_pay(BigDecimal hifob_pay) {
        this.hifob_pay = hifob_pay;
    }

    public BigDecimal getMaf_pay() {
        return maf_pay;
    }

    public void setMaf_pay(BigDecimal maf_pay) {
        this.maf_pay = maf_pay;
    }

    public BigDecimal getOth_pay() {
        return oth_pay;
    }

    public void setOth_pay(BigDecimal oth_pay) {
        this.oth_pay = oth_pay;
    }

    public BigDecimal getFund_pay_sumamt() {
        return fund_pay_sumamt;
    }

    public void setFund_pay_sumamt(BigDecimal fund_pay_sumamt) {
        this.fund_pay_sumamt = fund_pay_sumamt;
    }

    public BigDecimal getPsn_part_amt() {
        return psn_part_amt;
    }

    public void setPsn_part_amt(BigDecimal psn_part_amt) {
        this.psn_part_amt = psn_part_amt;
    }

    public BigDecimal getAcct_pay() {
        return acct_pay;
    }

    public void setAcct_pay(BigDecimal acct_pay) {
        this.acct_pay = acct_pay;
    }

    public BigDecimal getHosp_part_amt() {
        return hosp_part_amt;
    }

    public void setHosp_part_amt(BigDecimal hosp_part_amt) {
        this.hosp_part_amt = hosp_part_amt;
    }

    public BigDecimal getBalc() {
        return balc;
    }

    public void setBalc(BigDecimal balc) {
        this.balc = balc;
    }

    public BigDecimal getAcct_mulaid_pay() {
        return acct_mulaid_pay;
    }

    public void setAcct_mulaid_pay(BigDecimal acct_mulaid_pay) {
        this.acct_mulaid_pay = acct_mulaid_pay;
    }

    public String getMedins_setl_id() {
        return medins_setl_id;
    }

    public void setMedins_setl_id(String medins_setl_id) {
        this.medins_setl_id = medins_setl_id;
    }

    public String getClr_optins() {
        return clr_optins;
    }

    public void setClr_optins(String clr_optins) {
        this.clr_optins = clr_optins;
    }

    public String getClr_way() {
        return clr_way;
    }

    public void setClr_way(String clr_way) {
        this.clr_way = clr_way;
    }

    public String getClr_type() {
        return clr_type;
    }

    public void setClr_type(String clr_type) {
        this.clr_type = clr_type;
    }

    public BigDecimal getPsn_cash_pay() {
        return psn_cash_pay;
    }

    public void setPsn_cash_pay(BigDecimal psn_cash_pay) {
        this.psn_cash_pay = psn_cash_pay;
    }

    public SettlementResp toSettlementDto() {
        SettlementResp dto = new SettlementResp();
        BeanUtils.copyProperties(this, dto);
        if(!this.patientSignIn.selfPay()) {
            dto.setSelfPayRatio(BigDecimal.ONE.subtract(this.getPool_prop_selfpay()));
            dto.setSelfPayAmount(this.getPsn_part_amt().subtract(this.getOverlmt_selfpay()).subtract(this.getAct_pay_dedc()).subtract(this.getPreselfpay_amt()));
            dto.setTotalTC(dto.getSelfPayAmount().add(dto.getFund_pay_sumamt()));
        }
        return dto;
    }
}
