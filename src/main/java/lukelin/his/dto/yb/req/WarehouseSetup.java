package lukelin.his.dto.yb.req;

public class WarehouseSetup {
    //库房编号
    private String kfbh;

    //库房名称
    private String kfmc;

    //注销标志
    private String zxbz = "0";

    //能否门诊划价
    private String nfmzhj = "0";

    //能否住院划价
    private String nfzyhj = "0";

    public String getKfbh() {
        return kfbh;
    }

    public void setKfbh(String kfbh) {
        this.kfbh = kfbh;
    }

    public String getKfmc() {
        return kfmc;
    }

    public void setKfmc(String kfmc) {
        this.kfmc = kfmc;
    }

    public String getZxbz() {
        return zxbz;
    }

    public void setZxbz(String zxbz) {
        this.zxbz = zxbz;
    }

    public String getNfmzhj() {
        return nfmzhj;
    }

    public void setNfmzhj(String nfmzhj) {
        this.nfmzhj = nfmzhj;
    }

    public String getNfzyhj() {
        return nfzyhj;
    }

    public void setNfzyhj(String nfzyhj) {
        this.nfzyhj = nfzyhj;
    }
}
