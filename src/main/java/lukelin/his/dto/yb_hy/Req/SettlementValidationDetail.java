package lukelin.his.dto.yb_hy.Req;

import java.math.BigDecimal;
import java.util.Date;

public class SettlementValidationDetail {
    private String setl_optins;

    private String file_qury_no;

    private Date stmt_begndate;

    private Date stmt_enddate;

    private BigDecimal medfee_sumamt;

    private BigDecimal fund_pay_sumamt;

    private BigDecimal acct_pay;

    private BigDecimal cash_payamt;

    private Integer fixmedins_setl_cnt;

    private String clr_type;

    public String getSetl_optins() {
        return setl_optins;
    }

    public void setSetl_optins(String setl_optins) {
        this.setl_optins = setl_optins;
    }

    public String getFile_qury_no() {
        return file_qury_no;
    }

    public void setFile_qury_no(String file_qury_no) {
        this.file_qury_no = file_qury_no;
    }

    public Date getStmt_begndate() {
        return stmt_begndate;
    }

    public void setStmt_begndate(Date stmt_begndate) {
        this.stmt_begndate = stmt_begndate;
    }

    public Date getStmt_enddate() {
        return stmt_enddate;
    }

    public void setStmt_enddate(Date stmt_enddate) {
        this.stmt_enddate = stmt_enddate;
    }

    public BigDecimal getMedfee_sumamt() {
        return medfee_sumamt;
    }

    public void setMedfee_sumamt(BigDecimal medfee_sumamt) {
        this.medfee_sumamt = medfee_sumamt;
    }

    public BigDecimal getFund_pay_sumamt() {
        return fund_pay_sumamt;
    }

    public void setFund_pay_sumamt(BigDecimal fund_pay_sumamt) {
        this.fund_pay_sumamt = fund_pay_sumamt;
    }

    public BigDecimal getAcct_pay() {
        return acct_pay;
    }

    public void setAcct_pay(BigDecimal acct_pay) {
        this.acct_pay = acct_pay;
    }

    public BigDecimal getCash_payamt() {
        return cash_payamt;
    }

    public void setCash_payamt(BigDecimal cash_payamt) {
        this.cash_payamt = cash_payamt;
    }

    public Integer getFixmedins_setl_cnt() {
        return fixmedins_setl_cnt;
    }

    public void setFixmedins_setl_cnt(Integer fixmedins_setl_cnt) {
        this.fixmedins_setl_cnt = fixmedins_setl_cnt;
    }

    public String getClr_type() {
        return clr_type;
    }

    public void setClr_type(String clr_type) {
        this.clr_type = clr_type;
    }
}
