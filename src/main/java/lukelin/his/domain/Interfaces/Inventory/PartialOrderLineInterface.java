package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.enums.Inventory.TransactionType;

import java.math.BigDecimal;

public interface PartialOrderLineInterface extends InventoryLineInterface {


    OrderLineInterface getMasterOrderLine();

    default OrderLineInterface getOrderLine() {
        return this.getMasterOrderLine();
    }

//    default CachedTransactionInterface createOrderLineTransaction() {
//        CachedTransactionInterface newTransaction = this.createNewTransactionInstance();
//        BigDecimal minUomQuantity = this.getInventoryEntity().calculateUomQuantity(this.getUom(), this.getQuantity());
//        TransactionType transactionType;
//        OrderLineInterface originPurchaseLine;
//        if (this.getMasterOrderLine().getOrder().isReturnOrder()) {
//            minUomQuantity = minUomQuantity.multiply(new BigDecimal(-1));
//            transactionType = TransactionType.returnOrder;
//            originPurchaseLine = this.getMasterOrderLine().getOriginPurchaseLine();
//        } else {
//            transactionType = TransactionType.purchaseOrder;
//            originPurchaseLine = this.getMasterOrderLine();
//        }
//        newTransaction.setPropertyValue(transactionType, minUomQuantity, this.getUuid(),this.getMasterOrderLine().getOrder().getToWarehouse(), this.getInventoryEntity(), originPurchaseLine);
//        return newTransaction;
//    }

}
