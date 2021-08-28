package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

import static lukelin.his.domain.enums.Inventory.TransferStatus.pendingConfirm;

public enum OrderStatus {
    created("已创建"),
    submitted("待审核"),
    approved("已批准"),
    pendingReturn("待出库"),
    returned("已出库");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
