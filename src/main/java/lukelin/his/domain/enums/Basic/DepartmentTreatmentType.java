package lukelin.his.domain.enums.Basic;

import com.fasterxml.jackson.annotation.JsonValue;


//Todo this need to changed into a table
public enum DepartmentTreatmentType {
    ward("病区科室"),
    recover("康复科室"),
    support("后勤科室"),
    fangshe("放射科"),
    jianyan("检验科"),
    bingli("病理科"),
    bichao("B超室");

    private String description;

    DepartmentTreatmentType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
