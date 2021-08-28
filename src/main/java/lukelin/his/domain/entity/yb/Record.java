package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "yb.record")
public class Record extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "quantity_total")
    private BigDecimal totalQuantity;

    @Column(name = "quantity_left")
    private BigDecimal leftQuantity;

    @Column(name = "record_number")
    private String recordNumber;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
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

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getLeftQuantity() {
        return leftQuantity;
    }

    public void setLeftQuantity(BigDecimal leftQuantity) {
        this.leftQuantity = leftQuantity;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
