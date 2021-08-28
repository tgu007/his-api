package lukelin.his.dto.internal_account;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.system.Utils;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

public class ChargeableItemRespDto {
    private String name;

    private BigDecimal listPrice;

    private Boolean enabled;

    private UUID uuid;

    private String uom;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ChargeableItem toEntity() {
        ChargeableItem item;
        if (this.getUuid() != null)
            item = Ebean.find(ChargeableItem.class, this.getUuid());
        else
            item = new ChargeableItem();
        BeanUtils.copyPropertiesIgnoreNull(this, item);
        return item;
    }
}
