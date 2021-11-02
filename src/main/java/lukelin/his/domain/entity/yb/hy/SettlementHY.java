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
