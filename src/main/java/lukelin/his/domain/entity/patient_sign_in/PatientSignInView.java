package lukelin.his.domain.entity.patient_sign_in;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.ebean.annotation.View;

import javax.persistence.Column;
import java.util.UUID;

@javax.persistence.Entity
@View(name = "patient_sign_in.patient_sign_in_view")
public class PatientSignInView extends BasePatientSignIn {
    @Column(name = "pendingfeecount")
    private Integer pendingFeeCount;

    public Integer getPendingFeeCount() {
        return pendingFeeCount;
    }
}
