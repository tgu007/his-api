package lukelin.his.domain.entity.inventory.item;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.Interfaces.Inventory.CachedEntityStockInterface;
import lukelin.his.dto.Inventory.resp.item.ItemOrderStockRespDto;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "inventory.cached_item_stock")
public class CachedItemStock extends BaseCachedItemInventoryTransaction implements DtoConvertible<ItemOrderStockRespDto>, CachedEntityStockInterface {


    @Override
    public ItemOrderStockRespDto toDto() {
        ItemOrderStockRespDto dto = new ItemOrderStockRespDto();
        super.setStockDetailProperty(dto, this.getItem());
        dto.setTotalCost(this.getItem().calculateStockCost(this));
        dto.setOriginPurchaseOrderLine(this.getOriginPurchaseLine().toDto());
        return dto;
    }

    public void updateFromTransaction(CachedItemTransaction itemTransaction) {
        super.updateFromTransaction(itemTransaction);
        this.setItem(itemTransaction.getItem());
    }

}
