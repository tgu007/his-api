package lukelin.his.dto.yb;

import java.util.UUID;

public class EntityMatchUploadResultSaveDto {
    private UUID uuid;

    private String error;

    private String ybscbz;

    private String sbyy;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getYbscbz() {
        return ybscbz;
    }

    public void setYbscbz(String ybscbz) {
        this.ybscbz = ybscbz;
    }

    public String getSbyy() {
        return sbyy;
    }

    public void setSbyy(String sbyy) {
        this.sbyy = sbyy;
    }
}
