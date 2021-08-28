package lukelin.his.dto.yb.req.settlement;

import lukelin.his.dto.yb.ClientIpInfo;

public class SettleUploadReq extends PreSettleUploadReq {
    private String cjsbh;

    private String jsbh;

    private ClientIpInfo clientIpInfo;

    public ClientIpInfo getClientIpInfo() {
        return clientIpInfo;
    }

    public void setClientIpInfo(ClientIpInfo clientIpInfo) {
        this.clientIpInfo = clientIpInfo;
    }

    public String getCjsbh() {
        return cjsbh;
    }

    public void setCjsbh(String cjsbh) {
        this.cjsbh = cjsbh;
    }

    public String getJsbh() {
        return jsbh;
    }

    public void setJsbh(String jsbh) {
        this.jsbh = jsbh;
    }
}
