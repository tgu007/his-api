package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;

public interface TransferInterface {
    DepartmentWarehouse getFromWarehouse();

    DepartmentWarehouse getToWarehouse();

}
