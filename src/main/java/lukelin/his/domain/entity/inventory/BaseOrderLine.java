package lukelin.his.domain.entity.inventory;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.Brand;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@MappedSuperclass
public abstract class BaseOrderLine extends BaseEntity {
    @Column(name = "batch")
    private String batchNumber;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(nullable = false)
    private BigDecimal quantity;


    @Column(nullable = false)
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "uom_id", nullable = false)
    private UnitOfMeasure uom;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }

    public BigDecimal getTotalCost() {
        return this.getCost().multiply(this.getQuantity()).setScale(2, RoundingMode.HALF_UP);
    }

}
