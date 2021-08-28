package lukelin.his.domain.Interfaces.Inventory;

import java.util.Date;

import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;

public interface OrderInterface {
    boolean isReturnOrder();

    DepartmentWarehouse getToWarehouse();

    Date getApprovedDate();
}
