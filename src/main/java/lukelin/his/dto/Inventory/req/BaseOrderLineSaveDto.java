package lukelin.his.dto.Inventory.req;

import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.inventory.BaseOrderLine;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BaseOrderLineSaveDto {
    private UUID uuid;

    private BigDecimal quantity;

    private BigDecimal cost;

    private String invoiceNumber;

    private UUID uomId;

    private UUID originPurchaseLineId;

    private String batch;

    private UUID manufacturerId;

    private UUID brandId;

    public UUID getBrandId() {
        return brandId;
    }

    public void setBrandId(UUID brandId) {
        this.brandId = brandId;
    }

    public UUID getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(UUID manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public UUID getUomId() {
        return uomId;
    }

    public void setUomId(UUID uomId) {
        this.uomId = uomId;
    }

    public UUID getOriginPurchaseLineId() {
        return originPurchaseLineId;
    }

    public void setOriginPurchaseLineId(UUID originPurchaseLineId) {
        this.originPurchaseLineId = originPurchaseLineId;
    }

    protected void setOrderLineProperty(BaseOrderLine orderLine) {
        if (this.getQuantity().compareTo(BigDecimal.ZERO) != 1)
            throw new ApiValidationException("invalid quantity");
        orderLine.setUom(new UnitOfMeasure(this.getUomId()));
        orderLine.setBatchNumber(this.batch);
    }
}
