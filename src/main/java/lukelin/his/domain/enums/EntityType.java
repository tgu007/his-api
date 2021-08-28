package lukelin.his.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityType {
    medicine("药品"),
    treatment("诊疗"),
    item("耗材");

    private String description;

    EntityType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
