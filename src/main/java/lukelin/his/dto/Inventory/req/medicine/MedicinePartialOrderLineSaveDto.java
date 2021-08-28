package lukelin.his.dto.Inventory.req.medicine;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrderLine;
import lukelin.his.dto.Inventory.req.BasePartialOrderLineSaveDto;

public class MedicinePartialOrderLineSaveDto extends BasePartialOrderLineSaveDto {
    public MedicinePartialOrderLine toEntity() {
        MedicinePartialOrderLine orderLine = new MedicinePartialOrderLine();
        BeanUtils.copyPropertiesIgnoreNull(this, orderLine);
        orderLine.setUom(new UnitOfMeasure(this.getUomId()));
        orderLine.setMasterOrderLine(new MedicineOrderLine(this.getMasterOrderLineId()));
        return orderLine;
    }
}
