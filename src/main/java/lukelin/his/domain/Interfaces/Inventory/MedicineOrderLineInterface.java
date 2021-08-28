package lukelin.his.domain.Interfaces.Inventory;

import java.util.Date;

public interface MedicineOrderLineInterface extends OrderLineInterface {
    Date  getExpireDate();
}
