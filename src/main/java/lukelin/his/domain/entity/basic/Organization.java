package lukelin.his.domain.entity.basic;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.basic.resp.OrganizationDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.organization")
public class Organization extends BaseCodeEntity implements DtoConvertible<OrganizationDto> {
    public Organization(UUID uuid) {
        super();
        this.setUuid(uuid);
    }

    public Organization() {
        super();
    }

    private String templateHeader;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTemplateHeader() {
        return templateHeader;
    }

    public void setTemplateHeader(String templateHeader) {
        this.templateHeader = templateHeader;
    }

    @Override
    public OrganizationDto toDto() {
        return DtoUtils.convertRawDto(this);
    }
}
