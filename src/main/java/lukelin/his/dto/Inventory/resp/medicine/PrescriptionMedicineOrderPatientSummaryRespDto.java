package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;

import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineOrderPatientSummaryRespDto extends BaseWardPatientListRespDto {
    private UUID medicineId;

    private int orderQuantity;

    private String orderQuantityInfo;

    private String pharmacyModel;

    private String medicineName;

    private String pharmacyUom;

    private List<PrescriptionMedicineOrderLineRespDto> orderLineList;

    public String getPharmacyUom() {
        return pharmacyUom;
    }

    public void setPharmacyUom(String pharmacyUom) {
        this.pharmacyUom = pharmacyUom;
    }


    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
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

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public List<PrescriptionMedicineOrderLineRespDto> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<PrescriptionMedicineOrderLineRespDto> orderLineList) {
        this.orderLineList = orderLineList;
    }
}