package lukelin.his.domain.entity.basic.entity;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public class BaseTreatment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Dictionary type;

    @Column(nullable = false)
    private String name;

    private boolean enabled;

    @Column(name = "search_code", nullable = false, length = 50)
    private String searchCode;

    @ManyToOne
    @JoinColumn(name = "min_uom_id", nullable = false)
    private UnitOfMeasure minUom;

    @ManyToOne
    @JoinColumn(name = "fee_type_id")
    private Dictionary feeType;

    @Column(name = "list_price")
    private BigDecimal listPrice;


    public Dictionary getType() {
        return type;
    }

    public void setType(Dictionary type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public UnitOfMeasure getMinUom() {
        return minUom;
    }

    public void setMinUom(UnitOfMeasure minUom) {
        this.minUom = minUom;
    }

    public Dictionary getFeeType() {
        return feeType;
    }

    public void setFeeType(Dictionary feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }


}
