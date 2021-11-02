package lukelin.his.dto.yb_hy.Req;

public class CancelFeeReq {
    private String feedetl_sn = "0000";

    private String mdtrt_id;

    private String psn_no;

    public String getFeedetl_sn() {
        return feedetl_sn;
    }

    public void setFeedetl_sn(String feedetl_sn) {
        this.feedetl_sn = feedetl_sn;
    }

    public String getMdtrt_id() {
        return mdtrt_id;
    }

    public void setMdtrt_id(String mdtrt_id) {
        this.mdtrt_id = mdtrt_id;
    }

    public String getPsn_no() {
        return psn_no;
    }

    public void setPsn_no(String psn_no) {
        this.psn_no = psn_no;
    }
}
