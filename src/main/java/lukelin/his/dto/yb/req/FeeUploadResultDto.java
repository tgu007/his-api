package lukelin.his.dto.yb.req;

import java.util.List;
import java.util.UUID;

public class FeeUploadResultDto {
    private UUID signInId;

    private List<FeeUploadLineResultDto> fyfymx;

    private List<FeeUploadLineErrorDto> ccxx;

    private String message;

    public UUID getSignInId() {
        return signInId;
    }

    public void setSignInId(UUID signInId) {
        this.signInId = signInId;
    }

    public List<FeeUploadLineResultDto> getFyfymx() {
        return fyfymx;
    }

    public void setFyfymx(List<FeeUploadLineResultDto> fyfymx) {
        this.fyfymx = fyfymx;
    }

    public List<FeeUploadLineErrorDto> getCcxx() {
        return ccxx;
    }

    public void setCcxx(List<FeeUploadLineErrorDto> ccxx) {
        this.ccxx = ccxx;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
