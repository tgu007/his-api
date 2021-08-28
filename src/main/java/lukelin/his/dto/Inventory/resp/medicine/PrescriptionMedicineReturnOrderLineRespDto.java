package lukelin.his.dto.Inventory.resp.medicine;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineReturnOrderLineStatus;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;

import java.util.Date;
import java.util.UUID;

public class PrescriptionMedicineReturnOrderLineRespDto extends BaseWardPatientListRespDto {
    private UUID uuid;

    private PrescriptionMedicineOrderLineRespDto originOrderLine;

    private PrescriptionMedicineReturnOrderLineStatus status;

    private String orderNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date processedDate;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PrescriptionMedicineOrderLineRespDto getOriginOrderLine() {
        return originOrderLine;
    }

    public void setOriginOrderLine(PrescriptionMedicineOrderLineRespDto originOrderLine) {
        this.originOrderLine = originOrderLine;
    }

    public PrescriptionMedicineReturnOrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionMedicineReturnOrderLineStatus status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
}
