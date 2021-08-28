package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UrineVolume {
    normal("+"),
    abnormal("*");

    private String description;

    UrineVolume(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
