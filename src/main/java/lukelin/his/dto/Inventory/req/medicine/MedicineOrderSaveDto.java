package lukelin.his.dto.Inventory.req.medicine;

import lukelin.his.domain.entity.inventory.medicine.MedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderRequest;
import lukelin.his.dto.Inventory.req.BaseOrderSaveDto;

import java.util.ArrayList;
import java.util.List;

public class MedicineOrderSaveDto extends BaseOrderSaveDto {
    private List<MedicineOrderLineSaveDto> orderLineList;

    public List<MedicineOrderLineSaveDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<MedicineOrderLineSaveDto> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public MedicineOrder toEntity() {
        MedicineOrder medicineOrder = new MedicineOrder();
        super.setOrderProperty(medicineOrder);
        List<MedicineOrderLine> lineList = new ArrayList<>();
        for (MedicineOrderLineSaveDto medicineOrderLineSaveDto : this.getOrderLineList())
            lineList.add(medicineOrderLineSaveDto.toEntity());
        medicineOrder.setLineList(lineList);
        if (this.getOrderRequestId() != null)
            medicineOrder.setOrderRequest(new MedicineOrderRequest(this.getOrderRequestId()));
        return medicineOrder;
    }
}
