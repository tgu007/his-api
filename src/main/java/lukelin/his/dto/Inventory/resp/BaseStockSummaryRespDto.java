package lukelin.his.dto.Inventory.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.dto.basic.resp.setup.DepartmentWarehouseDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BaseStockSummaryRespDto {
    private DepartmentWarehouseDto warehouse;

    private BigDecimal minUomQuantity;

    private String displayQuantity;

    private String warehouseDisplayQuantity;

    private BigDecimal totalValue;

    private BigDecimal totalCost;

    private List<UUID> stockIdList;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date firstExpireDate;

    public Date getFirstExpireDate() {
        return firstExpireDate;
    }

    public void setFirstExpireDate(Date firstExpireDate) {
        this.firstExpireDate = firstExpireDate;
    }

    public List<UUID> getStockIdList() {
        return stockIdList;
    }

    public void setStockIdList(List<UUID> stockIdList) {
        this.stockIdList = stockIdList;
    }

    public String getWarehouseDisplayQuantity() {
        return warehouseDisplayQuantity;
    }

    public void setWarehouseDisplayQuantity(String warehouseDisplayQuantity) {
        this.warehouseDisplayQuantity = warehouseDisplayQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public DepartmentWarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(DepartmentWarehouseDto warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getMinUomQuantity() {
        return minUomQuantity;
    }

    public void setMinUomQuantity(BigDecimal minUomQuantity) {
        this.minUomQuantity = minUomQuantity;
    }

    public String getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(String displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

}
