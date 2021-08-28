package lukelin.his.dto.internal_account.temp;

import lukelin.common.sdk.PagedDTO;

import java.math.BigDecimal;

public class FeeListTempRespDto {
    private BigDecimal totalAmount;

    PagedDTO<FeeRespTempDto> feeList;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PagedDTO<FeeRespTempDto> getFeeList() {
        return feeList;
    }

    public void setFeeList(PagedDTO<FeeRespTempDto> feeList) {
        this.feeList = feeList;
    }
}
