package lukelin.his.dto.yb_hy.Req;

import java.math.BigDecimal;
import java.util.Date;

public class SettlementValidationOverall {
    private Integer insutypeId;

    private String insutype;

    private String clr_type;

    private String setl_optins;

    private Date stmt_begndate;

    private Date stmt_enddate;

    private BigDecimal medfee_sumamt;

    private BigDecimal fund_pay_sumamt;

    private BigDecimal acct_pay;

    private Integer fixmedins_setl_cnt;

    public String getSetl_optins() {
        return setl_optins;
    }

    public void setSetl_optins(String setl_optins) {
        this.setl_optins = setl_optins;
    }

    public String getInsutype() {
        return insutype;
    }

    public void setInsutype(String insutype) {
        this.insutype = insutype;
    }

    public Integer getInsutypeId() {
        return insutypeId;
    }

    public void setInsutypeId(Integer insutypeId) {
        this.insutypeId = insutypeId;
    }

    public String getClr_type() {
        return clr_type;
    }

    public void setClr_type(String clr_type) {
        this.clr_type = clr_type;
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

    public Integer getFixmedins_setl_cnt() {
        return fixmedins_setl_cnt;
    }

    public void setFixmedins_setl_cnt(Integer fixmedins_setl_cnt) {
        this.fixmedins_setl_cnt = fixmedins_setl_cnt;
    }
}
