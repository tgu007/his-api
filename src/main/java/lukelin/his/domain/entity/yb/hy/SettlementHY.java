package lukelin.his.domain.entity.yb.hy;

import lukelin.his.dto.yb.resp.SettlementDto;
import lukelin.his.dto.yb_hy.Resp.SettlementResp;
import org.springframework.beans.BeanUtils;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_hy.settlement")
public class SettlementHY extends BaseSettlementHY {
    private String setl_id;

    private String upload_result;

    private String full_info_download;

    private String accumulated_info;

    public String getAccumulated_info() {
        return accumulated_info;
    }

    public void setAccumulated_info(String accumulated_info) {
        this.accumulated_info = accumulated_info;
    }

    public String getFull_info_download() {
        return full_info_download;
    }

    public void setFull_info_download(String full_info_download) {
        this.full_info_download = full_info_download;
    }

    public String getSetl_id() {
        return setl_id;
    }

    public void setSetl_id(String setl_id) {
        this.setl_id = setl_id;
    }

    public String getUpload_result() {
        return upload_result;
    }

    public void setUpload_result(String upload_result) {
        this.upload_result = upload_result;
    }
}
