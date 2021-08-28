package lukelin.his.dto.prescription.request;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.PrescriptionSaveDtoInterface;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PrescriptionMedicineSaveDto implements PrescriptionSaveDtoInterface {
    private UUID uuid;

    private UUID medicineId;

    private Integer useMethodId;

    private String dropSpeed;

    //private BigDecimal issueQuantity;

    private PrescriptionChargeableSaveDto prescriptionChargeableSaveDto;

    private BigDecimal serveQuantity;

    private BigDecimal fixedQuantity;

    private UUID diagnoseId;

    private List<PrescriptionGroupMedicineSaveDto> batchNewMedicineList;

    public List<PrescriptionGroupMedicineSaveDto> getBatchNewMedicineList() {
        return batchNewMedicineList;
    }

    public void setBatchNewMedicineList(List<PrescriptionGroupMedicineSaveDto> batchNewMedicineList) {
        this.batchNewMedicineList = batchNewMedicineList;
    }

    public UUID getDiagnoseId() {
        return diagnoseId;
    }

    public void setDiagnoseId(UUID diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public BigDecimal getFixedQuantity() {
        return fixedQuantity;
    }

    public void setFixedQuantity(BigDecimal fixedQuantity) {
        this.fixedQuantity = fixedQuantity;
    }

    public BigDecimal getServeQuantity() {
        return serveQuantity;
    }

    public void setServeQuantity(BigDecimal serveQuantity) {
        this.serveQuantity = serveQuantity;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PrescriptionChargeableSaveDto getPrescriptionChargeableSaveDto() {
        return prescriptionChargeableSaveDto;
    }

    public void setPrescriptionChargeableSaveDto(PrescriptionChargeableSaveDto prescriptionChargeableSaveDto) {
        this.prescriptionChargeableSaveDto = prescriptionChargeableSaveDto;
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getUseMethodId() {
        return useMethodId;
    }

    public void setUseMethodId(Integer useMethodId) {
        this.useMethodId = useMethodId;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

//    public BigDecimal getIssueQuantity() {
//        return issueQuantity;
//    }
//
//    public void setIssueQuantity(BigDecimal issueQuantity) {
//        this.issueQuantity = issueQuantity;
//    }

    public Prescription toEntity() {
        Prescription prescription = this.prescriptionChargeableSaveDto.toEntity();
        PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionMedicine);
        prescription.getPrescriptionChargeable().setPrescriptionMedicine(prescriptionMedicine);
        prescriptionMedicine.setUseMethod(new Dictionary(this.useMethodId));
        Medicine medicine = new Medicine(this.medicineId);
        prescriptionMedicine.setMedicine(medicine);
        MedicineSnapshot latestSnapshot = medicine.findLatestSnapshot();
        prescriptionMedicine.setMedicineSnapshot(latestSnapshot);
        if (this.getDiagnoseId() != null)
            prescriptionMedicine.setDiagnose(Ebean.find(Diagnose.class, this.getDiagnoseId()));
        //开药为剂量单位，使用时向上取整数
        BigDecimal chargeableQuantity = this.getServeQuantity().divide(latestSnapshot.getServeToMinRate(), 0, RoundingMode.CEILING);
        prescription.getPrescriptionChargeable().setQuantity(chargeableQuantity);
        prescription.setDescription(prescriptionMedicine.getMedicineSnapshot().getName());

        //如果为中药，传进的数量为帖数，而非总数量。
        prescriptionMedicine.setFixedChineseMedicineQuantity(null);
        if (latestSnapshot.getMedicine().chineseMedicine()) {
            prescriptionMedicine.setFixedChineseMedicineQuantity(this.getFixedQuantity());
            //总数量帖数乘以数量
            prescriptionMedicine.setFixedQuantity(this.fixedQuantity.multiply(chargeableQuantity).setScale(2, RoundingMode.CEILING));
        }
        return prescription;
    }
}
