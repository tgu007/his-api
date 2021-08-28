package lukelin.his.dto.Inventory.req.medicine;

import java.util.List;

public class PrescriptionMedicineOrderProcessDto {
    private List<PrescriptionMedicineOrderLineProcessDto> orderLineList;


    public List<PrescriptionMedicineOrderLineProcessDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<PrescriptionMedicineOrderLineProcessDto> orderLineList) {
        this.orderLineList = orderLineList;
    }
}
