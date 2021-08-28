package lukelin.his.api.user;

import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.dto.yb.req.DoctorAgreementSaveDto;
import lukelin.his.dto.basic.req.EmployeeProfileReqDto;
import lukelin.his.dto.basic.req.UserPermissionUpdateDto;
import lukelin.his.dto.basic.req.filter.EmployeeFilter;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;
import lukelin.his.dto.basic.resp.setup.EmployeeListDto;
import lukelin.his.dto.conveter.BasicDtoConverter;
import lukelin.his.service.UserService;
import lukelin.sdk.account.dto.request.ResetPasswordDTO;
import lukelin.sdk.account.service.CurrentUserAccountRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UserApi extends BaseController {
    @Autowired
    private CurrentUserAccountRestService currentUserAccountRestService;

    @Autowired
    private UserService userService;




    @PostMapping("password/reset")
    public void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDto) {
        this.currentUserAccountRestService.resetPassword(resetPasswordDto);

    }

    @PostMapping("list")
    public DecoratedDTO<List<EmployeeListDto>> getEmployeeList(@RequestBody EmployeeFilter employeeFilter) {
        List<Employee> employeeList = this.userService.getEmployeeList(employeeFilter);
        return decoratedResponse(BasicDtoConverter.toEmployeeListDto(employeeList));
    }


    @PostMapping("list/by_department")
    public DecoratedDTO<List<EmployeeListDto>> getDepartmentEmployeeList(@RequestBody EmployeeFilter employeeFilter) {
        List<Employee> employeeList = this.userService.getEmployeeList(employeeFilter);
        return decoratedResponse(BasicDtoConverter.toEmployeeListDto(employeeList));
    }

    @PostMapping("list/{userId}")
    public DecoratedDTO<EmployeeDto> getEmployee(@PathVariable UUID userId) {
        return decoratedResponse(this.userService.findById(Employee.class, userId).toDto());
    }

    @PostMapping("{employeeId}/activation/{active}")
    public void setEmployeeActivation(@PathVariable UUID employeeId, @PathVariable boolean active) {
        this.userService.activateEmployee(employeeId, active);
    }

    @PostMapping("permission/update")
    public void updateUserPermission(@RequestBody UserPermissionUpdateDto updatePermissionDto) {
        this.userService.updateUserPermission(updatePermissionDto);
    }

    @PostMapping("profile/update/{employeeId}")
    public void updateEmployeeProfile(@PathVariable UUID employeeId, @RequestBody EmployeeProfileReqDto reqDto) {
        this.userService.updateEmployeeProfile(employeeId, reqDto);
    }



}
