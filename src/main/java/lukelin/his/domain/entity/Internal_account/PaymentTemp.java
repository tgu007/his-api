package lukelin.his.domain.entity.Internal_account;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;
import lukelin.his.dto.internal_account.temp.PaymentTempRespDto;
import lukelin.his.system.Utils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@javax.persistence.Entity
@Table(name = "internal_account.payment_temp")
public class PaymentTemp extends BaseEntity implements DtoConvertible<PaymentTempRespDto> {
    public PaymentTemp() {
    }

    public PaymentTemp(UUID uuid) {
        this.setUuid(uuid);
    }

    @Column(name = "patient_info")
    private String patientInfo;

    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(name = "status", nullable = false)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "payment_method", nullable = false)
    private Dictionary paymentMethod;

    @Column(name = "payment_number", nullable = false, insertable = false, updatable = false)
    private Integer paymentNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "sign_in_number")
    private String singInNumber;

    @Column(name = "ward")
    private String ward;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ChargeableItem item;

    public ChargeableItem getItem() {
        return item;
    }

    public void setItem(ChargeableItem item) {
        this.item = item;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getSingInNumber() {
        return singInNumber;
    }

    public void setSingInNumber(String singInNumber) {
        this.singInNumber = singInNumber;
    }

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
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

    public Dictionary getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Dictionary paymentMethod) {
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

    @Override
    public PaymentTempRespDto toDto() {
        PaymentTempRespDto dto = DtoUtils.convertRawDto(this);
        dto.setPaymentMethod(this.getPaymentMethod().toDto());
        dto.setCreatedBy(this.getWhoCreatedName());
        dto.setMoneyInChinese(Utils.numberToChinese(this.getAmount().toString()));
        if(this.getItem() != null)
            dto.setItem(this.getItem().toDto());
        return dto;
    }

}
