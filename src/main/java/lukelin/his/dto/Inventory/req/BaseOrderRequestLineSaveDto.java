package lukelin.his.dto.Inventory.req;

import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.inventory.BaseOrderLine;
import lukelin.his.domain.entity.inventory.BaseOrderRequestLine;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BaseOrderRequestLineSaveDto {
    private UUID uuid;

    private BigDecimal requestQuantity;

    private UUID requestUomId;

    private String reference;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(BigDecimal requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public UUID getRequestUomId() {
        return requestUomId;
    }

    public void setRequestUomId(UUID requestUomId) {
        this.requestUomId = requestUomId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    protected void setRequestLineProperty(BaseOrderRequestLine orderRequestLine) {
        if (this.getRequestQuantity().compareTo(BigDecimal.ZERO) != 1)
            throw new ApiValidationException("invalid quantity");
        BeanUtils.copyPropertiesIgnoreNull(this, orderRequestLine);
        orderRequestLine.setRequestUom(new UnitOfMeasure(this.getRequestUomId()));
    }
}
