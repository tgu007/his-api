package lukelin.his.api;

import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.entity.yb.hy.SettlementHY;
import lukelin.his.dto.account.response.FeeListDto;
import lukelin.his.dto.basic.SearchCodeDto;
import lukelin.his.dto.signin.response.PatientSignInRespDto;
import lukelin.his.dto.yb.*;
import lukelin.his.dto.yb.SettlementPaymentRequest.SettlementPaymentRequest;
import lukelin.his.dto.yb.inventory.req.MediaDownloadReq;
import lukelin.his.dto.yb.inventory.req.MediaUploadReq;
import lukelin.his.dto.yb.inventory.resp.CenterMedicineRespDto;
import lukelin.his.dto.yb.inventory.resp.MediaDownloadResp;
import lukelin.his.dto.yb.req.*;
import lukelin.his.dto.yb.req.settlement.PreSettlementReq;
import lukelin.his.dto.yb.req.settlement.SettlementPaymentFilter;
import lukelin.his.dto.yb.resp.*;
import lukelin.his.dto.yb_drg.DrgInitPramDto;
import lukelin.his.dto.yb_drg.DrgMedicalRecordInfo;
import lukelin.his.dto.yb_drg.DrgRecordRespDto;
import lukelin.his.dto.yb_drg.DrgRecordSaveDto;
import lukelin.his.dto.yb_hy.Req.SettlementValidationDetail;
import lukelin.his.dto.yb_hy.Req.SettlementValidationOverall;
import lukelin.his.dto.yb_hy.Resp.SettlementResp;
import lukelin.his.service.YBInventoryService;
import lukelin.his.service.YBService;
import lukelin.his.service.YBServiceHY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping("api/yb")
@RequestMapping("yb")
public class YBApi extends BaseController {
    @Autowired
    private YBService ybService;

    @Autowired
    private YBServiceHY ybServiceHy;

    @Autowired
    private YBInventoryService ybInventoryService;


    @PostMapping("medicine/center/download")
    public void downloadCenterMedicine() {
        if (enableYBService)
            this.ybInventoryService.downloadCenterMedicine();
        else if (enableHYYBService)
            this.ybServiceHy.downloadCenterMedicine();
    }


    @PostMapping("treatment/center/download")
    public void downloadCenterTreatment() {
        if (enableYBService)
            this.ybService.downloadCenterTreatment();
        else if (enableHYYBService)
            this.ybServiceHy.downloadCenterTreatment();

    }


    @PostMapping("medicine/list/{pageNum}/{pageSize}")
    public PagedDTO<CenterMedicineRespDto> loadCenterMedicineList(@PathVariable int pageNum, @RequestBody SearchCodeDto searchCodeDto, @PathVariable int pageSize) {
        PagedList<CenterMedicine> medicineList = this.ybService.getPagedCenterMedicineList(searchCodeDto, pageNum, pageSize);
        return pagedResponse(DtoUtils.toDtoList(medicineList.getList()), medicineList);
    }

    @PostMapping("treatment/list/{pageNum}/{pageSize}")
    public PagedDTO<CenterTreatmentRespDto> loadCenterTreatmentList(@PathVariable int pageNum, @RequestBody SearchCodeDto searchCodeDto, @PathVariable int pageSize) {
        PagedList<CenterTreatment> treatmentList = this.ybService.getPagedCenterTreatmentList(searchCodeDto, pageNum, pageSize);
        return pagedResponse(DtoUtils.toDtoList(treatmentList.getList()), treatmentList);
    }

    @PostMapping("medicine/uploaded/download")
    public void rebuildUploadedMedicineCache() {
        this.ybInventoryService.rebuildUploadedMedicineCache();
    }

    @PostMapping("treatment/uploaded/download")
    public void rebuildUploadedTreatmentCache() {
        this.ybService.rebuildUploadedTreatmentCache();
    }

    @PostMapping("treatment/upload/all")
    public void uploadAllTreatment() {
        this.ybService.uploadAllTreatment();
    }

    @PostMapping("medicine/upload/all")
    public void uploadAllMedicine() {
        this.ybInventoryService.uploadAllMedicine();
    }

    @PostMapping("item/upload/all")
    public void uploadAllItem() {
        this.ybInventoryService.uploadAllItem();
    }

    @PostMapping("medicine/match")
    public void matchAllMedicine() {
        this.ybInventoryService.matchMedicine(null);
    }

    @PostMapping("medicine/match/{medicineId}")
    public void matchSingleMedicine(@PathVariable UUID medicineId) {
        if (enableYBService)
            this.ybInventoryService.matchMedicine(medicineId);
        else if (enableHYYBService)
            this.ybServiceHy.matchMedicine(medicineId);

    }

    @PostMapping("item/match")
    public void matchItem() {
        this.ybInventoryService.matchItem(null);

    }

    @PostMapping("item/match/{itemId}")
    public void matchSingleItem(@PathVariable UUID itemId) {

        if (enableYBService)
            this.ybInventoryService.matchItem(itemId);
        else if (enableHYYBService)
            this.ybServiceHy.matchItem(itemId);
    }


    @PostMapping("treatment/match")
    public void matchTreatment() {
        this.ybService.matchTreatment(null);
    }

    @PostMapping("treatment/match/{treatmentId}")
    public void matchTreatment(@PathVariable UUID treatmentId) {

        if (enableYBService)
            this.ybService.matchTreatment(treatmentId);
        else if (enableHYYBService)
            this.ybServiceHy.matchTreatment(treatmentId);

    }

    @PostMapping("patient/ic_card/read")
    public ICCardResponse readIcCard(@RequestBody ClientIpInfo clientIpInfo) {
        return this.ybService.readIcCard(clientIpInfo);
    }

    @PostMapping("patient/insurance_to_self/{patientSignInId}")
    public DecoratedDTO<PatientSignInRespDto> insuranceToSelf(@PathVariable UUID patientSignInId) {
        return decoratedResponse(this.ybService.insuranceToSelf(patientSignInId).toDto());
    }

    @PostMapping("patient/self_to_insurance/{patientSignInId}")
    public DecoratedDTO<PatientSignInRespDto> selfToInsurance(@PathVariable UUID patientSignInId, @RequestBody ClientIpInfo clientIpInfo) {
        return decoratedResponse(this.ybService.selfToInsurance(patientSignInId, clientIpInfo).toDto());
    }

    @PostMapping("patient/sign_in/fee/pending_upload/list/{patientSignInId}")
    public DecoratedDTO<FeeUploadReq> getPatientPendingUploadFeeList(@PathVariable UUID patientSignInId) {
        PatientSignIn patientSignIn = this.ybService.findById(PatientSignIn.class, patientSignInId);
        if (patientSignIn.selfPay())
            throw new ApiValidationException("invalid patient type");
        List<Fee> pendingUploadFeeList = this.ybService.getPatientPendingUploadFee(patientSignInId);
        FeeUploadReq feeUploadReq = patientSignIn.toUploadDto(pendingUploadFeeList);
        return decoratedResponse(feeUploadReq);
    }

    @PostMapping("patient/sign_in/fee/upload/{patientSignInId}")
    public void uploadPatientFee(@PathVariable UUID patientSignInId) {
        if (enableYBService) {
            this.ybService.uploadPatientPendingFee(patientSignInId);
            this.ybInventoryService.uploadPatientPendingFeeInventory(patientSignInId);
        } else if (enableHYYBService)
            this.ybServiceHy.uploadPatientPendingFee(patientSignInId);

    }


    @PostMapping("patient/sign_in/fee/upload_result/save")
    public void saveFeeUploadResult(@RequestBody FeeUploadResultDto result) {
        this.ybService.saveFeeUploadResult(result);
    }

    @PostMapping("patient/sign_in/fee/all/upload")
    public void uploadAllFee() {
        this.ybService.uploadAllFee();
    }

    @PostMapping("medicine/pharmacy_order/all/upload")
    public void uploadAllPharmacyOrder() {
        this.ybInventoryService.uploadAllPharmacyOrder();
    }

    @PostMapping("patient/sign_in/fee/delete/{patientSignInId}")
    public void deleteAllSignInFee(@PathVariable UUID patientSignInId) {
        if (enableYBService)
            this.ybService.deleteAllSignInFee(patientSignInId);
        else if (enableHYYBService)
            this.ybServiceHy.deleteAllSignInFee(patientSignInId);

    }

    @PostMapping("patient/sign_in/settle/{patientSignInId}")
    public DecoratedDTO<SettlementResp> settle(@PathVariable UUID patientSignInId, @RequestBody ClientIpInfo clientIpInfo) {
//        Settlement settlement = null;
//        if (enableYBService)
//            settlement = this.ybService.finalSettle(patientSignInId, clientIpInfo);
//        else if(enableHYYBService)
//            settlement = this.ybServiceHy.finalSettle(patientSignInId);
        SettlementResp settlementResp = this.ybServiceHy.finalSettle(patientSignInId);
        return decoratedResponse(settlementResp);
    }

    @PostMapping("patient/sign_in/settle/upload/{patientSignInId}")
    public String uploadSettlement(@PathVariable UUID patientSignInId) {
        return this.ybServiceHy.uploadSettlement(patientSignInId);
    }

    @PostMapping("patient/sign_in/settle/self/{patientSignInId}")
    public DecoratedDTO<SettlementResp> selfSettle(@PathVariable UUID patientSignInId) {
        SettlementHY settlement = this.ybService.finalSelfSettle(patientSignInId);
        return decoratedResponse(settlement.toSettlementDto());
    }

    @PostMapping("patient/sign_in/settlement/get/{patientSignInId}")
    public DecoratedDTO<SettlementDto> getPatientSignInSettlement(@PathVariable UUID patientSignInId) {
        Settlement settlement = this.ybService.getPatientSignInSettlement(patientSignInId);
        SettlementDto settlementDto = null;
        if (settlement != null)
            settlementDto = settlement.toSettlementDto();
        return decoratedResponse(settlementDto);
    }

    @PostMapping("manufacturer/medicine/initialize")
    public void initializeMedicineManufacturer() {
        this.ybService.initializeMedicineManufacturer();
    }

    @PostMapping("manufacturer/item/initialize")
    public void initializeItemManufacturer() {
        this.ybService.initializeItemManufacturer();
    }

    @PostMapping("patient/sign_in/pre_settle/download")
    public DecoratedDTO<SettlementResp> preSettlement(@RequestBody PreSettlementReq req) {
//        PreSettlement preSettlement = null;
//        if (enableYBService)
//            preSettlement= this.ybService.preSettle(req);
//        else if(enableHYYBService)
        SettlementResp preSettlement = this.ybServiceHy.preSettle(req.getPatientSignInId());

        return decoratedResponse(preSettlement);
    }

    @PostMapping("patient/fee/download/{patientSignInId}")
    public void downloadFee(@PathVariable UUID patientSignInId) {
        this.ybService.downloadFee(patientSignInId);
    }

    @PostMapping("patient/fee/download/list/{pageNumber}")
    public PagedDTO<FeeDownLoadLineRespDto> getDownloadFeeList(@RequestBody FeeDownloadFilter filter, @PathVariable Integer pageNumber) {
        PagedList<FeeDownloadLine> feeDownloadLineList = this.ybService.getDownloadedFeeList(filter, pageNumber);
        return pagedResponse(DtoUtils.toDtoList(feeDownloadLineList.getList()), feeDownloadLineList);
    }

    @PostMapping("patient/fee/download/cancel/{feeId}")
    public void deleteCenterFee(@PathVariable UUID feeId) {
        this.ybService.deleteUploadedFee(feeId);
    }

    @PostMapping("warehouse/initialize")
    public void initializeWarehouse() {
        this.ybInventoryService.initializeWareHouse();
    }

    @PostMapping("media/upload")
    public void uploadMedia(@RequestBody MediaUploadReq uploadReq) {
        this.ybInventoryService.uploadMedia(uploadReq);
    }

    @PostMapping("media/download")
    public DecoratedDTO<MediaDownloadResp> uploadMedia(@RequestBody MediaDownloadReq downloadReq) {
        return decoratedResponse(this.ybInventoryService.downloadMedia(downloadReq));
    }


    @PostMapping("doctor/agreement/save")
    public void saveDoctorAgreement(@RequestBody DoctorAgreementSaveDto saveDto) {
        this.ybService.saveDoctorAgreement(saveDto);
    }

    @Value("${uploadYBPatient}")
    private Boolean enableYBService;

    @Value("${enableHYYB}")
    private Boolean enableHYYBService;

    @PostMapping("patient/sign_in/cancel/{signInId}")
    public void cancelYBSignIn(@PathVariable UUID signInId, @RequestBody ClientIpInfo clientIpInfo) {
        PatientSignIn patientSignIn = this.ybService.findById(PatientSignIn.class, signInId);
        if (enableYBService)
            this.ybService.cancelYBSignIn(patientSignIn, clientIpInfo);
        else if (enableHYYBService)
            this.ybServiceHy.cancelYBSignIn(patientSignIn);

    }

    @PostMapping("patient/sign_in/self/signIn/{signInId}")
    public void selfYBSignIn(@PathVariable UUID signInId) {
        PatientSignIn patientSignIn = this.ybService.findById(PatientSignIn.class, signInId);
        this.ybService.selfSignIn(patientSignIn);
    }

    @PostMapping("patient/sign_in/record/save")
    public DecoratedDTO<SignInRecordRespDto> saveYBSignInRecord(@RequestBody SignInRecordRespDto saveDto) {
        YBSignInRecord signInRecord = this.ybService.saveSignInRecord(saveDto);
        return decoratedResponse(signInRecord.toDto());
    }

    @PostMapping("icd9/list/{pageNum}/{pageSize}")
    public PagedDTO<ICD9RespDto> getIcd9PagedList(@RequestBody SearchCodeDto searchCodeDto, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PagedList<ICD9> icd9List = ybService.getPagedIcd9List(searchCodeDto, pageNum, pageSize);
        return pagedResponse(DtoUtils.toDtoList(icd9List.getList()), icd9List);
    }

    @PostMapping("drg_record/init_pram")
    public DecoratedDTO<DrgInitPramDto> getDrgInitialPram() {
        return decoratedResponse(ybService.getDrgRecordInitPram());
    }

    @PostMapping("drg_record/medical_record_info/{signInId}")
    public DecoratedDTO<DrgMedicalRecordInfo> getDrgMedicalRecordInfo(@PathVariable UUID signInId) {
        return decoratedResponse(this.ybService.getDrgMedicalRecordInfo(signInId));
    }

    @PostMapping("drg_record/save")
    public DecoratedDTO<UUID> saveDrgRecord(@RequestBody DrgRecordSaveDto saveDto) {
        return decoratedResponse(this.ybService.saveDrgRecord(saveDto));
    }

    @PostMapping("drg_record/{drgRecordId}")
    public DecoratedDTO<DrgRecordRespDto> getDrgRecord(@PathVariable UUID drgRecordId) {
        return decoratedResponse(this.ybService.getDrgRecord(drgRecordId).toDto());
    }

    @PostMapping("drg_record/upload/{drgRecordId}")
    public DecoratedDTO<DrgRecordRespDto> uploadDrgRecord(@PathVariable UUID drgRecordId) {
        return decoratedResponse(this.ybService.uploadDrgRecord(drgRecordId).toDto());
    }

    @PostMapping("drg_record/cancel/{drgRecordId}")
    public DecoratedDTO<DrgRecordRespDto> cancelDrgRecord(@PathVariable UUID drgRecordId) {
        return decoratedResponse(this.ybService.cancelUploadedDrgRecord(drgRecordId).toDto());
    }

    @PostMapping("patient/fee/has_download_all/{patientSignInId}")
    public DecoratedDTO<Boolean> allFeeValidDownloaded(@PathVariable UUID patientSignInId) {
        return decoratedResponse(this.ybService.allFeeDownloadedFromYB(patientSignInId));
    }

    @PostMapping("patient/fee/all_valid/{patientSignInId}")
    public DecoratedDTO<Boolean> allFeeValid(@PathVariable UUID patientSignInId) {
        return decoratedResponse(this.ybService.allFeeValidFromYB(patientSignInId));
    }

    @PostMapping("settlement/payment/request")
    public DecoratedDTO<SettlementPaymentRequest> calculateSettlementPayment(@RequestBody SettlementPaymentFilter dateFilter) {
        return decoratedResponse(this.ybService.calculateSettlementPayment(dateFilter));
    }

    @PostMapping("fee/cancel/{feeId}")
    public DecoratedDTO<FeeListDto> cancelFee(@PathVariable UUID feeId) {
        Fee feeToCancel = this.ybService.findById(Fee.class, feeId);
        if (enableYBService)
            this.ybService.cancelFee(feeToCancel);
        else if (enableHYYBService)
            this.ybServiceHy.cancelFee(feeToCancel);

        Fee feeCanceled = this.ybService.findById(Fee.class, feeId);
        return decoratedResponse(feeCanceled.toListDto());
    }

    @PostMapping("settlement/validate/overall")
    public void validateSettlementSummary(@RequestBody SettlementValidationOverall settlementValidationOverall) {
        if (enableHYYBService)
            this.ybServiceHy.validateSettlementSummary(settlementValidationOverall);
    }

    @PostMapping("settlement/validate/detail")
    public void validateSettlementSummaryDetail(@RequestBody SettlementValidationDetail settlementValidationDetail) {
        if (enableHYYBService)
            this.ybServiceHy.validateSettlementSummaryDetail(settlementValidationDetail);
    }
}
