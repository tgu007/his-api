package lukelin.his.domain.enums.YB;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InventoryOrderType {
    buyOrder("11"),
    transferIn("12"),
    borrowIn("13"),
    sellReturn("15"),
    buyReturn("31"),
    transferOut("32"),
    borrowOut("33"),
    damage("35"),
    sellOut("36"),
    packageSplit("41"),
    repackage("45"),
    adjust("50");

    private String description;

    InventoryOrderType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
