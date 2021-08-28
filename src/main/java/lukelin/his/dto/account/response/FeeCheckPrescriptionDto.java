package lukelin.his.dto.account.response;

import lukelin.his.dto.prescription.response.PrescriptionListRespDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

public class FeeCheckPrescriptionDto {
    private Integer duration;

    private PrescriptionListRespDto prescriptionListDto;

    private List<FeeCheckEntityDto> entityList;

    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PrescriptionListRespDto getPrescriptionListDto() {
        return prescriptionListDto;
    }

    public void setPrescriptionListDto(PrescriptionListRespDto prescriptionListDto) {
        this.prescriptionListDto = prescriptionListDto;
    }

    public BigDecimal getTotalAmount() {
        return this.entityList.stream().map(FeeCheckEntityDto::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
    }


    public List<FeeCheckEntityDto> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<FeeCheckEntityDto> entityList) {
        this.entityList = entityList;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
