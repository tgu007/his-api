package lukelin.his.dto.yb.req;

import java.util.UUID;

public class FeeDownloadFilter {
    private UUID patientSignInId;

    private Boolean loadAll;

    private String searchCode;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public Boolean getLoadAll() {
        return loadAll;
    }

    public void setLoadAll(Boolean loadAll) {
        this.loadAll = loadAll;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }
}
