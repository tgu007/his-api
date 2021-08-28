package lukelin.his.dto.account.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class FeeListSummaryDto extends BaseFeeListDto {
    private Date feeDate;

    private UUID feeEntityId;

    private UUID patientSignInId;

    private String patientName;

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

    public UUID getFeeEntityId() {
        return feeEntityId;
    }

    public void setFeeEntityId(UUID feeEntityId) {
        this.feeEntityId = feeEntityId;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }
}
