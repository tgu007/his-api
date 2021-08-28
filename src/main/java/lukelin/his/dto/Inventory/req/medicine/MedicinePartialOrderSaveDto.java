package lukelin.his.dto.Inventory.req.medicine;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.inventory.item.ItemOrder;
import lukelin.his.domain.entity.inventory.item.ItemPartialOrder;
import lukelin.his.domain.entity.inventory.item.ItemPartialOrderLine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrderLine;
import lukelin.his.dto.Inventory.req.BasePartialOrderSaveDto;
import lukelin.his.dto.Inventory.req.item.ItemPartialOrderLineSaveDto;

import java.util.ArrayList;
import java.util.List;

public class MedicinePartialOrderSaveDto extends BasePartialOrderSaveDto {
    private List<MedicinePartialOrderLineSaveDto> orderLineList;

    public List<MedicinePartialOrderLineSaveDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<MedicinePartialOrderLineSaveDto> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public MedicinePartialOrder toEntity() {
        MedicinePartialOrder medicinePartialOrder = new MedicinePartialOrder();
        BeanUtils.copyPropertiesIgnoreNull(this, medicinePartialOrder);
        List<MedicinePartialOrderLine> lineList = new ArrayList<>();
        for (MedicinePartialOrderLineSaveDto medicinePartialOrderLineSaveDto : this.getOrderLineList())
            lineList.add(medicinePartialOrderLineSaveDto.toEntity());
        medicinePartialOrder.setLineList(lineList);
        medicinePartialOrder.setMasterOrder(new MedicineOrder(this.getMasterOrderId()));
        return medicinePartialOrder;
    }
}
