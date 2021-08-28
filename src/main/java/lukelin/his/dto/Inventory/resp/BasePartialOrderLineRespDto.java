package lukelin.his.dto.Inventory.resp;

import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BasePartialOrderLineRespDto {
    private UUID uuid;

    private BigDecimal quantity;

    private UnitOfMeasureDto uom;

    private Boolean exceedMasterLineQuantity = false;

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

    public UnitOfMeasureDto getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasureDto uom) {
        this.uom = uom;
    }

    public Boolean getExceedMasterLineQuantity() {
        return exceedMasterLineQuantity;
    }

    public void setExceedMasterLineQuantity(Boolean exceedMasterLineQuantity) {
        this.exceedMasterLineQuantity = exceedMasterLineQuantity;
    }
}
