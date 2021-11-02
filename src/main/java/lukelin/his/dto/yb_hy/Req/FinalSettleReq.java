package lukelin.his.dto.yb_hy.Req;

public class FinalSettleReq {
    private String psn_no;

    private String mdtrt_cert_type;

    private String mdtrt_cert_no;

    private String psn_cert_type;

    private String certno;

    private String psn_type;

    private String medfee_sumamt;

    private String psn_setlway;

    private String mdtrt_id;

    private String insutype;

    private String mid_setl_flag;

    private String acct_used_flag;


    public String getMid_setl_flag() {
        return mid_setl_flag;
    }

    public void setMid_setl_flag(String mid_setl_flag) {
        this.mid_setl_flag = mid_setl_flag;
    }

    public String getPsn_no() {
        return psn_no;
    }

    public void setPsn_no(String psn_no) {
        this.psn_no = psn_no;
    }

    public String getMdtrt_cert_type() {
        return mdtrt_cert_type;
    }

    public void setMdtrt_cert_type(String mdtrt_cert_type) {
        this.mdtrt_cert_type = mdtrt_cert_type;
    }

    public String getMdtrt_cert_no() {
        return mdtrt_cert_no;
    }

    public void setMdtrt_cert_no(String mdtrt_cert_no) {
        this.mdtrt_cert_no = mdtrt_cert_no;
    }

    public String getPsn_cert_type() {
        return psn_cert_type;
    }

    public void setPsn_cert_type(String psn_cert_type) {
        this.psn_cert_type = psn_cert_type;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getPsn_type() {
        return psn_type;
    }

    public void setPsn_type(String psn_type) {
        this.psn_type = psn_type;
    }

    public String getMedfee_sumamt() {
        return medfee_sumamt;
    }

    public void setMedfee_sumamt(String medfee_sumamt) {
        this.medfee_sumamt = medfee_sumamt;
    }

    public String getPsn_setlway() {
        return psn_setlway;
    }

    public void setPsn_setlway(String psn_setlway) {
        this.psn_setlway = psn_setlway;
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

    public String getAcct_used_flag() {
        return acct_used_flag;
    }

    public void setAcct_used_flag(String acct_used_flag) {
        this.acct_used_flag = acct_used_flag;
    }
}
