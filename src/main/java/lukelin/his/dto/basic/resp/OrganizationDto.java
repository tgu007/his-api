package lukelin.his.dto.basic.resp;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

public class OrganizationDto extends BaseCodeDto {
    private String templateHeader;

    public String getTemplateHeader() {
        return templateHeader;
    }

    public void setTemplateHeader(String templateHeader) {
        this.templateHeader = templateHeader;
    }
}
