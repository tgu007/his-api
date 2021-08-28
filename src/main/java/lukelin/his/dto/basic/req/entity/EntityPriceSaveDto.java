package lukelin.his.dto.basic.req.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class EntityPriceSaveDto {
    private UUID uuid;

    private BigDecimal price;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
