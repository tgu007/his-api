package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BaseOrderLineRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineSnapshotRespDto;
import lukelin.his.dto.basic.resp.setup.ManufacturerMedicineRespDto;

import java.util.Date;

public class MedicineOrderLineRespDto extends BaseOrderLineRespDto {
    private MedicineSnapshotRespDto medicine;

    private String batchText;

    private Date expireDate;

    private ManufacturerMedicineRespDto manufacturer;

    public MedicineSnapshotRespDto getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineSnapshotRespDto medicine) {
        this.medicine = medicine;
    }

    public String getBatchText() {
        return batchText;
    }

    public void setBatchText(String batchText) {
        this.batchText = batchText;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public ManufacturerMedicineRespDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerMedicineRespDto manufacturer) {
        this.manufacturer = manufacturer;
    }
}
