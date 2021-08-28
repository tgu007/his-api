package lukelin.his.dto.yb.resp;

import java.util.List;
import java.util.UUID;

public class PatientFeeUploadReq {
    private String jzxh;

    private List<FeePendingUploadReq> fyxx;

    private String grbh;

    private UUID signInId;

    public String getJzxh() {
        return jzxh;
    }

    public void setJzxh(String jzxh) {
        this.jzxh = jzxh;
    }

    public List<FeePendingUploadReq> getFyxx() {
        return fyxx;
    }

    public void setFyxx(List<FeePendingUploadReq> fyxx) {
        this.fyxx = fyxx;
    }

    public String getGrbh() {
        return grbh;
    }

    public void setGrbh(String grbh) {
        this.grbh = grbh;
    }

    public UUID getSignInId() {
        return signInId;
    }

    public void setSignInId(UUID signInId) {
        this.signInId = signInId;
    }
}
