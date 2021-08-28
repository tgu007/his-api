package lukelin.his.dto.account.request;

import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.account.Payment;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PaymentSaveDto {
    private UUID signInId;

    private PaymentType paymentType;

    private BigDecimal amount;

    private Integer paymentMethodId;

    private UUID originPaymentId;

    private PaymentStatus status;

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public UUID getOriginPaymentId() {
        return originPaymentId;
    }

    public void setOriginPaymentId(UUID originPaymentId) {
        this.originPaymentId = originPaymentId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public UUID getSignInId() {
        return signInId;
    }

    public void setSignInId(UUID signInId) {
        this.signInId = signInId;
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

    public Payment toEntity() {
        Payment newPayment = new Payment();
        BeanUtils.copyPropertiesIgnoreNull(this, newPayment);
        newPayment.setPaymentMethod(new Dictionary(this.getPaymentMethodId()));
        newPayment.setStatus(PaymentStatus.pending);
        newPayment.setPatientSignIn(new PatientSignIn(this.getSignInId()));

        if (originPaymentId != null) {
            Payment originPayment = Ebean.find(Payment.class, originPaymentId);
            originPayment.checkIsValidRefund(this.getAmount());
            newPayment.setOriginPayment(originPayment);
            newPayment.setStatus(PaymentStatus.pendingRefund);
        }

        if (this.getStatus() != null)
            newPayment.setStatus(this.getStatus());
        return newPayment;
    }

}
