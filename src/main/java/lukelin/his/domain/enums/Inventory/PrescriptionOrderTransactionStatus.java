package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionOrderTransactionStatus {
    confirmed("已确认"),
    pendingConfirm("待确认"),
    returned("已退药");

    private String description;

    PrescriptionOrderTransactionStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
