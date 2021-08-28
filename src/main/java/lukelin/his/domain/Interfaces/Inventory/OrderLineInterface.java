package lukelin.his.domain.Interfaces.Inventory;

import java.math.BigDecimal;

public interface OrderLineInterface extends InventoryLineInterface {
    OrderLineInterface getOriginPurchaseLine();

    OrderInterface getOrder();

    BigDecimal getTotalCost();

    BigDecimal getMinUomQuantity();
}
