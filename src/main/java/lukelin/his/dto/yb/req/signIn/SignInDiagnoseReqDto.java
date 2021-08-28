package lukelin.his.dto.yb.req.signIn;

import com.fasterxml.jackson.databind.ser.Serializers;

public class SignInDiagnoseReqDto extends BaseSignInDiagnoseReq {
    private String ylrylb;

    public String getYlrylb() {
        return ylrylb;
    }

    public void setYlrylb(String ylrylb) {
        this.ylrylb = ylrylb;
    }
}
