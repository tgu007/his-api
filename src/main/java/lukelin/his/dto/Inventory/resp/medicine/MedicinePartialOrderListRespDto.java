package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BasePartialOrderListRespDto;

public class MedicinePartialOrderListRespDto extends BasePartialOrderListRespDto {
    private MedicineOrderListRespDto masterOrder;

    public MedicineOrderListRespDto getMasterOrder() {
        return masterOrder;
    }

    public void setMasterOrder(MedicineOrderListRespDto masterOrder) {
        this.masterOrder = masterOrder;
    }
}
