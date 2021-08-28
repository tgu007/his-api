package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;

import javax.persistence.*;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "yb.fee_uploaded")
public class FeeUploadResult extends BaseEntity {
    @OneToOne()
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    @Column(name = "server_id")
    private String serverId;

    @Column(name = "insurance_attribute")
    private String insuranceAttribute;

    @Column(name = "self_ratio")
    private BigDecimal selfRatio;

    @Column(name = "self_zl_amount")
    private BigDecimal selfRatioPayAmount;

    @Column(name = "self_zf_amount")
    private BigDecimal selfPayAmount;

    @Column(name = "unit_amount_limit")
    private BigDecimal unitAmountLimit;

    @Column(name = "upload_status")
    private String uploadStatus;

    @Column(name = "reference")
    private String reference;

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getInsuranceAttribute() {
        return insuranceAttribute;
    }

    public void setInsuranceAttribute(String insuranceAttribute) {
        this.insuranceAttribute = insuranceAttribute;
    }

    public BigDecimal getSelfRatio() {
        return selfRatio;
    }

    public void setSelfRatio(BigDecimal selfRatio) {
        this.selfRatio = selfRatio;
    }

    public BigDecimal getSelfRatioPayAmount() {
        return selfRatioPayAmount;
    }

    public void setSelfRatioPayAmount(BigDecimal selfRatioPayAmount) {
        this.selfRatioPayAmount = selfRatioPayAmount;
    }

    public BigDecimal getSelfPayAmount() {
        return selfPayAmount;
    }

    public void setSelfPayAmount(BigDecimal selfPayAmount) {
        this.selfPayAmount = selfPayAmount;
    }

    public BigDecimal getUnitAmountLimit() {
        return unitAmountLimit;
    }

    public void setUnitAmountLimit(BigDecimal unitAmountLimit) {
        this.unitAmountLimit = unitAmountLimit;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getInsuranceAmount()
    {
        BigDecimal totalSelfAmount = BigDecimal.ZERO;
        if(this.getSelfPayAmount() != null)
            totalSelfAmount = totalSelfAmount.add(this.getSelfPayAmount());
        if(this.getSelfRatioPayAmount() != null)
            totalSelfAmount = totalSelfAmount.add(this.getSelfRatioPayAmount());
        return this.getFee().getTotalAmount().subtract(totalSelfAmount);
    }
}
