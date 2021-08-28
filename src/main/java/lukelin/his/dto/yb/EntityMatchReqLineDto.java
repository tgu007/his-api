package lukelin.his.dto.yb;

import java.util.UUID;

public class EntityMatchReqLineDto {
    private UUID uuid;

    private String fydm;

    private String cfydm;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFydm() {
        return fydm;
    }

    public void setFydm(String fydm) {
        this.fydm = fydm;
    }

    public String getCfydm() {
        return cfydm;
    }

    public void setCfydm(String cfydm) {
        this.cfydm = cfydm;
    }
}
