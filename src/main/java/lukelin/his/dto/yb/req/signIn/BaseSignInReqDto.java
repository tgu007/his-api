package lukelin.his.dto.yb.req.signIn;

public class BaseSignInReqDto {
    //用户端就诊序号
    private String CJZXH;

    //用户端住院号
    private String ZYH;

    //CWKS
    private String CWKS;

    //CWH
    private String CWH;

    //ZGKS
    private String ZGKS;

    //主管医生
    private String ZGYS;

    //操作科室
    private String CZKS;

    //入院日期
    private String RYRQ;

    //入院科室名称
    private String RYKSMC;


    public String getCJZXH() {
        return CJZXH;
    }

    public void setCJZXH(String CJZXH) {
        this.CJZXH = CJZXH;
    }

    public String getZYH() {
        return ZYH;
    }

    public void setZYH(String ZYH) {
        this.ZYH = ZYH;
    }

    public String getCWKS() {
        return CWKS;
    }

    public void setCWKS(String CWKS) {
        this.CWKS = CWKS;
    }

    public String getCWH() {
        return CWH;
    }

    public void setCWH(String CWH) {
        this.CWH = CWH;
    }

    public String getZGKS() {
        return ZGKS;
    }

    public void setZGKS(String ZGKS) {
        this.ZGKS = ZGKS;
    }

    public String getZGYS() {
        return ZGYS;
    }

    public void setZGYS(String ZGYS) {
        this.ZGYS = ZGYS;
    }

    public String getCZKS() {
        return CZKS;
    }

    public void setCZKS(String CZKS) {
        this.CZKS = CZKS;
    }

    public String getRYRQ() {
        return RYRQ;
    }

    public void setRYRQ(String RYRQ) {
        this.RYRQ = RYRQ;
    }

    public String getRYKSMC() {
        return RYKSMC;
    }

    public void setRYKSMC(String RYKSMC) {
        this.RYKSMC = RYKSMC;
    }
}
