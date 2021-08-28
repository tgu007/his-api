package lukelin.his.dto.yb.resp;

public class FeeCancelReq {
    private String jzxh;

    private String fybh;

    private String sfqc = "0";

    public String getSfqc() {
        return sfqc;
    }

    public void setSfqc(String sfqc) {
        this.sfqc = sfqc;
    }

    public String getJzxh() {
        return jzxh;
    }

    public void setJzxh(String jzxh) {
        this.jzxh = jzxh;
    }

    public String getFybh() {
        return fybh;
    }

    public void setFybh(String fybh) {
        this.fybh = fybh;
    }
}
