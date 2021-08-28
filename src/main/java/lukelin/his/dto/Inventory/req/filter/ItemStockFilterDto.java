package lukelin.his.dto.Inventory.req.filter;

import lukelin.his.dto.basic.SearchCodeDto;

import java.util.List;
import java.util.UUID;

public class ItemStockFilterDto extends StockFilterDto {
    private UUID itemId;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }
}
