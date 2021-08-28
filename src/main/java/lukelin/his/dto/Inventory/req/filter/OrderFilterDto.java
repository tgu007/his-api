package lukelin.his.dto.Inventory.req.filter;

import lukelin.his.domain.enums.Inventory.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderFilterDto {
    private Boolean returnOrder;

    private UUID warehouseId;

    private Date startDate;

    private Date endDate;

    //本身单号
    private String orderNumber;

    //本身单号或者关联单号查询
    private String searchNumber;

    public String getSearchNumber() {
        return searchNumber;
    }

    public void setSearchNumber(String searchNumber) {
        this.searchNumber = searchNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    private List<OrderStatus> orderStatusList;

    public List<OrderStatus> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(List<OrderStatus> orderStatusList) {
        this.orderStatusList = orderStatusList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Boolean getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(Boolean returnOrder) {
        this.returnOrder = returnOrder;
    }
}
