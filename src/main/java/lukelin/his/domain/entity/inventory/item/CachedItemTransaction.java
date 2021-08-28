package lukelin.his.domain.entity.inventory.item;

import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.OrderLineInterface;
import lukelin.his.domain.Interfaces.Inventory.PartialOrderLineInterface;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.enums.Inventory.TransactionType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "inventory.cached_item_transaction")
public class CachedItemTransaction extends BaseCachedItemInventoryTransaction implements CachedTransactionInterface {
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(nullable = false, name = "reason_Id")
    private UUID reasonId;

    public UUID getReasonId() {
        return reasonId;
    }

    public void setReasonId(UUID reasonId) {
        this.reasonId = reasonId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }


    @Override
    public void setInventoryEntity(InventoryEntityInterface inventoryItem) {
        this.setItem((Item) inventoryItem);
    }

    @Override
    public void setOriginPurchaseLine(OrderLineInterface orderLine) {
        this.setOriginPurchaseLine((ItemOrderLine) orderLine);
    }

    public CachedItemTransaction createReverseTransaction() {
        CachedItemTransaction newTransaction = this.cloneTransaction();
        newTransaction.setMinUomQuantity(this.getMinUomQuantity().multiply(new BigDecimal(-1)));
        return newTransaction;
    }

    public CachedItemTransaction cloneTransaction() {
        CachedItemTransaction newTransaction = new CachedItemTransaction();
        newTransaction.setItem(this.getItem());
        newTransaction.setOriginPurchaseLine(this.getOriginPurchaseLine());
        newTransaction.setReasonId(this.reasonId);
        newTransaction.setWarehouse(this.getWarehouse());
        newTransaction.setMinUomQuantity(this.getMinUomQuantity());
        return newTransaction;
    }
}
