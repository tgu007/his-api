package lukelin.his.domain.Interfaces.Inventory;

import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.enums.Inventory.StockMovementRule;
import lukelin.his.system.NoStockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface StockTransactionInterface extends NewTransactionInterface {

    List<CachedTransactionInterface> createStockTransaction(BigDecimal newTransactionQuantity, UUID reasonId, InventoryEntityInterface inventoryEntity, OrderLineInterface originPurchaseLine, DepartmentWarehouse warehouse);

    default List<CachedTransactionInterface> createStockListTransaction(List<? extends CachedEntityStockInterface> currentStockList, BigDecimal totalQuantityToUpdate, DepartmentWarehouse warehouse) {
        List<CachedTransactionInterface> newTransactionList = new ArrayList<>();
        for (CachedEntityStockInterface stock : currentStockList) {
            BigDecimal newTransactionQuantity;
            //判断当前单条库存数量是否大于出库数量
            if (stock.getMinUomQuantity().subtract(totalQuantityToUpdate).compareTo(BigDecimal.ZERO) > 0)
                newTransactionQuantity = totalQuantityToUpdate;
            else
                newTransactionQuantity = stock.getMinUomQuantity();


            List<CachedTransactionInterface> newStockTransactionList = this.createStockTransaction(newTransactionQuantity, this.getUuid(), this.getInventoryEntity(), stock.getOriginPurchaseLine(), warehouse);
            newTransactionList.addAll(newStockTransactionList);

            totalQuantityToUpdate = totalQuantityToUpdate.subtract(newTransactionQuantity);
            if (totalQuantityToUpdate.compareTo(BigDecimal.ZERO) == 0)
                break;
        }
        return newTransactionList;
    }


    default List<? extends CachedEntityStockInterface> getCurrentStockList(BigDecimal quantityToUpdate, DepartmentWarehouse warehouse, StockMovementRule movementRule) {
        //List<? extends CachedEntityStockInterface> test =  this.getInventoryEntity().getStockList();
        List<? extends CachedEntityStockInterface> currentStockList = this.getInventoryEntity().getStockList().stream().filter(s -> s.getWarehouse().getUuid().equals(warehouse.getUuid())).collect(Collectors.toList());
        BigDecimal totoMinUomQuantity = currentStockList.stream().map(CachedEntityStockInterface::getMinUomQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);

        //Todo 需验证完整个TRANSFER返回所有错误信息
        if (totoMinUomQuantity.subtract(quantityToUpdate).compareTo(BigDecimal.ZERO) < 0)
            throw new NoStockException(this.getInventoryEntity().getName() + "库存不足");

        if (movementRule == StockMovementRule.expireDateFirst) {
            Comparator<CachedEntityStockInterface> expireDateComparator = Comparator.comparing(s -> ((MedicineOrderLineInterface) s.getOriginPurchaseLine()).getExpireDate());
            currentStockList.sort(expireDateComparator); //过期先出
        } else {
            Comparator<CachedEntityStockInterface> dateComparator = Comparator.comparing(s -> s.getOriginPurchaseLine().getOrder().getApprovedDate());
            if (movementRule == StockMovementRule.inFirstOutFirst)
                currentStockList.sort(dateComparator); //先进先出
            else//后进先出
                currentStockList.sort(dateComparator.reversed());
        }
        return currentStockList;
    }
}
