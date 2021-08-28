package lukelin.his.dto.yb;

import java.util.UUID;

public class EntityMatchDownloadSaveDto {
    private UUID uuid;

    //上传结果
    private String spjg;

    //SBYY
    private String spbz;

    private String error;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getSpjg() {
        return spjg;
    }

    public void setSpjg(String spjg) {
        this.spjg = spjg;
    }

    public String getSpbz() {
        return spbz;
    }

    public void setSpbz(String spbz) {
        this.spbz = spbz;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
