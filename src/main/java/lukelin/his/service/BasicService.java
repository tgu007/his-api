package lukelin.his.service;

import io.ebean.ExpressionList;
import io.ebean.PagedList;
import lukelin.his.domain.entity.basic.*;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.basic.template.*;
import lukelin.his.domain.entity.basic.codeEntity.*;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.yb.ICD9;
import lukelin.his.domain.enums.Basic.*;
import lukelin.his.dto.basic.SearchCodeDto;
import lukelin.his.dto.basic.req.*;
import lukelin.his.dto.basic.req.filter.*;
import lukelin.his.dto.basic.req.template.MedicalRecordTemplateSaveDto;
import lukelin.his.dto.basic.req.template.TemplateTagSaveDto;
import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;
import lukelin.his.dto.basic.resp.setup.TreeNodeDto;
import lukelin.his.dto.yb.inventory.resp.ManufacturerUploadResp;
import lukelin.his.system.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasicService extends BaseHisService {
    @Autowired
    YBInventoryService ybInventoryService;

    @Value("${uploadYBInventory}")
    private Boolean uploadYBInventory;


    public List<Dictionary> getDictionaryList(String typeCode) {
        return ebeanServer.find(Dictionary.class)
                .where()
                .eq("group.code", typeCode)
                .findList();
    }

    public List<UnitOfMeasure> getUnitOfMeasureList(UnitOfMeasureType uomType) {
        return ebeanServer.find(UnitOfMeasure.class)
                .where()
                .eq("type", uomType)
                .findList();
    }


    public PagedList<Diagnose> getPagedDiseaseList(SearchCodeDto searchCodeDto, Integer pageNum) {
        return this.getPagedDiseaseList(searchCodeDto, pageNum, 0);
    }

    public PagedList<Diagnose> getPagedDiseaseList(SearchCodeDto searchCodeDto, Integer pageNum, Integer pageSize) {
        ExpressionList<Diagnose> el = ebeanServer.find(Diagnose.class).where();
        this.buildCodeTableListQuery(el, searchCodeDto);
        return this.findPagedList(el, pageNum, pageSize);
    }

    private <T> void buildCodeTableListQuery(ExpressionList<T> el, SearchCodeDto searchCodeDto) {
        if (searchCodeDto.getEnabled() != null)
            el = el.eq("enabled", searchCodeDto.getEnabled());
        if (searchCodeDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, searchCodeDto.getSearchCode());
    }


    public List<ManufacturerMedicine> getMedicineManufacturerList(SearchCodeDto searchCodeDto, Boolean enabled) {

        return this.getMedicineManufacturerListQuery(searchCodeDto, enabled).findList();
    }

    private ExpressionList<ManufacturerMedicine> getMedicineManufacturerListQuery(SearchCodeDto searchCodeDto, Boolean enabled) {
        ExpressionList<ManufacturerMedicine> el = ebeanServer.find(ManufacturerMedicine.class)
                .where();
        if (enabled != null)
            el = el.eq("enabled", enabled);
        if (searchCodeDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, searchCodeDto.getSearchCode());
        return el;
    }

    public List<ManufacturerItem> getItemManufacturerList(SearchCodeDto searchCodeDto, Boolean enabled) {

        return this.getItemManufacturerListQuery(searchCodeDto, enabled).findList();
    }

    private ExpressionList<ManufacturerItem> getItemManufacturerListQuery(SearchCodeDto searchCodeDto, Boolean enabled) {
        ExpressionList<ManufacturerItem> el = ebeanServer.find(ManufacturerItem.class)
                .where();
        if (enabled != null)
            el = el.eq("enabled", enabled);
        if (searchCodeDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, searchCodeDto.getSearchCode());
        return el;
    }

    public List<Supplier> getSupplierList(SupplierFilterDto supplierFilterDto) {
        ExpressionList<Supplier> el = ebeanServer.find(Supplier.class)
                .where();
        if (supplierFilterDto.getSupplierType() != null)
            el = el.eq("type", supplierFilterDto.getSupplierType());
        if (supplierFilterDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, supplierFilterDto.getSearchCode());
        return el.findList();

    }

    public List<DepartmentWarehouse> getWarehouseList(DepartmentFilterDto departmentFilterDto) {
        ExpressionList<DepartmentWarehouse> el = ebeanServer.find(DepartmentWarehouse.class)
                .orderBy("department.sequence").where();
        if (departmentFilterDto.getWarehouseTypeList() != null)
            el.in("warehouseType", departmentFilterDto.getWarehouseTypeList());

        if (departmentFilterDto.getWarehouseIdList() != null)
            el.in("uuid", departmentFilterDto.getWarehouseIdList());

        return el.findList();

    }

    public List<DepartmentTreatment> getDepartmentList(DepartmentFilterDto departmentFilterDto) {
        ExpressionList<DepartmentTreatment> el = ebeanServer.find(DepartmentTreatment.class)
                .orderBy("department.sequence").where();
        if (departmentFilterDto.getDepartmentTreatmentType() != null)
            el.eq("type", departmentFilterDto.getDepartmentTreatmentType());

        if (departmentFilterDto.getDepartmentTreatmentIdList() != null)
            el.in("uuid", departmentFilterDto.getDepartmentTreatmentIdList());

        if (departmentFilterDto.getDepartmentTreatmentTypeList() != null)
            el.in("type", departmentFilterDto.getDepartmentTreatmentTypeList());

        return el.findList();
    }


    public List<MedicalRecordType> getMedicalRecordTypeList(MedicalRecordTypeFilter filter) {
        ExpressionList<MedicalRecordType> el = ebeanServer.find(MedicalRecordType.class)
                .orderBy("order").where();
        if (filter.getFixedFormat() != null)
            el = el.eq("fixedFormat", filter.getFixedFormat());
        if (filter.getEnabled() != null)
            el = el.eq("enabled", filter.getEnabled());

        if (filter.getUserRoleIIdList() != null)
            el = el.query().fetch("permittedRoleSet").
                    where()
                    .in("permittedRoleSet.uuid", filter.getUserRoleIIdList())
                    .filterMany("permittedRoleSet")
                    .in("uuid", filter.getUserRoleIIdList());

        return el.findList();
    }

    public List<MedicalRecordTemplate> getMedicalRecordTemplateList(MedicalTemplateFilter filter) {
        ExpressionList<MedicalRecordTemplate> el =
                ebeanServer.find(MedicalRecordTemplate.class)
                        .select("name, uuid, permissionType, ownedByDoctor, type, whenModified, whoCreated, whoModified, enabled")
                        .where();

        if (filter.getEnabled() != null)
            el = el.eq("enabled", filter.getEnabled());

        if (filter.getTypeId() != null)
            el = el.eq("type.uuid", filter.getTypeId());

        if (filter.getTemplateName() != null)
            el = el.like("name", "%" + filter.getTemplateName() + "%");

        EmployeeDto employee = filter.getEmployee();
        if (!(employee.getUserRole().getUserRoleType() == UserRoleType.admin)) {
            el = el.or()
                    .and()
                    .eq("permissionType", MedicalRecordTemplatePermissionType.publicTemplate)
                    .endAnd()
                    .and()
                    .eq("permissionType", MedicalRecordTemplatePermissionType.privateTemplate)
                    .eq("ownedByDoctor.uuid", employee.getUuid())
                    .endAnd()
                    .and()
                    .eq("permissionType", MedicalRecordTemplatePermissionType.departmentTemplate)
                    .in("permittedDepartmentList.uuid", employee.getDepartmentIdList())
                    .endAnd();
        }

        return el.findList();
    }

    public UUID saveTemplate(MedicalRecordTemplateSaveDto templateSaveDto) {
        MedicalRecordTemplate template = templateSaveDto.toEntity();
        if (template.getUuid() != null)
            ebeanServer.update(template);
        else
            ebeanServer.save(template);
        return template.getUuid();
    }

    public void setMedicalRecordTypeStatus(UUID typeId, boolean enabled) {
        MedicalRecordType recordType = this.findById(MedicalRecordType.class, typeId);
        recordType.setEnabled(enabled);
        ebeanServer.save(recordType);
    }

    public void setMedicalRecordTemplateStatus(UUID templateId, boolean enabled) {
        MedicalRecordTemplate template = this.findById(MedicalRecordTemplate.class, templateId);
        template.setEnabled(enabled);
        ebeanServer.save(template);
    }

    public List<TemplateTagMenu> getTemplateTagList() {
        ExpressionList<TemplateTagMenu> el = ebeanServer.find(TemplateTagMenu.class)
                .fetch("childMenuList")
                .filterMany("childMenuList")
                .eq("enabled", true)
                //.fetch("templateTagList")
                .orderBy("order, childMenuList.order")
                .where()
                .eq("parent.uuid", null);

        return el.findList();
    }

    public TreeNodeDto saveTemplateTag(TemplateTagSaveDto saveDto) {
        TemplateTag templateTag = saveDto.toEntity();
        if (templateTag.getUuid() != null)
            ebeanServer.update(templateTag);
        else
            ebeanServer.save(templateTag);
        return templateTag.toDto();
    }

    public String getNewTemplateCommonHeader() {
        List<TemplateTag> commonHeaderTagList = ebeanServer.find(TemplateTag.class)
                .where().eq("name", "通用头部")
                .findList();
        if (commonHeaderTagList.size() > 0)
            return commonHeaderTagList.get(0).getTemplateHtml();
        else
            return "";
    }

    public List<Ward> getWardList(WardFilterDto filter) {
        ExpressionList<Ward> el = ebeanServer.find(Ward.class)
                .where();
        if (filter.getWardIdList() != null)
            el.in("uuid", filter.getWardIdList());
        return el.findList();

    }

    @Transactional
    public FromHospital findOrCreateFromHospital(QuickAddCodeEntityDto quickAddCodeEntityDto) {
        Optional<FromHospital> optionalFromHospital = this.findExistingEntity(ebeanServer.find(FromHospital.class).where(), quickAddCodeEntityDto);
        if (optionalFromHospital.isPresent())
            return optionalFromHospital.get();
        else {
            FromHospital fromHospital = new FromHospital();
            return (FromHospital) this.createNewSearchableEntity(fromHospital, quickAddCodeEntityDto);
        }
    }

    @Transactional
    public Supplier findOrCreateSuppler(QuickAddSupplierDto quickAddSupplierDto) {
        ExpressionList<Supplier> el = ebeanServer.find(Supplier.class).where().eq("type", quickAddSupplierDto.getEntityType());
        Optional<Supplier> optionalSupplier = this.findExistingEntity(el, quickAddSupplierDto);
        if (optionalSupplier.isPresent())
            return optionalSupplier.get();
        else {
            Supplier supplier = new Supplier();
            supplier.setType(quickAddSupplierDto.getEntityType());
            supplier.setContactNumber(quickAddSupplierDto.getContactNumber());
            return (Supplier) this.createNewSearchableEntity(supplier, quickAddSupplierDto);
        }
    }

    @Transactional
    public ManufacturerMedicine findOrCreateMedicineManufacture(QuickAddManufactureDto quickAddManufactureDto) {
        Optional<ManufacturerMedicine> optionalManufacturer = this.findExistingEntity(ebeanServer.find(ManufacturerMedicine.class).where(), quickAddManufactureDto);
        if (optionalManufacturer.isPresent())
            return optionalManufacturer.get();
        else {
            ManufacturerMedicine manufacturerMedicine = new ManufacturerMedicine();
            if (this.uploadYBInventory) {
                ManufacturerUploadResp ybUploadResp = this.ybInventoryService.uploadManufacturer(quickAddManufactureDto);
                manufacturerMedicine.setServerId(ybUploadResp.getGysbh());
            } else
                manufacturerMedicine.setServerId("not uploaded");
            return (ManufacturerMedicine) this.createNewSearchableEntity(manufacturerMedicine, quickAddManufactureDto);
        }
    }

    @Transactional
    public ManufacturerItem findOrCreateItemManufacture(QuickAddManufactureDto quickAddManufactureDto) {
        Optional<ManufacturerItem> optionalManufacturer = this.findExistingEntity(ebeanServer.find(ManufacturerItem.class).where(), quickAddManufactureDto);
        if (optionalManufacturer.isPresent())
            return optionalManufacturer.get();
        else {
            ManufacturerItem manufacturerItem = new ManufacturerItem();
            if (this.uploadYBInventory) {
                ManufacturerUploadResp ybUploadResp = this.ybInventoryService.uploadManufacturer(quickAddManufactureDto);
                manufacturerItem.setServerId(ybUploadResp.getGysbh());
            } else {
                manufacturerItem.setServerId("not uploaded");
            }
            return (ManufacturerItem) this.createNewSearchableEntity(manufacturerItem, quickAddManufactureDto);
        }
    }

    private BaseSearchableCodeEntity createNewSearchableEntity(BaseSearchableCodeEntity newEntity, QuickAddCodeEntityDto quickAddCodeEntityDto) {
        newEntity.setSearchCode(Utils.getFirstSpell(quickAddCodeEntityDto.getName()));
        return (BaseSearchableCodeEntity) this.createNewCodeEntity(newEntity, quickAddCodeEntityDto);
    }

    private BaseCodeEntity createNewCodeEntity(BaseCodeEntity newEntity, QuickAddCodeEntityDto quickAddCodeEntityDto) {
        newEntity.setCode(quickAddCodeEntityDto.getName());
        newEntity.setName(quickAddCodeEntityDto.getName());
        newEntity.setEnabled(true);
        ebeanServer.save(newEntity);
        return newEntity;
    }

    public <T> Optional<T> findExistingEntity(ExpressionList<T> el, QuickAddCodeEntityDto quickAddCodeEntityDto) {
        return el.eq("name", quickAddCodeEntityDto.getName())
                .eq("enabled", true)
                .findOneOrEmpty();
    }

    public UnitOfMeasure findOrCreateUom(QuickAddUomDto quickAddUomDto) {
        ExpressionList<UnitOfMeasure> el = ebeanServer.find(UnitOfMeasure.class)
                .where().eq("type", quickAddUomDto.getUomType());
        Optional<UnitOfMeasure> optionalUom = this.findExistingEntity(el, quickAddUomDto);
        if (optionalUom.isPresent())
            return optionalUom.get();
        else {
            UnitOfMeasure uom = new UnitOfMeasure();
            uom.setType(quickAddUomDto.getUomType());
            return (UnitOfMeasure) this.createNewCodeEntity(uom, quickAddUomDto);
        }
    }

    @Transactional
    public DepartmentWarehouse findOrCreateLevelTwoWarehouse(QuickAddCodeEntityDto warehouseDto) {
        Optional<DepartmentWarehouse> optionalWarehouse = ebeanServer.find(DepartmentWarehouse.class)
                .where().eq("department.name", warehouseDto.getName())
                .eq("department.enabled", true)
                .eq("warehouseType", WarehouseType.levelTwoWarehouse)
                .findOneOrEmpty();

        if (optionalWarehouse.isPresent())
            return optionalWarehouse.get();
        else {
            DepartmentWarehouse warehouse = new DepartmentWarehouse();
            warehouse.setWarehouseType(WarehouseType.levelTwoWarehouse);
            Department department = new Department();
            department.setType(DepartmentType.warehouse);
            department.setCode(warehouseDto.getName());
            department.setName(warehouseDto.getName());
            department.setEnabled(true);
            Optional<DepartmentWarehouse> lastWarehouse =
                    ebeanServer.find(DepartmentWarehouse.class).orderBy("department.sequence desc")
                            .setMaxRows(1).findOneOrEmpty();
            BigDecimal sequence = new BigDecimal(1);
            if (lastWarehouse.isPresent())
                sequence = lastWarehouse.get().getDepartment().getSequence().add(new BigDecimal(1));
            department.setSequence(sequence);
            warehouse.setDepartment(department);
            ebeanServer.save(warehouse);
            return warehouse;
        }
    }


    public void rebuildSearchCode() {
//        List<Treatment> treatmentList = ebeanServer.find(Treatment.class).findList();
//        for (Treatment treatment : treatmentList) {
//            //if (treatment.getSearchCode() == null)
//                treatment.setSearchCode(Utils.getFirstSpell(treatment.getName()).toUpperCase());
//        }
//        ebeanServer.saveAll(treatmentList);
//
        List<Item> itemList = ebeanServer.find(Item.class).findList();
        for (Item item : itemList) {
            //if (item.getSearchCode() == null)
            item.setSearchCode(Utils.getFirstSpell(item.getName()).toUpperCase());
        }
        ebeanServer.saveAll(itemList);

        List<ManufacturerItem> manufacturerItemList = ebeanServer.find(ManufacturerItem.class).findList();
        for (ManufacturerItem manufacturerItem : manufacturerItemList)
            //if (manufacturerMedicine.getSearchCode() == null)
            manufacturerItem.setSearchCode(Utils.getFirstSpell(manufacturerItem.getName()).toUpperCase());
        ebeanServer.saveAll(manufacturerItemList);
//
//        List<Medicine> medicineList = ebeanServer.find(Medicine.class).findList();
//        for (Medicine medicine : medicineList)
//            //if (medicine.getSearchCode() == null)
//                medicine.setSearchCode(Utils.getFirstSpell(medicine.getName()).toUpperCase());
//        ebeanServer.saveAll(medicineList);
////
//        List<ManufacturerMedicine> manufacturerMedicineList = ebeanServer.find(ManufacturerMedicine.class).findList();
//        for (ManufacturerMedicine manufacturerMedicine : manufacturerMedicineList)
//            //if (manufacturerMedicine.getSearchCode() == null)
//                manufacturerMedicine.setSearchCode(Utils.getFirstSpell(manufacturerMedicine.getName()).toUpperCase());
//        ebeanServer.saveAll(manufacturerMedicineList);
//
//        List<Dictionary> dictionaryList = ebeanServer.find(Dictionary.class).findList();
//        for (Dictionary dictionary : dictionaryList)
//            if (dictionary.getSearchCode() == null)
//                dictionary.setSearchCode(Utils.getFirstSpell(dictionary.getName()).toUpperCase());
//        ebeanServer.saveAll(dictionaryList);

//        List<Diagnose> diagnoseList = ebeanServer.find(Diagnose.class).findList();
//        for (Diagnose diagnose : diagnoseList)
//            if (diagnose.getSearchCode() == null)
//                diagnose.setSearchCode(Utils.getFirstSpell(diagnose.getName()).toUpperCase());
//        ebeanServer.saveAll(diagnoseList);

//        List<ICD9> icd9List = ebeanServer.find(ICD9.class).findList();
//        for (ICD9 icd9 : icd9List)
//            if (icd9.getSearchCode() == null)
//                icd9.setSearchCode(Utils.getFirstSpell(icd9.getName()).toUpperCase());
//        ebeanServer.saveAll(icd9List);
    }


    public List<Brand> getBrandList(BrandFilterDto searchCodeDto) {
        ExpressionList<Brand> el = ebeanServer.find(Brand.class)
                .where();
        if (searchCodeDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, searchCodeDto.getSearchCode());
        if (searchCodeDto.getBrandType() != null)
            el = el.eq("type", searchCodeDto.getBrandType());
        return el.findList();
    }

    public Brand findOrCreateBrand(QuickAddBrandDto quickAddBrandDto) {
        Optional<Brand> optionalBrand = this.findExistingEntity(ebeanServer.find(Brand.class).where(), quickAddBrandDto);
        if (optionalBrand.isPresent())
            return optionalBrand.get();
        else {
            Brand brand = new Brand();
            brand.setType(quickAddBrandDto.getEntityType());
            return (Brand) this.createNewSearchableEntity(brand, quickAddBrandDto);
        }
    }

    public TreatmentRespDto updateWardRoomBedTreatment(WardRoomBedTreatmentReqDto reqDto) {
        WardRoomBed bed = this.findById(WardRoomBed.class, reqDto.getBedId());
        Treatment bedTreatment = this.findById(Treatment.class, reqDto.getTreatmentId());
        bed.setTreatment(bedTreatment);
        ebeanServer.update(bed);
        return bedTreatment.toDto();
    }

    public List<FromHospital> getFromHospitalList() {
        return ebeanServer.find(FromHospital.class).findList();
    }
}
