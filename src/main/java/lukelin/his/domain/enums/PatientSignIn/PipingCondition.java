package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PipingCondition {
    none("无"),
    normal("在位通畅"),
    jammed("阻塞"),
    slipped("滑脱");

    private String description;

    PipingCondition(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
