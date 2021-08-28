package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BaseTransferLineRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineSnapshotRespDto;

public class MedicineTransferLineRespDto extends BaseTransferLineRespDto {
    private MedicineSnapshotRespDto medicine;

    public MedicineSnapshotRespDto getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineSnapshotRespDto medicine) {
        this.medicine = medicine;
    }
}
