package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Treatment;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "yb.treatment_upload_result")
public class TreatmentUploadResult extends BaseEntity {
    @Column(name = "server_code")
    private String serverCode;

    @Column(name = "upload_error")
    private String uploadError;

    @OneToOne()
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

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

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
}
