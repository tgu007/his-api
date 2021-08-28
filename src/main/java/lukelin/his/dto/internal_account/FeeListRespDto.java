package lukelin.his.dto.internal_account;

import lukelin.common.sdk.PagedDTO;
import lukelin.his.dto.internal_account.temp.FeeRespTempDto;

import java.math.BigDecimal;

public class FeeListRespDto {
    private BigDecimal totalAmount;

    PagedDTO<FeeRespDto> feeList;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PagedDTO<FeeRespDto> getFeeList() {
        return feeList;
    }

    public void setFeeList(PagedDTO<FeeRespDto> feeList) {
        this.feeList = feeList;
    }
}
