package lukelin.his.domain.entity.Internal_account;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.inventory.item.CachedItemStock;
import lukelin.his.dto.basic.resp.entity.ItemRespDto;
import lukelin.his.dto.internal_account.ChargeableItemRespDto;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@javax.persistence.Entity
@Table(name = "internal_account.chargeable_item")
public class ChargeableItem extends BaseEntity implements DtoConvertible<ChargeableItemRespDto> {
    public ChargeableItem() {
    }

    public ChargeableItem(UUID uuid) {
        this.setUuid(uuid);
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "uom")
    private String uom;

    @Column(name = "list_price", nullable = false)
    private BigDecimal listPrice;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "default_quantity")
    private Integer defaultQuantity;

    public Integer getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(Integer defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public ChargeableItemRespDto toDto() {
        ChargeableItemRespDto responseDto = DtoUtils.convertRawDto(this);
        return responseDto;
    }
}
