package lukelin.his.dto.yb.resp;

import java.util.List;
import java.util.UUID;

public class FeeUploadReq {
    private String jzxh;

    private List<FeeUploadLineReq> fyxx;

    private String grbh;

    private UUID signInId;

    public UUID getSignInId() {
        return signInId;
    }

    public void setSignInId(UUID signInId) {
        this.signInId = signInId;
    }

    public String getGrbh() {
        return grbh;
    }

    public void setGrbh(String grbh) {
        this.grbh = grbh;
    }

    public String getJzxh() {
        return jzxh;
    }

    public void setJzxh(String jzxh) {
        this.jzxh = jzxh;
    }

    public List<FeeUploadLineReq> getFyxx() {
        return fyxx;
    }

    public void setFyxx(List<FeeUploadLineReq> fyxx) {
        this.fyxx = fyxx;
    }
}
