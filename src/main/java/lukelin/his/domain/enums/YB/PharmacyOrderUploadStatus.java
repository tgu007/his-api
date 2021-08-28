package lukelin.his.domain.enums.YB;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PharmacyOrderUploadStatus {
    pending("待上传"),
    uploaded("已上传"),
    error("上传出错"),
    canceld("已作废");

    private String description;

    PharmacyOrderUploadStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
