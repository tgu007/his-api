package lukelin.his.dto.Inventory.resp;

import lukelin.his.dto.basic.resp.entity.BaseEntityStockSummaryRespDto;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BaseOrderRequestLineRespDto {
    private UUID uuid;

    private BigDecimal requestQuantity;

    private UnitOfMeasureDto requestUom;

    private String reference;

    private BaseEntityStockSummaryRespDto entityStockSummary;

    public BaseEntityStockSummaryRespDto getEntityStockSummary() {
        return entityStockSummary;
    }

    public void setEntityStockSummary(BaseEntityStockSummaryRespDto entityStockSummary) {
        this.entityStockSummary = entityStockSummary;
    }

    public BigDecimal getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(BigDecimal requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public UnitOfMeasureDto getRequestUom() {
        return requestUom;
    }

    public void setRequestUom(UnitOfMeasureDto requestUom) {
        this.requestUom = requestUom;
    }

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
}
