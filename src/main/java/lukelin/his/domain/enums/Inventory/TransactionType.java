package lukelin.his.domain.enums.Inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    purchaseOrder("采购"),
    returnOrder("退货"),
    transferOut("出库"),
    transferIn("入库"),
    manualFee("计费"),
    cancelFee("退费"),
    adjustmentDown("盘点减少"),
    adjustmentUp("盘点增加"),
    medicineOrder("医嘱领药"),
    rejectedTransferOut("出库未确认"),
    rejectedMedicineOrder("发药未确认");


    private String description;

    TransactionType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
