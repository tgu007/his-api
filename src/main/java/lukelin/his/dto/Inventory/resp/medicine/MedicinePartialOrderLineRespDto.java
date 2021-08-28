package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BasePartialOrderLineRespDto;

public class MedicinePartialOrderLineRespDto extends BasePartialOrderLineRespDto {
    private MedicineOrderLineRespDto masterOrderLine;

    public MedicineOrderLineRespDto getMasterOrderLine() {
        return masterOrderLine;
    }

    public void setMasterOrderLine(MedicineOrderLineRespDto masterOrderLine) {
        this.masterOrderLine = masterOrderLine;
    }
}
