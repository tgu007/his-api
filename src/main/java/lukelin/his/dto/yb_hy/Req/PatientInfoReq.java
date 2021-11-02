package lukelin.his.dto.yb_hy.Req;

public class PatientInfoReq {
    private String mdtrt_cert_type = "02"; //身份证

    private String mdtrt_cert_no;

    private String psn_cert_type = "2"; //身份证

    private String certno;

    private String psn_name;

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

    public String getPsn_name() {
        return psn_name;
    }

    public void setPsn_name(String psn_name) {
        this.psn_name = psn_name;
    }
}
