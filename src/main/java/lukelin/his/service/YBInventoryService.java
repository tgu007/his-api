package lukelin.his.service;

import io.ebean.EbeanServer;
import io.ebean.ExpressionList;
import io.ebean.SqlUpdate;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.inventory.medicine.CachedMedicineTransaction;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrderLine;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionOrderMedicineTransaction;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionOrderTransactionStatus;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.domain.enums.YB.PharmacyOrderUploadStatus;
import lukelin.his.dto.basic.req.QuickAddManufactureDto;
import lukelin.his.dto.yb.*;
import lukelin.his.dto.yb.inventory.req.*;
import lukelin.his.dto.yb.inventory.resp.*;
import lukelin.his.dto.yb.req.*;
import lukelin.his.dto.yb.inventory.req.InventoryUploadReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class YBInventoryService extends BaseHisService {
    @Autowired
    protected EbeanServer ebeanServer;

    @Autowired
    protected RestTemplate restTemplate;

    @Value("${ybInterface.baseUrl}")
    protected String interfaceBaseUrl;

    private boolean allPharmacyOrderUploading = false;

    @Value("${uploadYBPatient}")
    private Boolean ybServiceEnabled;

    protected Boolean allFeeInventoryUploading = false;

    @Transactional
    public void initializeWareHouse() {
        List<WarehouseUpload> existingList = this.ebeanServer.find(WarehouseUpload.class).findList();
        this.ebeanServer.deleteAll(existingList);

        String url = this.interfaceBaseUrl + "inventory/warehouse/setup";
        List<DepartmentWarehouse> warehouseList =
                this.ebeanServer.find(DepartmentWarehouse.class)
                        .where()
                        .or()
                        .eq("warehouseType", WarehouseType.pharmacy)
                        .eq("warehouseType", WarehouseType.wardWarehouse)
                        .endOr()
                        .findList();
        for (DepartmentWarehouse warehouse : warehouseList) {
            WarehouseSetup warehouseSetup = new WarehouseSetup();
            warehouseSetup.setKfmc(warehouse.getDepartment().getName());
            WarehouseSetup warehouseSetupRespond = this.restTemplate.postForObject(url, warehouseSetup, WarehouseSetup.class);
            WarehouseUpload warehouseUpload = new WarehouseUpload();
            warehouseUpload.setDepartmentWarehouse(warehouse);
            warehouseUpload.setServerCode(warehouseSetupRespond.getKfbh());
            ebeanServer.save(warehouseUpload);
        }
    }


    public void newYBInventoryOrder(OrderHeaderReq orderHeaderReq, InventoryOrderType orderType, OrderLineReq orderLineReq) {
        this.tryDeleteOrder(UUID.fromString(orderHeaderReq.getCYWDH()));
        String url = this.interfaceBaseUrl + "inventory/order/new";
        OrderHeaderResp resp = this.restTemplate.postForObject(url, orderHeaderReq, OrderHeaderResp.class);
        InventoryOrder newOrder;
        newOrder = resp.toInventoryOrderEntity(orderType);
        orderLineReq.setYWDH(newOrder.getYbId());
        String addLineurl = this.interfaceBaseUrl + "inventory/order/line/add";
        OrderLineResp lineResp = this.restTemplate.postForObject(addLineurl, orderLineReq, OrderLineResp.class);
        newOrder.createLineList(lineResp);
        ebeanServer.save(newOrder);
    }

    private void tryDeleteOrder(UUID hisSideId) {
        //增加新开单钱始终尝试删除，避免平台端ID没有储存在HIS端
        try {
            OrderHeaderResp tryDelete = new OrderHeaderResp();
            tryDelete.setCYWDH(hisSideId);
            this.callDeleteOrderService(tryDelete);
        } catch (Exception ex) {
            String test = ex.getMessage();
        }
    }

    public void deleteInventoryOrder(InventoryOrderType orderType, UUID hisId) {
        InventoryOrder inventoryOrder = this.findInventoryOrder(orderType, hisId);
        if (inventoryOrder != null) {
            OrderHeaderResp orderDelete = new OrderHeaderResp();
            orderDelete.setYWDH(inventoryOrder.getYbId());
            this.callDeleteOrderService(orderDelete);
            ebeanServer.delete(inventoryOrder);
        }
    }

    private void callDeleteOrderService(OrderHeaderResp tryDelete) {
        String url = this.interfaceBaseUrl + "inventory/order/delete";
        this.restTemplate.postForObject(url, tryDelete, OrderHeaderResp.class);
    }

    private InventoryOrder findInventoryOrder(InventoryOrderType orderType, UUID hisId) {
        Optional<InventoryOrder> optionalInventoryOrder = ebeanServer.find(InventoryOrder.class).where()
                .eq("hisId", hisId)
                .eq("orderType", orderType)
                .findOneOrEmpty();
        return optionalInventoryOrder.orElse(null);
    }

    public void confirmInventoryOrder(InventoryOrderType orderType, UUID hisId) {
        InventoryOrder inventoryOrder = this.findInventoryOrder(orderType, hisId);
        if (inventoryOrder != null) {
            OrderSubmitReq req = new OrderSubmitReq();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            req.setDzrq(df.format(new Date()));
            req.setYwdh(inventoryOrder.getYbId());
            req.setCywdh(hisId.toString());
            String url = this.interfaceBaseUrl + "inventory/order/submit";
            OrderSubmitResp resp = this.restTemplate.postForObject(url, req, OrderSubmitResp.class);
            inventoryOrder.updateLineYBStockNumber(resp);
            ebeanServer.save(inventoryOrder);
        }
    }

    public void processPrescriptionMedicineReturnOrder(PrescriptionMedicineReturnOrderLine returnOrderLine, List<CachedTransactionInterface> reverseTransactionList) {
        PrescriptionMedicineOrderLine orderLine = returnOrderLine.getOriginOrderLine();
        //发药时处理自费病人的医保开单，确认收药后自费病人登账，医保病人调用发药借口
        if (orderLine.getPatientSignIn().selfPay()) {
            this.newYBInventoryOrder(returnOrderLine.toYBOrderHeader(InventoryOrderType.sellReturn), InventoryOrderType.sellReturn, returnOrderLine.toYBOrderLineDto(reverseTransactionList, false));
            this.confirmInventoryOrder(InventoryOrderType.sellReturn, returnOrderLine.getUuid());
        } else if (orderLine.getYbOrderUpload() != null) {
            PharmacyOrderUpload orderUpload = orderLine.getYbOrderUpload();
            if (orderUpload.getStatus() == PharmacyOrderUploadStatus.uploaded) {
                //更新医保退药库存
                this.uploadPharmacyOrder(orderLine);
            } else {
                orderUpload.setStatus(PharmacyOrderUploadStatus.canceld);
                orderUpload.setErrorMessage("");
                ebeanServer.save(orderUpload);
            }
        }
    }

    public void confirmPrescriptionMedicineOrder(PrescriptionMedicineOrderLine orderLine) {
        //发药时处理自费病人的医保开单，确认收药后自费病人登账，医保病人建立待传记录，统一调用发药借口,费用上传时更新库存
        //需改为全部调用发药接口
        if (orderLine.getPatientSignIn().selfPay())
            this.confirmInventoryOrder(InventoryOrderType.sellOut, orderLine.getUuid());
        else {
            PharmacyOrderUpload pendingUpload = new PharmacyOrderUpload();
            pendingUpload.setOrderLine(orderLine);
            pendingUpload.setStatus(PharmacyOrderUploadStatus.pending);
            pendingUpload.setPatientSignIn(orderLine.getPatientSignIn());
            ebeanServer.save(pendingUpload);
        }
    }

    @Transactional
    public void uploadPharmacyOrder(PrescriptionMedicineOrderLine orderLine) {
        Fee fee = orderLine.getFee();
        PharmacyOrder newOrder = new PharmacyOrder();
        PharmacyOrderUpload orderUpload = orderLine.getYbOrderUpload();

        try {
            if (fee.getFeeUploadResult() == null)
                throw new ApiValidationException("相关费用未上传");

            newOrder.setFybh(fee.getFeeUploadResult().getServerId());
            if (orderLine.getReturnOrderLine() == null) {
                newOrder.setJzxh(fee.getPatientSignIn().getYbSignIn().getId());
            }

            List<PharmacyOrderLine> lineList = new ArrayList<>();
            List<PrescriptionOrderMedicineTransaction> transactionList;
            if (orderLine.getReturnOrderLine() == null)
                transactionList = orderLine.getMedicineTransactionList().stream().filter(t -> t.getTransactionType() == TransactionType.medicineOrder && t.getStatus() == PrescriptionOrderTransactionStatus.confirmed).collect(Collectors.toList());
            else
                transactionList = orderLine.getMedicineTransactionList().stream().filter(t -> t.getTransactionType() == TransactionType.cancelFee && t.getStatus() == PrescriptionOrderTransactionStatus.returned).collect(Collectors.toList());

            for (PrescriptionOrderMedicineTransaction orderMedicineTransaction : transactionList) {
                CachedMedicineTransaction cachedMedicineTransaction = orderMedicineTransaction.getMedicineTransaction();
                PharmacyOrderLine pharmacyOrderLine = new PharmacyOrderLine();
                pharmacyOrderLine.setKfbh(fee.getWarehouse().getWarehouseUploaded().getServerCode());
                pharmacyOrderLine.setPch(cachedMedicineTransaction.getOriginPurchaseLine().getBatchNumber());
                pharmacyOrderLine.setFysl(cachedMedicineTransaction.getMinUomQuantity().toString());
            }
            newOrder.setYpmx(lineList);

            String url = this.interfaceBaseUrl + "inventory/order/pharmacy/new";
            this.restTemplate.postForObject(url, newOrder, PharmacyOrder.class);

            orderUpload.setErrorMessage("");
            orderUpload.setStatus(PharmacyOrderUploadStatus.uploaded);
        } catch (Exception ex) {
            orderUpload.setErrorMessage(ex.getMessage());
            orderUpload.setStatus(PharmacyOrderUploadStatus.error);
        } finally {
            ebeanServer.save(orderUpload);
        }
    }

    public void uploadAllPharmacyOrder() {
        if (this.allPharmacyOrderUploading)
            throw new ApiValidationException("pharmacy order uploading");

        try {
            this.allPharmacyOrderUploading = true;
            List<PharmacyOrderUpload> pendingList = this.findPendingPharmacyOrderList(null);

            for (PharmacyOrderUpload pendingOrder : pendingList) {
                this.uploadPharmacyOrder(pendingOrder.getOrderLine());
            }
        } finally {
            this.allPharmacyOrderUploading = false;
        }
    }

    public List<PharmacyOrderUpload> findPendingPharmacyOrderList(PatientSignIn patientSignIn) {
        ExpressionList<PharmacyOrderUpload> ex =
                ebeanServer.find(PharmacyOrderUpload.class).where()
                        .or()
                        .eq("status", PharmacyOrderUploadStatus.pending)
                        .eq("status", PharmacyOrderUploadStatus.error)
                        .endOr();
        if (patientSignIn != null)
            ex = ex.eq("patientSignIn.uuid", patientSignIn.getUuid());
        return ex.findList();

    }

    public void updateYBPrice(Medicine medicine) {
        if (medicine.getUploadResult() == null || medicine.getUploadResult().getServerCode() == null)
            throw new ApiValidationException("药品未上传");
        PriceAdjustment priceAdjustment = new PriceAdjustment();
        priceAdjustment.setCwzbh(medicine.getUuid().toString());
        priceAdjustment.setWzbh(medicine.getUploadResult().getServerCode());
        priceAdjustment.setThdj(medicine.getListPrice());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        priceAdjustment.setTjrq(df.format(new Date()));
        String url = this.interfaceBaseUrl + "inventory/medicine/price/adjustment";
        this.restTemplate.postForObject(url, priceAdjustment, PriceAdjustment.class);
    }

    @Transactional
    public void downloadCenterMedicine() {
        String url = this.interfaceBaseUrl + "inventory/medicine/center/download";
        CenterMedicineListSave centerMedicineListResp = this.restTemplate.postForObject(url, null, CenterMedicineListSave.class);

        CenterMedicine centerMedicine = null;
        List<CenterMedicine> listToSave = new ArrayList<>();
        for (CenterMedicineSaveDto saveDto : centerMedicineListResp.getMedicineList()) {
            Optional<CenterMedicine> existingMedicine = ebeanServer.find(CenterMedicine.class).where()
                    .eq("ZXNM", saveDto.getZXNM())
                    .findOneOrEmpty();
            centerMedicine = existingMedicine.orElseGet(CenterMedicine::new);
            saveDto.copyProperty(centerMedicine);
            listToSave.add(centerMedicine);
        }
        ebeanServer.saveAll(listToSave);
    }

    @Transactional
    public void rebuildUploadedMedicineCache() {
        String url = this.interfaceBaseUrl + "inventory/medicine/uploaded/download";
        UploadedMedicineListSaveDto resp = this.restTemplate.postForObject(url, null, UploadedMedicineListSaveDto.class);

        String delStatement = "delete from yb.medicine_uploaded";
        SqlUpdate update = ebeanServer.createSqlUpdate(delStatement);
        ebeanServer.execute(update);

        List<UploadedMedicine> listToSave = new ArrayList<>();
        for (UploadedMedicineSaveDto medicineSaveDto : resp.getMedicineList())
            listToSave.add(medicineSaveDto.toEntity());
        ebeanServer.saveAll(listToSave);

    }


    private EntityUploadResp callInventoryUploadInterface(InventoryUploadList req) {
        String url = this.interfaceBaseUrl + "inventory/medicine/upload";
        EntityUploadResp uploadResp = this.restTemplate.postForObject(url, req, EntityUploadResp.class);
        return uploadResp;
    }

    @Transactional
    public MedicineUploadResult uploadSingleMedicine(Medicine medicine) {
        InventoryUploadReqDto reqDto = medicine.toInventoryUpload();
        List<InventoryUploadReqDto> reqDtoList = new ArrayList<>();
        reqDtoList.add(reqDto);
        InventoryUploadList req = new InventoryUploadList();
        req.setReqList(reqDtoList);
        EntityUploadResp resp = this.callInventoryUploadInterface(req);
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(medicine);
        List<MedicineUploadResult> listToSave = this.saveMedicineUploadedResp(resp, medicineList);
        return listToSave.get(0);
    }

    @Transactional
    public void uploadAllMedicine() {
        List<Medicine> medicineList = ebeanServer.find(Medicine.class).where()
                .eq("selfPay", false)
                .eq("enabled", true)
                .or()
                .eq("uploadResult", null)
                .eq("uploadResult.serverCode", null)
                .endOr().findList();
        List<InventoryUploadReqDto> reqDtoList = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            reqDtoList.add(medicine.toInventoryUpload());
        }
        InventoryUploadList req = new InventoryUploadList();
        req.setReqList(reqDtoList);
        EntityUploadResp resp = this.callInventoryUploadInterface(req);
        this.saveMedicineUploadedResp(resp, medicineList);
    }

    private List<MedicineUploadResult> saveMedicineUploadedResp(EntityUploadResp resp, List<Medicine> medicineList) {
        MedicineUploadResult result;
        List<MedicineUploadResult> listToSave = new ArrayList<>();
        for (EntityUploadResultSaveDto saveDto : resp.getEntityList()) {
            Optional<MedicineUploadResult> optionalUploadResult = this.ebeanServer.find(MedicineUploadResult.class).where()
                    .eq("medicine.uuid", saveDto.getUuid())
                    .findOneOrEmpty();
            if (optionalUploadResult.isPresent())
                result = optionalUploadResult.get();
            else {
                result = new MedicineUploadResult();
            }

            Medicine medicine = medicineList.stream().filter(m -> m.getUuid().equals(saveDto.getUuid())).findFirst().get();
            result.setMedicine(medicine);
            medicine.setUploadResult(result);

            result.setServerCode(saveDto.getServerCode());
            result.setUploadError(saveDto.getError());
            listToSave.add(result);
        }
        ebeanServer.saveAll(listToSave);
        return listToSave;
    }


    public ItemUploadResult uploadSingleItem(Item item) {
        InventoryUploadReqDto reqDto = item.toInventoryUpload();
        List<InventoryUploadReqDto> reqDtoList = new ArrayList<>();
        reqDtoList.add(reqDto);
        InventoryUploadList req = new InventoryUploadList();
        req.setReqList(reqDtoList);
        EntityUploadResp resp = this.callInventoryUploadInterface(req);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        List<ItemUploadResult> listToSave = this.saveItemUploadedResp(resp, itemList);
        ItemUploadResult result = listToSave.get(0);
        item.setUploadResult(result);
        return result;
    }

    @Transactional
    public void uploadAllItem() {
        List<Item> itemList = ebeanServer.find(Item.class).where()
                .eq("selfPay", false)
                .eq("enabled", true)
                .or()
                .eq("uploadResult", null)
                .eq("uploadResult.serverCode", null)
                .endOr()
                //.setMaxRows(10)
                .findList();
        List<InventoryUploadReqDto> reqDtoList = new ArrayList<>();
        for (Item item : itemList) {
            reqDtoList.add(item.toInventoryUpload());
        }

        InventoryUploadList req = new InventoryUploadList();
        req.setReqList(reqDtoList);
        EntityUploadResp resp = this.callInventoryUploadInterface(req);
        this.saveItemUploadedResp(resp, itemList);
    }

    private List<ItemUploadResult> saveItemUploadedResp(EntityUploadResp resp, List<Item> itemList) {
        ItemUploadResult result;
        List<ItemUploadResult> listToSave = new ArrayList<>();
        for (EntityUploadResultSaveDto saveDto : resp.getEntityList()) {
            Optional<ItemUploadResult> optionalUploadResult = this.ebeanServer.find(ItemUploadResult.class).where()
                    .eq("item.uuid", saveDto.getUuid())
                    .findOneOrEmpty();
            if (optionalUploadResult.isPresent())
                result = optionalUploadResult.get();
            else {
                result = new ItemUploadResult();
                //result.setItem(new Item(saveDto.getUuid()));
            }

            Item item = itemList.stream().filter(m -> m.getUuid().equals(saveDto.getUuid())).findFirst().get();
            result.setItem(item);
            item.setUploadResult(result);

            result.setServerCode(saveDto.getServerCode());
            result.setUploadError(saveDto.getError());
            listToSave.add(result);
        }
        ebeanServer.saveAll(listToSave);
        return listToSave;
    }

    @Transactional
    public ManufacturerUploadResp uploadManufacturer(QuickAddManufactureDto manufactureDto) {
        ManufacturerUploadReq req = new ManufacturerUploadReq();
        req.setMc(manufactureDto.getName());
        String url = this.interfaceBaseUrl + "inventory/manufacture/upload";
        ManufacturerUploadResp response = this.restTemplate.postForObject(url, req, ManufacturerUploadResp.class);
        return response;
    }


    public EntityMatchReqWrapper getPendingMatchMedicineList() {
        List<Medicine> medicineList = ebeanServer.find(Medicine.class).where()
                .eq("selfPay", false)
                .eq("enabled", true)
                .ne("uploadResult", null)
                .ne("uploadResult.serverCode", null)
                .or()
                .eq("matchedMedicine", null)
                .and()
                .ne("matchedMedicine.status", "0") //未审核
                .ne("matchedMedicine.status", "1") //审核通过
                .endAnd()
                .endOr()
                .findList();

        List<EntityMatchReqDto> reqDtoList = new ArrayList<>();
        EntityMatchReqDto reqDto = null;
        int counter = 0;
        for (Medicine medicine : medicineList) {
            reqDto = this.crateOrReturnMatchReq(counter, reqDtoList);
            counter++;
            reqDto.getXmxxy().add(medicine.toMatchReqLineDto());
        }
        EntityMatchReqWrapper matchReqWrapper = new EntityMatchReqWrapper();
        matchReqWrapper.setReqList(reqDtoList);
        return matchReqWrapper;
    }

    public EntityMatchReqDto crateOrReturnMatchReq(int counter, List<EntityMatchReqDto> reqDtoList) {
        EntityMatchReqDto reqDto = null;
        if (counter % 10 == 0) {
            reqDto = new EntityMatchReqDto();
            List<EntityMatchReqLineDto> reqLineDtoList = new ArrayList<>();
            reqDto.setSqlx("1"); //新增
            reqDto.setXmxxy(reqLineDtoList);
            reqDtoList.add(reqDto);
        } else
            reqDto = reqDtoList.get(reqDtoList.size() - 1);
        return reqDto;
    }

    public void matchMedicine(UUID medicineId) {
        if (!this.ybServiceEnabled)
            return;
        EntityMatchReqWrapper matchReqWrapper = null;
        if (medicineId == null) {
            matchReqWrapper = this.getPendingMatchMedicineList();
        } else {
            Medicine medicine = this.findById(Medicine.class, medicineId);
            matchReqWrapper = this.getMedicineMatchWrapper(medicine);
        }
        this.callMatchMedicineInterface(matchReqWrapper);
        this.downloadMatchedMedicine(matchReqWrapper);
    }

    @Transactional
    public EntityMatchReqWrapper getMedicineMatchWrapper(Medicine medicine) {
        EntityMatchReqLineDto reqLineDto = medicine.toMatchReqLineDto();
        String sqlx = "1";
        if (medicine.getMatchedMedicine() != null) {
            sqlx = "2";
            ebeanServer.delete(medicine.getMatchedMedicine());
        }
        return this.createSingleEntityMatchWrapper(reqLineDto, sqlx);

    }

    public EntityMatchReqWrapper createSingleEntityMatchWrapper(EntityMatchReqLineDto reqLineDto, String sqlx) {
        EntityMatchReqWrapper wrapper = new EntityMatchReqWrapper();
        List<EntityMatchReqDto> reqDtoList = new ArrayList<>();
        wrapper.setReqList(reqDtoList);

        EntityMatchReqDto entityMatchReqDto = new EntityMatchReqDto();
        entityMatchReqDto.setSqlx(sqlx);
        reqDtoList.add(entityMatchReqDto);
        List<EntityMatchReqLineDto> lineDtoList = new ArrayList<>();
        entityMatchReqDto.setXmxxy(lineDtoList);
        lineDtoList.add(reqLineDto);
        return wrapper;
    }


    @Transactional
    public void callMatchMedicineInterface(EntityMatchReqWrapper matchReqWrapper) {
        String url = this.interfaceBaseUrl + "yb/match/request";
        MatchResp matchResp = this.restTemplate.postForObject(url, matchReqWrapper, MatchResp.class);

        MedicineMatchUploadResult result;
        List<MedicineMatchUploadResult> listToSave = new ArrayList<>();
        for (EntityMatchUploadResultSaveDto saveDto : matchResp.getMatchRespList()) {
            result = new MedicineMatchUploadResult();
            result.setMedicine(new Medicine(saveDto.getUuid()));
            result.setStatus(saveDto.getYbscbz());
            if (result.getStatus() == null)
                result.setStatus("0");
            //系统错误
            result.setError(saveDto.getError());
            //审批失败原因
            if (!result.getStatus().equals("1") && saveDto.getSbyy() != null)
                result.setError(saveDto.getSbyy());
            listToSave.add(result);
        }
        ebeanServer.saveAll(listToSave);
    }

    @Transactional
    public void downloadMatchedMedicine(EntityMatchReqWrapper matchReqWrapper) {
        String url = this.interfaceBaseUrl + "yb/match/request/result/download";
        MatchDownloadResp downloadResp = this.restTemplate.postForObject(url, matchReqWrapper, MatchDownloadResp.class);

        MedicineMatchDownload matchedMedicine;
        List<MedicineMatchDownload> listToSave = new ArrayList<>();
        for (EntityMatchDownloadSaveDto saveDto : downloadResp.getXmxxy()) {
            Optional<MedicineMatchDownload> optionalDownloadedMedicine = this.ebeanServer.find(MedicineMatchDownload.class).where()
                    .eq("medicine.uuid", saveDto.getUuid())
                    .findOneOrEmpty();
            if (optionalDownloadedMedicine.isPresent())
                matchedMedicine = optionalDownloadedMedicine.get();
            else {
                matchedMedicine = new MedicineMatchDownload();
                matchedMedicine.setMedicine(new Medicine(saveDto.getUuid()));
            }

            matchedMedicine.setError(saveDto.getError());
            matchedMedicine.setReference(saveDto.getSpbz());
            matchedMedicine.setStatus(saveDto.getSpjg());
            //医保无返回结果，设为2无记录
            if (matchedMedicine.getStatus() == null)
                matchedMedicine.setStatus("2");
            listToSave.add(matchedMedicine);
        }
        ebeanServer.saveAll(listToSave);
    }

    public EntityMatchReqWrapper getPendingMatchItemList() {
        List<Item> itemList = ebeanServer.find(Item.class).where()
                .eq("selfPay", false)
                .eq("enabled", true)
                .ne("uploadResult", null)
                .ne("uploadResult.serverCode", null)
                .or()
                .eq("matchedItem", null)
                .and()
                .ne("matchedItem.status", "0") //未审核
                .ne("matchedItem.status", "1") //审核通过
                .endAnd()
                .endOr()
                //.eq("name", "ds1")
                //.setMaxRows(11)
                .findList();

        List<EntityMatchReqDto> reqDtoList = new ArrayList<>();
        EntityMatchReqDto reqDto = null;
        int counter = 0;
        for (Item item : itemList) {
            reqDto = this.crateOrReturnMatchReq(counter, reqDtoList);
            counter++;
            reqDto.getXmxxy().add(item.toMatchReqLineDto());
        }

        EntityMatchReqWrapper matchReqWrapper = new EntityMatchReqWrapper();
        matchReqWrapper.setReqList(reqDtoList);
        return matchReqWrapper;
    }

    public void matchItem(UUID itemId) {
        if (!this.ybServiceEnabled)
            return;

        EntityMatchReqWrapper matchReqWrapper = null;
        if (itemId == null) {
            matchReqWrapper = this.getPendingMatchItemList();
        } else {
            Item item = this.findById(Item.class, itemId);
            matchReqWrapper = this.getItemMatchWrapper(item);
        }
        this.callMatchItemInterface(matchReqWrapper);
        this.downloadMatchedItem(matchReqWrapper);
    }

    @Transactional
    public EntityMatchReqWrapper getItemMatchWrapper(Item item) {
        EntityMatchReqLineDto reqLineDto = item.toMatchReqLineDto();
        String sqlx = "1";
        if (item.getMatchedItem() != null) {
            sqlx = "2";
            ebeanServer.delete(item.getMatchedItem());
        }
        return this.createSingleEntityMatchWrapper(reqLineDto, sqlx);

    }


    @Transactional
    public void callMatchItemInterface(EntityMatchReqWrapper matchReqWrapper) {
        String url = this.interfaceBaseUrl + "yb/match/request";
        MatchResp matchResp = this.restTemplate.postForObject(url, matchReqWrapper, MatchResp.class);

        ItemMatchUploadResult result;
        List<ItemMatchUploadResult> listToSave = new ArrayList<>();
        for (EntityMatchUploadResultSaveDto saveDto : matchResp.getMatchRespList()) {
            result = new ItemMatchUploadResult();
            result.setItem(new Item(saveDto.getUuid()));
            result.setStatus(saveDto.getYbscbz());
            if (result.getStatus() == null)
                result.setStatus("0");
            //系统错误
            result.setError(saveDto.getError());
            //审批失败原因
            if (!result.getStatus().equals("1") && saveDto.getSbyy() != null)
                result.setError(saveDto.getSbyy());
            listToSave.add(result);
        }
        ebeanServer.saveAll(listToSave);
    }

    @Transactional
    public void downloadMatchedItem(EntityMatchReqWrapper matchReqWrapper) {
        String url = this.interfaceBaseUrl + "yb/match/request/result/download";
        MatchDownloadResp downloadResp = this.restTemplate.postForObject(url, matchReqWrapper, MatchDownloadResp.class);

        ItemMatchDownload matchedItem;
        List<ItemMatchDownload> listToSave = new ArrayList<>();
        for (EntityMatchDownloadSaveDto saveDto : downloadResp.getXmxxy()) {
            Optional<ItemMatchDownload> optionalDownloadedItem = this.ebeanServer.find(ItemMatchDownload.class).where()
                    .eq("item.uuid", saveDto.getUuid())
                    .findOneOrEmpty();
            if (optionalDownloadedItem.isPresent())
                matchedItem = optionalDownloadedItem.get();
            else {
                matchedItem = new ItemMatchDownload();
                matchedItem.setItem(new Item(saveDto.getUuid()));
            }

            matchedItem.setError(saveDto.getError());
            matchedItem.setReference(saveDto.getSpbz());
            matchedItem.setStatus(saveDto.getSpjg());
            //医保无返回结果，设为2无记录
            if (matchedItem.getStatus() == null)
                matchedItem.setStatus("2");
            listToSave.add(matchedItem);
        }
        ebeanServer.saveAll(listToSave);
    }

    @Transactional
    public void uploadMedia(MediaUploadReq uploadReq) {
        //String test = this.test();

        InventoryOrder order = ebeanServer.find(InventoryOrder.class).where()
                .eq("yb_Id", uploadReq.getYwdh())
                .findOne();
        String yxnr = uploadReq.getYxnr();
//        yxnr = yxnr.substring(yxnr.indexOf(",") + 1);
//        uploadReq.setYxnr(yxnr);
        //uploadReq.setYxnr(test);
        String url = this.interfaceBaseUrl + "inventory/media/upload";
        MediaUploadResp resp = this.restTemplate.postForObject(url, uploadReq, MediaUploadResp.class);

        order.setImageNumber(resp.getYxbh());
        ebeanServer.update(order);

    }

    @Transactional
    public MediaDownloadResp downloadMedia(MediaDownloadReq downloadReq) {
        //String test = this.test();
        InventoryOrder order = ebeanServer.find(InventoryOrder.class).where()
                .eq("yb_Id", downloadReq.getYwdh())
                .findOne();
        String url = this.interfaceBaseUrl + "inventory/media/download";
        return this.restTemplate.postForObject(url, downloadReq, MediaDownloadResp.class);
    }

    @Transactional
    public void uploadPatientPendingFeeInventory(UUID patientSignInId) {

        //所有费用已经上传了的
        ExpressionList<Fee> el = ebeanServer.find(Fee.class).where()
                .ne("feeUploadResult", null)
                .eq("feeInventoryUploadResult", null)
                .eq("feeStatus", FeeStatus.confirmed)
                .or()
                .eq("entityType", EntityType.medicine)
                .eq("entityType", EntityType.item)
                .endOr()
                .eq("patientSignIn.status", PatientSignInStatus.signedIn);
        if (patientSignInId != null)
            el = el.where().eq("patientSignIn.uuid", patientSignInId);

        List<Fee> pendingUploadFeeInventoryList = el.findList();
        if (pendingUploadFeeInventoryList.size() == 0)
            return;

        for (Fee fee : pendingUploadFeeInventoryList) {
            try {
                PharmacyOrder pharmacyOrder = fee.toYbPharmacyOrderDto();
                String url = this.interfaceBaseUrl + "inventory/order/pharmacy/new";
                this.restTemplate.postForObject(url, pharmacyOrder, Void.class);
                FeeInventoryUploadResult uploadResult = new FeeInventoryUploadResult();
                uploadResult.setFee(fee);
                ebeanServer.save(uploadResult);

            } catch (Exception ex) {
                //FeeInventoryUploadResult result = new FeeInventoryUploadResult();

            }
        }

    }

    public boolean anyPendingUploadFeeInventory(UUID patientSignInId) {
        return ebeanServer.find(Fee.class).where()
                .eq("feeInventoryUploadResult", null)
                .eq("feeStatus", FeeStatus.confirmed)
                .or()
                .eq("entityType", EntityType.medicine)
                .eq("entityType", EntityType.item)
                .endOr()
                .eq("patientSignIn.uuid", patientSignInId)
                .setMaxRows(1).findOneOrEmpty().isPresent();
    }


//    private String test() throws IOException, FileNotFoundException {
//        String imgFile = "C:\\Users\\Ben\\Pictures\\Screenshots\\test1.png";// 待处理的图片
//        InputStream in = null;
//        byte[] data = null;
//        String encode = null; // 返回Base64编码过的字节数组字符串
//        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        try {
//            // 读取图片字节数组
//            in = new FileInputStream(imgFile);
//            data = new byte[in.available()];
//            in.read(data);
//            encode = encoder.encode(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return encode;
//    }
}
