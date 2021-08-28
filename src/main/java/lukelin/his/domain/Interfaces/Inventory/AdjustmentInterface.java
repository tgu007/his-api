package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.enums.Inventory.StockMovementRule;
import lukelin.his.domain.enums.Inventory.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface AdjustmentInterface extends StockTransactionInterface {
    default List<CachedTransactionInterface> createAdjustmentTransaction() {
        BigDecimal totalQuantityToUpdate = this.getOldQuantity().subtract(this.getNewQuantity());
        List<? extends CachedEntityStockInterface> currentStockList = this.getCurrentStockList(totalQuantityToUpdate, this.getWarehouse(), StockMovementRule.inFirstOutLast);

        List<CachedTransactionInterface> newTransactionList = new ArrayList<>();
        //增加库存
        if (totalQuantityToUpdate.compareTo(BigDecimal.ZERO) < 0) {
            //如果为增加，则选取第一条库存信息
            OrderLineInterface originPurchaseLine = currentStockList.stream().findFirst().get().getOriginPurchaseLine();
            CachedTransactionInterface newTransaction = this.createNewTransactionInstance();
            newTransaction.setPropertyValue(TransactionType.adjustmentUp, totalQuantityToUpdate.multiply(new BigDecimal(-1)), this.getUuid(), this.getWarehouse(), this.getInventoryEntity(), originPurchaseLine);
            newTransactionList.add(newTransaction);
        } else //减少库存
            newTransactionList.addAll(this.createStockListTransaction(currentStockList, totalQuantityToUpdate, this.getWarehouse()));

        return newTransactionList;

    }

    default List<CachedTransactionInterface> createStockTransaction(BigDecimal newTransactionQuantity, UUID reasonId, InventoryEntityInterface inventoryEntity, OrderLineInterface originPurchaseLine, DepartmentWarehouse warehouse) {
        List<CachedTransactionInterface> newStockTransactionList = new ArrayList<>();
        CachedTransactionInterface newTransaction = this.createNewTransactionInstance();
        newTransaction.setPropertyValue(TransactionType.adjustmentDown, newTransactionQuantity.multiply(new BigDecimal(-1)), this.getUuid(), warehouse, this.getInventoryEntity(), originPurchaseLine);
        newStockTransactionList.add(newTransaction);
        return newStockTransactionList;
    }

    DepartmentWarehouse getWarehouse();

    BigDecimal getOldQuantity();

    BigDecimal getNewQuantity();
}
