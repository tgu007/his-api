package lukelin.his.dto.Inventory.req.filter;

import lukelin.his.domain.enums.Inventory.OrderRequestStatus;

import java.util.Date;
import java.util.List;

public class OrderRequestFilterDto {
    private Date startDate;

    private Date endDate;

    private String orderNumber;

    private List<OrderRequestStatus> orderStatusList;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public List<OrderRequestStatus> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(List<OrderRequestStatus> orderStatusList) {
        this.orderStatusList = orderStatusList;
    }
}
