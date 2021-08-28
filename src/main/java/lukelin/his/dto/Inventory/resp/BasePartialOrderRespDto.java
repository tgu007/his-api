package lukelin.his.dto.Inventory.resp;


import lukelin.his.domain.enums.Inventory.OrderStatus;

import java.util.List;
import java.util.UUID;

public abstract class BasePartialOrderRespDto {
    private UUID uuid;

    private String orderNumber;

    private OrderStatus orderStatus;

    private List<BasePartialOrderLineRespDto> lineList;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<BasePartialOrderLineRespDto> getLineList() {
        return lineList;
    }

    public void setLineList(List<BasePartialOrderLineRespDto> lineList) {
        this.lineList = lineList;
    }
}
