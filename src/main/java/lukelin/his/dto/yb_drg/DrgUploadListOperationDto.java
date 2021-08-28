package lukelin.his.dto.yb_drg;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.*;

public class DrgUploadListOperationDto {
    @JSONField(name = "OperationRecordNo")
    private String operationRecordNo;

    @JSONField(name = "OperationDoctorCode")
    private String operationDoctorCode;

    @JSONField(name = "OperationDoctorName")
    private String operationDoctorName;

    @JSONField(name = "OperationDate")
    private String operationDate;

    @JSONField(name = "OperationFinishDate")
    private String operationFinishDate;

    @JSONField(name = "IsComplication")
    private String isComplication;

    @JSONField(name = "AnaesthesiaType")
    private String anaesthesiaType;

    @JSONField(name = "ListOperationDetail")
    private List<DrgUploadOperationDetail> listOperationDetail;

    public String getOperationRecordNo() {
        return operationRecordNo;
    }

    public void setOperationRecordNo(String operationRecordNo) {
        this.operationRecordNo = operationRecordNo;
    }

    public String getOperationDoctorCode() {
        return operationDoctorCode;
    }

    public void setOperationDoctorCode(String operationDoctorCode) {
        this.operationDoctorCode = operationDoctorCode;
    }

    public String getOperationDoctorName() {
        return operationDoctorName;
    }

    public void setOperationDoctorName(String operationDoctorName) {
        this.operationDoctorName = operationDoctorName;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationFinishDate() {
        return operationFinishDate;
    }

    public void setOperationFinishDate(String operationFinishDate) {
        this.operationFinishDate = operationFinishDate;
    }

    public String getIsComplication() {
        return isComplication;
    }

    public void setIsComplication(String isComplication) {
        this.isComplication = isComplication;
    }

    public String getAnaesthesiaType() {
        return anaesthesiaType;
    }

    public void setAnaesthesiaType(String anaesthesiaType) {
        this.anaesthesiaType = anaesthesiaType;
    }

    public List<DrgUploadOperationDetail> getListOperationDetail() {
        return listOperationDetail;
    }

    public void setListOperationDetail(List<DrgUploadOperationDetail> listOperationDetail) {
        this.listOperationDetail = listOperationDetail;
    }
}
