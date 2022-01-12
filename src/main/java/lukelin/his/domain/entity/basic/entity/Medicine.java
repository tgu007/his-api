package lukelin.his.domain.entity.basic.entity;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.ChargeableEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.inventory.medicine.CachedMedicineStock;
import lukelin.his.domain.entity.yb.MedicineMatchDownload;
import lukelin.his.domain.entity.yb.MedicineMatchUploadResult;
import lukelin.his.domain.entity.yb.MedicineUploadResult;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.YB.YBMatchStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineStockSummaryRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineRespDto;
import lukelin.his.dto.yb.EntityMatchReqLineDto;
import lukelin.his.dto.yb.inventory.req.InventoryUploadCenterInfo;
import lukelin.his.dto.yb.inventory.req.InventoryUploadReqDto;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@javax.persistence.Entity
@Table(name = "basic.entity_medicine")
public class Medicine extends BaseMedicine implements DtoConvertible<MedicineRespDto>, InventoryEntityInterface, ChargeableEntityInterface {
    public Medicine() {
    }

    public Medicine(UUID uuid) {
        this.setUuid(uuid);
    }


    @Column(name = "code", insertable = false, updatable = false)
    private Integer code;

    //单条记录有进货信息
    @OneToMany(mappedBy = "medicine")
    private List<CachedMedicineStock> stockList;

    @OneToMany(mappedBy = "medicine")
    private List<MedicineMatchUploadResult> medicineMatchList;

    @Column(name = "ff_sign")
    private Boolean ffSign = false;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "medicine")
    private MedicineUploadResult uploadResult;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "medicine")
    private MedicineMatchDownload matchedMedicine;

    @Column(name = "prescription_required")
    private Boolean prescriptionRequired;


    @Column(name = "pending_list_price")
    private BigDecimal pendingListPrice = BigDecimal.ZERO;

    public BigDecimal getPendingListPrice() {
        return pendingListPrice;
    }

    public void setPendingListPrice(BigDecimal pendingListPrice) {
        this.pendingListPrice = pendingListPrice;
    }

    public Boolean getPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public Boolean getFfSign() {
        return ffSign;
    }

    public void setFfSign(Boolean ffSign) {
        this.ffSign = ffSign;
    }

    public MedicineMatchDownload getMatchedMedicine() {
        return matchedMedicine;
    }

    public void setMatchedMedicine(MedicineMatchDownload matchedMedicine) {
        this.matchedMedicine = matchedMedicine;
    }

    public MedicineUploadResult getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(MedicineUploadResult uploadResult) {
        this.uploadResult = uploadResult;
    }

    public List<MedicineMatchUploadResult> getMedicineMatchList() {
        return medicineMatchList;
    }

    public void setMedicineMatchList(List<MedicineMatchUploadResult> medicineMatchList) {
        this.medicineMatchList = medicineMatchList;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.medicine;
    }

    @Override
    public List<CachedMedicineStock> getStockList() {
        return stockList;
    }

    public void setStockList(List<CachedMedicineStock> stockList) {
        this.stockList = stockList;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public BasePriceLog getNewPriceLog() {
        MedicinePriceLog newLog = new MedicinePriceLog();
        newLog.setMedicine(this);
        return newLog;
    }

    @Override
    public MedicineRespDto toDto() {
        MedicineRespDto responseDto = DtoUtils.convertRawDto(this);
        this.copyDtoProperty(responseDto);
        responseDto.setManufacturer(this.getManufacturerMedicine().toDto());
        if (this.getCenterMedicine() != null) {
            responseDto.setCenterCode(this.getCenterMedicine().getBZBM());
            responseDto.setCenterName(this.getCenterMedicine().getSFXMMC());
            responseDto.setCenterPrice(this.getCenterMedicine().getBZJG());
            responseDto.setCenterMedicine(this.getCenterMedicine().toDto());
        }
        if (!this.getSelfPay()) {
            if (this.getUploadResult() == null)
                responseDto.setYbUploadStatus(YBUploadStatus.notUploaded);
            else {
                if (StringUtils.isBlank(this.getUploadResult().getServerCode())) {
                    responseDto.setYbUploadStatus(YBUploadStatus.error);
                    responseDto.setYbUploadError(this.getUploadResult().getUploadError());
                } else
                    responseDto.setYbUploadStatus(YBUploadStatus.uploaded);

                if (this.getMatchedMedicine() == null)
                    responseDto.setYbMatchStatus(YBMatchStatus.notUploaded);
                else {
                    switch (this.getMatchedMedicine().getStatus()) {
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
                        responseDto.setYbMatchError(this.getMatchedMedicine().getReference());
                }
            }
        } else {
            responseDto.setYbUploadStatus(YBUploadStatus.selfPay);
            responseDto.setYbMatchStatus(YBMatchStatus.notApply);
        }
        return responseDto;
    }


    public MedicineStockSummaryRespDto toMedicineStockRespDto() {
        MedicineStockSummaryRespDto medicineStockSummaryRespDto = new MedicineStockSummaryRespDto();
        medicineStockSummaryRespDto.setStockEntity(this.toDto());
        this.assignEntityAggregatedStockInfo(medicineStockSummaryRespDto);
        for (BaseStockSummaryRespDto warehouseStock : medicineStockSummaryRespDto.getStockSummaryList()) {
            warehouseStock.setTotalValue(this.getListPrice().multiply(warehouseStock.getMinUomQuantity()).divide(this.getDepartmentToMinRate(), 2, RoundingMode.HALF_UP));
            Date firstExpireDate = this.getStockList().stream().filter(st -> warehouseStock.getStockIdList().contains(st.getUuid()))
                    .min(Comparator.comparing(st -> st.getOriginPurchaseLine().getExpireDate())).get().getOriginPurchaseLine().getExpireDate();
            warehouseStock.setFirstExpireDate(firstExpireDate);
        }
        //medicineStockSummaryRespDto.setLatestExpireDate(medicineStockSummaryRespDto.getStockSummaryList());
        return medicineStockSummaryRespDto;
    }

    public MedicineSnapshot createSnapshot() {
        MedicineSnapshot newSnapShot = new MedicineSnapshot();
        BeanUtils.copyPropertiesIgnoreNull(this, newSnapShot);
        newSnapShot.setMedicine(this);
        newSnapShot.setUuid(null); //需要自己生成ID
        return newSnapShot;
    }

    public MedicineSnapshot findLatestSnapshot() {
        MedicineSnapshot latestSnapShot = Ebean.find(MedicineSnapshot.class)
                .where()
                .eq("medicine_id", this.getUuid())
                .orderBy("whenCreated desc").setMaxRows(1).findOne();

        if (latestSnapShot == null) {
            Medicine thisMedicine = Ebean.find(Medicine.class, this.getUuid());
            latestSnapShot = thisMedicine.createSnapshot();
            Ebean.save(latestSnapShot);
        }
        return latestSnapShot;
    }

    public InventoryUploadReqDto toInventoryUpload() {
        InventoryUploadReqDto reqDto = new InventoryUploadReqDto();
        reqDto.setUuid(this.getUuid());
        if (this.getUploadResult() != null)
            reqDto.setCWZBH(this.getUploadResult().getServerCode());
        reqDto.setCWZBH(this.getCode().toString());
        reqDto.setWZMC(this.getName());
        reqDto.setTYMC(this.getName());
        reqDto.setGGMC(this.getDepartmentModel());
        reqDto.setCDMC(this.getManufacturerMedicine().getName());
        reqDto.setJLDW(this.getServeUom().getName());
        reqDto.setZBDW(this.getDepartmentUom().getName());
        reqDto.setLSDJ(this.getListPrice());
        reqDto.setJX(this.getDoseType().getName());
        reqDto.setKPXM(this.getFeeType().getExtraInfo());
        if (this.getDepartmentToMinRate().compareTo(BigDecimal.ONE) != 0) {
            reqDto.setSFKCCL("1");
            reqDto.setCLDW(this.getMinUom().getName());
            reqDto.setCLBL(this.getDepartmentToMinRate());
            reqDto.setCLDJ(this.getMinUomListPrice());
            reqDto.setZHB(this.getDepartmentToMinRate());
        } else {
            reqDto.setSFKCCL("0");
            reqDto.setZHB(BigDecimal.ONE);
        }
        reqDto.setWZBZ(this.getDepartmentUom().getName());
        reqDto.setZXBZ("0");

        reqDto.setWZLB("0");

        List<InventoryUploadCenterInfo> centerInfoList = new ArrayList<>();
        InventoryUploadCenterInfo centerInfo = new InventoryUploadCenterInfo();
        centerInfo.setBzbm(this.getCenterMedicine().getBZBM());
        centerInfo.setYbmc(this.getCenterMedicine().getSFXMMC());
        centerInfo.setZfbl(this.getCenterMedicine().getPTZFBL());
        centerInfo.setZxnm(this.getCenterMedicine().getZXNM());
        centerInfoList.add(centerInfo);
        reqDto.setDZXXY(centerInfoList);
        return reqDto;
    }

    public BigDecimal getMinUomListPrice() {
        if (this.getDepartmentToMinRate().compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else
            return this.getListPrice().divide(this.getDepartmentToMinRate(), 4, RoundingMode.HALF_UP);
    }

    public EntityMatchReqLineDto toMatchReqLineDto() {
        EntityMatchReqLineDto reqLineDto = new EntityMatchReqLineDto();
        reqLineDto.setCfydm(this.getCode().toString());
        reqLineDto.setFydm(this.getUploadResult().getServerCode());
        reqLineDto.setUuid(this.getUuid());
        return reqLineDto;
    }


    public String getLastPeriodUsage() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);
        List<Fee> feeList = Ebean.find(Fee.class)
                .where()
                .eq("medicineSnapshot.medicine.uuid", this.getUuid())
                .eq("feeStatus", FeeStatus.confirmed)
                .ge("feeDate", lastMonth)
                .findList();
        BigDecimal totalQuantity = feeList.stream().map(Fee::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.getDisplayQuantity(WarehouseType.pharmacy, totalQuantity);
    }

    public void setMedicineFeeValue(Fee newFee) {
        newFee.setUomName(this.getMinUom().getName());
        newFee.setFeeTypeName(this.getFeeType().getName());
        newFee.setSearchCode(this.getSearchCode());
        newFee.setUnitAmount(this.getListPrice().divide(this.getDepartmentToMinRate(), 4, RoundingMode.HALF_UP));
        newFee.setDescription(this.getName());
        newFee.setUnitAmountInfo(newFee.getUnitAmount() + "元/" + this.getMinUom().getName());
        newFee.setQuantityInfo(this.getDisplayQuantity(WarehouseType.wardWarehouse, newFee.getQuantity()));
        newFee.setTotalAmount(newFee.getQuantity().multiply(newFee.getUnitAmount()));
        newFee.setMedicineSnapshot(this.findLatestSnapshot());
    }

    public boolean centerMedicineChanged(UUID newCenterMedicineId) {
        if (this.getCenterMedicine() != null) {
            UUID currentCenterMedicineId = this.getCenterMedicine().getUuid();
            if (currentCenterMedicineId != null && !currentCenterMedicineId.equals(newCenterMedicineId))
                return true;
        }
        return false;
    }

}
