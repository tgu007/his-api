package lukelin.his.dto.internal_account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.signin.response.PatientSignInRespDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PaymentRespDto {
    private UUID uuid;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    private DictionaryDto paymentMethod;

    private Integer paymentNumber;

    private BigDecimal amount;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date paymentDate;

    private PatientSignInRespDto patient;

    private String moneyInChinese;

    private ChargeableItemRespDto item;

    public ChargeableItemRespDto getItem() {
        return item;
    }

    public void setItem(ChargeableItemRespDto item) {
        this.item = item;
    }

    public PatientSignInRespDto getPatient() {
        return patient;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }


    public void setPatient(PatientSignInRespDto patient) {
        this.patient = patient;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public DictionaryDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(DictionaryDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(Integer paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
