package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderRequestStatus {
    created("已创建"),
    submitted("待审核"),
    approved("已批准");

    private String description;

    OrderRequestStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
