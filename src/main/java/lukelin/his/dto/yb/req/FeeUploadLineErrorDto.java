package lukelin.his.dto.yb.req;

import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.yb.FeeUploadError;
import lukelin.his.domain.entity.yb.FeeUploadResult;

import java.util.UUID;

public class FeeUploadLineErrorDto {
    private UUID cfybh;

    private String wfscyy;

    public UUID getCfybh() {
        return cfybh;
    }

    public void setCfybh(UUID cfybh) {
        this.cfybh = cfybh;
    }

    public String getWfscyy() {
        return wfscyy;
    }

    public void setWfscyy(String wfscyy) {
        this.wfscyy = wfscyy;
    }

    public FeeUploadError toEntity()
    {
        FeeUploadError error = new FeeUploadError();
        error.setFee(new Fee(this.getCfybh()));
        error.setError(this.getWfscyy());
        return error;
    }
}
