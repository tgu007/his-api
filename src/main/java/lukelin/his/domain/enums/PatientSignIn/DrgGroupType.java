package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DrgGroupType {
    average("日均"),
    lumpsum("总额");

    private String description;

    DrgGroupType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
