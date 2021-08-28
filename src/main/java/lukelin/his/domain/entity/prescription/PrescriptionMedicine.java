package lukelin.his.domain.entity.prescription;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Frequency;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrderLine;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderLineRespDto;
import lukelin.his.dto.prescription.response.PrescriptionNursingCardRespDto;
import lukelin.his.dto.prescription.response.PrescriptionMedicineRespDto;
import lukelin.his.dto.prescription.response.PrescriptionListRespDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Table(name = "prescription.prescription_medicine_pharmacy")
public class PrescriptionMedicine extends BaseEntity implements DtoConvertible<PrescriptionMedicineRespDto> {
    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "medicine_snapshot_id", nullable = false)
    private MedicineSnapshot medicineSnapshot;

//    @Column(name = "issue_quantity", nullable = false)
//    private BigDecimal issueQuantity;

    @ManyToOne
    @JoinColumn(name = "use_method_id", nullable = false)
    private Dictionary useMethod;

    @Column(name = "drop_speed")
    private String dropSpeed;

    @OneToOne()
    @JoinColumn(name = "prescription_chargeable_id")
    private PrescriptionChargeable prescriptionChargeable;

    @Column(name = "last_order_date")
    private Date last_order_date;

    @Column(name = "serve_quantity", nullable = false)
    private BigDecimal serveQuantity;

    @Column(name = "fixed_quantity")
    private BigDecimal fixedQuantity;

    @Column(name = "fixed_quantity_chinese")
    private BigDecimal fixedChineseMedicineQuantity;

    public BigDecimal getFixedChineseMedicineQuantity() {
        return fixedChineseMedicineQuantity;
    }

    public void setFixedChineseMedicineQuantity(BigDecimal fixedChineseMedicineQuantity) {
        this.fixedChineseMedicineQuantity = fixedChineseMedicineQuantity;
    }

    public BigDecimal getFixedQuantity() {
        return fixedQuantity;
    }

    public void setFixedQuantity(BigDecimal fixedQuantity) {
        this.fixedQuantity = fixedQuantity;
    }

    @OneToMany(mappedBy = "prescriptionMedicine", cascade = CascadeType.ALL)
    private List<PrescriptionMedicineOrderLine> orderLineList;

    public List<PrescriptionMedicineOrderLine> getOrderLineList() {
        return orderLineList;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fromPrescriptionMedicine")
    private PrescriptionMedicineText prescriptionMedicineText;

    @OneToOne()
    @JoinColumn(name = "diagnose_id")
    private Diagnose diagnose;

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    public BigDecimal getServeQuantity() {
        return serveQuantity;
    }

    public void setServeQuantity(BigDecimal serveQuantity) {
        this.serveQuantity = serveQuantity;
    }

    public PrescriptionMedicineText getPrescriptionMedicineText() {
        return prescriptionMedicineText;
    }

    public void setPrescriptionMedicineText(PrescriptionMedicineText prescriptionMedicineText) {
        this.prescriptionMedicineText = prescriptionMedicineText;
    }

    public void setOrderLineList(List<PrescriptionMedicineOrderLine> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public Date getLast_order_date() {
        return last_order_date;
    }

    public void setLast_order_date(Date last_order_date) {
        this.last_order_date = last_order_date;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public MedicineSnapshot getMedicineSnapshot() {
        return medicineSnapshot;
    }

    public void setMedicineSnapshot(MedicineSnapshot medicineSnapshot) {
        this.medicineSnapshot = medicineSnapshot;
    }

//    public BigDecimal getIssueQuantity() {
//        return issueQuantity;
//    }
//
//    public void setIssueQuantity(BigDecimal issueQuantity) {
//        this.issueQuantity = issueQuantity;
//    }

    public Dictionary getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(Dictionary useMethod) {
        this.useMethod = useMethod;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public PrescriptionChargeable getPrescriptionChargeable() {
        return prescriptionChargeable;
    }

    public void setPrescriptionChargeable(PrescriptionChargeable prescriptionChargeable) {
        this.prescriptionChargeable = prescriptionChargeable;
    }

    public PrescriptionListRespDto copyValueToListDto(PrescriptionListRespDto prescriptionListRespDto) {
        //MedicineSnapshot medicineSnapshot = this.getMedicineSnapshot();
        PrescriptionChargeable prescriptionChargeable = this.getPrescriptionChargeable();

        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionListRespDto);
        prescriptionListRespDto.setQuantityInfo(this.getUseMinQuantityInfo());
        prescriptionListRespDto.setServeInfo(this.getServeQuantity().toString() + this.getMedicine().getServeUom().getName());

        if (this.getMedicine().chineseMedicine())
            prescriptionListRespDto.setFixedQuantity(this.getFixedChineseMedicineQuantity());
        else
            prescriptionListRespDto.setFixedQuantity(this.getFixedQuantity());
        if (prescriptionListRespDto.getFixedQuantity() != null) {
            if (this.getMedicine().chineseMedicine())
                prescriptionListRespDto.setFixedQuantityInfo(prescriptionListRespDto.getFixedQuantity().toString() + "帖");
            else
                prescriptionListRespDto.setFixedQuantityInfo(this.getMedicine().getDisplayQuantity(WarehouseType.pharmacy, this.getFixedQuantity()));
        }

        if (prescriptionChargeable.getFirstDayQuantity() != null)
            prescriptionListRespDto.setFirstDayQuantityInfo(prescriptionChargeable.getFirstDayQuantity().setScale(0).toString());
        List<PrescriptionMedicineOrderLineStatus> filterStatusList = new ArrayList<>();
        filterStatusList.add(PrescriptionMedicineOrderLineStatus.approved);
        prescriptionListRespDto.setIssueQuantityInfo(this.getMedicine().getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(this.totalIssuedQuantity(filterStatusList))));
        prescriptionListRespDto.setDropSpeed(this.getDropSpeed());
        prescriptionListRespDto.setUseMethod(this.getUseMethod().getName());
        prescriptionListRespDto.setUnitPrice(this.getMedicine().getListPrice());
        prescriptionListRespDto.setDescription(this.getMedicine().getName());
        prescriptionListRespDto.setPrescriptionDetailId(this.getUuid());
        if (this.getDiagnose() != null)
            prescriptionListRespDto.setDiagnoseName(this.getDiagnose().getName());
        return prescriptionListRespDto;
    }

    public PrescriptionMedicineRespDto toDto() {
        PrescriptionMedicineRespDto respDto = DtoUtils.convertRawDto(this);
        respDto.setUseMethod(this.getUseMethod().toDto());
        respDto.setMedicine(this.getMedicineSnapshot().getMedicine().toDto());
        respDto.setPrescriptionChargeable(this.getPrescriptionChargeable().toDto());
        if (this.getDiagnose() != null)
            respDto.setDiagnose(this.getDiagnose().toDto());
        respDto.setSlipDescription(this.getMedicine().getName() + " " + this.getPrescriptionChargeable().getQuantity() + this.getMedicine().getMinUom().getName());

        //若为中药，设置帖数为总数量
        if (this.getMedicine().chineseMedicine())
            respDto.setFixedQuantity(this.getFixedChineseMedicineQuantity());
        return respDto;
    }

    public PrescriptionMedicine toCloneEntity() {
        PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionMedicine);
        prescriptionMedicine.setUuid(null);
        prescriptionMedicine.setPrescriptionChargeable(null);
        //prescriptionMedicine.setMedicine(this.getMedicine());
        prescriptionMedicine.setMedicineSnapshot(this.medicine.findLatestSnapshot());
        prescriptionMedicine.setDiagnose(this.diagnose);
        //prescriptionMedicine.setUseMethod(this.useMethod);

        return prescriptionMedicine;
    }

//    public void setFeeValue(Fee newFee) {
//        newFee.setUomInfo(this.getMedicine().getDepartmentUom().getName());
//        newFee.setFeeTypeName(this.getMedicine().getFeeType().getName());
//        newFee.setSearchCode(this.getMedicine().getSearchCode());
//        newFee.setUnitAmount(this.getMedicine().getListPrice().multiply(this.getIssueQuantity()));
//        newFee.setMedicine(this.getMedicine().findLatestSnapshot());
//        newFee.setDescription(this.getMedicine().getName());
//    }

    public Integer totalNeedIssueQuantity(Date mockFutureDate) {
        //mockFutureDate used to mock future Date.
        Date date = new Date();
        if (mockFutureDate != null)
            date = mockFutureDate;

        //如果为开始日
        Frequency frequency = this.getPrescriptionChargeable().getFrequency().getFrequencyInfo();
        BigDecimal totalMinSizeQuantity = null;
        int daysBetween = this.getPrescriptionChargeable().getPrescription().daysFromPrescriptionStart(date);
        if (frequency.getRangeLimit() == null) //按每日数量计算
        {
            daysBetween++; //需要预领明天的药物
            BigDecimal consumedDays = new BigDecimal(daysBetween).divide(new BigDecimal(frequency.getDayInterval()), 0, RoundingMode.CEILING);

            BigDecimal firstDayQuantity;
            BigDecimal dayQuantity = new BigDecimal(frequency.getDayQuantity());
            if (this.getPrescriptionChargeable().executeOnPrescriptionStartDate())
                firstDayQuantity = this.getPrescriptionChargeable().getFirstDayQuantity().multiply(this.getPrescriptionChargeable().getQuantity());
            else
                firstDayQuantity = dayQuantity.multiply(this.getPrescriptionChargeable().getQuantity());
            //  到今日一共应该发了多少数量 每次数量*执行天数*每天数量 +首日数量
            totalMinSizeQuantity = (this.getPrescriptionChargeable().getQuantity().multiply(dayQuantity).multiply(new BigDecimal(consumedDays.intValue() - 1))).add(firstDayQuantity);
        } else //按周期计算
        {
            int rangeNumber = daysBetween / frequency.getDayRange() + 1;
            totalMinSizeQuantity = this.getPrescriptionChargeable().getQuantity().multiply(new BigDecimal(rangeNumber)).multiply(new BigDecimal(frequency.getRangeLimit()));
        }

        //转换为药库包装，至今应该一共发了多少数量;
        //BigDecimal factor = this.getMedicine().getDepartmentToMinRate();
        //Integer issueQuantity = totalMinSizeQuantity.divide(factor, 0, RoundingMode.CEILING).intValue();
        return totalMinSizeQuantity.intValue();
    }

    public Integer totalIssuedQuantity(List<PrescriptionMedicineOrderLineStatus> filterStatusList) {
        return this.getOrderLineList().stream()
                .filter(l -> filterStatusList.contains(l.getStatus()))
                .map(PrescriptionMedicineOrderLine::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0).intValue();
    }

    public List<PrescriptionMedicineReturnOrderLine> createReturnOrderList() {
        List<PrescriptionMedicineReturnOrderLine> returnOrderLineList = new ArrayList<>();
        for (PrescriptionMedicineOrderLine orderLine : this.getOrderLineList()) {
            if (orderLine.getStatus() != PrescriptionMedicineOrderLineStatus.approved)
                continue;

            PrescriptionMedicineReturnOrderLine returnOrderLine = orderLine.createReturnOrderLine();
            returnOrderLineList.add(returnOrderLine);

        }
        return returnOrderLineList;
    }

//    public Prescription createMedicineTextPrescription() {
//        Prescription newPrescription = this.getPrescriptionChargeable().getPrescription().createNewPrescriptionFromThis(PrescriptionType.TextMedicine, false);
//        PrescriptionMedicineText newPrescriptionMedicineText = new PrescriptionMedicineText();
//        newPrescription.setPrescriptionMedicineText(newPrescriptionMedicineText);
//        newPrescriptionMedicineText.setFromPrescriptionMedicine(this);
//
//        newPrescriptionMedicineText.setFrequency(this.getPrescriptionChargeable().getFrequency());
//        newPrescriptionMedicineText.setUseMethod(this.getUseMethod());
//        newPrescriptionMedicineText.setMedicineName(this.getMedicine().getName());
//        newPrescriptionMedicineText.setDropSpeed(this.getDropSpeed());
//
//        PrescriptionChargeable thisPrescriptionChargeable = this.getPrescriptionChargeable();
//        Medicine medicine = this.getMedicine();
//
//        if (this.getPrescriptionChargeable().getFirstDayQuantity() != null && this.getPrescriptionChargeable().getFirstDayQuantity().compareTo(BigDecimal.ZERO) > 0)
//            newPrescriptionMedicineText.setFirstDayQuantity(thisPrescriptionChargeable.getFirstDayQuantity().setScale(0).toString());
//        newPrescriptionMedicineText.setQuantity(this.getUseMinQuantityInfo());
//        newPrescriptionMedicineText.setServeQuantity(this.getServeQuantity().toString() + this.getMedicine().getServeUom().getName());
//        return newPrescription;
//    }

    public PrescriptionNursingCardRespDto toMedicineCardDto() {
        PrescriptionNursingCardRespDto dto = new PrescriptionNursingCardRespDto();
        dto.setPrescriptionId(this.getPrescriptionChargeable().getPrescription().getUuid());
        dto.setDropSpeed(this.getDropSpeed());
        dto.setUseMethod(this.getUseMethod().getName());
        dto.setFrequency(this.getPrescriptionChargeable().getFrequency().getCode());
        dto.setEntityName(this.getMedicine().getName());
        dto.setServeInfo(this.getServeQuantity().toString() + this.getMedicine().getServeUom().getName());
        dto.setQuantityInfo(this.getUseMinQuantityInfo());
        Prescription prescription = this.getPrescriptionChargeable().getPrescription();
        if (prescription.getPrescriptionGroup() != null)
            dto.setGroupId(prescription.getUuid());
        dto.setReference(prescription.getReference());
        dto.setSelfMedicine(false);
        if (prescriptionChargeable.getFirstDayQuantity() != null)
            dto.setFirstDayQuantityInfo(prescription.getPrescriptionChargeable().getFirstDayQuantity().setScale(0).toString());
        return dto;
    }

    private String getUseMinQuantityInfo() {
        return this.getServeQuantity().divide(this.getMedicine().getServeToMinRate(), 2, RoundingMode.UP).toString() + this.getMedicine().getMinUom().getName();
    }

    public void setPrescriptionInfo(PrescriptionMedicineOrderLineRespDto dto) {
        dto.setPrescriptionValid(true);
        String prescriptionDescription;
        Prescription prescription = this.getPrescriptionChargeable().getPrescription();
        if (prescription.isOneOff())
            prescriptionDescription = "临时";
        else
            prescriptionDescription = "长期";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        prescriptionDescription += " 开嘱日期:" + sdf.format(prescription.getStartDate()) + " 开嘱医生：" + this.getWhoCreatedName();
        //dto.setPrescriptionDescription(prescriptionDescription);

        if (prescription.getStatus() == PrescriptionStatus.canceled) {
            dto.setPrescriptionValid(false);
            prescriptionDescription = "医嘱已取消 " + prescriptionDescription;
        }

        if (prescription.getStatus() == PrescriptionStatus.disabled || prescription.getStatus() == PrescriptionStatus.pendingDisable) {
            dto.setPrescriptionValid(false);
            prescriptionDescription = "医嘱已停用或待停用 " + prescriptionDescription;
        }

        if (!medicine.isEnabled()) {
            dto.setPrescriptionValid(false);
            prescriptionDescription = "药品已作废 " + prescriptionDescription;
        }

        dto.setPrescriptionDescription(prescriptionDescription);
    }

//    public BigDecimal getPharmacyIssueQuantity() {
//        BigDecimal dayFrequencyNumber = new BigDecimal(this.getPrescriptionChargeable().getFrequency().getFrequencyInfo().getDayQuantity());
//        return this.getServeQuantity().multiply(dayFrequencyNumber).divide(this.getMedicine().getServeToMinRate()).divide(this.getMedicine().getDepartmentToMinRate(), 0, RoundingMode.CEILING);
//    }

//    public String getIssueQuantityInfo() {
//        BigDecimal dayFrequencyNumber = new BigDecimal(this.getPrescriptionChargeable().getFrequency().getFrequencyInfo().getDayQuantity());
//        BigDecimal dailyTotalServeQuantity = this.getServeQuantity().multiply(dayFrequencyNumber);
//        BigDecimal dailyTotalPharmacyQuantity = dailyTotalServeQuantity.divide(this.getMedicine().getServeToMinRate()).divide(this.getMedicine().getDepartmentToMinRate(), 0, RoundingMode.CEILING);
//        String issueQuantityInfo = dailyTotalPharmacyQuantity.toString() + this.getMedicine().getDepartmentUom().getName();
//
//        BigDecimal firstDayQuantity = this.getPrescriptionChargeable().getFirstDayQuantity();
//        if (firstDayQuantity.compareTo(BigDecimal.ZERO) > 0) {
//            BigDecimal firstDayTotalServeQuantity = this.getServeQuantity().multiply(firstDayQuantity).add(dailyTotalServeQuantity);
//            BigDecimal firstDayTotalPharmacyQuantity = firstDayTotalServeQuantity.divide(this.getMedicine().getServeToMinRate()).divide(this.getMedicine().getDepartmentToMinRate(), 0, RoundingMode.CEILING);
//            issueQuantityInfo += " (首日:" + firstDayTotalPharmacyQuantity.toString() + this.getMedicine().getDepartmentUom().getName() + ")";
//        }
//        return issueQuantityInfo;
    //}


}
