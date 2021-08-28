package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InventoryEntityType {
    medicine("药品"),
    item("物品");

    private String description;

    InventoryEntityType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
