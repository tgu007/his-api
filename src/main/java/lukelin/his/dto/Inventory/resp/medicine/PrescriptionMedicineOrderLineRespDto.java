package lukelin.his.dto.Inventory.resp.medicine;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.domain.enums.YB.PharmacyOrderUploadStatus;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;

import java.util.Date;
import java.util.UUID;

public class PrescriptionMedicineOrderLineRespDto extends BaseWardPatientListRespDto {
    private UUID uuid;

    private UUID prescriptionMedicineId;

    private PrescriptionMedicineOrderLineCommonDto orderLineCommon;

    private PrescriptionMedicineOrderLineStatus status;

    private String orderNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date processedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date returnOrderProcessedDate;

    private int orderQuantity;

    private String orderQuantityInfo;

    private PharmacyOrderUploadStatus uploadStatus;

    private String uploadError;

    private Boolean slipRequired;

    private Boolean prescriptionValid;

    private String prescriptionDescription;

    public Boolean getPrescriptionValid() {
        return prescriptionValid;
    }

    public void setPrescriptionValid(Boolean prescriptionValid) {
        this.prescriptionValid = prescriptionValid;
    }

    public String getPrescriptionDescription() {
        return prescriptionDescription;
    }

    public void setPrescriptionDescription(String prescriptionDescription) {
        this.prescriptionDescription = prescriptionDescription;
    }

    public Boolean getSlipRequired() {
        return slipRequired;
    }

    public void setSlipRequired(Boolean slipRequired) {
        this.slipRequired = slipRequired;
    }

    public PharmacyOrderUploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(PharmacyOrderUploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getUploadError() {
        return uploadError;
    }

    public void setUploadError(String uploadError) {
        this.uploadError = uploadError;
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

    public Date getReturnOrderProcessedDate() {
        return returnOrderProcessedDate;
    }

    public void setReturnOrderProcessedDate(Date returnOrderProcessedDate) {
        this.returnOrderProcessedDate = returnOrderProcessedDate;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public PrescriptionMedicineOrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionMedicineOrderLineStatus status) {
        this.status = status;
    }

    public UUID getPrescriptionMedicineId() {
        return prescriptionMedicineId;
    }

    public void setPrescriptionMedicineId(UUID prescriptionMedicineId) {
        this.prescriptionMedicineId = prescriptionMedicineId;
    }

    public PrescriptionMedicineOrderLineCommonDto getOrderLineCommon() {
        return orderLineCommon;
    }

    public void setOrderLineCommon(PrescriptionMedicineOrderLineCommonDto orderLineCommon) {
        this.orderLineCommon = orderLineCommon;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}
