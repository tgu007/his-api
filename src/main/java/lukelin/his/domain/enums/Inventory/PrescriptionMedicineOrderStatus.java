package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionMedicineOrderStatus {
    submitted("已提交"),
    processed("已处理"),
    confirmed("已确认");

    private String description;

    PrescriptionMedicineOrderStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String PrescriptionStatus() {
        return this.description;
    }
}
