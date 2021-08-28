package lukelin.his.domain.entity.yb;


import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "yb.medicine_match_download")
public class MedicineMatchDownload extends BaseEntity {
    @Column(name = "match_status", nullable = false)
    private String status;

    @Column(name = "error")
    private String error;

    @OneToOne()
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

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

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
