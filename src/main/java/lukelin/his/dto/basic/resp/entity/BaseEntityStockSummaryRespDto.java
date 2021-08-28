package lukelin.his.dto.basic.resp.entity;

import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;

import java.math.BigDecimal;
import java.util.List;

public abstract class BaseEntityStockSummaryRespDto {
    private BigDecimal totalQuantity;

    private BigDecimal totalValue;

    private String minUomDisplayQuantity;

    private String warehouseDisplayQuantity;

    private String departmentDisplayQuantity;

    private List<BaseStockSummaryRespDto> stockSummaryList;

    public List<BaseStockSummaryRespDto> getStockSummaryList() {
        return stockSummaryList;
    }

    public BigDecimal getTotalCost() {
        return stockSummaryList.stream().map(BaseStockSummaryRespDto::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2);
    }

    public BigDecimal getTotalValue() {
        return stockSummaryList.stream().map(BaseStockSummaryRespDto::getTotalValue).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2);
    }

    public void setStockSummaryList(List<BaseStockSummaryRespDto> stockSummaryList) {
        this.stockSummaryList = stockSummaryList;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getMinUomDisplayQuantity() {
        return minUomDisplayQuantity;
    }

    public void setMinUomDisplayQuantity(String minUomDisplayQuantity) {
        this.minUomDisplayQuantity = minUomDisplayQuantity;
    }

    public String getWarehouseDisplayQuantity() {
        return warehouseDisplayQuantity;
    }

    public void setWarehouseDisplayQuantity(String warehouseDisplayQuantity) {
        this.warehouseDisplayQuantity = warehouseDisplayQuantity;
    }

    public String getDepartmentDisplayQuantity() {
        return departmentDisplayQuantity;
    }

    public void setDepartmentDisplayQuantity(String departmentDisplayQuantity) {
        this.departmentDisplayQuantity = departmentDisplayQuantity;
    }
}
