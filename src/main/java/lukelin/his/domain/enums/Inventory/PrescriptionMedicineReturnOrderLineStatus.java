package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionMedicineReturnOrderLineStatus {
    pending("待处理"),
    approved("已退"),
    rejected("未退药"),
    canceled("取消退药");

    private String description;

    PrescriptionMedicineReturnOrderLineStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String PrescriptionStatus() {
        return this.description;
    }
}
