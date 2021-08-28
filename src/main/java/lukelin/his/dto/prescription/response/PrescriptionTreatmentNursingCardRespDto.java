package lukelin.his.dto.prescription.response;

import java.util.*;

public class PrescriptionTreatmentNursingCardRespDto {
    private UUID treatmentId;

    private String treatmentName;

    public UUID getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(UUID treatmentId) {
        this.treatmentId = treatmentId;
    }

    private List<PrescriptionPatientTreatmentNursingCardRespDto> patientList;

    public List<PrescriptionPatientTreatmentNursingCardRespDto> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<PrescriptionPatientTreatmentNursingCardRespDto> patientList) {
        this.patientList = patientList;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }
}
