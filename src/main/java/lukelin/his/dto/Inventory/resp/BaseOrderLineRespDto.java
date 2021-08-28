package lukelin.his.dto.Inventory.resp;

import lukelin.his.dto.basic.resp.setup.BrandDto;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BaseOrderLineRespDto {
    private UUID uuid;

    private String orderNumber;

    private BigDecimal quantity;

    private BigDecimal cost;

    private String invoiceNumber;

    private UnitOfMeasureDto uom;

    private BaseOrderLineRespDto originPurchaseLine;

    private String batchNumber;

    private Boolean inRequest = true;

    private Boolean exceedRequestQuantity = false;

    private BigDecimal totalCost;

    private String totalPartialLineQuantity;

    private String reference;

    private BrandDto brand;

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTotalPartialLineQuantity() {
        return totalPartialLineQuantity;
    }

    public void setTotalPartialLineQuantity(String totalPartialLineQuantity) {
        this.totalPartialLineQuantity = totalPartialLineQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getExceedRequestQuantity() {
        return exceedRequestQuantity;
    }

    public void setExceedRequestQuantity(Boolean exceedRequestQuantity) {
        this.exceedRequestQuantity = exceedRequestQuantity;
    }

    public Boolean getInRequest() {
        return inRequest;
    }

    public void setInRequest(Boolean inRequest) {
        this.inRequest = inRequest;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
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

    public UnitOfMeasureDto getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasureDto uom) {
        this.uom = uom;
    }

    public BaseOrderLineRespDto getOriginPurchaseLine() {
        return originPurchaseLine;
    }

    public void setOriginPurchaseLine(BaseOrderLineRespDto originPurchaseLine) {
        this.originPurchaseLine = originPurchaseLine;
    }

}
