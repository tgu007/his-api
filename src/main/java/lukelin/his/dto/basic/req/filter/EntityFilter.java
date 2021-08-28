package lukelin.his.dto.basic.req.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.dto.basic.SearchCodeDto;

import java.util.Date;

public class EntityFilter extends SearchCodeDto {
    private Boolean allowManualFee;

    private Boolean allowAutoFee;

    private String feeTypeName;

    private Boolean pendingPriceUpdate;

    private String modifiedBy;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date modifyStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date modifyEndDate;

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifyStartDate() {
        return modifyStartDate;
    }

    public void setModifyStartDate(Date modifyStartDate) {
        this.modifyStartDate = modifyStartDate;
    }

    public Date getModifyEndDate() {
        return modifyEndDate;
    }

    public void setModifyEndDate(Date modifyEndDate) {
        this.modifyEndDate = modifyEndDate;
    }

    public Boolean getPendingPriceUpdate() {
        return pendingPriceUpdate;
    }

    public void setPendingPriceUpdate(Boolean pendingPriceUpdate) {
        this.pendingPriceUpdate = pendingPriceUpdate;
    }

    public String getFeeTypeName() {
        return feeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

    public Boolean getAllowManualFee() {
        return allowManualFee;
    }

    public void setAllowManualFee(Boolean allowManualFee) {
        this.allowManualFee = allowManualFee;
    }

    public Boolean getAllowAutoFee() {
        return allowAutoFee;
    }

    public void setAllowAutoFee(Boolean allowAutoFee) {
        this.allowAutoFee = allowAutoFee;
    }
}
