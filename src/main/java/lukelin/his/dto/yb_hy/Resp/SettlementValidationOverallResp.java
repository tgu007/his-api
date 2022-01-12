package lukelin.his.dto.yb_hy.Resp;

import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.dto.signin.response.PatientSignInRespDto;

import java.math.BigDecimal;
import java.util.*;

public class SettlementValidationOverallResp {
    private String errorMessage;

    private List<PatientSignInRespDto> settledPatientList = new ArrayList<>();

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<PatientSignInRespDto> getSettledPatientList() {
        return settledPatientList;
    }

    public void setSettledPatientList(List<PatientSignInRespDto> settledPatientList) {
        this.settledPatientList = settledPatientList;
    }
}
