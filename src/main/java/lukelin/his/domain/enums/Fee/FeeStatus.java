package lukelin.his.domain.enums.Fee;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeStatus {
    confirmed("正常"),
    canceled("已退费"),
    pendingOnMedicineReturn("待退药"),
    calculated("组套汇总");
    //canceledNoReturn("退费未退药");

    private String description;

    FeeStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
