package lukelin.his.dto.Inventory.resp.medicine;

import lukelin.his.dto.Inventory.resp.BaseOrderRespDto;

public class MedicineOrderRespDto extends BaseOrderRespDto {
    private MedicineOrderRequestListRespDto orderRequest;

    private String YBOrderId;


    public MedicineOrderRequestListRespDto getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(MedicineOrderRequestListRespDto orderRequest) {
        this.orderRequest = orderRequest;
    }

    public String getYBOrderId() {
        return YBOrderId;
    }

    public void setYBOrderId(String YBOrderId) {
        this.YBOrderId = YBOrderId;
    }
}
