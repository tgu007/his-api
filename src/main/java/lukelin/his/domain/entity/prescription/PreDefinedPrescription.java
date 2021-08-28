package lukelin.his.domain.entity.prescription;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.domain.entity.basic.codeEntity.BaseSearchableCodeEntity;
import lukelin.his.domain.entity.basic.template.MedicalRecordTemplate;
import lukelin.his.domain.enums.Prescription.PreDefinedPrescriptionType;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionDto;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionListDto;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionMedicineDto;
import lukelin.his.dto.prescription.response.PreDefinedPrescriptionTreatmentDto;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "prescription.prescription_predefined_group")
public class PreDefinedPrescription extends BaseSearchableCodeEntity implements DtoConvertible<PreDefinedPrescriptionDto> {
    public PreDefinedPrescription() {
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee createdByDoctor;

    @OneToMany(mappedBy = "preDefinedGroup", cascade = CascadeType.ALL)
    private List<PreDefinedPrescriptionTreatment> treatmentList;

    @OneToMany(mappedBy = "preDefinedGroup", cascade = CascadeType.ALL)
    private List<PreDefinedPrescriptionMedicine> medicineList;


    @Column(nullable = false)
    private PreDefinedPrescriptionType type;

    @Column(name = "is_one_off", nullable = false)
    private boolean oneOff;

    public boolean isOneOff() {
        return oneOff;
    }

    public void setOneOff(boolean oneOff) {
        this.oneOff = oneOff;
    }

    public PreDefinedPrescriptionType getType() {
        return type;
    }

    public void setType(PreDefinedPrescriptionType type) {
        this.type = type;
    }

    public Employee getCreatedByDoctor() {
        return createdByDoctor;
    }

    public void setCreatedByDoctor(Employee createdByDoctor) {
        this.createdByDoctor = createdByDoctor;
    }

    public List<PreDefinedPrescriptionTreatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<PreDefinedPrescriptionTreatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public List<PreDefinedPrescriptionMedicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<PreDefinedPrescriptionMedicine> medicineList) {
        this.medicineList = medicineList;
    }

    @Override
    public PreDefinedPrescriptionDto toDto() {
        PreDefinedPrescriptionDto dto = DtoUtils.convertRawDto(this);
        dto.setCreatedByDoctor(this.getCreatedByDoctor().toDto());

        List<PreDefinedPrescriptionTreatmentDto> treatmentDtoList = new ArrayList<>();
        if (this.getTreatmentList() != null) {
            for (PreDefinedPrescriptionTreatment treatment : this.getTreatmentList())
                treatmentDtoList.add(treatment.toDto());
        }
        dto.setTreatmentList(treatmentDtoList);

        List<PreDefinedPrescriptionMedicineDto> medicineDtoList = new ArrayList<>();
        if (this.getMedicineList() != null) {
            for (PreDefinedPrescriptionMedicine medicine : this.getMedicineList())
                medicineDtoList.add(medicine.toDto());
        }
        dto.setMedicineList(medicineDtoList);
        return dto;
    }

    public PreDefinedPrescriptionListDto toListDto() {
        PreDefinedPrescriptionListDto listDto = new PreDefinedPrescriptionListDto();
        BeanUtils.copyProperties(this, listDto);
        listDto.setCreatedByDoctor(this.getCreatedByDoctor().toDto());
        return listDto;
    }
}
