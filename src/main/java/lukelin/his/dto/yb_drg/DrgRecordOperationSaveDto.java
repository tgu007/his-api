package lukelin.his.dto.yb_drg;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.yb.ICD9;
import lukelin.his.domain.entity.yb.drg.DrgRecordOperation;
import lukelin.his.domain.entity.yb.drg.OperationLevel;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

public class DrgRecordOperationSaveDto {
    private boolean mainOperation;

    private boolean yyxOperation;

    private UUID icd9Id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operationStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operationEndDate;

    private UUID operationLevelId;

    private UUID operatorId;

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

    public UUID getIcd9Id() {
        return icd9Id;
    }

    public void setIcd9Id(UUID icd9Id) {
        this.icd9Id = icd9Id;
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

    public UUID getOperationLevelId() {
        return operationLevelId;
    }

    public void setOperationLevelId(UUID operationLevelId) {
        this.operationLevelId = operationLevelId;
    }

    public UUID getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(UUID operatorId) {
        this.operatorId = operatorId;
    }

    public DrgRecordOperation toEntity() {
        DrgRecordOperation operation = new DrgRecordOperation();
        BeanUtils.copyProperties(this, operation);
        operation.setOperation(Ebean.find(ICD9.class, this.getIcd9Id()));
        operation.setOperationLevel(Ebean.find(OperationLevel.class, this.getOperationLevelId()));
        if (this.getOperatorId() == null)
            throw new ApiValidationException("术者不能为空");
        operation.setOperator(Ebean.find(Employee.class, this.getOperatorId()));
        return operation;
    }
}
