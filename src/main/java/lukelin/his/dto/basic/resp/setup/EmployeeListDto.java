package lukelin.his.dto.basic.resp.setup;

import lukelin.sdk.account.dto.response.AccountDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EmployeeListDto extends BaseEmployeeDto {
    private String authorizedDepartment;

    public String getAuthorizedDepartment() {
        return authorizedDepartment;
    }

    public void setAuthorizedDepartment(String authorizedDepartment) {
        this.authorizedDepartment = authorizedDepartment;
    }
}
