package lukelin.his.dto.Inventory.req.item;

import lukelin.his.domain.entity.inventory.item.ItemStockAdjustment;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.dto.Inventory.req.BaseStockAdjustDto;

public class ItemStockAdjustSaveDto extends BaseStockAdjustDto {

    public ItemStockAdjustment toEntity() {
        ItemStockAdjustment newAdjustment = new ItemStockAdjustment();
        newAdjustment.setItem(new Item(this.getInventoryEntityId()));
        super.updateEntityValue(newAdjustment);
        return newAdjustment;
    }
}
