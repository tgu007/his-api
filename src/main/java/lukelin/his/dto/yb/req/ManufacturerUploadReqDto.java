package lukelin.his.dto.yb.req;

public class ManufacturerUploadReqDto {
    private String uuid;

    //供应商编号
    private String gysbh;

    //名称
    private String mc;

    //证书号
    private String zsh;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGysbh() {
        return gysbh;
    }

    public void setGysbh(String gysbh) {
        this.gysbh = gysbh;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getZsh() {
        return zsh;
    }

    public void setZsh(String zsh) {
        this.zsh = zsh;
    }
}
