package lukelin.his.system;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dic")
@PropertySource("classpath:config/config.properties")
public class ConfigBeanProp {

    private String frequency;

    private String medicineUseMethod;

    private String ethnic;

    private String marriageStatus;

    private String nursingLevel;

    private String idType;

    private String occupation;

    private String gender;

    private String signInMethod;

    private String patientCondition;

    private String insuranceType;

    private String paymentMethod;

    private String week;

    private String medicineStorageType;

    private String medicineLevel;

    private String medicineAttribute;

    private String medicineForm;

    private String medicineType;

    private String medicineFunctionType;

    private String warehouseType;

    private String itemStorageType;

    private String medicineFeeType;

    private String itemFeeType;

    private String treatmentFeeType;

    private String useMethodOral;

    private String useMethodExternal;

    private String useMethodInjectionVein;

    private String useMethodInjectionMuscle;

    private String useMethodInjectionSkinUnder;

    private String useMethodInjectionSkinIn;

    private String useMethodVeinDrop;

    private String useMethodAerosol;

    private String useMethodBladderWashout;

    private String useMethodNoseFeed;

    private String signOutReason;

    private String recoveryTreatmentType;

    private String labSampleType;

    private String labTreatmentType;

    public String getUseMethodNoseFeed() {
        return useMethodNoseFeed;
    }

    public void setUseMethodNoseFeed(String useMethodNoseFeed) {
        this.useMethodNoseFeed = useMethodNoseFeed;
    }

    public String getLabSampleType() {
        return labSampleType;
    }

    public void setLabSampleType(String labSampleType) {
        this.labSampleType = labSampleType;
    }

    public String getLabTreatmentType() {
        return labTreatmentType;
    }

    public void setLabTreatmentType(String labTreatmentType) {
        this.labTreatmentType = labTreatmentType;
    }

    public String getRecoveryTreatmentType() {
        return recoveryTreatmentType;
    }

    public void setRecoveryTreatmentType(String recoveryTreatmentType) {
        this.recoveryTreatmentType = recoveryTreatmentType;
    }

    public String getSignOutReason() {
        return signOutReason;
    }

    public void setSignOutReason(String signOutReason) {
        this.signOutReason = signOutReason;
    }

    public String getUseMethodOral() {
        return useMethodOral;
    }

    public void setUseMethodOral(String useMethodOral) {
        this.useMethodOral = useMethodOral;
    }

    public String getUseMethodExternal() {
        return useMethodExternal;
    }

    public void setUseMethodExternal(String useMethodExternal) {
        this.useMethodExternal = useMethodExternal;
    }

    public String getUseMethodInjectionVein() {
        return useMethodInjectionVein;
    }

    public void setUseMethodInjectionVein(String useMethodInjectionVein) {
        this.useMethodInjectionVein = useMethodInjectionVein;
    }

    public String getUseMethodInjectionMuscle() {
        return useMethodInjectionMuscle;
    }

    public void setUseMethodInjectionMuscle(String useMethodInjectionMuscle) {
        this.useMethodInjectionMuscle = useMethodInjectionMuscle;
    }

    public String getUseMethodInjectionSkinUnder() {
        return useMethodInjectionSkinUnder;
    }

    public void setUseMethodInjectionSkinUnder(String useMethodInjectionSkinUnder) {
        this.useMethodInjectionSkinUnder = useMethodInjectionSkinUnder;
    }

    public String getUseMethodInjectionSkinIn() {
        return useMethodInjectionSkinIn;
    }

    public void setUseMethodInjectionSkinIn(String useMethodInjectionSkinIn) {
        this.useMethodInjectionSkinIn = useMethodInjectionSkinIn;
    }

    public String getUseMethodVeinDrop() {
        return useMethodVeinDrop;
    }

    public void setUseMethodVeinDrop(String useMethodVeinDrop) {
        this.useMethodVeinDrop = useMethodVeinDrop;
    }

    public String getUseMethodAerosol() {
        return useMethodAerosol;
    }

    public void setUseMethodAerosol(String useMethodAerosol) {
        this.useMethodAerosol = useMethodAerosol;
    }

    public String getUseMethodBladderWashout() {
        return useMethodBladderWashout;
    }

    public void setUseMethodBladderWashout(String useMethodBladderWashout) {
        this.useMethodBladderWashout = useMethodBladderWashout;
    }

    public String getMedicineFeeType() {
        return medicineFeeType;
    }

    public void setMedicineFeeType(String medicineFeeType) {
        this.medicineFeeType = medicineFeeType;
    }

    public String getItemFeeType() {
        return itemFeeType;
    }

    public void setItemFeeType(String itemFeeType) {
        this.itemFeeType = itemFeeType;
    }

    public String getTreatmentFeeType() {
        return treatmentFeeType;
    }

    public void setTreatmentFeeType(String treatmentFeeType) {
        this.treatmentFeeType = treatmentFeeType;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getItemStorageType() {
        return itemStorageType;
    }

    public void setItemStorageType(String itemStorageType) {
        this.itemStorageType = itemStorageType;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getMedicineFunctionType() {
        return medicineFunctionType;
    }

    public void setMedicineFunctionType(String medicineFunctionType) {
        this.medicineFunctionType = medicineFunctionType;
    }

    public String getMedicineStorageType() {
        return medicineStorageType;
    }

    public void setMedicineStorageType(String medicineStorageType) {
        this.medicineStorageType = medicineStorageType;
    }

    public String getMedicineLevel() {
        return medicineLevel;
    }

    public void setMedicineLevel(String medicineLevel) {
        this.medicineLevel = medicineLevel;
    }

    public String getMedicineAttribute() {
        return medicineAttribute;
    }

    public void setMedicineAttribute(String medicineAttribute) {
        this.medicineAttribute = medicineAttribute;
    }

    public String getMedicineForm() {
        return medicineForm;
    }

    public void setMedicineForm(String medicineForm) {
        this.medicineForm = medicineForm;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSignInMethod() {
        return signInMethod;
    }

    public void setSignInMethod(String signInMethod) {
        this.signInMethod = signInMethod;
    }

    public String getPatientCondition() {
        return patientCondition;
    }

    public void setPatientCondition(String patientCondition) {
        this.patientCondition = patientCondition;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getNursingLevel() {
        return nursingLevel;
    }

    public void setNursingLevel(String nursingLevel) {
        this.nursingLevel = nursingLevel;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMedicineUseMethod() {
        return medicineUseMethod;
    }

    public void setMedicineUseMethod(String medicineUseMethod) {
        this.medicineUseMethod = medicineUseMethod;
    }
}
