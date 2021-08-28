package lukelin.his.domain.entity.prescription;

import io.ebean.annotation.WhenModified;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.enums.Prescription.PrescriptionChangeAction;
import lukelin.his.dto.prescription.response.PrescriptionChangeLogRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "prescription.prescription_change_log")
public class PrescriptionChangeLog extends BaseEntity implements DtoConvertible<PrescriptionChangeLogRespDto> {
    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @Column(name = "manual_date")
    private Date manualDate;

    @ManyToOne
    @JoinColumn(name = "manual_user_id")
    private Employee manualChangedUser;

    public Employee getManualChangedUser() {
        return manualChangedUser;
    }

    public void setManualChangedUser(Employee manualChangedUser) {
        this.manualChangedUser = manualChangedUser;
    }

    public Date getManualDate() {
        return manualDate;
    }

    public void setManualDate(Date manualDate) {
        this.manualDate = manualDate;
    }

    private PrescriptionChangeAction action;

    public PrescriptionChangeAction getAction() {
        return action;
    }

    public void setAction(PrescriptionChangeAction action) {
        this.action = action;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Date getDate() {
        if (this.getManualDate() != null)
            return this.getManualDate();
        else
            return this.getWhenCreated();
    }

    public String getLogUser() {
        if (this.getManualChangedUser() != null)
            return this.getManualChangedUser().getName();
        else
            return this.getWhoCreatedName();
    }


    @Override
    public PrescriptionChangeLogRespDto toDto() {
        PrescriptionChangeLogRespDto dto = DtoUtils.convertRawDto(this);
        dto.setWhoCreated(this.getLogUser());
        dto.setWhenCreated(this.getDate());
        return dto;
    }
}
