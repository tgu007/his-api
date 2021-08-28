package lukelin.his.service;

import io.ebean.ExpressionList;
import io.ebean.PagedList;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.springboot.service.BaseService;
import lukelin.his.domain.entity.Internal_account.*;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;
import lukelin.his.dto.basic.SearchCodeDto;
import lukelin.his.dto.internal_account.*;
import lukelin.his.dto.internal_account.temp.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class InternalAccountService extends BaseService {

    public PagedList<ChargeableItem> getPagedItemList(int pageNum, SearchCodeDto searchCode) {
        ExpressionList<ChargeableItem> el = this.buildItemListQuery(searchCode);
        return findPagedList(el, pageNum);
    }

    public List<ChargeableItem> getItemList(SearchCodeDto searchCode) {
        ExpressionList<ChargeableItem> el = this.buildItemListQuery(searchCode);
        return el.findList();
    }

    private ExpressionList<ChargeableItem> buildItemListQuery(SearchCodeDto searchCode) {
        ExpressionList<ChargeableItem> el = ebeanServer.find(ChargeableItem.class)
                .where();
        if (searchCode.getSearchCode() != null)
            el = el.where()
                    .like("name", "%" + searchCode.getSearchCode() + "%");

        if (searchCode.getEnabled() != null)
            el = el.where()
                    .eq("enabled", searchCode.getEnabled());
        return el;
    }

    public ChargeableItem saveItem(ChargeableItemRespDto itemSaveDto) {
        ChargeableItem item = itemSaveDto.toEntity();
        ebeanServer.save(item);
        return item;
    }

    public PagedList<AutoFeeTemp> getPagedAutoFeeList(int pageNum, PatientFilterDto patientFilter) {
        ExpressionList<AutoFeeTemp> el = ebeanServer.find(AutoFeeTemp.class).orderBy("whenCreated desc")
                .where();
        if (patientFilter.getSignInNumber() != null)
            el = el.where()
                    .like("singInNumber", "%" + patientFilter.getSignInNumber() + "%");

        if (patientFilter.getPatientInfo() != null)
            el = el.where()
                    .like("patientInfo", "%" + patientFilter.getPatientInfo() + "%");

        return findPagedList(el, pageNum);

    }

    public AutoFeeTemp saveAutoFee(AutoFeeTempSaveDto autoSaveFeeDto) {
        AutoFeeTemp autoFeeTemp = autoSaveFeeDto.toEntity();
        ebeanServer.save(autoFeeTemp);
        return autoFeeTemp;
    }


    public PagedList<FeeTemp> getPagedFeeList(int pageNum, FeeFilterDto feeFilter) {
        ExpressionList<FeeTemp> el = this.buildFeeListQuery(feeFilter);
        PagedList<FeeTemp> feePagedList = this.findPagedList(el, pageNum);
        return feePagedList;
    }

    public BigDecimal getTotalFeeAmount(FeeFilterDto feeFilter) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (!StringUtils.isBlank(feeFilter.getSignInNumber())) {
            ExpressionList<FeeTemp> el = this.buildFeeListQuery(feeFilter);
            List<FeeTemp> allFeeTempList = el.findList();
            totalAmount = allFeeTempList.stream().map(f -> f.getTotalAmount()).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        }
        return totalAmount;
    }

    private ExpressionList<FeeTemp> buildFeeListQuery(FeeFilterDto feeFilter) {
        ExpressionList<FeeTemp> el = ebeanServer.find(FeeTemp.class)
                .orderBy("feeDate desc").where();
        if (feeFilter.getStartDate() != null && feeFilter.getEndDate() != null)
            el = el.where()
                    .between("feeDate", feeFilter.getStartDate(), feeFilter.getEndDate());

        if (feeFilter.getFeeStatusList() != null && feeFilter.getFeeStatusList().size() > 0)
            el = el.where()
                    .in("feeStatus", feeFilter.getFeeStatusList());

        if (feeFilter.getItemName() != null)
            el = el.where()
                    .like("item.name", "%" + feeFilter.getItemName() + "%");

        if (feeFilter.getSignInNumber() != null)
            el = el.where()
                    .like("singInNumber", "%" + feeFilter.getSignInNumber() + "%");

        if (feeFilter.getPatientInfo() != null)
            el = el.where()
                    .like("patientInfo", "%" + feeFilter.getPatientInfo() + "%");
        return el;
    }

    public FeeTemp saveFee(FeeTempSaveDto feeTempSaveDtoo) {
        FeeTemp feeTemp = feeTempSaveDtoo.toEntity();
        ebeanServer.save(feeTemp);
        return feeTemp;
    }

    @Transactional
    public void cancelFee(UUID feeId) {
        FeeTemp feeTempToCancel = this.findById(FeeTemp.class, feeId);
        if (feeTempToCancel.getFeeStatus() != FeeStatus.confirmed)
            throw new ApiValidationException("invalid status");

        feeTempToCancel.setFeeStatus(FeeStatus.canceled);
        ebeanServer.update(feeTempToCancel);
    }

    public PagedList<PaymentTemp> getPagedPaymentList(int pageNum, PaymentFilterDto paymentFilter) {
        ExpressionList<PaymentTemp> el = this.buildPaymentListQuery(paymentFilter);
        return findPagedList(el, pageNum);
    }

    public BigDecimal getTotalPaymentAmount(PaymentFilterDto paymentFilter) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (!StringUtils.isBlank(paymentFilter.getSignInNumber())) {
            ExpressionList<PaymentTemp> el = this.buildPaymentListQuery(paymentFilter);
            List<PaymentTemp> allPaymentTempList = el.findList();
            BigDecimal totalPaidAmount = allPaymentTempList.stream().filter(p -> p.getPaymentType() != PaymentType.refund).map(PaymentTemp::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalRefundAmount = allPaymentTempList.stream().filter(p -> p.getPaymentType() == PaymentType.refund).map(PaymentTemp::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
            totalAmount = totalPaidAmount.subtract(totalRefundAmount);
        }
        return totalAmount;
    }

    private ExpressionList<PaymentTemp> buildPaymentListQuery(PaymentFilterDto paymentFilter) {
        ExpressionList<PaymentTemp> el = ebeanServer.find(PaymentTemp.class)
                .orderBy("paymentDate desc").where();
        if (paymentFilter.getSignInNumber() != null)
            el = el.where()
                    .like("singInNumber", "%" + paymentFilter.getSignInNumber() + "%");

        if (paymentFilter.getPatientInfo() != null)
            el = el.where()
                    .like("patientInfo", "%" + paymentFilter.getPatientInfo() + "%");

        if (paymentFilter.getPaymentStatusList() != null && paymentFilter.getPaymentStatusList().size() > 0)
            el = el.where()
                    .in("paymentStatus", paymentFilter.getPaymentStatusList());
        return el;
    }

    @Transactional
    public PaymentTemp savePayment(PaymentTempSaveDto paymentTempSaveDto) {
        PaymentTemp paymentTemp = paymentTempSaveDto.toEntity();
        ebeanServer.save(paymentTemp);
        if (paymentTempSaveDto.getSelectedItemIdList() != null) {
            int existingCount = ebeanServer.find(AutoFeeTemp.class).where()
                    .eq("singInNumber", paymentTempSaveDto.getSingInNumber())
                    .eq("enabled", true)
                    .findCount();
            if (existingCount > 0)
                throw new ApiValidationException("已经有自动计费存在，如要添加，从自动计费设置页面重新添加");
            for (UUID itemId: paymentTempSaveDto.getSelectedItemIdList())
            {
                ChargeableItem item = this.findById(ChargeableItem.class, itemId);
                AutoFeeTemp newAutoFeeTemp = new AutoFeeTemp();
                newAutoFeeTemp.setEnabled(true);
                newAutoFeeTemp.setItem(item);
                if(item.getDefaultQuantity() != null)
                    newAutoFeeTemp.setQuantity(item.getDefaultQuantity());
                else
                    newAutoFeeTemp.setQuantity(1);
                newAutoFeeTemp.setPatientInfo(paymentTemp.getWard() + ":" + paymentTemp.getPatientInfo());
                newAutoFeeTemp.setSingInNumber(paymentTempSaveDto.getSingInNumber());
                ebeanServer.save(newAutoFeeTemp);
            }
        }
        return paymentTemp;
    }

    @Transactional
    public void cancelPayment(UUID paymentId) {
        PaymentTemp paymentTempToCancel = this.findById(PaymentTemp.class, paymentId);
        if (paymentTempToCancel.getPaymentStatus() == PaymentStatus.canceled)
            throw new ApiValidationException("invalid status");

        paymentTempToCancel.setPaymentStatus(PaymentStatus.canceled);
        ebeanServer.update(paymentTempToCancel);
    }

    @Transactional
    public void confirmPayment(UUID paymentId) {
        PaymentTemp paymentTempToConfirm = this.findById(PaymentTemp.class, paymentId);
        if (paymentTempToConfirm.getPaymentStatus() != PaymentStatus.pending)
            throw new ApiValidationException("invalid status");

        paymentTempToConfirm.setPaymentStatus(PaymentStatus.paid);
        ebeanServer.update(paymentTempToConfirm);
    }

    public List<FeePaymentSummaryTemp> getFeePaymentSummaryList() {
        return ebeanServer.find(FeePaymentSummaryTemp.class).findList();
    }

    public PagedList<AutoFee> getHisPagedAutoFeeList(int pageNum, UUID patientSignInId) {
        ExpressionList<AutoFee> el = ebeanServer.find(AutoFee.class).orderBy("whenCreated desc")
                .where();
        el = el.where()
                .eq("patientSignIn.uuid", patientSignInId);

        return findPagedList(el, pageNum);
    }

    public AutoFee saveHisAutoFee(AutoFeeSaveDto autoSaveFeeDto) {
        AutoFee autoFee = autoSaveFeeDto.toEntity();
        ebeanServer.save(autoFee);
        return autoFee;
    }

    public PagedList<Fee> getHisPagedFeeList(int pageNum, FeeFilterDto feeFilter) {
        ExpressionList<Fee> el = this.buildHisFeeListQuery(feeFilter);
        return findPagedList(el, pageNum);
    }
    public List<Fee> getHisFeeList(FeeFilterDto feeFilter) {
        ExpressionList<Fee> el = this.buildHisFeeListQuery(feeFilter);
        return el.findList();
    }

    public BigDecimal getHisTotalFeeAmount(FeeFilterDto feeFilter) {
        ExpressionList<Fee> el = this.buildHisFeeListQuery(feeFilter);
        List<Fee> allFeeList = el.findList();
        return allFeeList.stream().map(f -> f.getTotalAmount()).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getHisTotalPaymentAmount(PaymentFilterDto paymentFilter) {
        ExpressionList<Payment> el = this.buildHisPaymentListQuery(paymentFilter);
        List<Payment> allPaymentTempList = el.findList();
        BigDecimal totalPaidAmount = allPaymentTempList.stream().filter(p -> p.getPaymentType() != PaymentType.refund).map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalRefundAmount = allPaymentTempList.stream().filter(p -> p.getPaymentType() == PaymentType.refund).map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = totalPaidAmount.subtract(totalRefundAmount);
        return totalAmount;
    }

    public Fee saveHisFee(FeeSaveDto feeSaveDto) {
        Fee fee = feeSaveDto.toEntity();
        ebeanServer.save(fee);
        return fee;
    }

    @Transactional
    public void cancelHisFee(UUID feeId) {
        Fee feeToCancel = this.findById(Fee.class, feeId);
        if (feeToCancel.getFeeStatus() != FeeStatus.confirmed)
            throw new ApiValidationException("invalid status");

        feeToCancel.setFeeStatus(FeeStatus.canceled);
        ebeanServer.update(feeToCancel);
    }

    public PagedList<Payment> getHisPagedPaymentList(int pageNum, PaymentFilterDto paymentFilter) {
        ExpressionList<Payment> el = this.buildHisPaymentListQuery(paymentFilter);
        return findPagedList(el, pageNum);
    }

    public Payment saveHisPayment(PaymentSaveDto paymentSaveDto) {
        Payment payment = paymentSaveDto.toEntity();
        ebeanServer.save(payment);
        return payment;
    }

    @Transactional
    public void cancelHisPayment(UUID paymentId) {
        Payment paymentToCancel = this.findById(Payment.class, paymentId);
        if (paymentToCancel.getPaymentStatus() == PaymentStatus.canceled)
            throw new ApiValidationException("invalid status");

        paymentToCancel.setPaymentStatus(PaymentStatus.canceled);
        ebeanServer.update(paymentToCancel);
    }

    @Transactional
    public void confirmHisPayment(UUID paymentId) {
        Payment paymentToConfirm = this.findById(Payment.class, paymentId);
        if (paymentToConfirm.getPaymentStatus() != PaymentStatus.pending)
            throw new ApiValidationException("invalid status");

        paymentToConfirm.setPaymentStatus(PaymentStatus.paid);
        ebeanServer.update(paymentToConfirm);
    }

    private ExpressionList<Fee> buildHisFeeListQuery(FeeFilterDto feeFilter) {
        ExpressionList<Fee> el = ebeanServer.find(Fee.class)
                .orderBy("feeDate desc").where();
        if (feeFilter.getStartDate() != null && feeFilter.getEndDate() != null)
            el = el.where()
                    .between("feeDate", feeFilter.getStartDate(), feeFilter.getEndDate());

        if (feeFilter.getFeeStatusList() != null && feeFilter.getFeeStatusList().size() > 0)
            el = el.where()
                    .in("feeStatus", feeFilter.getFeeStatusList());

        if (feeFilter.getItemName() != null)
            el = el.where()
                    .like("item.name", "%" + feeFilter.getItemName() + "%");

        el = el.where()
                .eq("patientSignIn.uuid", feeFilter.getPatientSignInId());
        return el;
    }

    private ExpressionList<Payment> buildHisPaymentListQuery(PaymentFilterDto paymentFilter) {
        ExpressionList<Payment> el = ebeanServer.find(Payment.class)
                .orderBy("paymentDate desc").where();

        el = el.where()
                .eq("patientSignIn.uuid", paymentFilter.getPatientSignInId());

        if (paymentFilter.getPaymentStatusList() != null && paymentFilter.getPaymentStatusList().size() > 0)
            el = el.where()
                    .in("paymentStatus", paymentFilter.getPaymentStatusList());
        return el;
    }

    public List<FeePaymentSummary> getHisFeePaymentSummaryList() {
        return ebeanServer.find(FeePaymentSummary.class).findList();
    }
}