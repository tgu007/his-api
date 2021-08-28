package lukelin.his.dto.yb.resp;

import lukelin.his.dto.yb.BaseSettlementSummaryDto;

import java.math.BigDecimal;
import java.util.UUID;

public class BaseSettlementDto extends BaseSettlementSummaryDto {
    private UUID uuid;

    private String jsbh;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getJsbh() {
        return jsbh;
    }

    public void setJsbh(String jsbh) {
        this.jsbh = jsbh;
    }
}
