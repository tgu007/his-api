package lukelin.his.dto.signin.response;

import java.util.List;
import java.util.UUID;

public class PatientMedicalRecordTypeDto extends MedicalRecordDto {


    private Integer RecordCount;

    private boolean fixedFormat;


    private List<UUID> permittedRoleIdList;

    public List<UUID> getPermittedRoleIdList() {
        return permittedRoleIdList;
    }

    public void setPermittedRoleIdList(List<UUID> permittedRoleIdList) {
        this.permittedRoleIdList = permittedRoleIdList;
    }

    public boolean isFixedFormat() {
        return fixedFormat;
    }

    public void setFixedFormat(boolean fixedFormat) {
        this.fixedFormat = fixedFormat;
    }

    public Integer getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(Integer recordCount) {
        RecordCount = recordCount;
    }
}
