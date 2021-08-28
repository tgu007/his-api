package lukelin.his.dto.Inventory.resp.medicine;

import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineReturnOrderMedicineSummaryRespDto {
    private UUID medicineId;

    private String medicineName;

    private int orderQuantity;

    private String orderQuantityInfo;

    private String pharmacyModel;

    private String pharmacyUom;

    private List<PrescriptionMedicineReturnOrderLineRespDto> orderLineList;

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderQuantityInfo() {
        return orderQuantityInfo;
    }

    public void setOrderQuantityInfo(String orderQuantityInfo) {
        this.orderQuantityInfo = orderQuantityInfo;
    }

    public String getPharmacyModel() {
        return pharmacyModel;
    }

    public void setPharmacyModel(String pharmacyModel) {
        this.pharmacyModel = pharmacyModel;
    }

    public String getPharmacyUom() {
        return pharmacyUom;
    }

    public void setPharmacyUom(String pharmacyUom) {
        this.pharmacyUom = pharmacyUom;
    }

    public List<PrescriptionMedicineReturnOrderLineRespDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<PrescriptionMedicineReturnOrderLineRespDto> orderLineList) {
        this.orderLineList = orderLineList;
    }
}
