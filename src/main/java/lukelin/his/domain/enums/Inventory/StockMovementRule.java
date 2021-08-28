package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StockMovementRule {
    inFirstOutFirst("先进先出"),
    inFirstOutLast("先进后出"),
    expireDateFirst("过期日期先出");

    private String description;

    StockMovementRule(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}

