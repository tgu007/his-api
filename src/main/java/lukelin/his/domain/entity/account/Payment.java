package lukelin.his.domain.entity.account;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.Internal_account.AutoFee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;
import lukelin.his.dto.account.response.PaymentRespDto;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@javax.persistence.Entity
@Table(name = "account.payment")
public class Payment extends BaseEntity implements DtoConvertible<PaymentRespDto> {
    @ManyToOne
    @JoinColumn(name = "sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "payment_number", nullable = false, insertable = false, updatable = false)
    private Integer paymentNumber;

    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "payment_method", nullable = false)
    private Dictionary paymentMethod;

    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "origin_payment_id")
    private Payment originPayment;

    @OneToMany(mappedBy = "originPayment", cascade = CascadeType.ALL)
    private List<Payment> refundPaymentList;


    public List<Payment> getRefundPaymentList() {
        return refundPaymentList;
    }

    public void setRefundPaymentList(List<Payment> refundPaymentList) {
        this.refundPaymentList = refundPaymentList;
    }

    public Payment getOriginPayment() {
        return originPayment;
    }

    public void setOriginPayment(Payment originPayment) {
        this.originPayment = originPayment;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public Integer getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(Integer paymentNumber) {
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

    public Dictionary getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Dictionary paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentRespDto toDto() {
        PaymentRespDto dto = DtoUtils.convertRawDto(this);
        dto.setWhoCreated(this.getWhoCreatedName());
        dto.setPaymentNumber(Utils.buildDisplayCode(this.paymentNumber));
        dto.setPaymentMethod(this.paymentMethod.toDto());
        dto.setMoneyInChinese(Utils.numberToChinese(this.getAmount().toString()));
        if (this.getPaymentType() == PaymentType.refund)
            dto.setMoneyInChinese("退款：" + dto.getMoneyInChinese());
        dto.setSignInNumber(this.getPatientSignIn().getSignInNumberCode());
        dto.setPatientName(this.getPatientSignIn().getPatient().getName());
        dto.setTotalRefundAmount(this.getRefundPaymentList().stream().filter(r -> r.getStatus() == PaymentStatus.refunded)
                .map(r -> r.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
        if (this.getOriginPayment() != null) {
            dto.setOriginPaymentDate(this.getOriginPayment().getWhenCreated());
            dto.setOriginPaymentNumber(Utils.buildDisplayCode(this.getOriginPayment().getPaymentNumber()));
            dto.setAmount(dto.getAmount().multiply(new BigDecimal("-1")));
        } else
            dto.setOriginPaymentDate(this.getWhenCreated());
        return dto;
    }

    public void checkIsValidRefund(BigDecimal newRefundAmount) {
        BigDecimal totalRefundAmount = this.getRefundPaymentList().stream().filter(r -> r.getStatus() == PaymentStatus.refunded).map(r -> r.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        if (totalRefundAmount.add(newRefundAmount).compareTo(this.getAmount()) > 0)
            throw new ApiValidationException("退款金额超过原缴费金额");

    }
}
