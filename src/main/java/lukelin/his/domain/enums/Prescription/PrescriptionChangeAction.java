package lukelin.his.domain.enums.Prescription;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionChangeAction {
    submit("提交"),
    approve("通过"),
    reject("退回"),
    disable("停用"),
    delete("删除"),
    cancel("作废"),
    executed("已执行"),
    failedOnMedicineReturn("退药失败"),
    signOut("出院停嘱"),
    confirmDisable("停嘱确认");

    private String description;

    PrescriptionChangeAction(String description) {
        this.description = description;
    }

    @JsonValue
    public String PrescriptionStatus() {
        return this.description;
    }
}
