package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;

import java.util.UUID;

public class BasePrescriptionMedicineOrderRespDto {
    private UUID uuid;

    private String orderNumberCode;

    private String orderTimeInfo;

    PrescriptionMedicineOrderStatus status;

    private String submitBy;

    private String wardName;

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getSubmitBy() {
        return submitBy;
    }

    public void setSubmitBy(String submitBy) {
        this.submitBy = submitBy;
    }

    public PrescriptionMedicineOrderStatus getStatus() {
        return status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setStatus(PrescriptionMedicineOrderStatus status) {
        this.status = status;
    }

    public String getOrderNumberCode() {
        return orderNumberCode;
    }

    public void setOrderNumberCode(String orderNumberCode) {
        this.orderNumberCode = orderNumberCode;
    }

    public String getOrderTimeInfo() {
        return orderTimeInfo;
    }

    public void setOrderTimeInfo(String orderTimeInfo) {
        this.orderTimeInfo = orderTimeInfo;
    }
}
