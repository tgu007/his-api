package lukelin.his.domain.entity.inventory.medicine;

import lukelin.his.domain.entity.inventory.BaseOrderRequest;
import lukelin.his.dto.Inventory.resp.BaseOrderRequestLineRespDto;
import lukelin.his.dto.Inventory.resp.NZTransferRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderRequestListRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderRequestRespDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "inventory.medicine_order_request")
public class MedicineOrderRequest extends BaseOrderRequest {
    public MedicineOrderRequest(UUID uuid) {
        this.setUuid(uuid);
    }

    public MedicineOrderRequest() {

    }

    @OneToMany(mappedBy = "orderRequest")
    private List<MedicineOrder> orderList;

    @OneToMany(mappedBy = "orderRequest", cascade = CascadeType.ALL)
    private List<MedicineOrderRequestLine> lineList;

    public List<MedicineOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<MedicineOrder> orderList) {
        this.orderList = orderList;
    }

    public List<MedicineOrderRequestLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<MedicineOrderRequestLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public MedicineOrderRequestRespDto toDto() {
        MedicineOrderRequestRespDto dto = new MedicineOrderRequestRespDto();
        super.setOrderRequestDtoValue(dto);

        List<BaseOrderRequestLineRespDto> lineList = new ArrayList<>();
        this.getLineList().sort(Comparator.comparing(MedicineOrderRequestLine::getWhenCreated));
        for (MedicineOrderRequestLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }
        dto.setLineList(lineList);
        return dto;
    }

    public MedicineOrderRequestListRespDto toListDto() {
        MedicineOrderRequestListRespDto dto = new MedicineOrderRequestListRespDto();
        super.copyListDtoValue(dto);
        return dto;
    }


}
