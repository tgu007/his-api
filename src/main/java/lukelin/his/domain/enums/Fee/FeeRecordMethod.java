package lukelin.his.domain.enums.Fee;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeRecordMethod {
    def("电脑计费"),
    miniProgram("小程序扫码"),
    auto("自动生成"),
    manual("手动计费");
    //canceledNoReturn("退费未退药");

    private String description;

    FeeRecordMethod(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
