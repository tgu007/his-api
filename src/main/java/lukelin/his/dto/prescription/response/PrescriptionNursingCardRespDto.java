package lukelin.his.dto.prescription.response;

import java.util.UUID;

public class PrescriptionNursingCardRespDto {
    private String entityName;

    private String quantityInfo;

    private String serveInfo;

    private String useMethod;

    private String frequency;

    private UUID groupId;

    private String dropSpeed;

    private int groupIndex;

    private String reference;

    private Integer rowSpan = 1;

    private Boolean hideColumn = false;

    private UUID prescriptionId;


    private PrescriptionSignatureRespDto signatureOne;

    private PrescriptionSignatureRespDto signatureTwo;

    private PrescriptionSignatureRespDto signatureThree;

    private PrescriptionSignatureRespDto signatureFour;

    private Boolean selfMedicine;

    private Boolean firstDay;

    private String firstDayQuantityInfo;

    private String groupColour;

    private String bottleColour;

    private String sampleType;

    public String getBottleColour() {
        return bottleColour;
    }

    public void setBottleColour(String bottleColour) {
        this.bottleColour = bottleColour;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getGroupColour() {
        return groupColour;
    }

    public void setGroupColour(String groupColour) {
        this.groupColour = groupColour;
    }

    public Boolean getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Boolean firstDay) {
        this.firstDay = firstDay;
    }

    public String getFirstDayQuantityInfo() {
        return firstDayQuantityInfo;
    }

    public void setFirstDayQuantityInfo(String firstDayQuantityInfo) {
        this.firstDayQuantityInfo = firstDayQuantityInfo;
    }

    public Boolean getSelfMedicine() {
        return selfMedicine;
    }

    public void setSelfMedicine(Boolean selfMedicine) {
        this.selfMedicine = selfMedicine;
    }

    public PrescriptionSignatureRespDto getSignatureOne() {
        return signatureOne;
    }

    public void setSignatureOne(PrescriptionSignatureRespDto signatureOne) {
        this.signatureOne = signatureOne;
    }

    public PrescriptionSignatureRespDto getSignatureTwo() {
        return signatureTwo;
    }

    public void setSignatureTwo(PrescriptionSignatureRespDto signatureTwo) {
        this.signatureTwo = signatureTwo;
    }

    public PrescriptionSignatureRespDto getSignatureThree() {
        return signatureThree;
    }

    public void setSignatureThree(PrescriptionSignatureRespDto signatureThree) {
        this.signatureThree = signatureThree;
    }

    public PrescriptionSignatureRespDto getSignatureFour() {
        return signatureFour;
    }

    public void setSignatureFour(PrescriptionSignatureRespDto signatureFour) {
        this.signatureFour = signatureFour;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getQuantityInfo() {
        return quantityInfo;
    }

    public void setQuantityInfo(String quantityInfo) {
        this.quantityInfo = quantityInfo;
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Boolean getHideColumn() {
        return hideColumn;
    }

    public void setHideColumn(Boolean hideColumn) {
        this.hideColumn = hideColumn;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getServeInfo() {
        return serveInfo;
    }

    public void setServeInfo(String serveInfo) {
        this.serveInfo = serveInfo;
    }

    public String getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(String useMethod) {
        this.useMethod = useMethod;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

}
