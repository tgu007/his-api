package lukelin.his.dto.basic.resp.entity;

import java.util.UUID;

public class TreatmentListMiniRespDto {
    private UUID uuid;

    private String name;

    private Boolean operate;

    public Boolean getOperate() {
        return operate;
    }

    public void setOperate(Boolean operate) {
        this.operate = operate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
