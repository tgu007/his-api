package lukelin.his.domain.entity.patient_sign_in;

import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "patient_sign_in.patient_sign_in_medicine_stock_cache")
public class PatientMedicineStockCache {
    @ManyToOne
    @JoinColumn(name = "origin_purchase_line_id", nullable = false)
    private MedicineOrderLine originPurchaseLine;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(nullable = false, name = "min_uom_quantity")
    private BigDecimal minUomQuantity;

    public MedicineOrderLine getOriginPurchaseLine() {
        return originPurchaseLine;
    }

    public void setOriginPurchaseLine(MedicineOrderLine originPurchaseLine) {
        this.originPurchaseLine = originPurchaseLine;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public BigDecimal getMinUomQuantity() {
        return minUomQuantity;
    }

    public void setMinUomQuantity(BigDecimal minUomQuantity) {
        this.minUomQuantity = minUomQuantity;
    }
}
