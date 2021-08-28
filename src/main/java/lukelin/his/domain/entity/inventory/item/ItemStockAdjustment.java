package lukelin.his.domain.entity.inventory.item;


import lukelin.his.domain.Interfaces.Inventory.AdjustmentInterface;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.entity.inventory.BaseStockAdjustment;
import lukelin.his.domain.entity.basic.entity.Item;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "inventory.item_stock_adjustment")
public class ItemStockAdjustment extends BaseStockAdjustment implements AdjustmentInterface {
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedItemTransaction();
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getItem();
    }

}
