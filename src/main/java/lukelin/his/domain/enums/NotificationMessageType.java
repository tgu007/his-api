package lukelin.his.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationMessageType {
    pendingPrescription("待审批医嘱"),
    pendingDisablePrescription("确认停嘱"),
    rejectedPrescription("医嘱被退回"),
    pendingExecutionPrescription("待执行项目医嘱"),
    pendingSubmitMedicineOrder("待提交发药单"),
    pendingProcessMedicineOrder("待处理发药单"),
    pendingProcessReturnMedicineOrder("待处理退药单"),
    pendingReturnOrderConfirmation("药房确认退药"),
    newPatientSignIn("新入院病人"),
    newMedicineTransferRequest("药品申领"),
    newItemTransferRequest("物品申领"),
    pendingConfirmMedicineTransfer("待确认药品申领"),
    pendingConfirmItemTransferRequest("待确认物品申领"),
    newSignOutRequest("病人出院"),
    prepareSignOut("准备出院"),
    pendingConfirmPrescriptionMedicineOrder("确认领取药品"),
    noStockAutoFee("自动计费无库存");

    private String description;

    NotificationMessageType(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
