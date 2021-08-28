package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WarehouseType {
    levelOneWarehouse("药库"),
    wardWarehouse("病区库房"),
    pharmacy("药房"),
    levelTwoWarehouse("后勤库房"),
    treatmentWarehouse("科室库房");

    private String description;

    WarehouseType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
