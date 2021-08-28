package lukelin.his.dto.internal_account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.signin.response.PatientSignInRespDto;

import java.math.BigDecimal;
import java.util.UUID;

public class FeeSummaryRespDto {

    private UUID patientSignInId;

    private String patientName;

    private BigDecimal quantity;

    private String uomName;

    private BigDecimal unitAmount;

    private String displayUnitAmount;

    private String displayQuantityInfo;

    private BigDecimal totalAmount;

    private ChargeableItemRespDto item;


    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getDisplayUnitAmount() {
        return displayUnitAmount;
    }

    public void setDisplayUnitAmount(String displayUnitAmount) {
        this.displayUnitAmount = displayUnitAmount;
    }

    public String getDisplayQuantityInfo() {
        return displayQuantityInfo;
    }

    public void setDisplayQuantityInfo(String displayQuantityInfo) {
        this.displayQuantityInfo = displayQuantityInfo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ChargeableItemRespDto getItem() {
        return item;
    }

    public void setItem(ChargeableItemRespDto item) {
        this.item = item;
    }
}
