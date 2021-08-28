package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionMedicineOrderLineStatus {
    pending("待处理"),
    approved("已发药"),
    rejected("未发药"),
    pendingReturn("退药中"),
    returned("已退药"),
    pendingConfirm("待确认");

    private String description;

    PrescriptionMedicineOrderLineStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String PrescriptionStatus() {
        return this.description;
    }
}
