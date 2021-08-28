package lukelin.his.domain.entity.basic.entity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.dto.basic.resp.entity.TreatmentSnapshotRespDto;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.snapshot_treatment")
public class TreatmentSnapshot extends BaseTreatment implements DtoConvertible<TreatmentSnapshotRespDto> {
    public TreatmentSnapshot() {
    }

    public TreatmentSnapshot(UUID uuid) {
        this.setUuid(uuid);
    }

    private Integer code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public TreatmentSnapshotRespDto toDto() {
        TreatmentSnapshotRespDto responseDto = DtoUtils.convertRawDto(this);
        responseDto.setFeeType(this.getFeeType().toDto());
        responseDto.setMinSizeUom(this.getMinUom().toDto());
        //前台调用时DTO UUID 为Treatment ID
        responseDto.setSnapshotId(this.getUuid());
        responseDto.setUuid(this.getTreatment().getUuid());
        if (this.getTreatment().getDefaultExecuteDepartment() != null) {
            responseDto.setDefaultExecuteDepartment(this.getTreatment().getDefaultExecuteDepartment().toDto());
        }
        return responseDto;
    }


}
