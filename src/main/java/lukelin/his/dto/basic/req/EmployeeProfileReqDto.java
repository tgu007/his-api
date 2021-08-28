package lukelin.his.dto.basic.req;

import java.util.UUID;

public class EmployeeProfileReqDto {
    private UUID uuid;

    private String certificationNumber;

    private Boolean allowSupervise;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public Boolean getAllowSupervise() {
        return allowSupervise;
    }

    public void setAllowSupervise(Boolean allowSupervise) {
        this.allowSupervise = allowSupervise;
    }
}
