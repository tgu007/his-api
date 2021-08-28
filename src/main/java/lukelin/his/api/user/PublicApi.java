package lukelin.his.api.user;

import io.ebean.Ebean;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.UserRole;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Basic.UserRoleType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.JDYDemo;
import lukelin.his.dto.JDYRequestBody;
import lukelin.his.dto.account.filter.FeeFilterDto;
import lukelin.his.dto.account.request.BindWechatAccountDto;
import lukelin.his.dto.account.response.FeeListDto;
import lukelin.his.dto.account.response.FeeListSummaryDto;
import lukelin.his.dto.basic.req.NewEmployeeReqDto;
import lukelin.his.dto.basic.req.filter.DepartmentFilterDto;
import lukelin.his.dto.basic.resp.setup.*;
import lukelin.his.dto.conveter.AccountDtoConverter;
import lukelin.his.dto.conveter.BasicDtoConverter;
import lukelin.his.dto.signin.response.PatientSignInMobileDto;
import lukelin.his.dto.yb.resp.CenterTreatmentListSave;
import lukelin.his.service.AccountService;
import lukelin.his.service.BasicService;
import lukelin.his.service.UserService;
import lukelin.his.service.YBService;
import lukelin.sdk.account.dto.request.AccountReqDTO;
import lukelin.sdk.account.dto.request.CredentialLoginReqDTO;
import lukelin.sdk.account.dto.request.WechatLoginReqDTO;
import lukelin.sdk.account.dto.response.AccountDTO;
import lukelin.sdk.account.dto.response.OauthLoginRespDTO;
import lukelin.sdk.account.service.GeneralAccountRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("public")
public class PublicApi extends BaseController {
    @Autowired
    private GeneralAccountRestService generalAccountService;

    @Autowired
    private UserService userService;

    @Autowired
    private BasicService basicService;

    @Autowired
    private AccountService accountService;


    @Value("${wx.appId}")
    private String wxAppId;

    @Value("${wx.appSecret}")
    private String wxAppSecret;


    @PostMapping("user/login/simple")
    public DecoratedDTO<EmployeeDto> simpleLogin(@RequestBody CredentialLoginReqDTO loginDto) {
        AccountDTO accountDto = this.generalAccountService.simpleLogin(loginDto);
        Employee employee = this.userService.getEmployee(accountDto.getUuid());
        return this.decorateEmployee(employee, accountDto);
    }

    @PostMapping("user/login/mini_program/{code}")
    public DecoratedDTO<EmployeeDto> wechatLogin(@PathVariable String code) {
        WechatLoginReqDTO wechatLoginReqDTO = new WechatLoginReqDTO();
        wechatLoginReqDTO.setAppId(this.wxAppId);
        wechatLoginReqDTO.setCode(code);
        wechatLoginReqDTO.setSecret(this.wxAppSecret);
        OauthLoginRespDTO respDTO = this.generalAccountService.wechatAppletLogin(wechatLoginReqDTO);
        Employee employee = this.userService.getEmployeeByOuathAccount(respDTO.getUuid());
        return this.decorateEmployee(employee, respDTO);
    }

    @PostMapping("user/wechat_account/bind")
    public DecoratedDTO<EmployeeDto> bindWechatAccount(@RequestBody BindWechatAccountDto bindWechatAccountDto) {
        bindWechatAccountDto.setLoginRole("default");
        AccountDTO hisAccountDto = this.generalAccountService.verifyAccount(bindWechatAccountDto);
        Employee employee = this.userService.getEmployee(hisAccountDto.getUuid());
        if (employee.getUserRoleList().stream().noneMatch(r -> r.getUserRoleType() == UserRoleType.therapist))
            throw new ApiValidationException("Role not valid");

        AccountReqDTO accountReqDTO = new AccountReqDTO();
        accountReqDTO.setFullName(hisAccountDto.getFullName());
        AccountDTO wechatAccountDto = this.generalAccountService.updateAccountById(bindWechatAccountDto.getOauthAccountId(), accountReqDTO);

        employee.setOauthAccountId(bindWechatAccountDto.getOauthAccountId());
        Ebean.save(employee);
        return this.decorateEmployee(employee, wechatAccountDto);
    }

    private DecoratedDTO<EmployeeDto> decorateEmployee(Employee employee, AccountDTO accountDto) {
        if (employee.getEnabled() != null && !employee.getEnabled())
            throw new ApiValidationException("login.error.accountNotEnabled");

        EmployeeDto dto;
        if (employee.getUuid() != null)
            dto = employee.toDto();
        else
            dto = new EmployeeDto();
        dto.setAccount(accountDto);
        return decoratedResponse(dto);
    }

    @PostMapping("user/create")
    public void createSimpleLogin(@RequestBody NewEmployeeReqDto newEmployee) {
        if (newEmployee.getUserRoleId() == null)
            throw new ApiValidationException("empty role Id");
        AccountReqDTO accountReqDto = newEmployee.toAccountReqDto();
        Set<String> defaultRoles = new HashSet<>();
        defaultRoles.add("default");
        accountReqDto.setRoles(defaultRoles);
        AccountDTO accountDto = this.generalAccountService.createAccount(accountReqDto);
        this.userService.createEmployee(accountDto, newEmployee);
    }

    @PostMapping("warehouse/list")
    public DecoratedDTO<List<DepartmentWarehouseDto>> getWarehouseList(@RequestBody DepartmentFilterDto departmentFilterDto) {
        List<DepartmentWarehouse> warehouseList = this.basicService.getWarehouseList(departmentFilterDto);
        return decoratedResponse(DtoUtils.toDtoList(warehouseList));
    }

    @PostMapping("department/list")
    public DecoratedDTO<List<DepartmentTreatmentDto>> getDepartmentList(@RequestBody DepartmentFilterDto departmentFilterDto) {
        List<DepartmentTreatment> departmentTreatmentList = this.basicService.getDepartmentList(departmentFilterDto);
        return decoratedResponse(DtoUtils.toDtoList(departmentTreatmentList));
    }

    @PostMapping("user/role/list")
    public DecoratedDTO<List<UserRoleListDto>> getUserRoleList() {
        List<UserRole> roleList = this.userService.findAll(UserRole.class);
        return decoratedResponse(BasicDtoConverter.toUserRoleListDto(roleList));
    }

    @PostMapping("patient/self/{patientSignInId}")
    public DecoratedDTO<PatientSignInMobileDto> getPatientDetail(@PathVariable UUID patientSignInId) {
        return decoratedResponse(this.basicService.findById(PatientSignIn.class, patientSignInId).toMobileDto());
    }

    @PostMapping("patient/self/{patientSignInId}/fee/summary")
    public DecoratedDTO<List<FeeListSummaryDto>> getPatientSelfFeeListSummary(@PathVariable UUID patientSignInId, @RequestBody FeeFilterDto feeFilter) {
        List<FeeStatus> feeStatusList = new ArrayList<>();
        feeStatusList.add(FeeStatus.confirmed);
        feeFilter.setFeeStatusList(feeStatusList);
        List<Fee> feeList = accountService.getFeeList(patientSignInId, feeFilter);
        return decoratedResponse(AccountDtoConverter.toFeeListByTypeRespDto(feeList, false, true));
    }

    @PostMapping("patient/self/{patientSignInId}/fee/list")
    public DecoratedDTO<List<FeeListDto>> getPatientSelfFeeList(@PathVariable UUID patientSignInId, @RequestBody FeeFilterDto feeFilter) {
        List<FeeStatus> feeStatusList = new ArrayList<>();
        feeStatusList.add(FeeStatus.confirmed);
        feeFilter.setFeeStatusList(feeStatusList);
        List<Fee> feeList = accountService.getFeeList(patientSignInId, feeFilter);
        return decoratedResponse(AccountDtoConverter.toFeeListRespDto(feeList));
    }


    @Autowired
    private YBService ybService;

    @PostMapping("jdy/demo")
    public JDYDemo jdyDemo() {
        String url = "https://api.jiandaoyun.com/api/v2/app/60d195e0a89d9d0009baf1c9/entry/60d196d3884cf00007354e80/data";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Authorization", "Bearer a4uvMkq7tcc62GKaDAodmk28ZgS7IANV");

        JDYRequestBody requestBody = new JDYRequestBody();
        requestBody.setLimit(100);

        HttpEntity<JDYRequestBody> requestEntity = new HttpEntity<JDYRequestBody>(requestBody, requestHeaders);
        ResponseEntity<JDYDemo> response = this.ybService.restTemplate().exchange(url, HttpMethod.POST, requestEntity, JDYDemo.class);

        JDYDemo body = response.getBody();


        requestBody.setData_id("60d196d4884cf00007354ee4");
        response = this.ybService.restTemplate().exchange(url, HttpMethod.POST, requestEntity, JDYDemo.class);
        body.getData().addAll(response.getBody().getData());
        return body;
        //JDYDemo test = this.ybService.restTemplate().postForObject(url, null, JDYDemo.class);
    }
}
