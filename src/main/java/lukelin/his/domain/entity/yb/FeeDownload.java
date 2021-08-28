package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "yb.fee_download")
public class FeeDownload extends BaseEntity {

    private String jzxh;

    @OneToOne()
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @OneToMany(mappedBy = "feeDownload", cascade = CascadeType.ALL)
    private List<FeeDownloadLine> lineList;

    @Column(name = "row_number")
    private Integer rowNumber;

    public List<FeeDownloadLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<FeeDownloadLine> lineList) {
        this.lineList = lineList;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getJzxh() {
        return jzxh;
    }

    public void setJzxh(String jzxh) {
        this.jzxh = jzxh;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

}


