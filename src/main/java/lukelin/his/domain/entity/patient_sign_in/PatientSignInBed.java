package lukelin.his.domain.entity.patient_sign_in;

import io.ebean.annotation.WhenCreated;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "patient_sign_in.patient_sign_in_bed")
public class PatientSignInBed extends BaseEntity {
    @Id
    private UUID uuid;

    @WhenCreated
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @ManyToOne
    @JoinColumn(name = "bed_id", nullable = false)
    private WardRoomBed wardRoomBed;

    public WardRoomBed getWardRoomBed() {
        return wardRoomBed;
    }

    public void setWardRoomBed(WardRoomBed wardRoomBed) {
        this.wardRoomBed = wardRoomBed;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }


}
