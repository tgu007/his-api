package lukelin.his.domain.enums.YB;

import com.fasterxml.jackson.annotation.JsonValue;

public enum YBMatchStatus {
    pending("未审核"),
    passed("审核通过"),
    noRecord("无记录"),
    failed("审核不通过"),
    notUploaded("待上传"),
    notApply("无需上传");

    private String description;

    YBMatchStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }

}
