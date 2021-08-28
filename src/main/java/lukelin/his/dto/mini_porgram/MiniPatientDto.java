package lukelin.his.dto.mini_porgram;

import java.util.List;
import java.util.UUID;

public class MiniPatientDto {
    private UUID uuid;

    private String name;

    private String signInNumberCode;

    private String bedNumber;

    private List<MiniPrescriptionDto> prescriptionList;

    public String getSignInNumberCode() {
        return signInNumberCode;
    }

    public void setSignInNumberCode(String signInNumberCode) {
        this.signInNumberCode = signInNumberCode;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MiniPrescriptionDto> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<MiniPrescriptionDto> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }
}
