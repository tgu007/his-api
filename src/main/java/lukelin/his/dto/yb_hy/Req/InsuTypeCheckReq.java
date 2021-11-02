package lukelin.his.dto.yb_hy.Req;

public class InsuTypeCheckReq {
    private String psn_no;

    private String insutype;

    private String fixmedins_code;

    private String med_type;

    private String begntime;

    public String getPsn_no() {
        return psn_no;
    }

    public void setPsn_no(String psn_no) {
        this.psn_no = psn_no;
    }

    public String getInsutype() {
        return insutype;
    }

    public void setInsutype(String insutype) {
        this.insutype = insutype;
    }

    public String getFixmedins_code() {
        return fixmedins_code;
    }

    public void setFixmedins_code(String fixmedins_code) {
        this.fixmedins_code = fixmedins_code;
    }

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
}
