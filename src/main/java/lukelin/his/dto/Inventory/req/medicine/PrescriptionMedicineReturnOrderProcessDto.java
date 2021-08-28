package lukelin.his.dto.Inventory.req.medicine;

import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineReturnOrderProcessDto {
    private List<UUID> returnOrderLineIdList;

    public List<UUID> getReturnOrderLineIdList() {
        return returnOrderLineIdList;
    }

    public void setReturnOrderLineIdList(List<UUID> returnOrderLineIdList) {
        this.returnOrderLineIdList = returnOrderLineIdList;
    }
}
