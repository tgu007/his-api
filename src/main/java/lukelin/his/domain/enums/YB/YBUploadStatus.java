package lukelin.his.domain.enums.YB;

import com.fasterxml.jackson.annotation.JsonValue;

public enum YBUploadStatus {
    uploaded("已上传"),
    error("上传出错"),
    selfPay("不需上传"),
    notUploaded("未上传");

    private String description;

    YBUploadStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
