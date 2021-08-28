package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderRespDto;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineReturnOrderRespDto;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Table(name = "inventory.prescription_medicine_return_order")
public class PrescriptionMedicineReturnOrder extends BasePrescriptionMedicineOrder implements DtoConvertible<PrescriptionMedicineReturnOrderRespDto> {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<PrescriptionMedicineReturnOrderLine> lineList;

    public List<PrescriptionMedicineReturnOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<PrescriptionMedicineReturnOrderLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public PrescriptionMedicineReturnOrderRespDto toDto() {
        PrescriptionMedicineReturnOrderRespDto dto = DtoUtils.convertRawDto(this);
        dto.setOrderNumberCode(dto.getOrderNumberCode().substring(4));
        dto.setWardName(this.getWard().getName());
        dto.setSubmitBy(this.getWhoCreatedName());
        super.setDtoValue(dto);
        return dto;
    }
}
