package lukelin.his.domain.entity.basic.entity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.dto.basic.resp.entity.MedicineSnapshotRespDto;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.snapshot_medicine")
public class MedicineSnapshot extends BaseMedicine implements DtoConvertible<MedicineSnapshotRespDto> {
    public MedicineSnapshot() {
    }

    public MedicineSnapshot(UUID uuid) {
        this.setUuid(uuid);
    }

    private Integer code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @Override
    public MedicineSnapshotRespDto toDto() {
        MedicineSnapshotRespDto responseDto = DtoUtils.convertRawDto(this);
        this.copyDtoProperty(responseDto);
        //前台调用时DTO UUID Medicine ID
        responseDto.setSnapshotId(this.getUuid());
        responseDto.setUuid(this.getMedicine().getUuid());

        responseDto.setManufacturer(this.getManufacturerMedicine().toDto());
        if (this.getCenterMedicine() != null)
            responseDto.setCenterMedicine(this.getCenterMedicine().toDto());
        return responseDto;
    }


}