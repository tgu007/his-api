package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransferStatus {
    created("已创建"),
    confirmed("已确认"),
    pendingConfirm("待确认");

    private String description;

    TransferStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
