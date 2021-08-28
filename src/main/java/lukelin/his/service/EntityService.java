package lukelin.his.service;

import io.ebean.ExpressionList;
import io.ebean.PagedList;
import lukelin.common.springboot.dto.DtoUtils;

import lukelin.his.domain.entity.basic.codeEntity.ManufacturerItem;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.entity.*;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.enums.Basic.DepartmentType;
import lukelin.his.domain.enums.Basic.UnitOfMeasureType;
import lukelin.his.dto.Inventory.req.filter.StockFilterDto;
import lukelin.his.dto.basic.req.entity.*;
import lukelin.his.dto.basic.req.filter.*;
import lukelin.his.dto.basic.resp.entity.ItemDetailPramDto;
import lukelin.his.dto.basic.resp.entity.MedicineDetailPramDto;
import lukelin.his.dto.basic.resp.entity.TreatmentDetailPramDto;
import lukelin.his.system.ConfigBeanProp;
import lukelin.his.system.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class EntityService extends BaseHisService {
    @Autowired
    private BasicService basicService;

    @Autowired
    private ConfigBeanProp configBeanProp;

    @Autowired
    private YBInventoryService ybInventoryService;

    @Autowired
    private YBService ybService;

    @Value("${uploadYBInventory}")
    private Boolean uploadYBInventory;


    @Value("${uploadYBPatient}")
    private Boolean ybServiceEnabled;

    @Transactional
    public Medicine saveMedicine(MedicineSaveDto medicineSaveDto) {
        Medicine medicine;
        if (medicineSaveDto.getUuid() == null) {
            medicine = new Medicine();
            medicine.setPrescriptionRequired(true);
        } else {
            medicine = this.findById(Medicine.class, medicineSaveDto.getUuid());
            if (medicine.centerMedicineChanged(medicineSaveDto.getCenterMedicineId())) {
                //中心药品改变，作废现有的匹配信息
                MedicineMatchDownload existingMatch = medicine.getMatchedMedicine();
                if (existingMatch != null) {
                    existingMatch.setStatus("rematch");
                    ebeanServer.save(existingMatch);
                }
            }
        }
        medicineSaveDto.copyPropertiesToEntity(medicine);

        if (StringUtils.isBlank(medicine.getSearchCode()))
            medicine.setSearchCode(Utils.getFirstSpell(medicine.getName()));

        medicine.setManufacturerMedicine(this.findById(ManufacturerMedicine.class, medicineSaveDto.getManufacturerId()));
        if (medicineSaveDto.getCenterMedicineId() != null)
            medicine.setCenterMedicine(this.findById(CenterMedicine.class, medicineSaveDto.getCenterMedicineId()));
        else
            medicine.setCenterMedicine(null);
        ebeanServer.save(medicine);


        //建立快照信息
        if (medicineSaveDto.getUuid() == null) //如果为新物品，需读取数据库生成的CODE
            medicine = this.findById(Medicine.class, medicine.getUuid());
        MedicineSnapshot newSnapShot = medicine.createSnapshot();
        ebeanServer.save(newSnapShot);

        if (!medicine.getSelfPay() && this.ybServiceEnabled)
            this.ybInventoryService.uploadSingleMedicine(medicine);
        //重新读取一遍
        //medicine = this.findById(Medicine.class, medicine.getUuid());
        return medicine;
    }


    public List<Medicine> getMedicineList(MedicineFilter medicineFilter) {
        ExpressionList<Medicine> el = this.buildMedicineListQuery(medicineFilter);
        return el.findList();
    }

    public PagedList<Medicine> getPagedMedicineList(Integer pageNum, MedicineFilter medicineFilter) {
        return getPagedMedicineList(pageNum, medicineFilter, 0);
    }

    public PagedList<Medicine> getPagedMedicineList(Integer pageNum, MedicineFilter medicineFilter, Integer pageSize) {
        ExpressionList<Medicine> el = this.buildMedicineListQuery(medicineFilter);
        return findPagedList(el, pageNum, pageSize);
    }

    private ExpressionList<Medicine> buildMedicineListQuery(MedicineFilter medicineFilter) {
        ExpressionList<Medicine> el = ebeanServer.find(Medicine.class)
                .where();
        if (medicineFilter.getNeedPrescription() != null)
            el = el.eq("needPrescription", medicineFilter.getNeedPrescription());

        if (medicineFilter.getMedicineTypeId() != null)
            el = el.eq("type.id", medicineFilter.getMedicineTypeId());


        if (medicineFilter.getMedicineTypeNameList() != null)
            el = el.in("type.name", medicineFilter.getMedicineTypeNameList());

        if (medicineFilter.getPendingPriceUpdate() != null && medicineFilter.getPendingPriceUpdate())
            el =
                    el.and()
                            .ne("pendingListPrice", null)
                            .ne("pendingListPrice", 0)
                            .endAnd();


        if (medicineFilter.getYbNotUploaded()) {
            el = el.eq("centerMedicine", null)
                    .or()
                    .eq("uploadResult", null)
                    .eq("uploadResult.serverCode", null)
                    .endOr();
        }


        if (medicineFilter.getYbNotMatched()) {
            List<String> invalidStatus = new ArrayList<>();
            invalidStatus.add("9"); //不通过
            invalidStatus.add("2");  //无记录
            el = el.and()
                    .eq("selfPay", false)
                    .or()
                    .eq("matchedMedicine", null)
                    .in("matchedMedicine.status", invalidStatus)
                    .endOr()
                    .endAnd();
        }

        if (medicineFilter.getCheckYbPrice() != null && medicineFilter.getCheckYbPrice())
            el = el.and()
                    .ne("centerMedicine", null)
                    .ne("listPrice -centerMedicine.BZJG", 0)
                    .endAnd();


        if (medicineFilter.getServeUomDataFix() != null)
            el = el.eq("serveUom.name", "未知");
        if (medicineFilter.getMinUomDataFix() != null)
            el = el.eq("minUom.name", "未知");

        StockFilterDto stockFilterDto = medicineFilter.getStockFilter();
        if (stockFilterDto != null && stockFilterDto.getExpireDate() != null) {
            el = el.query().fetch("stockList").
                    where()
                    .le("stockList.originPurchaseLine.expireDate", stockFilterDto.getExpireDate())
                    .filterMany("stockList")
                    .le("originPurchaseLine.expireDate", stockFilterDto.getExpireDate());
        }

        if (medicineFilter.getCenterMedicineExpired() != null && medicineFilter.getCenterMedicineExpired())
            el = el.lt("centerMedicine.JSRQ", LocalDate.now());

        return this.buildInventoryListQuery(el, medicineFilter);
    }

    private <T> ExpressionList<T> buildInventoryListQuery(ExpressionList<T> el, InventoryEntityFilter inventoryEntityFilter) {
        if (inventoryEntityFilter.getEnabled() != null)
            el = el.eq("enabled", inventoryEntityFilter.getEnabled());

        if (inventoryEntityFilter.getSearchCode() != null)
            el = Utils.addSearchExpression(el, inventoryEntityFilter.getSearchCode());
        if (inventoryEntityFilter.getStockFilter() != null) {
            StockFilterDto stockFilterDto = inventoryEntityFilter.getStockFilter();
            if (stockFilterDto.getInventorEntityId() != null)
                el = el.eq("uuid", inventoryEntityFilter.getStockFilter().getInventorEntityId());
            if (stockFilterDto.getWarehouseIdList() != null)
                el = el.query().fetch("stockList").
                        where()
                        .in("stockList.warehouse.uuid", inventoryEntityFilter.getStockFilter().getWarehouseIdList())
                        .filterMany("stockList")
                        .in("warehouse.uuid", inventoryEntityFilter.getStockFilter().getWarehouseIdList());


        }
        return el;
    }


    @Transactional
    public MedicineDetailPramDto getMedicineDetailPram() {
        MedicineDetailPramDto pramDto = new MedicineDetailPramDto();
        pramDto.setTypeDtoList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedicineType())));
        pramDto.setFunctionTypeDtoList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedicineFunctionType())));
        pramDto.setLevelDtoList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedicineLevel())));
        pramDto.setAttributeDtoList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedicineAttribute())));
        pramDto.setFormDtoList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedicineForm())));
        pramDto.setStorageTypeDtoList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedicineStorageType())));
        pramDto.setPharmacyUomDtoList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.medicinePharmacy)));
        pramDto.setMinUomDtoList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.medicineMin)));
        pramDto.setServeUomDtoList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.medicineServe)));
        pramDto.setWarehouseUomDtoList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.medicineWarehouse)));
        pramDto.setFeeTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedicineFeeType())));
        return pramDto;
    }


    public List<Item> getItemList(ItemFilter itemFilter) {
        ExpressionList<Item> el = this.buildItemListQuery(itemFilter);
        return el.findList();
    }

    public PagedList<Item> getPagedItemList(Integer pageNum, ItemFilter itemFilter) {
        return getPagedItemList(pageNum, itemFilter, 0);
    }

    public PagedList<Item> getPagedItemList(Integer pageNum, ItemFilter itemFilter, Integer pageSize) {
        ExpressionList<Item> el = this.buildItemListQuery(itemFilter);
        PagedList<Item> itemPagedList = findPagedList(el, pageNum, pageSize);
        return itemPagedList;
    }


    private ExpressionList<Item> buildItemListQuery(ItemFilter itemFilter) {
        ExpressionList<Item> el = ebeanServer.find(Item.class)
                .where();
        this.buildInventoryListQuery(el, itemFilter);
        if (itemFilter.getWarehouseTypeId() != null)
            el = el.eq("warehouseType.id", itemFilter.getWarehouseTypeId());

        if (itemFilter.getAllowAutoFee() != null)
            el = el.eq("allowAutoFee", itemFilter.getAllowAutoFee());

        if (itemFilter.getYbNotUploaded()) {
            el = el.eq("selfPay", false)
                    .or()
                    .eq("uploadResult", null)
                    .eq("uploadResult.serverCode", null)
                    .endOr();
        }

        if (itemFilter.getPendingPriceUpdate() != null && itemFilter.getPendingPriceUpdate())
            el =
                    el.and()
                            .ne("pendingListPrice", null)
                            .ne("pendingListPrice", 0)
                            .endAnd();

        if (itemFilter.getYbNotMatched()) {
            List<String> invalidStatus = new ArrayList<>();
            invalidStatus.add("9"); //不通过
            invalidStatus.add("2");  //无记录
            el = el.eq("selfPay", false)
                    .or()
                    .eq("matchedItem", null)
                    .in("matchedItem.status", invalidStatus)
                    .endOr();
        }

        //todo 这里需要更改为数据库结构，而不是写死耗材库名字
        if (itemFilter.getChargeableItem() != null)
            el = el.eq("warehouseType.name", "医用耗材库");


        return el;
    }

    @Transactional
    public Item saveItem(ItemSaveDto itemSaveDto) {
        Item item;
        if (itemSaveDto.getUuid() == null) {
            item = new Item();
            item.setPrescriptionRequired(true);
            item.setAllowAutoFee(false);
        } else {
            item = this.findById(Item.class, itemSaveDto.getUuid());
            if (item.centerTreatmentChanged(itemSaveDto.getCenterTreatmentId())) {
                //中心项目改变，作废现有的匹配信息
                ItemMatchDownload existingMatch = item.getMatchedItem();
                if (existingMatch != null) {
                    existingMatch.setStatus("rematch");
                    ebeanServer.save(existingMatch);
                }
            }
        }
        itemSaveDto.copyPropertiesToEntity(item);
        if (StringUtils.isEmpty(item.getSearchCode()))
            item.setSearchCode(Utils.getFirstSpell(item.getName()));

        if (itemSaveDto.getManufacturerId() != null)
            item.setManufacturerItem(this.findById(ManufacturerItem.class, itemSaveDto.getManufacturerId()));
        else
            item.setManufacturerItem(null);

        if (itemSaveDto.getCenterTreatmentId() != null)
            item.setCenterTreatment(this.findById(CenterTreatment.class, itemSaveDto.getCenterTreatmentId()));
        else
            item.setCenterTreatment(null);

        ebeanServer.save(item);


        //建立快照信息
        if (itemSaveDto.getUuid() == null) //如果为新物品，需读取数据库生成的CODE
            item = this.findById(Item.class, item.getUuid());
        ItemSnapshot newSnapShot = item.createSnapshot();
        ebeanServer.save(newSnapShot);

        if (!item.getSelfPay() && this.ybServiceEnabled)
            this.ybInventoryService.uploadSingleItem(item);
        return item;
    }

    @Transactional
    public ItemDetailPramDto getItemDetailPram() {
        ItemDetailPramDto pramDto = new ItemDetailPramDto();
        pramDto.setStorageTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getItemStorageType())));
        pramDto.setWarehouseList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getWarehouseType())));
        pramDto.setMinUomList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.item)));
        pramDto.setWarehouseUomList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.itemWarehouse)));
        pramDto.setDepartmentUomList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.itemDepartment)));
        pramDto.setFeeTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getItemFeeType())));
        return pramDto;
    }

    public List<Treatment> getTreatmentList(TreatmentFilter treatmentFilter) {
        ExpressionList<Treatment> el = this.buildTreatmentListQuery(treatmentFilter);
        return el.orderBy("searchCode").findList();
    }

    public PagedList<Treatment> getPagedTreatmentList(Integer pageNum, TreatmentFilter treatmentFilter) {
        return getPagedTreatmentList(pageNum, treatmentFilter, 0);
    }

    public PagedList<Treatment> getPagedTreatmentList(Integer pageNum, TreatmentFilter treatmentFilter, Integer pageSize) {
        ExpressionList<Treatment> el = this.buildTreatmentListQuery(treatmentFilter);
        return findPagedList(el, pageNum, pageSize);
    }

    private ExpressionList<Treatment> buildTreatmentListQuery(TreatmentFilter treatmentFilter) {
        ExpressionList<Treatment> el = ebeanServer.find(Treatment.class).where();
        if (treatmentFilter.getFeeTypeName() != null)
            el = el.eq("feeType.name", treatmentFilter.getFeeTypeName());

        if (treatmentFilter.getAllowAutoFee() != null)
            el = el.eq("allowAutoFee", treatmentFilter.getAllowAutoFee());

        if (treatmentFilter.getAllowManualFee() != null)
            el = el.eq("allowManualFee", treatmentFilter.getAllowManualFee());

        if (treatmentFilter.getEnabled() != null)
            el = el.eq("enabled", treatmentFilter.getEnabled());

        if (treatmentFilter.getSearchCode() != null)
            el = Utils.addSearchExpression(el, treatmentFilter.getSearchCode());
        if (treatmentFilter.getTreatmentType() != null)
            el = el.eq("executeDepartmentType", treatmentFilter.getTreatmentType());

        if (treatmentFilter.getTreatmentTypeList() != null && treatmentFilter.getTreatmentTypeList().size() > 0)
            el = el.in("executeDepartmentType", treatmentFilter.getTreatmentTypeList());

        if (treatmentFilter.getExcludeCombo() != null)
            el = el.eq("combo", !treatmentFilter.getExcludeCombo());

        if (treatmentFilter.getModifyStartDate() != null && treatmentFilter.getModifyEndDate() != null) {
            Date endDate = this.addDays(treatmentFilter.getModifyEndDate(), 1);
            el = el.between("whenModified", treatmentFilter.getModifyStartDate(), endDate);
        }


        if (treatmentFilter.getModifiedBy() != null)
            el = el.like("whoModified", "%" + treatmentFilter.getModifiedBy() + "%");

        if (treatmentFilter.getPendingPriceUpdate() != null && treatmentFilter.getPendingPriceUpdate())
            el =
                    el.and()
                            .ne("pendingListPrice", null)
                            .ne("pendingListPrice", 0)
                            .endAnd();

        if (treatmentFilter.getYbNotUploaded()) {
            el = el
                    .or()
                    .eq("uploadResult", null)
                    .eq("uploadResult.serverCode", null)
                    .endOr();
        }


        if (treatmentFilter.getYbNotMatched()) {
            List<String> invalidStatus = new ArrayList<>();
            invalidStatus.add("9"); //不通过
            invalidStatus.add("2");  //无记录
            el = el
                    .or()
                    .eq("matchedTreatment", null)
                    .in("matchedTreatment.status", invalidStatus)
                    .endOr();
        }
        return el;
    }


    @Transactional
    public void requestUpdateItemPrice(EntityPriceSaveDto entityPriceDto) {
        Item item = this.findById(Item.class, entityPriceDto.getUuid());
        item.setPendingListPrice(entityPriceDto.getPrice());
        ebeanServer.save(item);

    }

    @Transactional
    public Item confirmItemPrice(UUID itemId) {
        Item item = this.findById(Item.class, itemId);
        BasePriceLog newPriceLog = item.confirmNewPrice();
        if (newPriceLog != null) {
            ItemSnapshot itemSnapshot = item.createSnapshot();
            ebeanServer.save(itemSnapshot);
            ebeanServer.save(newPriceLog);
            ebeanServer.update(item);
        }
        return item;
    }

    @Transactional
    public void requestUpdateMedicinePrice(EntityPriceSaveDto entityPriceSaveDto) {
        Medicine medicine = this.findById(Medicine.class, entityPriceSaveDto.getUuid());
        medicine.setPendingListPrice(entityPriceSaveDto.getPrice());
        ebeanServer.save(medicine);

    }

    @Transactional
    public Medicine confirmMedicinePrice(UUID medicineId) {
        Medicine medicine = this.findById(Medicine.class, medicineId);
        BasePriceLog newPriceLog = medicine.confirmNewPrice();
        if (newPriceLog != null) {
            MedicineSnapshot snapshot = medicine.createSnapshot();
            ebeanServer.save(snapshot);
            ebeanServer.save(newPriceLog);
            ebeanServer.update(medicine);
        }

        if (!medicine.getSelfPay() && this.uploadYBInventory)
            this.ybInventoryService.updateYBPrice(medicine);
        return medicine;
    }

    @Transactional
    public void requestUpdateTreatmentPrice(EntityPriceSaveDto entityPriceSaveDto) {
        Treatment treatment = this.findById(Treatment.class, entityPriceSaveDto.getUuid());
        treatment.setPendingListPrice(entityPriceSaveDto.getPrice());
        ebeanServer.save(treatment);
    }

    @Transactional
    public Treatment confirmTreatmentPrice(UUID treatmentId) {
        Treatment treatment = this.findById(Treatment.class, treatmentId);
        BasePriceLog newPriceLog = treatment.confirmNewPrice();
        if (newPriceLog != null) {
            TreatmentSnapshot snapshot = treatment.createSnapshot();
            ebeanServer.save(snapshot);
            ebeanServer.save(newPriceLog);
            ebeanServer.update(treatment);
        }
        return treatment;
    }


    public Medicine getMedicineById(UUID medicineId) {
        return this.findById(Medicine.class, medicineId);
    }

    public Item getItemById(UUID itemId) {
        return this.findById(Item.class, itemId);
    }

    public Treatment getTreatmentById(UUID treatmentId) {
        return this.findById(Treatment.class, treatmentId);
    }


    public Boolean itemAllowEdit(UUID itemId) {
        Optional<ItemOrderLine> optionalItemOrderLine = this.ebeanServer.find(ItemOrderLine.class).where().eq("item.uuid", itemId).setMaxRows(1).findOneOrEmpty();
        return !optionalItemOrderLine.isPresent();
    }

    public Boolean medicineAllowEdit(UUID itemId) {
        Optional<MedicineOrderLine> optionalMedicineOrderLine = this.ebeanServer.find(MedicineOrderLine.class).where().eq("medicine.uuid", itemId).setMaxRows(1).findOneOrEmpty();
        return !optionalMedicineOrderLine.isPresent();
    }

    public TreatmentDetailPramDto getTreatmentDetailPram() {
        TreatmentDetailPramDto pramDto = new TreatmentDetailPramDto();
        pramDto.setMinUomList(DtoUtils.toDtoList(this.basicService.getUnitOfMeasureList(UnitOfMeasureType.treatment)));
        pramDto.setFeeTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getTreatmentFeeType())));
        pramDto.setRecoverTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getRecoveryTreatmentType())));
        DepartmentFilterDto departmentFilter = new DepartmentFilterDto();
        departmentFilter.setDepartmentType(DepartmentType.treatment);
        pramDto.setDepartmentTreatmentList(DtoUtils.toDtoList(this.basicService.getDepartmentList(departmentFilter)));
        pramDto.setLabSampleTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getLabSampleType())));
        pramDto.setLabTreatmentTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getLabTreatmentType())));
        return pramDto;
    }

    @Transactional
    public Treatment saveTreatment(TreatmentSaveDto treatmentSaveDto) {
        Treatment treatment;
        if (treatmentSaveDto.getUuid() == null) {
            treatment = new Treatment();
            treatment.setAllowAutoFee(false);
            treatment.setAllowManualFee(false);
            treatment.setPrescriptionRequired(true);
            treatment.setShowInCard(true);
            treatment.setYb3024Group(false);
        } else {
            treatment = this.findById(Treatment.class, treatmentSaveDto.getUuid());
            if (treatment.centerTreatmentChanged(treatmentSaveDto.getCenterTreatmentId())) {
                //中心项目改变，作废现有的匹配信息
                TreatmentMatchDownload existingMatch = treatment.getMatchedTreatment();
                if (existingMatch != null) {
                    existingMatch.setStatus("rematch");
                    ebeanServer.save(existingMatch);
                }
            }
        }
        treatmentSaveDto.copyPropertiesToEntity(treatment);

        if (StringUtils.isEmpty(treatment.getSearchCode()))
            treatment.setSearchCode(Utils.getFirstSpell(treatment.getName()));

        if (treatmentSaveDto.getCenterTreatmentId() != null)
            treatment.setCenterTreatment(this.findById(CenterTreatment.class, treatmentSaveDto.getCenterTreatmentId()));
        else
            treatment.setCenterTreatment(null);

        ebeanServer.save(treatment);
        //建立快照信息

        if (treatmentSaveDto.getUuid() == null) //如果为新物品，需读取数据库生成的CODE
            treatment = this.findById(Treatment.class, treatment.getUuid());
        TreatmentSnapshot newSnapShot = treatment.createSnapshot();
        ebeanServer.save(newSnapShot);

        if (this.ybServiceEnabled)
            this.ybService.uploadSingleTreatment(treatment);
        return treatment;
    }

    public void updateMedicineFeeSetting(EntityFeeSettingReqDto reqDto) {
        Medicine medicine = this.findById(Medicine.class, reqDto.getUuid());
        medicine.setPrescriptionRequired(reqDto.getPrescriptionRequired());
        ebeanServer.update(medicine);
    }

    public void updateTreatmentFeeSetting(EntityFeeSettingReqDto reqDto) {
        Treatment treatment = this.findById(Treatment.class, reqDto.getUuid());
        treatment.setPrescriptionRequired(reqDto.getPrescriptionRequired());
        treatment.setAllowAutoFee(reqDto.getAllowAutoFee());
        treatment.setAllowManualFee(reqDto.getAllowManualFee());
        treatment.setShowInCard(reqDto.getShowInCard());
        ebeanServer.update(treatment);
    }

    public void updateItemFeeSetting(EntityFeeSettingReqDto reqDto) {
        Item item = this.findById(Item.class, reqDto.getUuid());
        item.setPrescriptionRequired(reqDto.getPrescriptionRequired());
        item.setAllowAutoFee(reqDto.getAllowAutoFee());
        ebeanServer.update(item);
    }
}
