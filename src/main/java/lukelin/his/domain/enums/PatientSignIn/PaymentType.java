package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentType {
    prePayment("预交费"),
    refund("退费"),
    finalPayment("结算缴费"),
    finalRefund("结算退费");

    private String description;

    PaymentType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
