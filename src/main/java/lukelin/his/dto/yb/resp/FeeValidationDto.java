package lukelin.his.dto.yb.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.YB.FeeValidationErrorType;
import lukelin.his.dto.account.response.FeeListDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class FeeValidationDto {
    private FeeValidationErrorType errorType;

   private FeeListDto hisFee;

    private String ybFeeName;

    private BigDecimal ybFeeValue;

    private BigDecimal ybFeeCoveredValue;

    private UUID ybFeeId;

    private String ybFeeNumber;

    public String getYbFeeNumber() {
        return ybFeeNumber;
    }

    public void setYbFeeNumber(String ybFeeNumber) {
        this.ybFeeNumber = ybFeeNumber;
    }

    public FeeValidationErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(FeeValidationErrorType errorType) {
        this.errorType = errorType;
    }

    public FeeListDto getHisFee() {
        return hisFee;
    }

    public void setHisFee(FeeListDto hisFee) {
        this.hisFee = hisFee;
    }

    public String getYbFeeName() {
        return ybFeeName;
    }

    public void setYbFeeName(String ybFeeName) {
        this.ybFeeName = ybFeeName;
    }

    public BigDecimal getYbFeeValue() {
        return ybFeeValue;
    }

    public void setYbFeeValue(BigDecimal ybFeeValue) {
        this.ybFeeValue = ybFeeValue;
    }

    public BigDecimal getYbFeeCoveredValue() {
        return ybFeeCoveredValue;
    }

    public void setYbFeeCoveredValue(BigDecimal ybFeeCoveredValue) {
        this.ybFeeCoveredValue = ybFeeCoveredValue;
    }

    public UUID getYbFeeId() {
        return ybFeeId;
    }

    public void setYbFeeId(UUID ybFeeId) {
        this.ybFeeId = ybFeeId;
    }
}
