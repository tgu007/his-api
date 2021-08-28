package lukelin.his.domain.entity.inventory;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.dto.Inventory.resp.BaseTransferLineRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BaseTransferLine extends BaseEntity implements DtoConvertible<BaseTransferLineRespDto> {
    @Column(nullable = false)
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "uom_id", nullable = false)
    private UnitOfMeasure uom;

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
