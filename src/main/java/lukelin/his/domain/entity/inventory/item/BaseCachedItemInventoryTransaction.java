package lukelin.his.domain.entity.inventory.item;

import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.inventory.BaseCachedInventoryTransaction;
import lukelin.his.domain.entity.basic.entity.Item;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseCachedItemInventoryTransaction extends BaseCachedInventoryTransaction {
    @ManyToOne
    @JoinColumn(name = "origin_purchase_line_id")
    private ItemOrderLine originPurchaseLine;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemOrderLine getOriginPurchaseLine() {
        return originPurchaseLine;
    }

    public void setOriginPurchaseLine(ItemOrderLine originPurchaseLine) {
        this.originPurchaseLine = originPurchaseLine;
    }

    protected void updateFromTransaction(BaseCachedItemInventoryTransaction itemInventoryTransaction) {
        super.updateFromTransaction(itemInventoryTransaction);
        this.setOriginPurchaseLine(itemInventoryTransaction.getOriginPurchaseLine());
    }

    public String getStockName(){
        return item.getName();
    }
}
