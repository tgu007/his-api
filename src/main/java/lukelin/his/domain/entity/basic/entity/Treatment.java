package lukelin.his.domain.entity.basic.entity;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.ChargeableEntityInterface;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.Department;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.YB.YBMatchStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.yb.EntityMatchReqLineDto;
import lukelin.his.dto.yb.req.TreatmentUploadCenterInfo;
import lukelin.his.dto.yb.req.TreatmentUploadReqDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.entity_treatment")
public class Treatment extends BaseTreatment implements DtoConvertible<TreatmentRespDto>, ChargeableEntityInterface {
    public Treatment() {
    }

    public Treatment(UUID uuid) {
        this.setUuid(uuid);
    }

    @Column(name = "code", insertable = false, updatable = false)
    private Integer code;

    @Column(name = "execute_department_type", nullable = false)
    private DepartmentTreatmentType executeDepartmentType;

    @ManyToOne
    @JoinColumn(name = "default_department_id")
    private DepartmentTreatment defaultExecuteDepartment;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "treatment")
    private TreatmentUploadResult uploadResult;

    @Column(name = "execution_time")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "recovery_type_id")
    private Dictionary recoveryType;

    @Column(name = "allow_multi_execution")
    private Boolean allowMultiExecution;

    @ManyToOne
    @JoinColumn(name = "yb_center_id")
    private CenterTreatment centerTreatment;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "treatment")
    private TreatmentMatchDownload matchedTreatment;

    @Column(name = "allow_manual_fee")
    private Boolean allowManualFee;


    @Column(name = "allow_auto_fee")
    private Boolean allowAutoFee;

    @Column(name = "prescription_required")
    private Boolean prescriptionRequired;

    @Column(name = "show_in_card")
    private Boolean showInCard;

    @ManyToOne
    @JoinColumn(name = "lab_treatment_type_id")
    private Dictionary labTreatmentType;

    @ManyToOne
    @JoinColumn(name = "lab_sample_type_id")
    private Dictionary labSampleType;

    @Column(name = "combo")
    private Boolean combo;

    @ManyToMany
    @JoinTable(name = "basic.treatment_combo",
            joinColumns = {@JoinColumn(name = "parent_treatment_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "child_treatment_id", referencedColumnName = "uuid")})
    private Set<Treatment> childTreatmentSet;

    @Column(name = "pending_list_price")
    private BigDecimal pendingListPrice = BigDecimal.ZERO;


    @Column(name = "yb_3024_group")
    private Boolean yb3024Group;

    public Boolean getYb3024Group() {
        return yb3024Group;
    }

    public void setYb3024Group(Boolean yb3024Group) {
        this.yb3024Group = yb3024Group;
    }

    public BigDecimal getPendingListPrice() {
        return pendingListPrice;
    }

    public void setPendingListPrice(BigDecimal pendingListPrice) {
        this.pendingListPrice = pendingListPrice;
    }

    public Set<Treatment> getChildTreatmentSet() {
        return childTreatmentSet;
    }

    public void setChildTreatmentSet(Set<Treatment> childTreatmentSet) {
        this.childTreatmentSet = childTreatmentSet;
    }

    public Boolean getCombo() {
        return combo;
    }

    public void setCombo(Boolean combo) {
        this.combo = combo;
    }

    public Dictionary getLabTreatmentType() {
        return labTreatmentType;
    }

    public void setLabTreatmentType(Dictionary labTreatmentType) {
        this.labTreatmentType = labTreatmentType;
    }

    public Dictionary getLabSampleType() {
        return labSampleType;
    }

    public void setLabSampleType(Dictionary labSampleType) {
        this.labSampleType = labSampleType;
    }

    public Boolean getShowInCard() {
        return showInCard;
    }

    public void setShowInCard(Boolean showInCard) {
        this.showInCard = showInCard;
    }

    public Boolean getAllowManualFee() {
        return allowManualFee;
    }

    public void setAllowManualFee(Boolean allowManualFee) {
        this.allowManualFee = allowManualFee;
    }

    public Boolean getAllowAutoFee() {
        return allowAutoFee;
    }

    public void setAllowAutoFee(Boolean allowAutoFee) {
        this.allowAutoFee = allowAutoFee;
    }

    public Boolean getPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public TreatmentMatchDownload getMatchedTreatment() {
        return matchedTreatment;
    }

    public void setMatchedTreatment(TreatmentMatchDownload matchedTreatment) {
        this.matchedTreatment = matchedTreatment;
    }

    public Boolean getAllowMultiExecution() {
        return allowMultiExecution;
    }

    public void setAllowMultiExecution(Boolean allowMultiExecution) {
        this.allowMultiExecution = allowMultiExecution;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Dictionary getRecoveryType() {
        return recoveryType;
    }

    public void setRecoveryType(Dictionary recoveryType) {
        this.recoveryType = recoveryType;
    }

    public CenterTreatment getCenterTreatment() {
        return centerTreatment;
    }

    public void setCenterTreatment(CenterTreatment centerTreatment) {
        this.centerTreatment = centerTreatment;
    }

    public TreatmentUploadResult getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(TreatmentUploadResult uploadResult) {
        this.uploadResult = uploadResult;
    }

    public DepartmentTreatmentType getExecuteDepartmentType() {
        return executeDepartmentType;
    }

    public void setExecuteDepartmentType(DepartmentTreatmentType executeDepartmentType) {
        this.executeDepartmentType = executeDepartmentType;
    }

    public DepartmentTreatment getDefaultExecuteDepartment() {
        return defaultExecuteDepartment;
    }

    public void setDefaultExecuteDepartment(DepartmentTreatment defaultExecuteDepartment) {
        this.defaultExecuteDepartment = defaultExecuteDepartment;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public BasePriceLog getNewPriceLog() {
        TreatmentPriceLog newLog = new TreatmentPriceLog();
        newLog.setTreatment(this);
        return newLog;

    }

    @Override
    public TreatmentRespDto toDto() {
        TreatmentRespDto responseDto = DtoUtils.convertRawDto(this);
        responseDto.setFeeType(this.getFeeType().toDto());
        responseDto.setMinSizeUom(this.getMinUom().toDto());
        responseDto.setListPriceMinUom(this.getListPrice());
        if (this.getDefaultExecuteDepartment() != null) {
            responseDto.setDefaultExecuteDepartment(this.getDefaultExecuteDepartment().toDto());
        }
        if (this.getCenterTreatment() != null) {
            responseDto.setCenterTreatment(this.getCenterTreatment().toDto());
            responseDto.setCenterCode(this.getCenterTreatment().getBZBM());
            responseDto.setCenterName(this.getCenterTreatment().getSFXMMC());
        }
        if (this.getUploadResult() == null)
            responseDto.setYbUploadStatus(YBUploadStatus.notUploaded);
        else {
            if (StringUtils.isBlank(this.getUploadResult().getServerCode())) {
                responseDto.setYbUploadStatus(YBUploadStatus.error);
                responseDto.setYbUploadError(this.getUploadResult().getUploadError());
            } else
                responseDto.setYbUploadStatus(YBUploadStatus.uploaded);
        }

        if (this.getMatchedTreatment() == null)
            responseDto.setYbMatchStatus(YBMatchStatus.notUploaded);
        else {
            switch (this.getMatchedTreatment().getStatus()) {
                case "0":
                    responseDto.setYbMatchStatus(YBMatchStatus.pending);
                    break;
                case "1":
                    responseDto.setYbMatchStatus(YBMatchStatus.passed);
                    break;
                case "2":
                    responseDto.setYbMatchStatus(YBMatchStatus.noRecord);
                    break;
                case "9":
                    responseDto.setYbMatchStatus(YBMatchStatus.failed);
                    break;
                case "rematch": //需重新匹配，设为待上传
                    responseDto.setYbMatchStatus(YBMatchStatus.notUploaded);
                    break;
            }

            if (responseDto.getYbMatchStatus() == YBMatchStatus.failed)
                responseDto.setYbMatchError(this.getMatchedTreatment().getReference());
        }
        responseDto.setAllowMultiExecution(this.getAllowMultiExecution());
        responseDto.setDuration(this.getDuration());
        if (this.getRecoveryType() != null)
            responseDto.setRecoveryType(this.getRecoveryType().toDto());

        if (this.getLabSampleType() != null)
            responseDto.setLabSampleType(this.getLabSampleType().toDto());

        if (this.getLabTreatmentType() != null)
            responseDto.setLabTreatmentType(this.getLabTreatmentType().toDto());


        if (this.getChildTreatmentSet() != null && this.combo) {
            List<TreatmentRespDto> childTreatmentList = new ArrayList<>();
            BigDecimal totalChildPrice = BigDecimal.ZERO;
            for (Treatment childTreatment : this.getChildTreatmentSet()) {
                childTreatmentList.add(childTreatment.toDto());
                if (childTreatment.getListPrice() != null)
                    totalChildPrice = totalChildPrice.add(childTreatment.getListPrice());
            }
            responseDto.setListPrice(totalChildPrice);
            responseDto.setChildTreatmentList(childTreatmentList);
        }

        responseDto.setLastModifiedBy(this.getWhoModifiedName());
        responseDto.setLastModifiedDate(this.getWhenModified());

        return responseDto;
    }

    public TreatmentSnapshot createSnapshot() {
        TreatmentSnapshot newSnapShot = new TreatmentSnapshot();
        BeanUtils.copyPropertiesIgnoreNull(this, newSnapShot);
        newSnapShot.setTreatment(this);
        newSnapShot.setUuid(null); //需要自己生成ID
        return newSnapShot;
    }


    @Transactional
    public TreatmentSnapshot findLatestSnapshot() {
        TreatmentSnapshot latestSnapShot = Ebean.find(TreatmentSnapshot.class)
                .where()
                .eq("treatment_id", this.getUuid())
                .orderBy("whenCreated desc").setMaxRows(1).findOne();

        if (latestSnapShot == null) {
            Treatment thisTreatment = Ebean.find(Treatment.class, this.getUuid());
            latestSnapShot = thisTreatment.createSnapshot();
            Ebean.save(latestSnapShot);
        }
        return latestSnapShot;
    }

    public TreatmentUploadReqDto toTreatmentUpload() {
        TreatmentUploadReqDto upload = new TreatmentUploadReqDto();
        upload.setCfwdm(this.getCode().toString());
        upload.setDj(this.getListPrice().toString());
        upload.setDw(this.getMinUom().getName());
        if (this.getUploadResult() != null && this.getUploadResult().getServerCode() != null)
            upload.setFwdm(this.getUploadResult().getServerCode());
        //未知
        upload.setKpxm("99");
        upload.setFwmc(this.getName());
        upload.setUuid(this.getUuid());
        upload.setZxbz("0");
        if (this.getDefaultExecuteDepartment() != null)
            upload.setZxks(this.getDefaultExecuteDepartment().getSequence().toString());

        List<TreatmentUploadCenterInfo> centerInfoList = new ArrayList<>();
        TreatmentUploadCenterInfo centerInfo = new TreatmentUploadCenterInfo();
        centerInfo.setYbmc(this.getCenterTreatment().getSFXMMC());
        centerInfo.setBzbm(this.getCenterTreatment().getBZBM());
        centerInfo.setZxnm(this.getCenterTreatment().getZXNM());
        centerInfoList.add(centerInfo);
        upload.setDzxxy(centerInfoList);
        return upload;
    }

    public EntityMatchReqLineDto toMatchReqLineDto() {
        EntityMatchReqLineDto reqLineDto = new EntityMatchReqLineDto();
        reqLineDto.setCfydm(this.getCode().toString());
        reqLineDto.setFydm(this.getUploadResult().getServerCode());
        reqLineDto.setUuid(this.getUuid());
        return reqLineDto;
    }

    public void setFeeValue(Fee newFee) {
        this.setFeeValue(newFee, null);
    }

    public void setFeeValue(Fee newFee, BigDecimal feeLimit) {
        BigDecimal unitAmount = this.getListPrice();
        if (feeLimit != null && unitAmount.compareTo(feeLimit) > 0)
            unitAmount = feeLimit;
        newFee.setUomName(this.getMinUom().getName());
        newFee.setFeeTypeName(this.getFeeType().getName());
        newFee.setSearchCode(this.getSearchCode());
        newFee.setUnitAmount(unitAmount);
        newFee.setTreatmentSnapshot(this.findLatestSnapshot());
        newFee.setDescription(this.getName());
        newFee.setUnitAmountInfo(unitAmount + newFee.getUomName());
        newFee.setQuantityInfo(newFee.getQuantity() + newFee.getUomName());
        newFee.setTotalAmount(newFee.getUnitAmount().multiply(newFee.getQuantity()).setScale(2, RoundingMode.HALF_UP));
        newFee.setFeeEntityId(this.getUuid());
        newFee.setEntityType(EntityType.treatment);
    }

    public boolean centerTreatmentChanged(UUID newCenterTreatmentId) {
        if (this.getCenterTreatment() != null) {
            UUID currentCenterId = this.getCenterTreatment().getUuid();
            if (currentCenterId != null && !currentCenterId.equals(newCenterTreatmentId))
                return true;
        }
        return false;
    }
}
