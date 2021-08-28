package lukelin.his.dto.Inventory.req.item;

import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.inventory.item.ItemTransferLine;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.dto.Inventory.req.BaseTransferLineSaveDto;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemTransferLineSaveDto extends BaseTransferLineSaveDto {

    private UUID itemId;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public ItemTransferLine toEntity() {
        if (this.getQuantity().compareTo(BigDecimal.ZERO) != 1)
            throw new ApiValidationException("invalid quantity");

        ItemTransferLine transferLine = new ItemTransferLine();
        BeanUtils.copyPropertiesIgnoreNull(this, transferLine);
        transferLine.setUom(new UnitOfMeasure(this.getUomId()));
        Item item = new Item(this.getItemId());
        transferLine.setItem(item);
        ItemSnapshot snapshot = item.findLatestSnapshot();
        transferLine.setItemSnapshot(snapshot);
        return transferLine;
    }

}
