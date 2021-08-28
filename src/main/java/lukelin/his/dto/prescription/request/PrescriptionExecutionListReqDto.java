package lukelin.his.dto.prescription.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.config.JsonConfig;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;

import java.util.Date;
import java.util.List;

public class PrescriptionExecutionListReqDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feeDate;

    private FeeRecordMethod  feeRecordMethod = FeeRecordMethod.def;

    public FeeRecordMethod getFeeRecordMethod() {
        return feeRecordMethod;
    }

    public void setFeeRecordMethod(FeeRecordMethod feeRecordMethod) {
        this.feeRecordMethod = feeRecordMethod;
    }

    private List<PrescriptionExecutionReqDto> prescriptionExecutionList;

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public List<PrescriptionExecutionReqDto> getPrescriptionExecutionList() {
        return prescriptionExecutionList;
    }

    public void setPrescriptionExecutionList(List<PrescriptionExecutionReqDto> prescriptionExecutionList) {
        this.prescriptionExecutionList = prescriptionExecutionList;
    }
}
