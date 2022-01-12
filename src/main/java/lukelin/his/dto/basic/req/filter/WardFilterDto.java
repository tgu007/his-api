package lukelin.his.dto.basic.req.filter;

import java.util.List;
import java.util.UUID;

public class WardFilterDto {
    private List<UUID> wardIdList;

    private Boolean hideEmptyBed;

    private String searchCode;

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public Boolean getHideEmptyBed() {
        return hideEmptyBed;
    }

    public void setHideEmptyBed(Boolean hideEmptyBed) {
        this.hideEmptyBed = hideEmptyBed;
    }

    public List<UUID> getWardIdList() {
        return wardIdList;
    }

    public void setWardIdList(List<UUID> wardIdList) {
        this.wardIdList = wardIdList;
    }
}
