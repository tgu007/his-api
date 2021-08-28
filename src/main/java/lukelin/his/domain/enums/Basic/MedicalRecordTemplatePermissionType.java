package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MedicalRecordTemplatePermissionType {
    privateTemplate("个人模板"),
    departmentTemplate("科室模板"),
    publicTemplate("公开模板");

    private String description;

    MedicalRecordTemplatePermissionType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
