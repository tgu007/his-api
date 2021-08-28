package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PatientSignOutStatus {
    pendingPayment("待处理"),
    signedOut("确认出院"),
    created("已创建"),
    validation("病区核对"),
    validationCompleted("核对完成");

    private String description;

    PatientSignOutStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
