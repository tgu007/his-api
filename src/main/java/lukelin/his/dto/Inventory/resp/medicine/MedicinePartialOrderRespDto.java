package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BasePartialOrderRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderListRespDto;

public class MedicinePartialOrderRespDto extends BasePartialOrderRespDto {
    private MedicineOrderListRespDto masterOrder;

    private String YBOrderId;

    private String ybImageId;

    public String getYbImageId() {
        return ybImageId;
    }

    public void setYbImageId(String ybImageId) {
        this.ybImageId = ybImageId;
    }

    public String getYBOrderId() {
        return YBOrderId;
    }

    public void setYBOrderId(String YBOrderId) {
        this.YBOrderId = YBOrderId;
    }

    public MedicineOrderListRespDto getMasterOrder() {
        return masterOrder;
    }

    public void setMasterOrder(MedicineOrderListRespDto masterOrder) {
        this.masterOrder = masterOrder;
    }
}
