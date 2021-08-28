package lukelin.his.domain.entity.inventory;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BasePartialOrderLine extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "uom_id", nullable = false)
    private UnitOfMeasure uom;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }
}
