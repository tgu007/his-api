package lukelin.his.dto.yb_hy.Resp;

import java.math.BigDecimal;

public class FeeUploadResp {
    private String feedetl_sn;

    private BigDecimal det_item_fee_sumamt;

    private BigDecimal cnt;

    private BigDecimal pric;

    private BigDecimal pric_uplmt_amt;

    private BigDecimal selfpay_prop;

    private BigDecimal fulamt_ownpay_amt;

    private BigDecimal overlmt_amt;

    private BigDecimal preselfpay_amt;

    private BigDecimal inscp_scp_amt;

    private String chrgitm_lv;

    private String med_chrgitm_type;

    private String bas_medn_flag;

    private String hi_nego_drug_flag;

    private String chld_medc_flag;

    private String list_sp_item_flag;

    private String lmt_used_flag;

    private String drt_reim_flag;

    private String memo;

    public String getFeedetl_sn() {
        return feedetl_sn;
    }

    public void setFeedetl_sn(String feedetl_sn) {
        this.feedetl_sn = feedetl_sn;
    }

    public BigDecimal getDet_item_fee_sumamt() {
        return det_item_fee_sumamt;
    }

    public void setDet_item_fee_sumamt(BigDecimal det_item_fee_sumamt) {
        this.det_item_fee_sumamt = det_item_fee_sumamt;
    }

    public BigDecimal getCnt() {
        return cnt;
    }

    public void setCnt(BigDecimal cnt) {
        this.cnt = cnt;
    }

    public BigDecimal getPric() {
        return pric;
    }

    public void setPric(BigDecimal pric) {
        this.pric = pric;
    }

    public BigDecimal getPric_uplmt_amt() {
        return pric_uplmt_amt;
    }

    public void setPric_uplmt_amt(BigDecimal pric_uplmt_amt) {
        this.pric_uplmt_amt = pric_uplmt_amt;
    }

    public BigDecimal getSelfpay_prop() {
        return selfpay_prop;
    }

    public void setSelfpay_prop(BigDecimal selfpay_prop) {
        this.selfpay_prop = selfpay_prop;
    }

    public BigDecimal getFulamt_ownpay_amt() {
        return fulamt_ownpay_amt;
    }

    public void setFulamt_ownpay_amt(BigDecimal fulamt_ownpay_amt) {
        this.fulamt_ownpay_amt = fulamt_ownpay_amt;
    }

    public BigDecimal getOverlmt_amt() {
        return overlmt_amt;
    }

    public void setOverlmt_amt(BigDecimal overlmt_amt) {
        this.overlmt_amt = overlmt_amt;
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

    public String getChrgitm_lv() {
        return chrgitm_lv;
    }

    public void setChrgitm_lv(String chrgitm_lv) {
        this.chrgitm_lv = chrgitm_lv;
    }

    public String getMed_chrgitm_type() {
        return med_chrgitm_type;
    }

    public void setMed_chrgitm_type(String med_chrgitm_type) {
        this.med_chrgitm_type = med_chrgitm_type;
    }

    public String getBas_medn_flag() {
        return bas_medn_flag;
    }

    public void setBas_medn_flag(String bas_medn_flag) {
        this.bas_medn_flag = bas_medn_flag;
    }

    public String getHi_nego_drug_flag() {
        return hi_nego_drug_flag;
    }

    public void setHi_nego_drug_flag(String hi_nego_drug_flag) {
        this.hi_nego_drug_flag = hi_nego_drug_flag;
    }

    public String getChld_medc_flag() {
        return chld_medc_flag;
    }

    public void setChld_medc_flag(String chld_medc_flag) {
        this.chld_medc_flag = chld_medc_flag;
    }

    public String getList_sp_item_flag() {
        return list_sp_item_flag;
    }

    public void setList_sp_item_flag(String list_sp_item_flag) {
        this.list_sp_item_flag = list_sp_item_flag;
    }

    public String getLmt_used_flag() {
        return lmt_used_flag;
    }

    public void setLmt_used_flag(String lmt_used_flag) {
        this.lmt_used_flag = lmt_used_flag;
    }

    public String getDrt_reim_flag() {
        return drt_reim_flag;
    }

    public void setDrt_reim_flag(String drt_reim_flag) {
        this.drt_reim_flag = drt_reim_flag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
