package lukelin.his.dto.yb_drg;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.yb.ICD9;
import lukelin.his.domain.entity.yb.drg.DrgRecordOperation;
import lukelin.his.domain.entity.yb.drg.OperationLevel;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;
import lukelin.his.dto.yb.ICD9RespDto;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

public class DrgRecordOperationRespDto {
    private boolean mainOperation;

    private boolean yyxOperation;

    private ICD9RespDto icd9;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operationStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operationEndDate;

    private OperationLevelDto operationLevel;

    private EmployeeDto operator;

    public boolean isMainOperation() {
        return mainOperation;
    }

    public void setMainOperation(boolean mainOperation) {
        this.mainOperation = mainOperation;
    }

    public boolean isYyxOperation() {
        return yyxOperation;
    }

    public void setYyxOperation(boolean yyxOperation) {
        this.yyxOperation = yyxOperation;
    }

    public ICD9RespDto getIcd9() {
        return icd9;
    }

    public void setIcd9(ICD9RespDto icd9) {
        this.icd9 = icd9;
    }

    public Date getOperationStartDate() {
        return operationStartDate;
    }

    public void setOperationStartDate(Date operationStartDate) {
        this.operationStartDate = operationStartDate;
    }

    public Date getOperationEndDate() {
        return operationEndDate;
    }

    public void setOperationEndDate(Date operationEndDate) {
        this.operationEndDate = operationEndDate;
    }

    public OperationLevelDto getOperationLevel() {
        return operationLevel;
    }

    public void setOperationLevel(OperationLevelDto operationLevel) {
        this.operationLevel = operationLevel;
    }

    public EmployeeDto getOperator() {
        return operator;
    }

    public void setOperator(EmployeeDto operator) {
        this.operator = operator;
    }
}
