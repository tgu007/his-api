package lukelin.his.dto.notification.resp;

import lukelin.his.domain.enums.Basic.UserRoleType;
import lukelin.his.domain.enums.NotificationMessageType;
import lukelin.his.dto.signin.response.PatientSignInMessageDto;

import java.util.*;

public class NotificationDto {
    private String key;

    private NotificationMessageType notificationMessageType;

    private UserRoleType userRoleType;

    private UUID departmentId;

    private String message;

    private PatientSignInMessageDto patientInfo;

    private UUID patientSignInId;

    private UUID employeeId;

    private String tabName;

    private Object tabPram;

    private String tabComponent;

    private List<String> detailInfoList = new ArrayList<>();

    private String detailInfo;

    public String getDetailInfo() {
        return String.join(",", detailInfoList);
    }

    public List<String> getDetailInfoList() {
        return detailInfoList;
    }

    public void setDetailInfoList(List<String> detailInfoList) {
        this.detailInfoList = detailInfoList;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public Object getTabPram() {
        return tabPram;
    }

    public void setTabPram(Object tabPram) {
        this.tabPram = tabPram;
    }

    public String getTabComponent() {
        return tabComponent;
    }

    public void setTabComponent(String tabComponent) {
        this.tabComponent = tabComponent;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public PatientSignInMessageDto getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientSignInMessageDto patientInfo) {
        this.patientInfo = patientInfo;
    }

    public NotificationMessageType getNotificationMessageType() {
        return notificationMessageType;
    }

    public void setNotificationMessageType(NotificationMessageType notificationMessageType) {
        this.notificationMessageType = notificationMessageType;
    }


    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
