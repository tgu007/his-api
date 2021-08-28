package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PupilCondition {
    none("无"),
    sensitive("灵敏"),
    slow("迟钝"),
    disappear("消失"),
    irregular("不规则"),
    needlePoint("针尖样");

    private String description;

    PupilCondition(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
