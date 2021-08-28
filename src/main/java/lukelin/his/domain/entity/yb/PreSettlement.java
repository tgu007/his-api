package lukelin.his.domain.entity.yb;

import lukelin.his.dto.yb.resp.PreSettlementDto;
import lukelin.his.dto.yb.resp.SettlementDto;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@javax.persistence.Entity
@Table(name = "yb.settlement_pre")
public class PreSettlement extends BaseSettlement {
    @OneToMany(mappedBy = "preSettlement", cascade = CascadeType.ALL)
    private List<PreSettlementFee> feeList;

    @Column(name = "return_detail")
    private Boolean returnDetail;


    public Boolean getReturnDetail() {
        return returnDetail;
    }

    public void setReturnDetail(Boolean returnDetail) {
        this.returnDetail = returnDetail;
    }

    public List<PreSettlementFee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<PreSettlementFee> feeList) {
        this.feeList = feeList;
    }

    public PreSettlementDto toPreSettlementDto() {
        PreSettlementDto dto = new PreSettlementDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
