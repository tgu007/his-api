package lukelin.his.domain.entity.yb;


import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;

import javax.persistence.*;

@Entity
@Table(name = "yb.treatment_match_download")
public class TreatmentMatchDownload extends BaseEntity {
    @Column(name = "match_status", nullable = false)
    private String status;

    @Column(name = "error")
    private String error;

    @OneToOne
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;

    @Column(name = "match_reference")
    private String reference;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
