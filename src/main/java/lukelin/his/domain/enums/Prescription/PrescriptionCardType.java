package lukelin.his.domain.enums.Prescription;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrescriptionCardType {
    oral("口服卡"),
    noseFeed("鼻饲卡"),
    drop("静滴卡"),
    injection("注射卡"),
    aerosol("雾化卡"),
    bladderWashout("膀冲卡"),
    external("外用卡"),
    treatment("诊疗卡"),
    dropBottle("静滴瓶贴"),
    labBottle("化验瓶贴");

    private String description;

    PrescriptionCardType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return this.description;
    }
}
