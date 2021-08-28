package lukelin.his.domain.entity.prescription;

import lukelin.his.domain.entity.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@javax.persistence.Entity
@Table(name = "prescription.prescription_group")
public class PrescriptionGroup extends BaseEntity {
    @OneToMany(mappedBy = "prescriptionGroup", cascade = CascadeType.ALL)
    private List<Prescription> prescriptionList;

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }
}
