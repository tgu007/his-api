package lukelin.his.dto.yb;

import lukelin.his.dto.yb.req.signIn.BaseSignInReqDto;
import lukelin.his.dto.yb.req.signIn.SignInDiagnoseReqDto;

public class SignInReqDto extends BaseSignInReqDto {

    //行政科室
    private String XZKS;

    private String YWLX;

    private String RYLX;

    private ClientIpInfo clientIpInfo;

    public ClientIpInfo getClientIpInfo() {
        return clientIpInfo;
    }

    public void setClientIpInfo(ClientIpInfo clientIpInfo) {
        this.clientIpInfo = clientIpInfo;
    }

    //就诊信息域
    private SignInDiagnoseReqDto JZXX;

    public String getXZKS() {
        return XZKS;
    }

    public void setXZKS(String XZKS) {
        this.XZKS = XZKS;
    }

    public String getYWLX() {
        return YWLX;
    }

    public void setYWLX(String YWLX) {
        this.YWLX = YWLX;
    }

    public String getRYLX() {
        return RYLX;
    }

    public void setRYLX(String RYLX) {
        this.RYLX = RYLX;
    }

    public SignInDiagnoseReqDto getJZXX() {
        return JZXX;
    }

    public void setJZXX(SignInDiagnoseReqDto JZXX) {
        this.JZXX = JZXX;
    }
}
