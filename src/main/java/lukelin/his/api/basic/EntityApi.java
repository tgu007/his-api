package lukelin.his.api.basic;

import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.dto.basic.req.entity.*;
import lukelin.his.dto.basic.req.filter.ItemFilter;
import lukelin.his.dto.basic.req.filter.MedicineFilter;
import lukelin.his.dto.basic.req.filter.TreatmentFilter;
import lukelin.his.dto.basic.resp.entity.*;
import lukelin.his.dto.conveter.InventoryDtoConverter;
import lukelin.his.dto.basic.resp.entity.ItemDetailPramDto;
import lukelin.his.dto.basic.resp.entity.MedicineDetailPramDto;
import lukelin.his.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/basic")
public class EntityApi extends BaseController {
    @Autowired
    private EntityService entityService;

    @PostMapping("medicine/list/{pageNum}")
    public PagedDTO<MedicineRespDto> getPagedMedicineList(@PathVariable int pageNum, @RequestBody MedicineFilter medicineFilter) {
        PagedList<Medicine> list = entityService.getPagedMedicineList(pageNum, medicineFilter);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @PostMapping("medicine/list/{pageNum}/{pageSize}")
    public PagedDTO<MedicineRespDto> getPagedMedicineList(@PathVariable int pageNum, @RequestBody MedicineFilter medicineFilter, @PathVariable int pageSize) {
        PagedList<Medicine> list = entityService.getPagedMedicineList(pageNum, medicineFilter, pageSize);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @GetMapping("medicine/detail/pram")
    public DecoratedDTO<MedicineDetailPramDto> getMedicineDetailPram() {
        return decoratedResponse(entityService.getMedicineDetailPram());
    }

    @PostMapping("medicine/save")
    public DecoratedDTO<MedicineRespDto> saveMedicine(@RequestBody MedicineSaveDto medicineSaveDto) {
        return decoratedResponse(entityService.saveMedicine(medicineSaveDto).toDto());
    }

    @PostMapping("item/list/{pageNum}")
    public PagedDTO<ItemRespDto> getPagedItemList(@RequestBody ItemFilter itemFilter, @PathVariable int pageNum) {
        PagedList<Item> list = entityService.getPagedItemList(pageNum, itemFilter);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @PostMapping("item/list/{pageNum}/{pageSize}")
    public PagedDTO<ItemRespDto> getPagedItemList(@RequestBody ItemFilter itemFilter, @PathVariable int pageNum, @PathVariable int pageSize) {
        PagedList<Item> list = entityService.getPagedItemList(pageNum, itemFilter, pageSize);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @GetMapping("item/detail/pram")
    public DecoratedDTO<ItemDetailPramDto> getItemDetailPram() {
        return decoratedResponse(entityService.getItemDetailPram());
    }

    @PostMapping("item/save")
    public DecoratedDTO<ItemRespDto> saveItem(@RequestBody ItemSaveDto itemSaveDto) {
        return decoratedResponse(entityService.saveItem(itemSaveDto).toDto());

    }

    @PostMapping("medicine/all/stock/list/")
    public DecoratedDTO<List<MedicineStockSummaryRespDto>> getAllMedicineStockSummary(@RequestBody MedicineFilter medicineFilter) {
        List<Medicine> medicineList = this.entityService.getMedicineList(medicineFilter);
        List<MedicineStockSummaryRespDto> stockList = InventoryDtoConverter.toMedicineAggregatedStockRespDtoList(medicineList);
        return decoratedResponse(stockList);
    }

    @PostMapping("medicine/all/stock/list/{pageNum}")
    public PagedDTO<MedicineStockSummaryRespDto> getPagedMedicineStockSummary(@PathVariable Integer pageNum, @RequestBody MedicineFilter medicineFilter) {
        PagedList<Medicine> medicineList = this.entityService.getPagedMedicineList(pageNum, medicineFilter);
        List<MedicineStockSummaryRespDto> stockList = InventoryDtoConverter.toMedicineAggregatedStockRespDtoList(medicineList.getList());
        return pagedResponse(stockList, medicineList);
    }

    @PostMapping("medicine/all/stock/list/{pageNum}/{pageSize}")
    public PagedDTO<MedicineStockSummaryRespDto> getPagedMedicineStockSummary(@PathVariable Integer pageNum, @RequestBody MedicineFilter medicineFilter, @PathVariable Integer pageSize) {
        PagedList<Medicine> medicineList = this.entityService.getPagedMedicineList(pageNum, medicineFilter, pageSize);
        List<MedicineStockSummaryRespDto> stockList = InventoryDtoConverter.toMedicineAggregatedStockRespDtoList(medicineList.getList(), true);
        return pagedResponse(stockList, medicineList);
    }


    @PostMapping("item/all/stock/list/")
    public DecoratedDTO<List<ItemStockSummaryRespDto>> getAllItemStockSummary(@RequestBody ItemFilter itemFilter) {
        List<Item> itemList = this.entityService.getItemList(itemFilter);
        List<ItemStockSummaryRespDto> stockList = InventoryDtoConverter.toItemAggregatedStockRespDtoList(itemList);
        return decoratedResponse(stockList);
    }

    @PostMapping("item/all/stock/list/{pageNum}")
    public PagedDTO<ItemStockSummaryRespDto> getItemStockSummary(@PathVariable Integer pageNum, @RequestBody ItemFilter itemFilter) {
        PagedList<Item> itemPagedList = this.entityService.getPagedItemList(pageNum, itemFilter);
        List<ItemStockSummaryRespDto> stockList = InventoryDtoConverter.toItemAggregatedStockRespDtoList(itemPagedList.getList());
        return pagedResponse(stockList, itemPagedList);
    }

    @PostMapping("item/all/stock/list/{pageNum}/{pageSize}")
    public PagedDTO<ItemStockSummaryRespDto> getItemStockSummary(@PathVariable Integer pageNum, @RequestBody ItemFilter itemFilter, @PathVariable Integer pageSize) {
        PagedList<Item> itemPagedList = this.entityService.getPagedItemList(pageNum, itemFilter, pageSize);
        List<ItemStockSummaryRespDto> stockList = InventoryDtoConverter.toItemAggregatedStockRespDtoList(itemPagedList.getList());
        return pagedResponse(stockList, itemPagedList);
    }


    @PostMapping("treatment/list/{pageNum}")
    public PagedDTO<TreatmentRespDto> getPagedTreatmentList(@PathVariable int pageNum, @RequestBody TreatmentFilter treatmentFilter) {
        PagedList<Treatment> list = entityService.getPagedTreatmentList(pageNum, treatmentFilter);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @PostMapping("treatment/list/{pageNum}/{pageSize}")
    public PagedDTO<TreatmentRespDto> getPagedTreatmentList(@PathVariable int pageNum, @RequestBody TreatmentFilter treatmentFilter, @PathVariable int pageSize) {
        PagedList<Treatment> list = entityService.getPagedTreatmentList(pageNum, treatmentFilter, pageSize);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @GetMapping("treatment/detail/pram")
    public DecoratedDTO<TreatmentDetailPramDto> getTreatmentDetailPram() {
        return decoratedResponse(entityService.getTreatmentDetailPram());
    }

    @PostMapping("treatment/save")
    public DecoratedDTO<TreatmentRespDto> saveMedicine(@RequestBody TreatmentSaveDto treatmentSaveDto) {
        Treatment treatment = entityService.saveTreatment(treatmentSaveDto);
        TreatmentRespDto dto = treatment.toDto();
        return decoratedResponse(dto);
    }

    @PostMapping("item/price/update/request")
    public void requestUpdateItemPrice(@RequestBody EntityPriceSaveDto entityPriceSaveDto) {
        this.entityService.requestUpdateItemPrice(entityPriceSaveDto);
    }

    @PostMapping("item/price/update/confirm/{itemId}")
    public DecoratedDTO<ItemRespDto> confirmItemPrice(@PathVariable UUID itemId) {
        return decoratedResponse(this.entityService.confirmItemPrice(itemId).toDto());
    }

    @PostMapping("medicine/price/update/request")
    public void requestUpdateMedicinePrice(@RequestBody EntityPriceSaveDto entityPriceSaveDto) {
        this.entityService.requestUpdateMedicinePrice(entityPriceSaveDto);
    }

    @PostMapping("medicine/price/update/confirm/{medicineId}")
    public DecoratedDTO<MedicineRespDto> confirmMedicinePrice(@PathVariable UUID medicineId) {
        return decoratedResponse(this.entityService.confirmMedicinePrice(medicineId).toDto());
    }

    @PostMapping("treatment/price/update/request")
    public void requestUpdateTreatmentPrice(@RequestBody EntityPriceSaveDto entityPriceSaveDto) {
        this.entityService.requestUpdateTreatmentPrice(entityPriceSaveDto);
    }

    @PostMapping("treatment/price/update/confirm/{treatmentId}")
    public DecoratedDTO<TreatmentRespDto> confirmTreatmentPrice(@PathVariable UUID treatmentId) {
        return decoratedResponse(this.entityService.confirmTreatmentPrice(treatmentId).toDto());
    }

    @PostMapping("medicine/{medicineId}")
    public DecoratedDTO<MedicineRespDto> getMedicine(@PathVariable UUID medicineId) {
        return decoratedResponse(this.entityService.getMedicineById(medicineId).toDto());
    }

    @PostMapping("item/{itemId}")
    public DecoratedDTO<ItemRespDto> getItem(@PathVariable UUID itemId) {
        return decoratedResponse(this.entityService.getItemById(itemId).toDto());
    }

    @PostMapping("treatment/{treatmentId}")
    public DecoratedDTO<TreatmentRespDto> getTreatment(@PathVariable UUID treatmentId) {
        return decoratedResponse(this.entityService.getTreatmentById(treatmentId).toDto());
    }

    @PostMapping("item/allow_edit/{itemId}")
    public DecoratedDTO<Boolean> itemAllowEdit(@PathVariable UUID itemId) {
        return decoratedResponse(this.entityService.itemAllowEdit(itemId));
    }

    @PostMapping("medicine/allow_edit/{medicineId}")
    public DecoratedDTO<Boolean> medicineAllowEdit(@PathVariable UUID medicineId) {
        return decoratedResponse(this.entityService.medicineAllowEdit(medicineId));
    }

    @PostMapping("medicine/fee_setting")
    public void updateMedicineFeeSetting(@RequestBody EntityFeeSettingReqDto reqDto) {
        this.entityService.updateMedicineFeeSetting(reqDto);
    }

    @PostMapping("treatment/fee_setting")
    public void updateTreatmentFeeSetting(@RequestBody EntityFeeSettingReqDto reqDto) {
        this.entityService.updateTreatmentFeeSetting(reqDto);
    }

    @PostMapping("item/fee_setting")
    public void updateItemFeeSetting(@RequestBody EntityFeeSettingReqDto reqDto) {
        this.entityService.updateItemFeeSetting(reqDto);
    }

}

