package lukelin.his.dto.Inventory.resp;

import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BaseTransferLineRespDto {
    private UUID uuid;

    private BigDecimal quantity;

    private UnitOfMeasureDto uom;

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

    public UnitOfMeasureDto getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasureDto uom) {
        this.uom = uom;
    }

}
