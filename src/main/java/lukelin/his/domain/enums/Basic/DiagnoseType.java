package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiagnoseType {
    unknown("未知"),
    gs("工伤"),
    yb("医保");

    private String description;

    DiagnoseType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}

