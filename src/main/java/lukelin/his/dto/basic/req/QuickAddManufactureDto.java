package lukelin.his.dto.basic.req;

public class QuickAddManufactureDto extends QuickAddCodeEntityDto {
    private String serverId;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
