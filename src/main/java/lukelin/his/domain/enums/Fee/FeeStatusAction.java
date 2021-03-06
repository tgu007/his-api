package lukelin.his.domain.enums.Fee;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeStatusAction {
    cancel("้่ดน"),
    medicineReturn("้่ฏ");

    private String description;

    FeeStatusAction(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
