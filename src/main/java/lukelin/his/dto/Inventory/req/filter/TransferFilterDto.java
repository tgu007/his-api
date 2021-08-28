package lukelin.his.dto.Inventory.req.filter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TransferFilterDto {
    private List<UUID> fromWarehouseIdList;

    private List<UUID> toWarehouseIdList;

    private Date startDate;

    private Date endDate;

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

    public List<UUID> getFromWarehouseIdList() {
        return fromWarehouseIdList;
    }

    public void setFromWarehouseIdList(List<UUID> fromWarehouseIdList) {
        this.fromWarehouseIdList = fromWarehouseIdList;
    }

    public List<UUID> getToWarehouseIdList() {
        return toWarehouseIdList;
    }

    public void setToWarehouseIdList(List<UUID> toWarehouseIdList) {
        this.toWarehouseIdList = toWarehouseIdList;
    }
}
