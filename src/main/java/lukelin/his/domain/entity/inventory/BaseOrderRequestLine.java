package lukelin.his.domain.entity.inventory;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.dto.Inventory.resp.BaseOrderRequestLineRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderRequestLineRespDto;
import lukelin.his.system.Utils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BaseOrderRequestLine extends BaseEntity implements DtoConvertible<BaseOrderRequestLineRespDto> {
    @Column(name = "request_quantity", nullable = false)
    private BigDecimal requestQuantity;

    @ManyToOne
    @JoinColumn(name = "request_uom_id", nullable = false)
    private UnitOfMeasure requestUom;

    private String reference;

    public BigDecimal getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(BigDecimal requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public UnitOfMeasure getRequestUom() {
        return requestUom;
    }

    public void setRequestUom(UnitOfMeasure requestUom) {
        this.requestUom = requestUom;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    protected void setOrderRequestLineDtoValue(BaseOrderRequestLineRespDto lineDto) {
        BeanUtils.copyPropertiesIgnoreNull(this, lineDto);
        lineDto.setRequestUom(this.getRequestUom().toDto());
    }
}
