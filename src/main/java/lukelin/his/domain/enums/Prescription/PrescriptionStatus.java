package lukelin.his.domain.enums.Prescription;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionStatus {
    created("已创建"),
    submitted("已提交"),
    approved("执行中"),
    disabled("已停用"),
    deleted("已删除"),
    canceled("已作废"),
    pendingDisable("待停用");

    private String description;

    PrescriptionStatus(String description) {
        this.description = description;
    }

//    @JsonCreator
//    public static PrescriptionType fromString(String name) {
//        return valueOf(name);
//    }

    @JsonValue
    public String toDescription() {
        return this.description;
    }
}
