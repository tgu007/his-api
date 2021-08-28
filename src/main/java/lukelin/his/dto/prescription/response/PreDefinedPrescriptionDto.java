package lukelin.his.dto.prescription.response;


import lukelin.his.domain.enums.Prescription.PreDefinedPrescriptionType;
import lukelin.his.dto.basic.resp.setup.BaseCodeDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;

import java.util.*;

public class PreDefinedPrescriptionDto extends BaseCodeDto {

    private EmployeeDto createdByDoctor;

    private List<PreDefinedPrescriptionTreatmentDto> treatmentList;

    private List<PreDefinedPrescriptionMedicineDto> medicineList;

    private PreDefinedPrescriptionType type;

    private boolean oneOff;

    public boolean isOneOff() {
        return oneOff;
    }

    public void setOneOff(boolean oneOff) {
        this.oneOff = oneOff;
    }

    public List<PreDefinedPrescriptionMedicineDto> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<PreDefinedPrescriptionMedicineDto> medicineList) {
        this.medicineList = medicineList;
    }

    public PreDefinedPrescriptionType getType() {
        return type;
    }

    public void setType(PreDefinedPrescriptionType type) {
        this.type = type;
    }

    public EmployeeDto getCreatedByDoctor() {
        return createdByDoctor;
    }

    public void setCreatedByDoctor(EmployeeDto createdByDoctor) {
        this.createdByDoctor = createdByDoctor;
    }

    public List<PreDefinedPrescriptionTreatmentDto> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<PreDefinedPrescriptionTreatmentDto> treatmentList) {
        this.treatmentList = treatmentList;
    }
}
