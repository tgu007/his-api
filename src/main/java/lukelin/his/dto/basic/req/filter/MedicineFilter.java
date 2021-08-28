package lukelin.his.dto.basic.req.filter;

import java.util.List;

public class MedicineFilter extends InventoryEntityFilter {

    private Integer medicineTypeId;

    private Boolean serveUomDataFix;

    private Boolean minUomDataFix;

    private Boolean needPrescription;

    private List<String> medicineTypeNameList;

    private Boolean checkYbPrice;

    private Boolean centerMedicineExpired;

    public Boolean getCenterMedicineExpired() {
        return centerMedicineExpired;
    }

    public void setCenterMedicineExpired(Boolean centerMedicineExpired) {
        this.centerMedicineExpired = centerMedicineExpired;
    }

    public Boolean getCheckYbPrice() {
        return checkYbPrice;
    }

    public void setCheckYbPrice(Boolean checkYbPrice) {
        this.checkYbPrice = checkYbPrice;
    }

    public List<String> getMedicineTypeNameList() {
        return medicineTypeNameList;
    }

    public void setMedicineTypeNameList(List<String> medicineTypeNameList) {
        this.medicineTypeNameList = medicineTypeNameList;
    }

    public Boolean getNeedPrescription() {
        return needPrescription;
    }

    public void setNeedPrescription(Boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    public Boolean getServeUomDataFix() {
        return serveUomDataFix;
    }

    public void setServeUomDataFix(Boolean serveUomDataFix) {
        this.serveUomDataFix = serveUomDataFix;
    }

    public Boolean getMinUomDataFix() {
        return minUomDataFix;
    }

    public void setMinUomDataFix(Boolean minUomDataFix) {
        this.minUomDataFix = minUomDataFix;
    }

    public Integer getMedicineTypeId() {
        return medicineTypeId;
    }

    public void setMedicineTypeId(Integer medicineTypeId) {
        this.medicineTypeId = medicineTypeId;
    }
}
