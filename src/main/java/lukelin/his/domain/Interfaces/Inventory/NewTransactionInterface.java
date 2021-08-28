package lukelin.his.domain.Interfaces.Inventory;

import java.util.UUID;

public interface NewTransactionInterface {
    CachedTransactionInterface createNewTransactionInstance();

    InventoryEntityInterface getInventoryEntity();

    UUID getUuid();
}
