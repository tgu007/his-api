package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UnitOfMeasureType {
    medicineServe("药品剂量"),
    medicineMin("药品最小"),
    medicinePharmacy("药品药房"),
    medicineWarehouse("药品药库"),
    treatment("诊疗"),
    item("物品最小"),
    itemWarehouse("物品入库"),
    itemDepartment("物品出库");

    private String description;

    UnitOfMeasureType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }

}
