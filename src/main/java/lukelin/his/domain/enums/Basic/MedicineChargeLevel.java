package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MedicineChargeLevel {
    JIA("甲等"),
    YI("乙等"),
    BING("丙等");

    private String description;

    MedicineChargeLevel(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
