package lukelin.his.domain.enums.Fee;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeStatusAction {
    cancel("退费"),
    medicineReturn("退药");

    private String description;

    FeeStatusAction(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
