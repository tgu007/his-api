package lukelin.his.domain.enums.Prescription;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionCardSignatureType {
    cardLevel("卡片"),
    prescriptionLevel("医嘱");

    private String description;

    PrescriptionCardSignatureType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return this.description;
    }
}
