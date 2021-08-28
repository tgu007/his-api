package lukelin.his.api;

import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.Internal_account.*;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.dto.basic.SearchCodeDto;
import lukelin.his.dto.conveter.AccountDtoConverter;
import lukelin.his.dto.internal_account.*;
import lukelin.his.dto.internal_account.temp.*;
import lukelin.his.service.InternalAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/internal_account")
public class InternalAccountApi extends BaseController {
    @Autowired
    private InternalAccountService internalAccountService;

    @PostMapping("item/list/{pageNum}")
    public PagedDTO<ChargeableItemRespDto> getPagedItemList(@PathVariable int pageNum, @RequestBody SearchCodeDto searchCode) {
        PagedList<ChargeableItem> list = internalAccountService.getPagedItemList(pageNum, searchCode);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @PostMapping("item/list")
    public DecoratedDTO<List<ChargeableItemRespDto>> getPagedItemList(@RequestBody SearchCodeDto searchCode) {
        List<ChargeableItem> list = internalAccountService.getItemList(searchCode);
        return decoratedResponse(DtoUtils.toDtoList(list));
    }


    @PostMapping("item/save")
    public DecoratedDTO<ChargeableItemRespDto> saveItem(@RequestBody ChargeableItemRespDto itemSaveDto) {
        return decoratedResponse(internalAccountService.saveItem(itemSaveDto).toDto());
    }

    @PostMapping("auto_fee/list/{pageNum}")
    public PagedDTO<AutoFeeTempRespDto> getPagedAutoFeeList(@PathVariable int pageNum, @RequestBody PatientFilterDto patientFilter) {
        PagedList<AutoFeeTemp> list = internalAccountService.getPagedAutoFeeList(pageNum, patientFilter);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }


    @PostMapping("auto_fee/save")
    public DecoratedDTO<AutoFeeTempRespDto> saveItem(@RequestBody AutoFeeTempSaveDto autoSaveFeeDto) {
        return decoratedResponse(internalAccountService.saveAutoFee(autoSaveFeeDto).toDto());
    }

    @PostMapping("fee/list/{pageNum}")
    public DecoratedDTO<FeeListTempRespDto> getPagedFeeList(@PathVariable int pageNum, @RequestBody FeeFilterDto feeFilter) {
        PagedList<FeeTemp> list = internalAccountService.getPagedFeeList(pageNum, feeFilter);
        BigDecimal totalAmount = internalAccountService.getTotalFeeAmount(feeFilter);
        FeeListTempRespDto dto = new FeeListTempRespDto();
        dto.setTotalAmount(totalAmount);
        dto.setFeeList(pagedResponse(DtoUtils.toDtoList(list.getList()), list));
        return decoratedResponse(dto);
    }


    @PostMapping("fee/save")
    public DecoratedDTO<FeeRespTempDto> saveFee(@RequestBody FeeTempSaveDto feeTempSaveDto) {
        return decoratedResponse(internalAccountService.saveFee(feeTempSaveDto).toDto());
    }

    @PostMapping("fee/cancel/{feeId}")
    public void cancelFee(@PathVariable UUID feeId) {
        internalAccountService.cancelFee(feeId);
    }

    @PostMapping("payment/list/{pageNum}")
    public DecoratedDTO<PaymentListTempRespDto> getPagedPaymentList(@PathVariable int pageNum, @RequestBody PaymentFilterDto paymentFilter) {
        PagedList<PaymentTemp> list = internalAccountService.getPagedPaymentList(pageNum, paymentFilter);
        FeeFilterDto feeFilter = new FeeFilterDto();
        feeFilter.setSignInNumber(paymentFilter.getSignInNumber());
        feeFilter.setPatientInfo(paymentFilter.getPatientInfo());
        List<FeeStatus> feeStatusList = new ArrayList<>();
        feeStatusList.add(FeeStatus.confirmed);
        feeFilter.setFeeStatusList(feeStatusList);
        BigDecimal totalFeeAmount = internalAccountService.getTotalFeeAmount(feeFilter);
        BigDecimal totalPaidAmount = internalAccountService.getTotalPaymentAmount(paymentFilter);
        PaymentListTempRespDto dto = new PaymentListTempRespDto();
        dto.setTotalFeeAmount(totalFeeAmount);
        dto.setTotalPaidAmount(totalPaidAmount);
        dto.setPaymentList(pagedResponse(DtoUtils.toDtoList(list.getList()), list));
        return decoratedResponse(dto);
    }


    @PostMapping("payment/save")
    public DecoratedDTO<PaymentTempRespDto> savePayment(@RequestBody PaymentTempSaveDto paymentTempSaveDto) {
        return decoratedResponse(internalAccountService.savePayment(paymentTempSaveDto).toDto());
    }

    @PostMapping("payment/cancel/{paymentId}")
    public void cancelPayment(@PathVariable UUID paymentId) {
        internalAccountService.cancelPayment(paymentId);
    }

    @PostMapping("payment/confirm/{paymentId}")
    public void confirmPayment(@PathVariable UUID paymentId) {
        internalAccountService.confirmPayment(paymentId);
    }

    @PostMapping("fee/payment/summary")
    public DecoratedDTO<List<FeePaymentSummaryTemp>> getFeePaymentSummaryList() {
        return decoratedResponse(internalAccountService.getFeePaymentSummaryList());
    }

    @PostMapping("his/auto_fee/list/{pageNum}/{patientSignInId}")
    public PagedDTO<AutoFeeRespDto> getPagedAutoFeeList(@PathVariable int pageNum, @PathVariable UUID patientSignInId) {
        PagedList<AutoFee> list = internalAccountService.getHisPagedAutoFeeList(pageNum, patientSignInId);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }


    @PostMapping("his/auto_fee/save")
    public DecoratedDTO<AutoFeeRespDto> saveAutoFee(@RequestBody AutoFeeSaveDto autoSaveFeeDto) {
        return decoratedResponse(internalAccountService.saveHisAutoFee(autoSaveFeeDto).toDto());
    }

    @PostMapping("his/fee/list/{pageNum}")
    public DecoratedDTO<FeeListRespDto> getHisPagedFeeList(@PathVariable int pageNum,  @RequestBody FeeFilterDto feeFilter) {
        PagedList<Fee> list = internalAccountService.getHisPagedFeeList(pageNum, feeFilter);
        BigDecimal totalAmount = internalAccountService.getHisTotalFeeAmount(feeFilter);
        FeeListRespDto dto = new FeeListRespDto();
        dto.setTotalAmount(totalAmount);
        dto.setFeeList(pagedResponse(DtoUtils.toDtoList(list.getList()), list));
        return decoratedResponse(dto);
    }


    @PostMapping("his/fee/save")
    public DecoratedDTO<FeeRespDto> saveFee(@RequestBody FeeSaveDto feeSaveDto) {
        return decoratedResponse(internalAccountService.saveHisFee(feeSaveDto).toDto());
    }

    @PostMapping("his/fee/cancel/{feeId}")
    public void cancelHisFee(@PathVariable UUID feeId) {
        internalAccountService.cancelHisFee(feeId);
    }

    @PostMapping("his/payment/list/{pageNum}")
    public DecoratedDTO<PaymentListRespDto> getHisPagedPaymentList(@PathVariable int pageNum,   @RequestBody PaymentFilterDto paymentFilter) {
        PagedList<Payment> list = internalAccountService.getHisPagedPaymentList(pageNum, paymentFilter);
        FeeFilterDto feeFilter = new FeeFilterDto();
        feeFilter.setPatientSignInId(paymentFilter.getPatientSignInId());
        List<FeeStatus> feeStatusList = new ArrayList<>();
        feeStatusList.add(FeeStatus.confirmed);
        feeFilter.setFeeStatusList(feeStatusList);
        BigDecimal totalFeeAmount = internalAccountService.getHisTotalFeeAmount(feeFilter);

        BigDecimal totalPaidAmount = internalAccountService.getHisTotalPaymentAmount(paymentFilter);
        PaymentListRespDto dto = new PaymentListRespDto();
        dto.setTotalFeeAmount(totalFeeAmount);
        dto.setTotalPaidAmount(totalPaidAmount);
        dto.setPaymentList(pagedResponse(DtoUtils.toDtoList(list.getList()), list));
        return decoratedResponse(dto);
    }

    @PostMapping("his/payment/save")
    public DecoratedDTO<PaymentRespDto> savePayment(@RequestBody PaymentSaveDto paymentSaveDto) {
        return decoratedResponse(internalAccountService.saveHisPayment(paymentSaveDto).toDto());
    }

    @PostMapping("his/payment/cancel/{paymentId}")
    public void cancelHisPayment(@PathVariable UUID paymentId) {
        internalAccountService.cancelHisPayment(paymentId);
    }

    @PostMapping("his/payment/confirm/{paymentId}")
    public void confirmHisPayment(@PathVariable UUID paymentId) {
        internalAccountService.confirmHisPayment(paymentId);
    }

    @PostMapping("his/fee/payment/summary")
    public DecoratedDTO<List<FeePaymentSummaryRespDto>> getHisFeePaymentSummaryList() {
        List<FeePaymentSummary> feePaymentList = internalAccountService.getHisFeePaymentSummaryList();
        return decoratedResponse(DtoUtils.toDtoList(feePaymentList));
    }

    @PostMapping("fee/{patientSignInId}/list/summary")
    public DecoratedDTO<List<FeeSummaryRespDto>> getHisFeeSummary(@PathVariable UUID patientSignInId) {
        FeeFilterDto feeFilterDto = new FeeFilterDto();
        feeFilterDto.setPatientSignInId(patientSignInId);
        List<FeeStatus> feeStatusList = new ArrayList<>();
        feeStatusList.add(FeeStatus.confirmed);
        feeFilterDto.setFeeStatusList(feeStatusList);
        List<Fee> allFeeList = internalAccountService.getHisFeeList(feeFilterDto);
        return decoratedResponse(AccountDtoConverter.toInternalFeeSummaryDto(allFeeList));
    }
}
