package lukelin.his.api;

import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedEntityStockInterface;
import lukelin.his.domain.entity.account.AutoFee;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.account.Payment;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.dto.account.filter.AccountSummaryFilter;
import lukelin.his.dto.account.filter.AutoFeeFilterDto;
import lukelin.his.dto.account.filter.FeeFilterDto;
import lukelin.his.dto.account.filter.PaymentListFilter;
import lukelin.his.dto.account.request.*;
import lukelin.his.dto.account.response.*;
import lukelin.his.dto.conveter.AccountDtoConverter;
import lukelin.his.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/fee")
public class AccountApi extends BaseController {
    @Autowired
    private AccountService accountService;

    //Payment
    @PostMapping("payment/create")
    public void createPayment(@RequestBody PaymentSaveDto paymentSaveDto) {
        this.accountService.createPayment(paymentSaveDto);
    }

    @PostMapping("payment/confirm/{paymentId}")
    public void confirmPayment(@PathVariable UUID paymentId) {
        Payment existingPayment = this.accountService.findById(Payment.class, paymentId);
        PaymentStatus status = PaymentStatus.paid;
        if (existingPayment.getOriginPayment() != null) {
            status = PaymentStatus.refunded;
            existingPayment.getOriginPayment().checkIsValidRefund(existingPayment.getAmount());
        }
        accountService.updatePaymentStatus(paymentId, status);
    }

    @PostMapping("payment/cancel/{paymentId}")
    public void cancelPayment(@PathVariable UUID paymentId) {
        accountService.updatePaymentStatus(paymentId, PaymentStatus.canceled);
    }

    @PostMapping("payment/list/{patientSignInId}/{pageNum}")
    public PagedDTO<PaymentRespDto> getPatientPaymentList(@PathVariable int pageNum, @PathVariable UUID patientSignInId) {
        PagedList<Payment> list = accountService.getPatientPaymentList(patientSignInId, pageNum);
        List<PaymentRespDto> paymentRespDtoList = DtoUtils.toDtoList(list.getList());
        Comparator<PaymentRespDto> comparator = Comparator.comparing(PaymentRespDto::getOriginPaymentDate).thenComparing(PaymentRespDto::getWhenCreated);
        paymentRespDtoList.sort(comparator);
        return pagedResponse(paymentRespDtoList, list);
    }

    @PostMapping("payment/list/all/{pageNum}")
    public PagedDTO<PaymentRespDto> getAllPaymentList(@PathVariable int pageNum, @RequestBody PaymentListFilter filter) {
        PagedList<Payment> list = accountService.getAllPaymentList(filter, pageNum);
        List<PaymentRespDto> paymentRespDtoList = DtoUtils.toDtoList(list.getList());
        Comparator<PaymentRespDto> comparator = Comparator.comparing(PaymentRespDto::getOriginPaymentDate).thenComparing(PaymentRespDto::getWhenCreated);
        paymentRespDtoList.sort(comparator);
        return pagedResponse(paymentRespDtoList, list);
    }

    @PostMapping("payment/list/summary")
    public DecoratedDTO<List<PaymentSummaryResp>> getPatientPaymentList(@RequestBody AccountSummaryFilter accountSummaryFilter) {
        List<Payment> paymentList = accountService.getPaymentSummaryList(accountSummaryFilter);
        List<PaymentSummaryResp> summaryRespList = AccountDtoConverter.toPaymentSummaryList(paymentList);
        return decoratedResponse(summaryRespList);
    }

    //Fee List
    @PostMapping("{patientSignInId}/type/list")
    public DecoratedDTO<List<String>> getPatientFeeTypeList(@PathVariable UUID patientSignInId) {
        List<String> feeTypeList = accountService.getPatientFeeTypeList(patientSignInId);
        return decoratedResponse(feeTypeList);
    }

    @PostMapping("fee_type/department/list")
    public DecoratedDTO<List<String>> getDepartmentFeeTypeList(@RequestBody FeeFilterDto feeFilterDto) {
        List<String> feeTypeList = accountService.getDepartmentFeeTypeList(feeFilterDto);
        return decoratedResponse(feeTypeList);
    }

    @PostMapping("{patientSignInId}/list/simple/{pageNum}")
    public PagedDTO<FeeListDto> getFeeList(@PathVariable int pageNum, @PathVariable UUID patientSignInId, @RequestBody FeeFilterDto feeFilterDto) {
        PagedList<Fee> list = accountService.getFeePagedList(patientSignInId, pageNum, feeFilterDto);
        return pagedResponse(AccountDtoConverter.toFeeListRespDto(list.getList()), list);
    }

    @PostMapping("{patientSignInId}/list/simple/all")
    public DecoratedDTO<List<FeeListDto>> getFeeList(@PathVariable UUID patientSignInId, @RequestBody FeeFilterDto feeFilterDto) {
        List<Fee> list = accountService.getFeeList(patientSignInId, feeFilterDto);
        return decoratedResponse(AccountDtoConverter.toFeeListRespDto(list));
    }

    @PostMapping("{patientSignInId}/list/byDate")
    //public PagedDTO<FeeListByTypeDto> getFeeListByDate(@PathVariable int pageNum, @PathVariable UUID patientSignInId, @RequestBody FeeFilterDto feeFilterDto) {
    public DecoratedDTO<List<FeeListSummaryDto>> getFeeListByDate(@PathVariable UUID patientSignInId, @RequestBody FeeFilterDto feeFilterDto) {
        List<Fee> feeList = accountService.getFeeList(patientSignInId, feeFilterDto);
        return decoratedResponse(AccountDtoConverter.toFeeListByTypeRespDto(feeList, true, true));
    }

    @PostMapping("{patientSignInId}/list/byType")
    public DecoratedDTO<List<FeeListSummaryDto>> getFeeListByType(@PathVariable UUID patientSignInId, @RequestBody FeeFilterDto feeFilterDto) {
        List<Fee> feeList = accountService.getFeeList(patientSignInId, feeFilterDto);
        List<FeeListSummaryDto> dtoList = AccountDtoConverter.toFeeListByTypeRespDto(feeList, false, true);
        return decoratedResponse(dtoList);
    }

    @PostMapping("department/summary/list")
    public DecoratedDTO<List<FeeListSummaryDto>> getDepartmentFeeSummary(@RequestBody FeeFilterDto feeFilterDto) {
        List<Fee> feeList = accountService.getFeeList(null, feeFilterDto);
        List<FeeListSummaryDto> dtoList = AccountDtoConverter.toFeeListByTypeRespDto(feeList, false, false);
        return decoratedResponse(dtoList);
    }


    @PostMapping("cancel/partial")
    public void cancelPartialFee(@RequestBody PartialFeeCancelReq reqDto) {
        accountService.cancelPartialFee(reqDto);
    }

    @PostMapping("cancel")
    public void cancelFullFee(@RequestBody FullFeeCancelListReq reqDto) {
        List<FeeStatus> validStatus = new ArrayList<>();
        validStatus.add(FeeStatus.confirmed);
        List<Fee> validatedList = accountService.getValidatedStatusFeeList(reqDto.getUuidList(), validStatus);
        accountService.cancelFee(validatedList, "manual");
    }


    @PostMapping("time_adjustment")
    public void timeAdjustment(@RequestBody FeeTimeAdjust reqDto) {
        accountService.timeAdjustment(reqDto);
    }

    @PostMapping("supervisor_adjustment")
    public void supervisorAdjustment(@RequestBody SupervisorAdjust reqDto) {
        accountService.supervisorAdjustment(reqDto);
    }

    @PostMapping("save")
    public UUID createFee(@RequestBody FeeSaveDto feeSaveDto) {
        if (feeSaveDto.isAutoFee())
            return accountService.saveAutoFee(feeSaveDto).getUuid();
        else
            return accountService.saveFee(feeSaveDto).getUuid();
    }

    @PostMapping("auto/{patientSignInId}/list/{pageNum}")
    public PagedDTO<AutoFeeListDto> getAutoFeeList(@PathVariable int pageNum, @PathVariable UUID patientSignInId, @RequestBody AutoFeeFilterDto filterDto) {
        PagedList<AutoFee> list = accountService.getAutoFeeList(patientSignInId, pageNum, filterDto);
        return pagedResponse(AccountDtoConverter.toAutoFeeListRespDto(list.getList()), list);
    }

    @PostMapping("auto/enable/{autoFeeId}")
    public void enableAutoFee(@PathVariable UUID autoFeeId) {
        accountService.enableAutoFee(autoFeeId, true);
    }

    @PostMapping("auto/disable/{autoFeeId}")
    public void disableAutoFee(@PathVariable UUID autoFeeId) {
        accountService.enableAutoFee(autoFeeId, false);
    }

    @PostMapping("check/{patientSignInId}")
    public DecoratedDTO<List<FeeCheckPrescriptionDto>> feeCheck(@PathVariable UUID patientSignInId, @RequestBody FeeFilterDto filter) {
        return decoratedResponse(accountService.patientFeeCheck(patientSignInId, filter));
    }

    @PostMapping("invoice_number/current")
    public DecoratedDTO<String> getCurrentInvoiceNumber() {
        return decoratedResponse(this.accountService.returnCurrentInvoiceNumber());
    }

    @PostMapping("invoice/generate/{patientSignInId}/{invoiceNumber}")
    public DecoratedDTO<InvoiceDto> generateInvoice(@PathVariable UUID patientSignInId, @PathVariable String invoiceNumber) {
        return decoratedResponse(this.accountService.generateInvoice(patientSignInId, invoiceNumber));
    }

    @PostMapping("invoice/settle/hy/summary/{patientSignInId}")
    public DecoratedDTO<SettlementHYSummaryResp> getSettlementHYSummary(@PathVariable UUID patientSignInId) {
        return decoratedResponse(this.accountService.getSettlementHYSummary(patientSignInId));
    }

    @PostMapping("auto/{autoFeeId}/manual_run")
    public void autoFeeManualRun(@PathVariable UUID autoFeeId) {
        this.accountService.autoFeeManualRun(autoFeeId);
    }

    @PostMapping("daily_run/manual")
    public void dailyJobManualRun() {
        this.accountService.feeAutoRun();
    }
}
