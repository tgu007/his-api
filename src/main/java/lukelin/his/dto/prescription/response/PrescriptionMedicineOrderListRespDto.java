package lukelin.his.dto.prescription.response;

import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;

import java.util.UUID;

public class PrescriptionMedicineOrderListRespDto extends BaseWardPatientListRespDto {
    private UUID uuid;

    private int orderQuantity;

    private String orderQuantityInfo;

    private String pharmacyModel;

    private String priceInfo;

    private String useMethodInfo;

    private String medicineName;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getPharmacyModel() {
        return pharmacyModel;
    }

    public void setPharmacyModel(String pharmacyModel) {
        this.pharmacyModel = pharmacyModel;
    }

    public String getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(String priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getUseMethodInfo() {
        return useMethodInfo;
    }

    public void setUseMethodInfo(String useMethodInfo) {
        this.useMethodInfo = useMethodInfo;
    }

    public String getOrderQuantityInfo() {
        return orderQuantityInfo;
    }

    public void setOrderQuantityInfo(String orderQuantityInfo) {
        this.orderQuantityInfo = orderQuantityInfo;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}