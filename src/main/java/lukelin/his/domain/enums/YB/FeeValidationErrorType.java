package lukelin.his.domain.enums.YB;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeValidationErrorType {
    notUploadedButInSettle("HIS未上传但存在于与预结"),
    uploadedNotInSettlement("上传成功但不存在与预结费用中"),
    feeAmountNotMatch("His报销费用与预结报销费用不符");
    private String description;

    FeeValidationErrorType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
