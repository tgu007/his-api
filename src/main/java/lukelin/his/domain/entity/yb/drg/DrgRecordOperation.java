package lukelin.his.domain.entity.yb.drg;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.yb.ICD9;
import lukelin.his.dto.yb_drg.DrgRecordOperationRespDto;
import lukelin.his.dto.yb_drg.DrgUploadListOperationDto;
import lukelin.his.dto.yb_drg.DrgUploadOperationDetail;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.*;

@javax.persistence.Entity
@Table(name = "yb_drg.drg_record_operation")
public class DrgRecordOperation extends BaseEntity implements DtoConvertible<DrgRecordOperationRespDto> {
    @ManyToOne
    @JoinColumn(name = "drg_record_id")
    private DrgRecord drgRecord;

    @Column(name = "is_main")
    private boolean mainOperation;

    @Column(name = "is_yyx")
    private boolean yyxOperation;

    @ManyToOne
    @JoinColumn(name = "icd_9_id")
    private ICD9 operation;

    @Column(name = "start_date")
    private Date operationStartDate;

    @Column(name = "end_date")
    private Date operationEndDate;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private OperationLevel operationLevel;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Employee operator;

    public DrgRecord getDrgRecord() {
        return drgRecord;
    }

    public void setDrgRecord(DrgRecord drgRecord) {
        this.drgRecord = drgRecord;
    }

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

    public ICD9 getOperation() {
        return operation;
    }

    public void setOperation(ICD9 operation) {
        this.operation = operation;
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

    public OperationLevel getOperationLevel() {
        return operationLevel;
    }

    public void setOperationLevel(OperationLevel operationLevel) {
        this.operationLevel = operationLevel;
    }

    public Employee getOperator() {
        return operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    @Override
    public DrgRecordOperationRespDto toDto() {
        DrgRecordOperationRespDto dto = DtoUtils.convertRawDto(this);
        dto.setIcd9(this.getOperation().toDto());
        dto.setOperationLevel(this.getOperationLevel().toDto());
        dto.setOperator(this.getOperator().toDto());
        return dto;
    }

    public DrgUploadListOperationDto toDrgOperationUploadDto() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DrgUploadListOperationDto dto = new DrgUploadListOperationDto();
        dto.setOperationRecordNo(this.getUuid().toString());
        if(StringUtils.isBlank(this.getOperator().getCertificationNumber()))
            throw new ApiValidationException("手术操作人员" + this.getOperator().getName() + "缺少证书号，请其填写证书号后再上传");
        dto.setOperationDoctorName(this.getOperator().getCertificationNumber());
        dto.setOperationDoctorName(this.getOperator().getName());
        dto.setOperationDate(sdf.format(this.operationStartDate));
        dto.setOperationFinishDate(sdf.format(this.operationEndDate));
        dto.setIsComplication("0");

        List<DrgUploadOperationDetail> operationDetailList = new ArrayList<>();
        DrgUploadOperationDetail detail = new DrgUploadOperationDetail();
        detail.setOperationRecordNo(this.getUuid().toString());
        detail.setOperationNo(this.getUuid().toString());
        detail.setOperationCode(this.getOperation().getCode());
        detail.setOperationName(this.getOperation().getName());
        detail.setOperationLevel(this.getOperationLevel().getCode());
        detail.setOperationIncisionClass("1");
        detail.setOperationHealClass("1");
        if (this.mainOperation)
            detail.setIsMajorIden("1");
        else
            detail.setIsMajorIden("0");
        if (this.yyxOperation)
            detail.setIsIatrogenic("1");
        else
            detail.setIsIatrogenic("0");
        operationDetailList.add(detail);
        dto.setListOperationDetail(operationDetailList);

        return dto;
    }
}
