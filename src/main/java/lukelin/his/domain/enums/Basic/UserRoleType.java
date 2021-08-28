package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRoleType {
    admin("系统管理员"),
    doctor("医生"),
    nurse("护士"),
    pharmacy("药剂师"),
    warehouse("仓库管理员"),
    cashier("出入院管理员"),
    therapist("康复师"),
    accountant("会计"),
    purchaser("采购"),
    labTester("检验员"),
    radiologist("采购"),
    internalCashier("内部收费员"),
    insuranceOffice("医保办");

    private String description;

    UserRoleType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
