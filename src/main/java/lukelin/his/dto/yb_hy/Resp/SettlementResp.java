package lukelin.his.dto.yb_hy.Resp;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.yb.hy.PreSettlementHY;
import lukelin.his.domain.entity.yb.hy.SettlementHY;

import java.math.BigDecimal;
import java.util.Date;

public class SettlementResp {
    private String mdtrt_id;

    private String insutype;

    private String psn_type;

    private String cvlserv_flag;

    private Date setl_time;

    private String mdtrt_cert_type;

    private String med_type;

    private BigDecimal medfee_sumamt;

    private BigDecimal fulamt_ownpay_amt;

    private BigDecimal overlmt_selfpay;

    private BigDecimal preselfpay_amt;

    private BigDecimal inscp_scp_amt;

    private BigDecimal act_pay_dedc;

    private BigDecimal hifp_pay;

    private BigDecimal pool_prop_selfpay;

    private BigDecimal cvlserv_pay;

    private BigDecimal hifes_pay;

    private BigDecimal hifmi_pay;

    private BigDecimal hifob_pay;

    private BigDecimal maf_pay;

    private BigDecimal oth_pay;

    private BigDecimal fund_pay_sumamt;

    private BigDecimal psn_part_amt;

    private BigDecimal acct_pay;

    private BigDecimal psn_cash_pay;

    private BigDecimal hosp_part_amt;

    private BigDecimal balc;

    private BigDecimal acct_mulaid_pay;

    private String medins_setl_id;

    private String clr_optins;

    private String clr_way;

    private String clr_type;

    private String setl_id;

    private BigDecimal selfPayAmount;

    private BigDecimal selfPayRatio;

    private BigDecimal totalTC;

    public BigDecimal getSelfPayAmount() {
        return selfPayAmount;
    }

    public void setSelfPayAmount(BigDecimal selfPayAmount) {
        this.selfPayAmount = selfPayAmount;
    }

    public BigDecimal getSelfPayRatio() {
        return selfPayRatio;
    }

    public void setSelfPayRatio(BigDecimal selfPayRatio) {
        this.selfPayRatio = selfPayRatio;
    }

    public BigDecimal getTotalTC() {
        return totalTC;
    }

    public void setTotalTC(BigDecimal totalTC) {
        this.totalTC = totalTC;
    }

    public String getSetl_id() {
        return setl_id;
    }




    public void setSetl_id(String setl_id) {
        this.setl_id = setl_id;
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

    public BigDecimal getHifp_pay() {
        return hifp_pay;
    }

    public void setHifp_pay(BigDecimal hifp_pay) {
        this.hifp_pay = hifp_pay;
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

    public BigDecimal getHifes_pay() {
        return hifes_pay;
    }

    public void setHifes_pay(BigDecimal hifes_pay) {
        this.hifes_pay = hifes_pay;
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

    public BigDecimal getPsn_cash_pay() {
        return psn_cash_pay;
    }

    public void setPsn_cash_pay(BigDecimal psn_cash_pay) {
        this.psn_cash_pay = psn_cash_pay;
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

    public PreSettlementHY toPreSettlement() {
        PreSettlementHY preSettlement = new PreSettlementHY();
        BeanUtils.copyPropertiesIgnoreNull(this, preSettlement);
        return preSettlement;
    }

    public SettlementHY toSettlement() {
        SettlementHY settlement = new SettlementHY();
        BeanUtils.copyPropertiesIgnoreNull(this, settlement);
        return settlement;
    }
}
