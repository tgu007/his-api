package lukelin.his.dto.Inventory.req;

import lukelin.his.domain.entity.inventory.item.ItemPartialOrderLine;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BasePartialOrderLineSaveDto {
    private UUID uuid;

    private BigDecimal quantity;

    private UUID uomId;

    private UUID masterOrderLineId;

    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

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

    public UUID getMasterOrderLineId() {
        return masterOrderLineId;
    }

    public void setMasterOrderLineId(UUID masterOrderLineId) {
        this.masterOrderLineId = masterOrderLineId;
    }

}
