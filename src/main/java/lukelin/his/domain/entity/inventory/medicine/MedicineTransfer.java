package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.TransferInterface;
import lukelin.his.domain.entity.inventory.BaseTransfer;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.dto.Inventory.resp.BaseTransferLineRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineTransferListRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineTransferRespDto;
import lukelin.his.dto.yb.inventory.req.OrderHeaderReq;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;
import lukelin.his.dto.yb.inventory.req.OrderLineReq;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventory.medicine_transfer")
public class MedicineTransfer extends BaseTransfer implements TransferInterface {
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL)
    private List<MedicineTransferLine> lineList;

    public List<MedicineTransferLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<MedicineTransferLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public MedicineTransferRespDto toDto() {
        MedicineTransferRespDto transferDto = new MedicineTransferRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, transferDto);
        transferDto.setTransferNumber(Utils.buildDisplayCode(this.getTransferNumber()));
        transferDto.setFromWarehouse(this.getFromWarehouse().toDto());
        transferDto.setToWarehouse(this.getToWarehouse().toDto());

        List<BaseTransferLineRespDto> lineList = new ArrayList<>();
        for (MedicineTransferLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }
        transferDto.setLineList(lineList);
        return transferDto;
    }

    public MedicineTransferListRespDto toListDto() {
        MedicineTransferListRespDto dto = new MedicineTransferListRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        dto.setWhoCreated(this.getWhoCreatedName());
        dto.setTransferNumber(Utils.buildDisplayCode(this.getTransferNumber()));
        dto.setFromWarehouse(this.getFromWarehouse().toDto());
        dto.setToWarehouse(this.getToWarehouse().toDto());
        return dto;
    }

    public OrderHeaderReq toYBOrderHeader() {
        OrderHeaderReq req = new OrderHeaderReq();
        req.setCYWDH(this.getUuid().toString());
        if(this.getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy)
        {
            req.setKFBH(this.getFromWarehouse().getWarehouseUploaded().getServerCode());
            req.setYWDM(InventoryOrderType.transferOut.toString());
            req.setYWBM(this.getFromWarehouse().getDepartment().getName());
        }
        else {
            req.setKFBH(this.getToWarehouse().getWarehouseUploaded().getServerCode());
            req.setYWDM(InventoryOrderType.transferIn.toString());
            req.setYWBM(this.getToWarehouse().getDepartment().getName());
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        req.setFSRQ(df.format(new Date()));
        req.setGYSMC("不适用");
        return req;
    }

    public OrderLineReq toYBOrderLineDto() {
        OrderLineReq req = new OrderLineReq();
        req.setCYWDH(this.getUuid().toString());
        List<OrderLineDetailReq> lineDetailList = new ArrayList<>();
        for (MedicineTransferLine line : this.getLineList())
            if (!line.getMedicine().getSelfPay())
                lineDetailList.addAll(line.toYBOrderLineDetailDto());
        req.setYWMXLB(lineDetailList);
        return req;
    }
}
