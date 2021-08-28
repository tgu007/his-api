package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransferTransactionStatus {
    rejected("已退回"),
    confirmed("已确认"),
    pendingConfirm("待确认");

    private String description;

    TransferTransactionStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
