package lukelin.his.dto.basic.req.entity;

import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.yb.TreatmentUploadResult;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.util.UUID;

public class TreatmentSaveDto extends EntitySaveDto {
    private DepartmentTreatmentType departmentTreatmentType;

    private UUID defaultDepartmentId;

    private Integer duration;

    private UUID centerTreatmentId;

    private Integer recoveryTypeId;

    private Boolean allowMultiExecution;

    private Integer labTreatmentTypeId;

    private Integer labSampleTypeId;

    private Boolean combo;

    private List<UUID> childTreatmentIdList;

    public List<UUID> getChildTreatmentIdList() {
        return childTreatmentIdList;
    }

    public void setChildTreatmentIdList(List<UUID> childTreatmentIdList) {
        this.childTreatmentIdList = childTreatmentIdList;
    }

    public Boolean getCombo() {
        return combo;
    }

    public void setCombo(Boolean combo) {
        this.combo = combo;
    }

    public Integer getLabTreatmentTypeId() {
        return labTreatmentTypeId;
    }

    public void setLabTreatmentTypeId(Integer labTreatmentTypeId) {
        this.labTreatmentTypeId = labTreatmentTypeId;
    }

    public Integer getLabSampleTypeId() {
        return labSampleTypeId;
    }

    public void setLabSampleTypeId(Integer labSampleTypeId) {
        this.labSampleTypeId = labSampleTypeId;
    }

    public DepartmentTreatmentType getDepartmentTreatmentType() {
        return departmentTreatmentType;
    }

    public void setDepartmentTreatmentType(DepartmentTreatmentType departmentTreatmentType) {
        this.departmentTreatmentType = departmentTreatmentType;
    }

    public UUID getDefaultDepartmentId() {
        return defaultDepartmentId;
    }

    public void setDefaultDepartmentId(UUID defaultDepartmentId) {
        this.defaultDepartmentId = defaultDepartmentId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public UUID getCenterTreatmentId() {
        return centerTreatmentId;
    }

    public void setCenterTreatmentId(UUID centerTreatmentId) {
        this.centerTreatmentId = centerTreatmentId;
    }

    public Integer getRecoveryTypeId() {
        return recoveryTypeId;
    }

    public void setRecoveryTypeId(Integer recoveryTypeId) {
        this.recoveryTypeId = recoveryTypeId;
    }

    public Boolean getAllowMultiExecution() {
        return allowMultiExecution;
    }

    public void setAllowMultiExecution(Boolean allowMultiExecution) {
        this.allowMultiExecution = allowMultiExecution;
    }

    public void copyPropertiesToEntity(Treatment treatment) {
        treatment.setName(this.getName());
        if (!StringUtils.isEmpty(this.getSearchCode()))
            treatment.setSearchCode(this.getSearchCode().toUpperCase());
        treatment.setFeeType(new Dictionary(this.getFeeTypeId()));
        treatment.setMinUom(Ebean.find(UnitOfMeasure.class, this.getMinSizeUomId()));
        treatment.setEnabled(this.isEnabled());
        treatment.setExecuteDepartmentType(this.getDepartmentTreatmentType());
        treatment.setCombo(this.getCombo());
        if (this.getDefaultDepartmentId() != null) {
            DepartmentTreatment departmentTreatment = Ebean.find(DepartmentTreatment.class, this.getDefaultDepartmentId());
            treatment.setDefaultExecuteDepartment(departmentTreatment);
        } else
            treatment.setDefaultExecuteDepartment(null);
        treatment.setDuration(this.getDuration());
        if (this.getRecoveryTypeId() != null)
            treatment.setRecoveryType(new Dictionary(this.getRecoveryTypeId()));
        else
            treatment.setRecoveryType(null);
        treatment.setAllowMultiExecution(this.getAllowMultiExecution());
        if (treatment.getListPrice() == null)
            treatment.setListPrice(BigDecimal.ZERO);

        if (this.labSampleTypeId != null)
            treatment.setLabSampleType(new Dictionary(this.getLabSampleTypeId()));

        if (this.labTreatmentTypeId != null)
            treatment.setLabTreatmentType(new Dictionary(this.getLabTreatmentTypeId()));

        if (this.childTreatmentIdList != null) {
            Set<Treatment> childTreatmentSet = new HashSet<>();
            for (UUID childTreatmentId : this.getChildTreatmentIdList()) {
                Treatment childTreatment = Ebean.find(Treatment.class, childTreatmentId);
                childTreatmentSet.add(childTreatment);
            }
            treatment.setChildTreatmentSet(childTreatmentSet);
        }
    }
}