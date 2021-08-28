package lukelin.his.dto.Inventory.req;

import java.math.BigDecimal;
import java.util.UUID;

public class BaseTransferLineSaveDto {
    private UUID uuid;

    private BigDecimal quantity;

    private UUID uomId;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UUID getUomId() {
        return uomId;
    }

    public void setUomId(UUID uomId) {
        this.uomId = uomId;
    }

}
