package lukelin.his.api.basic;

import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.template.MedicalRecordTemplate;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.domain.entity.basic.codeEntity.*;
import lukelin.his.domain.entity.basic.template.TemplateTagMenu;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.dto.basic.req.*;
import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.notification.req.MessageDeleteReq;
import lukelin.his.dto.notification.req.NotificationFilterDto;
import lukelin.his.dto.notification.resp.NotificationDto;
import lukelin.his.dto.basic.SearchCodeDto;
import lukelin.his.dto.basic.req.filter.*;
import lukelin.his.dto.basic.req.template.MedicalRecordTemplateSaveDto;
import lukelin.his.dto.basic.req.template.TemplateTagSaveDto;
import lukelin.his.dto.basic.resp.setup.*;
import lukelin.his.dto.basic.resp.template.MedicalRecordTemplateDto;
import lukelin.his.dto.basic.resp.template.MedicalRecordTemplateListDto;
import lukelin.his.dto.basic.resp.template.MedicalRecordTypeDto;
import lukelin.his.dto.basic.resp.ward.PatientSignInWardDto;
import lukelin.his.dto.conveter.BasicDtoConverter;
import lukelin.his.service.BasicService;
import lukelin.his.service.NotificationService;
import lukelin.his.system.ConfigBeanProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/basic")
public class BasicApi extends BaseController {

    @Autowired
    private BasicService basicService;

    @Autowired
    private ConfigBeanProp configBeanProp;

    @Autowired
    private NotificationService notificationService;


    @PostMapping("manufacturer/medicine/list")
    public DecoratedDTO<List<ManufacturerMedicineRespDto>> getMedicineManufactureList(@RequestBody SearchCodeDto searchCodeDto) {
        List<ManufacturerMedicine> manufacturerMedicineList = this.basicService.getMedicineManufacturerList(searchCodeDto, true);
        return decoratedResponse(DtoUtils.toDtoList(manufacturerMedicineList));
    }

    @PostMapping("manufacturer/medicine/quick_add")
    public DecoratedDTO<ManufacturerMedicineRespDto> quickAddMedicineManufacture(@RequestBody QuickAddManufactureDto quickAddManufactureDto) {
        ManufacturerMedicine manufacturerMedicine = this.basicService.findOrCreateMedicineManufacture(quickAddManufactureDto);
        return decoratedResponse(manufacturerMedicine.toDto());
    }

    @PostMapping("manufacturer/item/list")
    public DecoratedDTO<List<ManufacturerItemRespDto>> getItemManufactureList(@RequestBody SearchCodeDto searchCodeDto) {
        List<ManufacturerItem> manufacturerMedicineList = this.basicService.getItemManufacturerList(searchCodeDto, true);
        return decoratedResponse(DtoUtils.toDtoList(manufacturerMedicineList));
    }

    @PostMapping("manufacturer/item/quick_add")
    public DecoratedDTO<ManufacturerItemRespDto> quickAddItemManufacture(@RequestBody QuickAddManufactureDto quickAddManufactureDto) {
        ManufacturerItem manufacturerItem = this.basicService.findOrCreateItemManufacture(quickAddManufactureDto);
        return decoratedResponse(manufacturerItem.toDto());
    }

    @GetMapping("frequency")
    public DecoratedDTO<List<DictionaryDto>> getFrequencyList() {
        List<Dictionary> frequencyList = basicService.getDictionaryList(configBeanProp.getFrequency());
        return decoratedResponse(DtoUtils.toDtoList(frequencyList));
    }

    @GetMapping("warehouse_type/list")
    public DecoratedDTO<List<DictionaryDto>> getWarehouseTypeList() {
        List<Dictionary> warehouseTypeList = basicService.getDictionaryList(configBeanProp.getWarehouseType());
        return decoratedResponse(DtoUtils.toDtoList(warehouseTypeList));
    }

    @GetMapping("medicine_type/list")
    public DecoratedDTO<List<DictionaryDto>> getMedicineTypeList() {
        List<Dictionary> medicineTypeList = basicService.getDictionaryList(configBeanProp.getMedicineType());
        return decoratedResponse(DtoUtils.toDtoList(medicineTypeList));
    }

    @GetMapping("sign_out_reason/list")
    public DecoratedDTO<List<DictionaryDto>> getSignOutReasonList() {
        List<Dictionary> signOutReasonList = basicService.getDictionaryList(configBeanProp.getSignOutReason());
        return decoratedResponse(DtoUtils.toDtoList(signOutReasonList));
    }

//    @GetMapping("fee/type/all")
//    public DecoratedDTO<List<DictionaryDto>> getFeeTypeList() {
//        List<Dictionary> feeTypeList = basicService.getDictionaryList(configBeanProp.getItemFeeType());
//        feeTypeList.addAll(basicService.getDictionaryList(configBeanProp.getMedicineFeeType()));
//        feeTypeList.addAll(basicService.getDictionaryList(configBeanProp.getTreatmentFeeType()));
//        return decoratedResponse( DtoUtils.toDtoList(feeTypeList));
//    }

    @GetMapping("week")
    public DecoratedDTO<List<DictionaryDto>> getWeekList() {
        List<Dictionary> weekList = basicService.getDictionaryList(configBeanProp.getWeek());
        return decoratedResponse(DtoUtils.toDtoList(weekList));
    }

    @GetMapping("medicine_use_method")
    public DecoratedDTO<List<DictionaryDto>> getMedicineUseMethodList() {
        List<Dictionary> methodList = basicService.getDictionaryList(configBeanProp.getMedicineUseMethod());
        return decoratedResponse(DtoUtils.toDtoList(methodList));
    }

    @GetMapping("payment_method")
    public DecoratedDTO<List<DictionaryDto>> getPaymentMethodList() {
        List<Dictionary> methodList = basicService.getDictionaryList(configBeanProp.getPaymentMethod());
        return decoratedResponse(DtoUtils.toDtoList(methodList));
    }

    @PostMapping("warehouse/list")
    public DecoratedDTO<List<DepartmentWarehouseDto>> getWarehouseList(@RequestBody DepartmentFilterDto departmentFilterDto) {
        List<DepartmentWarehouse> warehouseList = this.basicService.getWarehouseList(departmentFilterDto);
        return decoratedResponse(DtoUtils.toDtoList(warehouseList));
    }

    @PostMapping("department/list")
    public DecoratedDTO<List<DepartmentTreatmentDto>> getDepartmentList(@RequestBody DepartmentFilterDto departmentFilterDto) {
        List<DepartmentTreatment> departmentTreatmentList = this.basicService.getDepartmentList(departmentFilterDto);
        return decoratedResponse(DtoUtils.toDtoList(departmentTreatmentList));
    }

    @PostMapping("supplier/list")
    public DecoratedDTO<List<SupplierDto>> getSupplierList(@RequestBody SupplierFilterDto supplierFilterDto) {
        List<Supplier> supplierList = this.basicService.getSupplierList(supplierFilterDto);
        return decoratedResponse(DtoUtils.toDtoList(supplierList));
    }

    @PostMapping("supplier/quick_add")
    public DecoratedDTO<SupplierDto> quickAddSupplier(@RequestBody QuickAddSupplierDto quickAddSupplierDto) {
        Supplier supplier = this.basicService.findOrCreateSuppler(quickAddSupplierDto);
        return decoratedResponse(supplier.toDto());
    }

    @PostMapping("disease/list/{pageNum}")
    public PagedDTO<DiagnoseDto> getDiseasePagedList(@RequestBody SearchCodeDto searchCodeDto, @PathVariable Integer pageNum) {
        PagedList<Diagnose> diseaseList = basicService.getPagedDiseaseList(searchCodeDto, pageNum);
        return pagedResponse(DtoUtils.toDtoList(diseaseList.getList()), diseaseList);
    }

    @PostMapping("disease/list/{pageNum}/{pageSize}")
    public PagedDTO<DiagnoseDto> getDiseasePagedList(@RequestBody SearchCodeDto searchCodeDto, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PagedList<Diagnose> diseaseList = basicService.getPagedDiseaseList(searchCodeDto, pageNum, pageSize);
        return pagedResponse(DtoUtils.toDtoList(diseaseList.getList()), diseaseList);
    }

    @PostMapping("medical_record/type/list")
    public DecoratedDTO<List<MedicalRecordTypeDto>> getMedicalRecordTypeList(@RequestBody MedicalRecordTypeFilter filterDto) {
        filterDto.setFixedFormat(false);
        List<MedicalRecordType> medicalRecordTypeList = basicService.getMedicalRecordTypeList(filterDto);
        return decoratedResponse(DtoUtils.toDtoList(medicalRecordTypeList));
    }

    @PostMapping("medical_record/template/list")
    public DecoratedDTO<List<MedicalRecordTemplateListDto>> getMedicalRecordTemplateList(@RequestBody MedicalTemplateFilter filter) {
        List<MedicalRecordTemplate> medicalRecordTemplateList = basicService.getMedicalRecordTemplateList(filter);
        return decoratedResponse(BasicDtoConverter.toMedicalRecordTemplateList(medicalRecordTemplateList));
    }


    @PostMapping("medical_record/template/{templateId}")
    public DecoratedDTO<MedicalRecordTemplateDto> getMedicalRecordTemplate(@PathVariable UUID templateId) {
        return decoratedResponse(basicService.findById(MedicalRecordTemplate.class, templateId).toDto());
    }

    @PostMapping("medical_record/template/new/common_header")
    public DecoratedDTO<String> getNewTemplateCommonHeader() {
        return decoratedResponse(basicService.getNewTemplateCommonHeader());
    }

    @PostMapping("medical_record/template/save")
    public DecoratedDTO<UUID> saveMedicalRecordTemplate(@RequestBody MedicalRecordTemplateSaveDto templateSaveDto) {
        return decoratedResponse(basicService.saveTemplate(templateSaveDto));
    }

    @PostMapping("medical_record/type/{typeId}/enable")
    public void enableMedicalRecordType(@PathVariable UUID typeId) {
        this.basicService.setMedicalRecordTypeStatus(typeId, true);
    }

    @PostMapping("medical_record/type/{typeId}/disable")
    public void disableMedicalRecordType(@PathVariable UUID typeId) {
        this.basicService.setMedicalRecordTypeStatus(typeId, false);
    }

    @PostMapping("medical_record/template/{templateId}/enable")
    public void enableMedicalRecordTemplate(@PathVariable UUID templateId) {
        this.basicService.setMedicalRecordTemplateStatus(templateId, true);
    }

    @PostMapping("medical_record/template/{templateId}/disable")
    public void disableMedicalRecordTemplate(@PathVariable UUID templateId) {
        this.basicService.setMedicalRecordTemplateStatus(templateId, false);
    }

    @PostMapping("medical_record/template/tag/list")
    public DecoratedDTO<List<? extends TreeNodeDto>> getTemplateTagList(@RequestBody TagFilterDto filterDto) {
        List<TemplateTagMenu> rootMenuList = this.basicService.getTemplateTagList();
        return decoratedResponse(BasicDtoConverter.toTreeNodeDtoList(rootMenuList, filterDto));
    }

//    @PostMapping("medical_record/template/tag/{menuId}/list")
//    public List<? extends TreeNodeDto> getTemplateTagList(@RequestBody EmployeeDto owner, @PathVariable UUID menuId) {
//        List<TemplateTagMenu> tagList = this.basicService.getTemplateTagList(owner.getUuid(), menuId);
//        return DtoUtils.toDtoList(tagList);
//    }

    @PostMapping("medical_record/template/tag/save")
    public DecoratedDTO<TreeNodeDto> Save(@RequestBody TemplateTagSaveDto saveDto) {
        return decoratedResponse(this.basicService.saveTemplateTag(saveDto));
    }

//    @PostMapping("org/template/tag/save")
//    public OrganizationDto O(@RequestBody TemplateTagSaveDto saveDto) {
//        return this.basicService.saveTemplateTag(saveDto);
//    }

    @PostMapping("message/list")
    public DecoratedDTO<List<NotificationDto>> getMessageList(@RequestBody NotificationFilterDto messageFilter) {
        return decoratedResponse(this.notificationService.getNotificationList(messageFilter));
    }

    @PostMapping("message/delete")
    public void deleteMessage(@RequestBody MessageDeleteReq messageToDelete) {
        this.notificationService.deleteMessage(messageToDelete.getKey());
    }

    @PostMapping("message/rebuild")
    public void rebuildMessageCached() {
        this.notificationService.initNotificationList();
    }

    @PostMapping("ward/list")
    public DecoratedDTO<List<PatientSignInWardDto>> getWardList(@RequestBody WardFilterDto filter) {
        List<Ward> wardList = this.basicService.getWardList(filter);
        return decoratedResponse(BasicDtoConverter.toPatientSignInWardDtoList(wardList));
    }

    @PostMapping("uom/quick_add")
    public DecoratedDTO<UnitOfMeasureDto> quickAddUom(@RequestBody QuickAddUomDto quickAddUomDto) {
        UnitOfMeasure unitOfMeasure = this.basicService.findOrCreateUom(quickAddUomDto);
        return decoratedResponse(unitOfMeasure.toDto());
    }

    @PostMapping("warehouse/level_two/quick_add")
    public DecoratedDTO<DepartmentWarehouseDto> quickAddLevelTwoWarehouse(@RequestBody QuickAddCodeEntityDto warehouseDto) {
        DepartmentWarehouse warehouse = this.basicService.findOrCreateLevelTwoWarehouse(warehouseDto);
        return decoratedResponse(warehouse.toDto());
    }

    @PostMapping("system/search_code/rebuild")
    public void rebuildSearchCode() {
        this.basicService.rebuildSearchCode();
    }

    @PostMapping("brand/list")
    public DecoratedDTO<List<BrandDto>> getBrandList(@RequestBody BrandFilterDto searchCodeDto) {
        List<Brand> brandList = this.basicService.getBrandList(searchCodeDto);
        return decoratedResponse(DtoUtils.toDtoList(brandList));
    }

    @PostMapping("brand/quick_add")
    public DecoratedDTO<BrandDto> quickAddBrand(@RequestBody QuickAddBrandDto quickAddBrandDto) {
        Brand brand = this.basicService.findOrCreateBrand(quickAddBrandDto);
        return decoratedResponse(brand.toDto());
    }

    @PostMapping("ward/room/bed/treatment")
    public DecoratedDTO<TreatmentRespDto> wardRoomBedTreatment(@RequestBody WardRoomBedTreatmentReqDto reqDto) {
        return decoratedResponse(this.basicService.updateWardRoomBedTreatment(reqDto));
    }

    @PostMapping("from_hospital/quick_add")
    public DecoratedDTO<FromHospitalRespDto> quickAddFromHospital(@RequestBody QuickAddCodeEntityDto quickAddFromHospitalDto) {
        FromHospital fromHospital = this.basicService.findOrCreateFromHospital(quickAddFromHospitalDto);
        return decoratedResponse(fromHospital.toDto());
    }
}
