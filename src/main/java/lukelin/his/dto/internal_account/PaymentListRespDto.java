package lukelin.his.dto.internal_account;

import lukelin.common.sdk.PagedDTO;
import lukelin.his.dto.internal_account.temp.PaymentTempRespDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentListRespDto {
    private BigDecimal totalPaidAmount;

    private BigDecimal totalFeeAmount;

    PagedDTO<PaymentRespDto> paymentList;

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

    public PagedDTO<PaymentRespDto> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(PagedDTO<PaymentRespDto> paymentList) {
        this.paymentList = paymentList;
    }
}
