package lukelin.his.dto.prescription.request;

import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineOrderCreateDto {
    private List<PrescriptionMedicineOrderLineCreateDto> orderLineList;

    private UUID wardId;

    public UUID getWardId() {
        return wardId;
    }

    public void setWardId(UUID wardId) {
        this.wardId = wardId;
    }


    public List<PrescriptionMedicineOrderLineCreateDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<PrescriptionMedicineOrderLineCreateDto> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public PrescriptionMedicineOrder toEntity() {
        PrescriptionMedicineOrder order = new PrescriptionMedicineOrder();
        order.setStatus(PrescriptionMedicineOrderStatus.submitted);
        order.setWard(new Ward(wardId));

        List<PrescriptionMedicineOrderLine> lineList = new ArrayList<>();
        for (PrescriptionMedicineOrderLineCreateDto createOrderLineDto : this.getOrderLineList()) {
            PrescriptionMedicine prescriptionMedicine = Ebean.find(PrescriptionMedicine.class, createOrderLineDto.getPrescriptionMedicineId());
            PrescriptionMedicineOrderLine orderLine = new PrescriptionMedicineOrderLine();
            orderLine.setStatus(PrescriptionMedicineOrderLineStatus.pending);
            orderLine.setMedicineSnapshot(prescriptionMedicine.getMedicine().findLatestSnapshot());
            orderLine.setPrescriptionMedicine(prescriptionMedicine);
            orderLine.setQuantity(new BigDecimal(createOrderLineDto.getOrderQuantity()));
            orderLine.setPatientSignIn(prescriptionMedicine.getPrescriptionChargeable().getPrescription().getPatientSignIn());
            orderLine.setPatientName(orderLine.getPatientSignIn().getPatient().getName());
            orderLine.setPatientSignInCode(orderLine.getPatientSignIn().getSignInNumberCode());
            orderLine.setMedicine(prescriptionMedicine.getMedicine());
            lineList.add(orderLine);
            prescriptionMedicine.setLast_order_date(new Date());
            //ebeanServer.update(prescriptionMedicine);
        }
        order.setLineList(lineList);
        return order;
    }

}
