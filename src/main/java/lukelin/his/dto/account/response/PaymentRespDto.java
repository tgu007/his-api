package lukelin.his.dto.account.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.*;

public class PaymentRespDto {
    private UUID uuid;

    private PaymentStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date whenCreated;

    private String paymentNumber;

    private PaymentType paymentType;

    private BigDecimal amount;

    private DictionaryDto paymentMethod;

    private String whoCreated;

    private String moneyInChinese;

    private String signInNumber;

    private String patientName;

    private BigDecimal totalRefundAmount;

    private Date originPaymentDate;

    private String originPaymentNumber;

    public String getOriginPaymentNumber() {
        return originPaymentNumber;
    }

    public void setOriginPaymentNumber(String originPaymentNumber) {
        this.originPaymentNumber = originPaymentNumber;
    }

    public Date getOriginPaymentDate() {
        return originPaymentDate;
    }

    public void setOriginPaymentDate(Date originPaymentDate) {
        this.originPaymentDate = originPaymentDate;
    }

    public BigDecimal getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(BigDecimal totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getSignInNumber() {
        return signInNumber;
    }

    public void setSignInNumber(String signInNumber) {
        this.signInNumber = signInNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMoneyInChinese() {
        return moneyInChinese;
    }

    public void setMoneyInChinese(String moneyInChinese) {
        this.moneyInChinese = moneyInChinese;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public DictionaryDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(DictionaryDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }
}
