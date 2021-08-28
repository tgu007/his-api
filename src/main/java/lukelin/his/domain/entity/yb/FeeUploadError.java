package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;

import javax.persistence.*;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "yb.fee_upload_error")
public class FeeUploadError extends BaseEntity {
    @OneToOne()
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    private String error;

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
