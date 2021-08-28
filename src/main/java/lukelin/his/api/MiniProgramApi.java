package lukelin.his.api;

import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.account.filter.FeeFilterDto;
import lukelin.his.dto.account.response.FeeListSummaryDto;
import lukelin.his.dto.account.response.MiniFeeListDto;
import lukelin.his.dto.account.response.MiniSupervisor;
import lukelin.his.dto.basic.req.filter.EmployeeFilter;
import lukelin.his.dto.basic.req.filter.TreatmentFilter;
import lukelin.his.dto.basic.resp.entity.TreatmentListMiniRespDto;
import lukelin.his.dto.conveter.AccountDtoConverter;
import lukelin.his.dto.conveter.BasicDtoConverter;
import lukelin.his.dto.conveter.PatientSignInDtoConverter;
import lukelin.his.dto.mini_porgram.MiniOnGoingPrescriptionDto;
import lukelin.his.dto.mini_porgram.MiniPrescriptionDto;
import lukelin.his.dto.mini_porgram.MiniWardDto;
import lukelin.his.dto.prescription.request.filter.PrescriptionFilterDto;
import lukelin.his.service.AccountService;
import lukelin.his.service.EntityService;
import lukelin.his.service.PrescriptionService;
import lukelin.his.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("mini")
public class MiniProgramApi extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private AccountService accountService;


    @PostMapping("{employeeId}/treatment/operate/list")
    public DecoratedDTO<List<TreatmentListMiniRespDto>> getEmployeeTreatmentOperationList(@RequestBody TreatmentFilter treatmentFilter, @PathVariable UUID employeeId) {
        List<Treatment> treatmentList = entityService.getTreatmentList(treatmentFilter);
        Employee employee = this.userService.findById(Employee.class, employeeId);
        return decoratedResponse(BasicDtoConverter.toEmployeeTreatmentOperateList(treatmentList, employee));
    }

    @PostMapping("{employeeId}/treatment/{treatmentId}/operate/yes")
    public void activeEmployeeTreatmentOperation(@PathVariable UUID employeeId, @PathVariable UUID treatmentId) {
        this.userService.updateEmployeeTreatmentOperation(employeeId, treatmentId, true);
    }

    @PostMapping("{employeeId}/treatment/{treatmentId}/operate/no")
    public void deActiveEmployeeTreatmentOperation(@PathVariable UUID employeeId, @PathVariable UUID treatmentId) {
        this.userService.updateEmployeeTreatmentOperation(employeeId, treatmentId, false);
    }

    @PostMapping("execution/list")
    public DecoratedDTO<List<MiniWardDto>> getMiniPendingExecutionPrescriptionList(@RequestBody PrescriptionFilterDto filterDto) {
        List<Prescription> prescriptionList = this.prescriptionService.getPendingExecutionPrescriptionList(filterDto, false);
        return decoratedResponse(PatientSignInDtoConverter.toMiniWardList(prescriptionList));
    }

    @PostMapping("patient/prescription/list")
    public DecoratedDTO<List<MiniPrescriptionDto>> getMiniPatientAllPrescriptionList(@RequestBody PrescriptionFilterDto filterDto) {
        List<Prescription> prescriptionList = this.prescriptionService.getPrescriptionList(filterDto);
        return decoratedResponse(PatientSignInDtoConverter.toMiniPrescriptionList(prescriptionList));
    }

    @PostMapping("patient/prescription/executing/list")
    public DecoratedDTO<List<MiniOnGoingPrescriptionDto>> getOnGoingPrescriptionList(@RequestBody PrescriptionFilterDto filterDto) {
        List<Fee> onGoingFeeList = this.prescriptionService.getOnExecutingPrescriptionList(filterDto.getPatientSignInIdList().get(0));
        return decoratedResponse(AccountDtoConverter.toMiniOnGoingPrescriptionList(onGoingFeeList));
    }

    @PostMapping("my_fee/list")
    public DecoratedDTO<List<FeeListSummaryDto>> getMyFeeList(@RequestBody FeeFilterDto feeFilterDto) {
        List<FeeStatus> feeStatusList = new ArrayList<>();
        feeStatusList.add(FeeStatus.confirmed);
        feeFilterDto.setFeeStatusList(feeStatusList);
        List<Fee> feeList = accountService.getFeeList(feeFilterDto);
        return decoratedResponse(AccountDtoConverter.toFeeListByTypeRespDto(feeList, false, true));
    }

    @PostMapping("my_fee/today/list")
    public DecoratedDTO<List<MiniFeeListDto>> getTodayMyFeeList(@RequestBody FeeFilterDto feeFilterDto) throws ParseException {
        List<FeeStatus> feeStatusList = new ArrayList<>();
        feeStatusList.add(FeeStatus.confirmed);
        feeFilterDto.setFeeStatusList(feeStatusList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdf.format(new Date());
        Date today =  sdf.parse(todayString);
        feeFilterDto.setStartDate(today);
        feeFilterDto.setEndDate(today);
        List<Fee> feeList = accountService.getFeeList(feeFilterDto);
        return decoratedResponse(AccountDtoConverter.toMiniFeeList(feeList));
    }

    @PostMapping("supervisor/list")
    public DecoratedDTO<List<MiniSupervisor>> getSupervisorList(@RequestBody EmployeeFilter employeeFilter) {
        employeeFilter.setAllowSupervise(true);
        employeeFilter.setEnabled(true);
        List<Employee> employeeList = this.userService.getEmployeeList(employeeFilter);
        List<MiniSupervisor> supervisorList = new ArrayList<>();
        for(Employee employee: employeeList)
            supervisorList.add(employee.toMiniSupervisorDto());
        return decoratedResponse(supervisorList);
    }

}
