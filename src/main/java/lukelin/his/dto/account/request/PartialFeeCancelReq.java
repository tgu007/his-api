package lukelin.his.dto.account.request;

import java.math.BigDecimal;
import java.util.UUID;

public class PartialFeeCancelReq {
    private UUID feeId;

    private BigDecimal cancelQuantity;

    public UUID getFeeId() {
        return feeId;
    }

    public void setFeeId(UUID feeId) {
        this.feeId = feeId;
    }

    public BigDecimal getCancelQuantity() {
        return cancelQuantity;
    }

    public void setCancelQuantity(BigDecimal cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
    }
}
