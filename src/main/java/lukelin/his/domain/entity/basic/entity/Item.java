package lukelin.his.domain.entity.basic.entity;

import io.ebean.Ebean;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.ChargeableEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.inventory.item.CachedItemStock;
import lukelin.his.domain.entity.yb.ItemMatchDownload;
import lukelin.his.domain.entity.yb.ItemUploadResult;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.YB.YBMatchStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;
import lukelin.his.dto.basic.resp.entity.ItemRespDto;
import lukelin.his.dto.basic.resp.entity.ItemStockSummaryRespDto;
import lukelin.his.dto.yb.EntityMatchReqLineDto;
import lukelin.his.dto.yb.inventory.req.InventoryUploadCenterInfo;
import lukelin.his.dto.yb.inventory.req.InventoryUploadReqDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.entity_item")
public class Item extends BaseItem implements DtoConvertible<ItemRespDto>, InventoryEntityInterface, ChargeableEntityInterface {

    public Item() {
    }

    public Item(UUID uuid) {
        this.setUuid(uuid);
    }

    @Column(name = "code", insertable = false, updatable = false)
    private Integer code;

    //单条记录有进货信息
    @OneToMany(mappedBy = "item")
    private List<CachedItemStock> stockList;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemPriceLog> priceLogList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "item")
    private ItemUploadResult uploadResult;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "item")
    private ItemMatchDownload matchedItem;


    @Column(name = "allow_auto_fee")
    private Boolean allowAutoFee;


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

    public ItemMatchDownload getMatchedItem() {
        return matchedItem;
    }

    public void setMatchedItem(ItemMatchDownload matchedItem) {
        this.matchedItem = matchedItem;
    }

    public ItemUploadResult getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(ItemUploadResult uploadResult) {
        this.uploadResult = uploadResult;
    }

    public List<ItemPriceLog> getPriceLogList() {
        return priceLogList;
    }

    public void setPriceLogList(List<ItemPriceLog> priceLogList) {
        this.priceLogList = priceLogList;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.item;
    }

    public List<CachedItemStock> getStockList() {
        return stockList;
    }

    public void setStockList(List<CachedItemStock> stockList) {
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
        ItemPriceLog newLog = new ItemPriceLog();
        newLog.setItem(this);
        return newLog;
    }

    @Override
    public ItemRespDto toDto() {
        ItemRespDto responseDto = DtoUtils.convertRawDto(this);
        this.copyDtoProperty(responseDto);
        if (this.getManufacturerItem() != null)
            responseDto.setManufacturer(this.getManufacturerItem().toDto());
        if (this.getCenterTreatment() != null) {
            responseDto.setCenterCode(this.getCenterTreatment().getBZBM());
            responseDto.setCenterTreatment(this.getCenterTreatment().toDto());
            responseDto.setCenterName(this.getCenterTreatment().getSFXMMC());
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
            }

            if (this.getMatchedItem() == null)
                responseDto.setYbMatchStatus(YBMatchStatus.notUploaded);
            else {
                switch (this.getMatchedItem().getStatus()) {
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
                    responseDto.setYbMatchError(this.getMatchedItem().getReference());
            }
        } else {
            responseDto.setYbUploadStatus(YBUploadStatus.selfPay);
            responseDto.setYbMatchStatus(YBMatchStatus.notApply);
        }
        return responseDto;
    }

    public ItemStockSummaryRespDto toItemStockRespDto() {
        ItemStockSummaryRespDto itemStockSummaryRespDto = new ItemStockSummaryRespDto();
        itemStockSummaryRespDto.setStockEntity(this.toDto());
        this.assignEntityAggregatedStockInfo(itemStockSummaryRespDto);
        for (BaseStockSummaryRespDto warehouseStock : itemStockSummaryRespDto.getStockSummaryList())
            warehouseStock.setTotalValue(this.getListPrice().multiply(warehouseStock.getMinUomQuantity()).setScale(2, RoundingMode.HALF_UP));
        return itemStockSummaryRespDto;
    }


    public ItemSnapshot createSnapshot() {
        ItemSnapshot newSnapShot = new ItemSnapshot();
        BeanUtils.copyPropertiesIgnoreNull(this, newSnapShot);
        newSnapShot.setUuid(null); //清除复制的ITEM ID
        newSnapShot.setItem(this);
        return newSnapShot;
    }

    @Transactional
    public ItemSnapshot findLatestSnapshot() {
        ItemSnapshot latestSnapShot =
                Ebean.find(ItemSnapshot.class)
                        .where()
                        .eq("item_id", this.getUuid())
                        .orderBy("whenCreated desc").setMaxRows(1).findOne();
        if (latestSnapShot == null) {
            Item thisItem = Ebean.find(Item.class, this.getUuid());
            latestSnapShot = thisItem.createSnapshot();
            Ebean.save(latestSnapShot);
        }
        return latestSnapShot;
    }


    //yb dto
    public InventoryUploadReqDto toInventoryUpload() {
        InventoryUploadReqDto reqDto = new InventoryUploadReqDto();
        reqDto.setUuid(this.getUuid());
        if (this.getUploadResult() != null)
            reqDto.setCWZBH(this.getUploadResult().getServerCode());
        reqDto.setCWZBH(this.getCode().toString());
        reqDto.setWZMC(this.getName());
        reqDto.setTYMC(this.getName());
        reqDto.setGGMC(this.getDepartmentModel());
        reqDto.setCDMC(this.getManufacturerItem().getName());
        reqDto.setZBDW(this.getMinUom().getName());
        reqDto.setLSDJ(this.getListPrice());
        reqDto.setKPXM(this.getFeeType().getExtraInfo());
        reqDto.setSFKCCL("0");
        reqDto.setZHB(BigDecimal.ONE);
        reqDto.setWZBZ(this.getDepartmentUom().getName());
        reqDto.setZXBZ("0");

        reqDto.setWZLB("1");
        reqDto.setJLDW(this.getMinUom().getName());

        List<InventoryUploadCenterInfo> centerInfoList = new ArrayList<>();
        InventoryUploadCenterInfo centerInfo = new InventoryUploadCenterInfo();
        centerInfo.setBzbm(this.getCenterTreatment().getBZBM());
        centerInfo.setYbmc(this.getCenterTreatment().getSFXMMC());
        centerInfo.setZfbl(this.getCenterTreatment().getPTZFBL());
        centerInfo.setZxnm(this.getCenterTreatment().getZXNM());
        centerInfoList.add(centerInfo);
        reqDto.setDZXXY(centerInfoList);
        reqDto.setJLDW("物资");
        reqDto.setZBDW(this.getDepartmentUom().getName());
        return reqDto;
    }

    public EntityMatchReqLineDto toMatchReqLineDto() {
        EntityMatchReqLineDto reqLineDto = new EntityMatchReqLineDto();
        reqLineDto.setCfydm(this.getCode().toString());
        reqLineDto.setFydm(this.getUploadResult().getServerCode());
        reqLineDto.setUuid(this.getUuid());
        return reqLineDto;
    }

    public void setFeeValue(Fee newFee) {
        newFee.setUomName(this.getMinUom().getName());
        newFee.setFeeTypeName(this.getFeeType().getName());
        newFee.setSearchCode(this.getSearchCode());
        newFee.setUnitAmount(this.getListPrice());
        newFee.setItemSnapshot(this.findLatestSnapshot());
        newFee.setDescription(this.getName());
        newFee.setUnitAmountInfo(this.getListPrice() + newFee.getUomName());
        newFee.setQuantityInfo(this.getDisplayQuantity(WarehouseType.wardWarehouse, newFee.getQuantity()));
        newFee.setTotalAmount(newFee.getUnitAmount().multiply(newFee.getQuantity()).setScale(2, RoundingMode.HALF_UP));
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
