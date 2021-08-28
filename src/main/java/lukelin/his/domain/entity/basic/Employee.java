package lukelin.his.domain.entity.basic;

import io.ebean.annotation.DbJsonB;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.UserRole;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.yb.DoctorAgreement;
import lukelin.his.dto.account.response.MiniSupervisor;
import lukelin.his.dto.basic.resp.setup.*;
import org.apache.tomcat.util.buf.StringUtils;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "basic.employee")
public class Employee extends BaseEntity implements DtoConvertible<EmployeeDto> {

    public Employee(UUID uuid) {
        super();
        this.setUuid(uuid);
    }

    public Employee() {
        super();
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    private Dictionary gender;

    @ManyToOne
    @JoinColumn(name = "ethnic_id", nullable = false)
    private Dictionary ethnic;

    @ManyToMany
    @JoinTable(name = "basic.employee_treatment_department",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "department_id", referencedColumnName = "uuid")})
    private Set<DepartmentTreatment> departmentList;

    @ManyToMany
    @JoinTable(name = "basic.employee_warehouse",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "warehouse_id", referencedColumnName = "uuid")})
    private Set<DepartmentWarehouse> warehouseList;

    @ManyToMany
    @JoinTable(name = "basic.employee_role",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "uuid")})
    private Set<UserRole> userRoleList;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Dictionary title;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "wechat_number", length = 50)
    private String weChatNumber;

    @Column(name = "national_identifier_number", length = 50)
    private String nationalIdentificationNumber;


    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(length = 50)
    private String email;

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "oauth_account_id")
    private UUID oauthAccountId;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToMany
    @JoinTable(name = "basic.employee_treatment_operate",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "treatment_id", referencedColumnName = "uuid")})
    private Set<Treatment> treatmentList;

    @DbJsonB
    @Column(name = "ui_permission")
    private Map<String, Object> uiPermission;

    @Column(name = "extra_info")
    private String extraInfo;

    @Column(name = "certification_number")
    private String certificationNumber;

    @Column(name = "allow_supervise")
    private Boolean allowSupervise;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "doctor")
    private DoctorAgreement doctorAgreement;

    public DoctorAgreement getDoctorAgreementNumber() {
        return doctorAgreement;
    }

    public void setDoctorAgreementNumber(DoctorAgreement doctorAgreement) {
        this.doctorAgreement = doctorAgreement;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public Boolean getAllowSupervise() {
        return allowSupervise;
    }

    public void setAllowSupervise(Boolean allowSupervise) {
        this.allowSupervise = allowSupervise;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Map<String, Object> getUiPermission() {
        return uiPermission;
    }

    public void setUiPermission(Map<String, Object> uiPermission) {
        this.uiPermission = uiPermission;
    }

    public UUID getOauthAccountId() {
        return oauthAccountId;
    }

    public void setOauthAccountId(UUID oauthAccountId) {
        this.oauthAccountId = oauthAccountId;
    }

    public Set<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(Set<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Set<DepartmentTreatment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(Set<DepartmentTreatment> departmentList) {
        this.departmentList = departmentList;
    }

    public Set<DepartmentWarehouse> getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(Set<DepartmentWarehouse> warehouseList) {
        this.warehouseList = warehouseList;
    }

    public Set<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(Set<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    private String introduction;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Dictionary getGender() {
        return gender;
    }

    public void setGender(Dictionary gender) {
        this.gender = gender;
    }

    public Dictionary getEthnic() {
        return ethnic;
    }

    public void setEthnic(Dictionary ethnic) {
        this.ethnic = ethnic;
    }


    public Dictionary getTitle() {
        return title;
    }

    public void setTitle(Dictionary title) {
        this.title = title;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getWeChatNumber() {
        return weChatNumber;
    }

    public void setWeChatNumber(String weChatNumber) {
        this.weChatNumber = weChatNumber;
    }

    public String getNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    public void setNationalIdentificationNumber(String nationalIdentificationNumber) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public EmployeeDto toDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        this.setDtoProperty(employeeDto);
        //employeeDto.setDepartment(DtoUtils.convertRawDto(this.department));
        employeeDto.setEthnic(DtoUtils.convertRawDto(this.ethnic));
        employeeDto.setGender(DtoUtils.convertRawDto(this.gender));
        employeeDto.setOrganization(this.getOrganization().toDto());

        //List<DepartmentTreatmentDto> departmentList = new ArrayList<>();
        List<UUID> departmentIdList = new ArrayList<>();
        List<UUID> wardIdList = new ArrayList<>();
        List<UUID> warehouseIdList = new ArrayList<>();

        for (DepartmentTreatment department : this.getDepartmentList()) {
            departmentIdList.add(department.getUuid());
            wardIdList.addAll(department.getWardList().stream().map(d -> d.getUuid()).collect(Collectors.toList()));
            if (department.getWardWarehouse() != null)
                warehouseIdList.add(department.getWardWarehouse().getUuid());
        }

        for (DepartmentWarehouse warehouse : this.getWarehouseList())
            warehouseIdList.add(warehouse.getUuid());

        employeeDto.setDepartmentIdList(departmentIdList);
        employeeDto.setWardIdList(wardIdList);
        employeeDto.setWarehouseIdList(warehouseIdList);


        //如果用户本身有权限设置，设置本身权限，没有使用角色权限
        if (this.uiPermission != null)
            employeeDto.setUiPermission(this.uiPermission);
        else {
            //目前只需要支持一个ROLE
            for (UserRole userRole : this.getUserRoleList())
                employeeDto.setUiPermission(userRole.getUiPermission());
        }

        employeeDto.setAllowSupervise(this.getAllowSupervise());
        employeeDto.setCertificationNumber(this.getCertificationNumber());
        if (org.apache.commons.lang.StringUtils.isBlank(this.getCertificationNumber()))
            employeeDto.setNeedSupervise(true);
        else
            employeeDto.setNeedSupervise(false);
        return employeeDto;
    }

    public EmployeeListDto toListDto() {
        EmployeeListDto listDto = new EmployeeListDto();
        this.setDtoProperty(listDto);
        String authorizedDepartment = "";
        if (this.getDepartmentList().size() > 0)
            authorizedDepartment = StringUtils.join(this.getDepartmentList().stream().map(d -> d.getDepartment().getName()).collect(Collectors.toList()), '；');
        if (this.getWarehouseList().size() > 0)
            authorizedDepartment = StringUtils.join(this.getWarehouseList().stream().map(d -> d.getDepartment().getName()).collect(Collectors.toList()), '；');
        listDto.setAuthorizedDepartment(authorizedDepartment);

        return listDto;
    }

    private void setDtoProperty(BaseEmployeeDto baseEmployeeDto) {
        BeanUtils.copyPropertiesIgnoreNull(this, baseEmployeeDto);
        if (this.getDoctorAgreementNumber() != null) {
            baseEmployeeDto.setDoctorAgreementNumber(this.getDoctorAgreementNumber().getAgreementNumber());
            baseEmployeeDto.setDoctorAgreementId(this.getDoctorAgreementNumber().getUuid());
        }
        //目前只需要支持一个ROLE
        for (UserRole userRole : this.getUserRoleList())
            baseEmployeeDto.setUserRole(userRole.toDto());
    }

    public MiniSupervisor toMiniSupervisorDto() {
        MiniSupervisor miniSupervisor = new MiniSupervisor();
        miniSupervisor.setSupervisorId(this.getUuid());
        miniSupervisor.setSupervisorName(this.getName());
        return miniSupervisor;
    }
}
