package lukelin.his.dto.Inventory.req.medicine;

import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrder;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrderLine;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineReturnOrderLineStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineReturnOrderCreateDto {
    private List<UUID> originOrderLineIdList;

    private UUID wardId;

    public UUID getWardId() {
        return wardId;
    }

    public void setWardId(UUID wardId) {
        this.wardId = wardId;
    }

    public List<UUID> getOriginOrderLineIdList() {
        return originOrderLineIdList;
    }

    public void setOriginOrderLineIdList(List<UUID> originOrderLineIdList) {
        this.originOrderLineIdList = originOrderLineIdList;
    }

    public PrescriptionMedicineReturnOrder toEntity() {
        PrescriptionMedicineReturnOrder returnOrder = new PrescriptionMedicineReturnOrder();
        returnOrder.setStatus(PrescriptionMedicineOrderStatus.submitted);
        returnOrder.setWard(new Ward(wardId));

        List<PrescriptionMedicineReturnOrderLine> lineList = new ArrayList<>();
        for (UUID orderLineId : this.getOriginOrderLineIdList()) {
            PrescriptionMedicineOrderLine orderLine = Ebean.find(PrescriptionMedicineOrderLine.class, orderLineId);
            PrescriptionMedicineReturnOrderLine returnOrderLine = orderLine.createReturnOrderLine();
            lineList.add(returnOrderLine);
        }
        returnOrder.setLineList(lineList);
        return returnOrder;
    }
}
