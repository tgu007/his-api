package lukelin.his.dto.basic.resp.setup;

import java.util.Date;
import java.util.UUID;

public abstract class BaseEmployeeDto extends BaseCodeDto {
    private Date birthday;

    private DictionaryDto gender;

    private DictionaryDto ethnic;

    //private Organization organization;

    private DepartmentDto department;

    private DictionaryDto title;

    private String contactNumber;

    private String weChatNumber;

    private String nationalIdentificationNumber;

    private String mobileNumber;

    private String email;

    private String introduction;

    private UUID accountId;

    private UserRoleDto userRole;

    private UUID doctorAgreementId;

    private String doctorAgreementNumber;

    public UUID getDoctorAgreementId() {
        return doctorAgreementId;
    }

    public void setDoctorAgreementId(UUID doctorAgreementId) {
        this.doctorAgreementId = doctorAgreementId;
    }

    public String getDoctorAgreementNumber() {
        return doctorAgreementNumber;
    }

    public void setDoctorAgreementNumber(String doctorAgreementNumber) {
        this.doctorAgreementNumber = doctorAgreementNumber;
    }

    public UserRoleDto getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleDto userRole) {
        this.userRole = userRole;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public DictionaryDto getGender() {
        return gender;
    }

    public void setGender(DictionaryDto gender) {
        this.gender = gender;
    }

    public DictionaryDto getEthnic() {
        return ethnic;
    }

    public void setEthnic(DictionaryDto ethnic) {
        this.ethnic = ethnic;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public DictionaryDto getTitle() {
        return title;
    }

    public void setTitle(DictionaryDto title) {
        this.title = title;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
