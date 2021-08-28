package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Inventory.StockMovementRule;
import lukelin.his.domain.enums.Inventory.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TransferLineInterface extends StockTransactionInterface {
    BigDecimal getQuantity();

    UnitOfMeasure getUom();

    TransferInterface getTransfer();


    default List<CachedTransactionInterface> createTransferTransactionList() {
        BigDecimal totalQuantityToUpdate = this.getInventoryEntity().calculateUomQuantity(this.getUom(), this.getQuantity());

        StockMovementRule movementRule = StockMovementRule.inFirstOutFirst;
        if (this.getTransfer().getToWarehouse().getWarehouseType() == WarehouseType.levelOneWarehouse)
            movementRule = StockMovementRule.inFirstOutLast;; //退货到库房，后进先出
        if (this.getTransfer().getToWarehouse().getWarehouseType() != WarehouseType.levelOneWarehouse && this.getInventoryEntity().getEntityType() == EntityType.medicine)
            movementRule = StockMovementRule.expireDateFirst;; //科室领药，药品调拨，过期日期优先
        List<? extends CachedEntityStockInterface> currentStockList= this.getCurrentStockList(totalQuantityToUpdate, this.getTransfer().getFromWarehouse(), movementRule);
        return this.createStockListTransaction(currentStockList, totalQuantityToUpdate, this.getTransfer().getFromWarehouse());
    }

    default List<CachedTransactionInterface> createStockTransaction(BigDecimal newTransactionQuantity, UUID reasonId, InventoryEntityInterface inventoryEntity, OrderLineInterface originPurchaseLine, DepartmentWarehouse departmentWarehouse) {
        CachedTransactionInterface newTransaction;
        List<CachedTransactionInterface> newStockTransactionList = new ArrayList<>();
        //出库记录
        newTransaction = this.createNewTransactionInstance();
        newTransaction.setPropertyValue(TransactionType.transferOut, newTransactionQuantity.multiply(new BigDecimal(-1)), this.getUuid(), departmentWarehouse, this.getInventoryEntity(), originPurchaseLine);
        newStockTransactionList.add(newTransaction);
//        //入库记录
//        newTransaction = this.createNewTransactionInstance();
//        newTransaction.setPropertyValue(TransactionType.transferIn, newTransactionQuantity, this.getUuid(), this.getTransfer().getToWarehouse(),   this.getInventoryEntity(), originPurchaseLine);
//        newStockTransactionList.add(newTransaction);
        return newStockTransactionList;
    }
}
