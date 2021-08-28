package lukelin.his.domain.enums.Prescription;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PreDefinedPrescriptionType {
    treatment("诊疗"),
    medicine("药品");

    private String description;

    PreDefinedPrescriptionType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return this.description;
    }
}
