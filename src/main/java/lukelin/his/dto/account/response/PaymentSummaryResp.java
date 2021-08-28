package lukelin.his.dto.account.response;

import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.math.BigDecimal;
import java.util.*;

public class PaymentSummaryResp {
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private BigDecimal totalCashAmount = BigDecimal.ZERO;

    private BigDecimal totalBankAmount = BigDecimal.ZERO;

    private BigDecimal totalAliAmount = BigDecimal.ZERO;

    private BigDecimal totalWechatAmount = BigDecimal.ZERO;

    private String whoCreated;

    private String moneyInChinese;

    private List<PaymentRespDto> paymentDetailList;

    public List<PaymentRespDto> getPaymentDetailList() {
        return paymentDetailList;
    }

    public void setPaymentDetailList(List<PaymentRespDto> paymentDetailList) {
        this.paymentDetailList = paymentDetailList;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAliAmount.add(this.totalBankAmount).add(this.totalCashAmount).add(this.totalWechatAmount);
    }


    public BigDecimal getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(BigDecimal totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    public BigDecimal getTotalBankAmount() {
        return totalBankAmount;
    }

    public void setTotalBankAmount(BigDecimal totalBankAmount) {
        this.totalBankAmount = totalBankAmount;
    }

    public BigDecimal getTotalAliAmount() {
        return totalAliAmount;
    }

    public void setTotalAliAmount(BigDecimal totalAliAmount) {
        this.totalAliAmount = totalAliAmount;
    }

    public BigDecimal getTotalWechatAmount() {
        return totalWechatAmount;
    }

    public void setTotalWechatAmount(BigDecimal totalWechatAmount) {
        this.totalWechatAmount = totalWechatAmount;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public String getMoneyInChinese() {
        return moneyInChinese;
    }

    public void setMoneyInChinese(String moneyInChinese) {
        this.moneyInChinese = moneyInChinese;
    }
}
