package lukelin.his.dto.internal_account.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.Internal_account.ChargeableItem;
import lukelin.his.domain.entity.Internal_account.Payment;
import lukelin.his.domain.entity.Internal_account.PaymentTemp;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PaymentTempSaveDto {
    private PaymentType paymentType;

    private Integer paymentMethodId;

    private BigDecimal amount;

    private String singInNumber;

    private String patientInfo;

    private String ward;

    private UUID uuid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date paymentDate;

    private UUID itemId;

    private List<UUID> selectedItemIdList;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }


    public List<UUID> getSelectedItemIdList() {
        return selectedItemIdList;
    }

    public void setSelectedItemIdList(List<UUID> selectedItemIdList) {
        this.selectedItemIdList = selectedItemIdList;
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

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
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

    public String getSingInNumber() {
        return singInNumber;
    }

    public void setSingInNumber(String singInNumber) {
        this.singInNumber = singInNumber;
    }

    public PaymentTemp toEntity()
    {
        PaymentTemp paymentTemp;
        if (this.getUuid() == null)
            paymentTemp = new PaymentTemp();
        else
            paymentTemp = Ebean.find(PaymentTemp.class, this.getUuid());
        BeanUtils.copyPropertiesIgnoreNull(this, paymentTemp);
        paymentTemp.setPaymentStatus(PaymentStatus.pending);
        Dictionary paymentMethod = Ebean.find(Dictionary.class, this.getPaymentMethodId());
        paymentTemp.setPaymentMethod(paymentMethod);
        if(this.getItemId() != null)
        {
            ChargeableItem item =  Ebean.find(ChargeableItem.class, this.getItemId());
            paymentTemp.setItem(item);
        }
        return paymentTemp;
    }
}
