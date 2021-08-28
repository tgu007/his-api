package lukelin.his.dto.yb.req.signIn;

public class SelfSignInReqDto extends BaseSignInReqDto{
    //就诊信息域
    private SelfSignInDiagnoseReq jzxx;

    private String xyysbh;

    public String getXyysbh() {
        return xyysbh;
    }

    public void setXyysbh(String xyysbh) {
        this.xyysbh = xyysbh;
    }

    public SelfSignInDiagnoseReq getJzxx() {
        return jzxx;
    }

    public void setJzxx(SelfSignInDiagnoseReq jzxx) {
        this.jzxx = jzxx;
    }

}
