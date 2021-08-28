package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderRespDto;

import javax.persistence.*;
import java.time.*;
import java.util.Date;
import java.util.List;


@javax.persistence.Entity
@Table(name = "inventory.prescription_medicine_order")
public class PrescriptionMedicineOrder extends BasePrescriptionMedicineOrder implements DtoConvertible<PrescriptionMedicineOrderRespDto> {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<PrescriptionMedicineOrderLine> lineList;

    @Column(name = "confirmed_date")
    private Date confirmedDate;

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public List<PrescriptionMedicineOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<PrescriptionMedicineOrderLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public PrescriptionMedicineOrderRespDto toDto() {
        PrescriptionMedicineOrderRespDto dto = DtoUtils.convertRawDto(this);
        dto.setOrderNumberCode(dto.getOrderNumberCode().substring(4));
        dto.setSubmitBy(this.getWhoCreatedName());
        dto.setWardName(this.getWard().getName());
        super.setDtoValue(dto);
        return dto;
    }
}
