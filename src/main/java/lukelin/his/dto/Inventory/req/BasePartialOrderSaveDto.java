package lukelin.his.dto.Inventory.req;

import lukelin.his.domain.enums.Inventory.OrderStatus;

import java.util.Date;
import java.util.UUID;

public abstract class BasePartialOrderSaveDto {
    private UUID uuid;

    private OrderStatus orderStatus;

    private UUID approveById;

    private UUID masterOrderId;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getApproveById() {
        return approveById;
    }

    public void setApproveById(UUID approveById) {
        this.approveById = approveById;
    }

    public UUID getMasterOrderId() {
        return masterOrderId;
    }

    public void setMasterOrderId(UUID masterOrderId) {
        this.masterOrderId = masterOrderId;
    }
}
