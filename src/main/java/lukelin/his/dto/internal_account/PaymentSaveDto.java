package lukelin.his.dto.internal_account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;
import lukelin.his.domain.entity.Internal_account.Payment;
import lukelin.his.domain.entity.Internal_account.PaymentTemp;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;

import java.math.BigDecimal;
import java.util.*;

public class PaymentSaveDto {


    private PaymentType paymentType;

    private Integer paymentMethodId;

    private BigDecimal amount;

    private UUID patientSignInId;

    private UUID uuid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date paymentDate;

    private UUID itemId;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }


    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Payment toEntity() {
        Payment payment;
        if (this.getUuid() == null)
            payment = new Payment();
        else
            payment = Ebean.find(Payment.class, this.getUuid());

        BeanUtils.copyPropertiesIgnoreNull(this, payment);
        payment.setPaymentStatus(PaymentStatus.pending);
        Dictionary paymentMethod = Ebean.find(Dictionary.class, this.getPaymentMethodId());
        payment.setPaymentMethod(paymentMethod);
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, this.getPatientSignInId());
        payment.setPatientSignIn(patientSignIn);
        if(this.getItemId() != null)
        {
            ChargeableItem item =  Ebean.find(ChargeableItem.class, this.getItemId());
            payment.setItem(item);
        }

        return payment;
    }
}
