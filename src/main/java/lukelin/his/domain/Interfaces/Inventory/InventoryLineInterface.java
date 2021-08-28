package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.enums.Inventory.TransactionType;

import java.math.BigDecimal;

public interface InventoryLineInterface extends NewTransactionInterface {
    UnitOfMeasure getUom();

    BigDecimal getQuantity();

    OrderLineInterface getOrderLine();

    default CachedTransactionInterface createOrderLineTransaction() {
        CachedTransactionInterface newTransaction = this.createNewTransactionInstance();
        BigDecimal minUomQuantity = this.getInventoryEntity().calculateUomQuantity(this.getUom(), this.getQuantity());
        TransactionType transactionType;
        OrderLineInterface originPurchaseLine;
        if (this.getOrderLine().getOrder().isReturnOrder()) {
            minUomQuantity = minUomQuantity.multiply(new BigDecimal(-1));
            transactionType = TransactionType.returnOrder;
            originPurchaseLine = this.getOrderLine().getOriginPurchaseLine();
        } else {
            transactionType = TransactionType.purchaseOrder;
            originPurchaseLine = this.getOrderLine();
        }
        newTransaction.setPropertyValue(transactionType, minUomQuantity, this.getUuid(), this.getOrderLine().getOrder().getToWarehouse(), this.getInventoryEntity(), originPurchaseLine);
        return newTransaction;
    }
}
