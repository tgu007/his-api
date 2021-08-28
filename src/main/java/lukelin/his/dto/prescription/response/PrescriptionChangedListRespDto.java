package lukelin.his.dto.prescription.response;

import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;

public class PrescriptionChangedListRespDto extends BaseWardPatientListRespDto {
    private PrescriptionListRespDto listRespDto;

    private String changedStatus;

    public String getChangedStatus() {
        return changedStatus;
    }

    public void setChangedStatus(String changedStatus) {
        this.changedStatus = changedStatus;
    }

    public PrescriptionListRespDto getListRespDto() {
        return listRespDto;
    }

    public void setListRespDto(PrescriptionListRespDto listRespDto) {
        this.listRespDto = listRespDto;
    }
}