package lukelin.his.dto.internal_account.temp;

import lukelin.common.sdk.PagedDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentListTempRespDto {
    private BigDecimal totalPaidAmount;

    private BigDecimal totalFeeAmount;

    PagedDTO<PaymentTempRespDto> paymentList;

    public BigDecimal getBalanceAmount()
    {
        return this.totalPaidAmount.subtract(totalFeeAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public BigDecimal getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(BigDecimal totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public PagedDTO<PaymentTempRespDto> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(PagedDTO<PaymentTempRespDto> paymentList) {
        this.paymentList = paymentList;
    }
}
