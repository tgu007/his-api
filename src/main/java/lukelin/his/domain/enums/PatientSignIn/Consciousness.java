package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Consciousness {
    none("无"),
    clear("清醒"),
    sleepy("嗜睡"),
    confusion("意识模糊"),
    lethargy("昏睡"),
    lightComa("浅昏迷"),
    middleComa("中昏迷"),
    deepComa("深昏迷"),
    delirlium("谵忘状态");

    private String description;

    Consciousness(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
