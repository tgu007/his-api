package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {
    pending("待缴费"),
    canceled("已作废"),
    paid("已缴费"),
    pendingRefund("待退费"),
    refunded("已退费");

    private String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
