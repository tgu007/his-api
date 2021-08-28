package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;

import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineOrderMedicineSummaryRespDto {
    private UUID medicineId;

    private PrescriptionMedicineOrderLineCommonDto orderLineCommon;

    private String patientsName;

    private PrescriptionMedicineOrderLineStatus status;

    private List<String> patientNameList;

    private int orderQuantity;

    private String orderQuantityInfo;

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

    public List<String> getPatientNameList() {
        return patientNameList;
    }

    public void setPatientNameList(List<String> patientNameList) {
        this.patientNameList = patientNameList;
    }

    public PrescriptionMedicineOrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionMedicineOrderLineStatus status) {
        this.status = status;
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public PrescriptionMedicineOrderLineCommonDto getOrderLineCommon() {
        return orderLineCommon;
    }

    public void setOrderLineCommon(PrescriptionMedicineOrderLineCommonDto orderLineCommon) {
        this.orderLineCommon = orderLineCommon;
    }

    public String getPatientsName() {
        return String.join(",", this.patientNameList);
    }

    public void setPatientsName(String patientsName) {
        this.patientsName = patientsName;
    }
}