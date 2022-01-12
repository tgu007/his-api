package lukelin.his.dto.yb_hy.Req;

public class SignInDownload {
    private String med_type;

    private String begntime;

    private String endtime;

    private String psn_no;

    public String getMed_type() {
        return med_type;
    }

    public void setMed_type(String med_type) {
        this.med_type = med_type;
    }

    public String getBegntime() {
        return begntime;
    }

    public void setBegntime(String begntime) {
        this.begntime = begntime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPsn_no() {
        return psn_no;
    }

    public void setPsn_no(String psn_no) {
        this.psn_no = psn_no;
    }
}
