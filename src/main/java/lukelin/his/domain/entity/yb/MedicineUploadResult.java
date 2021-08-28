package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;

import javax.persistence.*;

@Entity
@Table(name = "yb.medicine_upload_result")
public class MedicineUploadResult extends BaseEntity {
    @Column(name = "server_code")
    private String serverCode;

    @Column(name = "upload_error")
    private String uploadError;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }

    public String getUploadError() {
        return uploadError;
    }

    public void setUploadError(String uploadError) {
        this.uploadError = uploadError;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
