package lukelin.his.dto.Inventory.req.filter;

import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PrescriptionMedicineOrderFilterDto {
    private String searchCode;

    private Date startDate;

    private Date endDate;

    private List<UUID> patientSignInIdList;

    private PrescriptionMedicineOrderLineStatus lineStatus;

    private String medicineSearchCode;

    private UUID warehouseId;

    private Boolean pendingUpload;

    public Boolean getPendingUpload() {
        return pendingUpload;
    }

    public void setPendingUpload(Boolean pendingUpload) {
        this.pendingUpload = pendingUpload;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getMedicineSearchCode() {
        return medicineSearchCode;
    }

    public void setMedicineSearchCode(String medicineSearchCode) {
        this.medicineSearchCode = medicineSearchCode;
    }

    public PrescriptionMedicineOrderLineStatus getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(PrescriptionMedicineOrderLineStatus lineStatus) {
        this.lineStatus = lineStatus;
    }

    public List<UUID> getPatientSignInIdList() {
        return patientSignInIdList;
    }

    public void setPatientSignInIdList(List<UUID> patientSignInIdList) {
        this.patientSignInIdList = patientSignInIdList;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
