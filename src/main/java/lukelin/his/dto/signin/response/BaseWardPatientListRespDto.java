package lukelin.his.dto.signin.response;

import java.util.UUID;

public abstract class BaseWardPatientListRespDto {
    private String ward;

    private String wardRoom;

    private String patientName;

    private Integer patientGroupIndex;

    private UUID patientSignInId;

    private Integer bedOrder;

    private Integer roomOrder;

    private Integer wardOrder;

    public Integer getBedOrder() {
        return bedOrder;
    }

    public void setBedOrder(Integer bedOrder) {
        this.bedOrder = bedOrder;
    }

    public Integer getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(Integer roomOrder) {
        this.roomOrder = roomOrder;
    }

    public Integer getWardOrder() {
        return wardOrder;
    }

    public void setWardOrder(Integer wardOrder) {
        this.wardOrder = wardOrder;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public Integer getPatientGroupIndex() {
        return patientGroupIndex;
    }

    public void setPatientGroupIndex(Integer patientGroupIndex) {
        this.patientGroupIndex = patientGroupIndex;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getWardRoom() {
        return wardRoom;
    }

    public void setWardRoom(String wardRoom) {
        this.wardRoom = wardRoom;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
