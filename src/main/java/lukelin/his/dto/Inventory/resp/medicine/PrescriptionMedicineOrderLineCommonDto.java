package lukelin.his.dto.Inventory.resp.medicine;

public class PrescriptionMedicineOrderLineCommonDto {
    //private int orderQuantity;

    //private String orderQuantityInfo;

    private String pharmacyModel;

    private String priceInfo;

    private String medicineName;

    private String useMethodInfo;

    private String manufacturer;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getUseMethodInfo() {
        return useMethodInfo;
    }

    public void setUseMethodInfo(String useMethodInfo) {
        this.useMethodInfo = useMethodInfo;
    }

//    public int getOrderQuantity() {
//        return orderQuantity;
//    }
//
//    public void setOrderQuantity(int orderQuantity) {
//        this.orderQuantity = orderQuantity;
//    }
//
//    public String getOrderQuantityInfo() {
//        return orderQuantityInfo;
//    }

//    public void setOrderQuantityInfo(String orderQuantityInfo) {
//        this.orderQuantityInfo = orderQuantityInfo;
//    }

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

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}
