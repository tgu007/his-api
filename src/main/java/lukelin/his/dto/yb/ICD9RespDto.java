package lukelin.his.dto.yb;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

public class ICD9RespDto extends BaseCodeDto {
    private String searchCode;

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }
}
