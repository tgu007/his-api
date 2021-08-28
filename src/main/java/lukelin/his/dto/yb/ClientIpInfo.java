package lukelin.his.dto.yb;

public class ClientIpInfo {
    private String clientUrl;

    private Boolean electronicCard;

    private String employeeId;

    private String employeeName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Boolean getElectronicCard() {
        return electronicCard;
    }

    public void setElectronicCard(Boolean electronicCard) {
        this.electronicCard = electronicCard;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }
}
