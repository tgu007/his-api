package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.YB.PharmacyOrderUploadStatus;

import javax.persistence.*;

@Entity
@Table(name = "yb.pharmacy_order_upload")
public class PharmacyOrderUpload extends BaseEntity {
    @Column(name = "status")
    private PharmacyOrderUploadStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    @OneToOne()
    @JoinColumn(name = "order_line_id")
    private PrescriptionMedicineOrderLine orderLine;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public PharmacyOrderUploadStatus getStatus() {
        return status;
    }

    public void setStatus(PharmacyOrderUploadStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PrescriptionMedicineOrderLine getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(PrescriptionMedicineOrderLine orderLine) {
        this.orderLine = orderLine;
    }
}
