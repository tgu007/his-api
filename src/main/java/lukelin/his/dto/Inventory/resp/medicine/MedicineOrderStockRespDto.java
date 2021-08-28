package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineRespDto;

public class MedicineOrderStockRespDto extends BaseStockSummaryRespDto {

    private MedicineOrderLineRespDto originPurchaseOrderLine;

    public MedicineOrderLineRespDto getOriginPurchaseOrderLine() {
        return originPurchaseOrderLine;
    }

    public void setOriginPurchaseOrderLine(MedicineOrderLineRespDto originPurchaseOrderLine) {
        this.originPurchaseOrderLine = originPurchaseOrderLine;
    }
}
