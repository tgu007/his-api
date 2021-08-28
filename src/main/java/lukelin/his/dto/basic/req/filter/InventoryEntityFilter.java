package lukelin.his.dto.basic.req.filter;

import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.Inventory.req.filter.StockFilterDto;
import lukelin.his.dto.basic.SearchCodeDto;

public class InventoryEntityFilter extends EntityFilter {
    private StockFilterDto stockFilter;

    private Boolean ybNotUploaded = false;

    private Boolean ybNotMatched = false;

    public Boolean getYbNotMatched() {
        return ybNotMatched;
    }

    public void setYbNotMatched(Boolean ybNotMatched) {
        this.ybNotMatched = ybNotMatched;
    }

    public Boolean getYbNotUploaded() {
        return ybNotUploaded;
    }

    public void setYbNotUploaded(Boolean ybNotUploaded) {
        this.ybNotUploaded = ybNotUploaded;
    }

    public StockFilterDto getStockFilter() {
        return stockFilter;
    }

    public void setStockFilter(StockFilterDto stockFilter) {
        this.stockFilter = stockFilter;
    }
}
