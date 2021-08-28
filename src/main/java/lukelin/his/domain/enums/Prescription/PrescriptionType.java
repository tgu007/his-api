package lukelin.his.domain.enums.Prescription;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionType {
    Medicine("药品"),
    Treatment("诊疗"),
    TextMedicine("药品文字"),
    Text("文字");

    private String description;

    PrescriptionType(String description) {
        this.description = description;
    }

    @JsonValue
    public String PrescriptionType() {
        return this.description;
    }
}
