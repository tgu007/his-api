package lukelin.his.dto.prescription.response;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;

import java.util.List;

public class PreDefinedPrescriptionListDto extends BaseCodeDto {

    private EmployeeDto createdByDoctor;

    public EmployeeDto getCreatedByDoctor() {
        return createdByDoctor;
    }

    public void setCreatedByDoctor(EmployeeDto createdByDoctor) {
        this.createdByDoctor = createdByDoctor;
    }
}
