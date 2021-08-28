package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DepartmentType {
    treatment("诊疗"),
    warehouse("仓库"),
    admin("行政");

    private String description;

    DepartmentType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}