package lukelin.his.dto.basic.req.filter;

import lukelin.his.dto.basic.resp.setup.EmployeeDto;

import java.util.UUID;

public class MedicalTemplateFilter {
    private EmployeeDto employee;

    private Boolean enabled;

    private UUID typeId;

    private String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }
}
