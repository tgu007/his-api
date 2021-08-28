package lukelin.his.dto.Inventory.req.medicine;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.Brand;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.dto.Inventory.req.BaseOrderLineSaveDto;

import java.util.Date;
import java.util.UUID;

public class MedicineOrderLineSaveDto extends BaseOrderLineSaveDto {
    private UUID medicineId;

    private String batchText;

    private Date expireDate;


    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
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

    public MedicineOrderLine toEntity() {
        MedicineOrderLine orderLine = new MedicineOrderLine();
        super.setOrderLineProperty(orderLine);
        BeanUtils.copyPropertiesIgnoreNull(this, orderLine);
        orderLine.setExpireDate(this.expireDate);
        if (this.getManufacturerId() != null)
            orderLine.setManufacturerMedicine(new ManufacturerMedicine(this.getManufacturerId()));

        if (this.getBrandId() != null)
            orderLine.setBrand(new Brand(this.getBrandId()));

        Medicine medicine = new Medicine(this.getMedicineId());
        orderLine.setMedicine(medicine);

        if (this.getOriginPurchaseLineId() != null) {
            //for return order
            MedicineOrderLine originPurchaseLine = Ebean.find(MedicineOrderLine.class, this.getOriginPurchaseLineId());
            orderLine.setOriginPurchaseLine(originPurchaseLine);
            orderLine.setMedicineSnapshot(originPurchaseLine.getMedicineSnapshot());
        } else {
            //找到最新的SNAPSHOT
            MedicineSnapshot snapshot = medicine.findLatestSnapshot();
            orderLine.setMedicineSnapshot(snapshot);
        }

        return orderLine;
    }

}
