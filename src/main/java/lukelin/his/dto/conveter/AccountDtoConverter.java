package lukelin.his.dto.conveter;

import lukelin.his.domain.entity.account.AutoFee;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.account.Payment;
import lukelin.his.domain.enums.PatientSignIn.PaymentType;
import lukelin.his.dto.account.response.*;
import lukelin.his.dto.internal_account.FeeSummaryRespDto;
import lukelin.his.dto.mini_porgram.MiniOnGoingPrescriptionDto;
import lukelin.his.system.Utils;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountDtoConverter {
    public static List<FeeListDto> toFeeListRespDto(List<Fee> feeList) {
        List<FeeListDto> feeDtoList = new ArrayList<>();
        for (Fee fee : feeList) {
            FeeListDto dto = fee.toListDto();
            feeDtoList.add(dto);
        }
        return feeDtoList;
    }

    public static List<FeeListSummaryDto> toFeeListByTypeRespDto(List<Fee> feeList, boolean summaryByDate, boolean summaryByPatient) {
        List<FeeListSummaryDto> feeDtoList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Fee fee : feeList) {
            FeeListSummaryDto summaryDto;
            //FeeListDto dto = fee.toListDto();
            Optional<FeeListSummaryDto> optionalSummaryDto;
            optionalSummaryDto = feeDtoList.stream().
                    filter(dto ->
                            (dto.getFeeEntityId().equals(fee.getFeeEntityId())
                                    && (!summaryByPatient || dto.getPatientSignInId().equals(fee.getPatientSignIn().getUuid())))
                                    && dto.getUnitAmount().compareTo(fee.getUnitAmount()) == 0
                                    && (!summaryByDate || (sdf.format(dto.getFeeDate()).equals(sdf.format(fee.getFeeDate())))

                            )
                    )
                    .findFirst();

            if (optionalSummaryDto.isPresent()) {
                summaryDto = optionalSummaryDto.get();
                summaryDto.setQuantity(summaryDto.getQuantity().add(fee.getQuantity()));
                BigDecimal feeTotalAmount = fee.getQuantity().multiply(fee.getUnitAmount()).setScale(2, RoundingMode.HALF_UP);
                summaryDto.setTotalAmount(summaryDto.getTotalAmount().add(feeTotalAmount));
            } else {
                summaryDto = new FeeListSummaryDto();
                fee.setBaseFeeListDtoValue(summaryDto);
                summaryDto.setFeeDate(fee.getFeeDate());
                summaryDto.setFeeEntityId(fee.getFeeEntityId());
                summaryDto.setPatientSignInId(fee.getPatientSignIn().getUuid());
                summaryDto.setPatientName(fee.getPatientSignIn().getPatient().getName());
                BigDecimal totalAmount = summaryDto.getQuantity().multiply(summaryDto.getUnitAmount()).setScale(2, RoundingMode.HALF_UP);
                summaryDto.setTotalAmount(totalAmount);
                feeDtoList.add(summaryDto);
            }
        }

        for (FeeListSummaryDto summaryDto : feeDtoList) {
            summaryDto.setDisplayQuantityInfo(summaryDto.getQuantity() + summaryDto.getUomName());
        }
        return feeDtoList;
    }

    public static List<AutoFeeListDto> toAutoFeeListRespDto(List<AutoFee> autoFeeList) {
        List<AutoFeeListDto> autoFeeListDtoList = new ArrayList<>();
        for (AutoFee autoFee : autoFeeList) {
            AutoFeeListDto dto = autoFee.toListDto();
            autoFeeListDtoList.add(dto);
        }
        return autoFeeListDtoList;
    }


    public static List<MiniFeeListDto> toMiniFeeList(List<Fee> feeList) {
        List<MiniFeeListDto> miniFeeListDtoList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        for (Fee fee : feeList) {
            MiniFeeListDto dto = new MiniFeeListDto();
            dto.setUuid(fee.getUuid());
            dto.setName(fee.getDescription() + '-' + fee.getPatientSignIn().getPatient().getName());
            String dateString = formatter.format(fee.getFeeDate());
            dto.setExecutionTime(dateString);
            miniFeeListDtoList.add(dto);
        }
        return miniFeeListDtoList;
    }

    public static List<MiniOnGoingPrescriptionDto> toMiniOnGoingPrescriptionList(List<Fee> onGoingFeeList) {
        List<MiniOnGoingPrescriptionDto> dtoList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        for (Fee fee : onGoingFeeList) {
            MiniOnGoingPrescriptionDto dto = new MiniOnGoingPrescriptionDto();
            dto.setDescription(fee.getDescription() + '-' + fee.getWhoCreatedName());
            dto.setUuid(fee.getUuid());
            dto.setStartTime(formatter.format(fee.getFeeDate()));
            dto.setExpectFinishTime(formatter.format(fee.getExpectedFinishTime()));
            dto.setExecutionTime(dto.getStartTime() + '-' + dto.getExpectFinishTime());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static List<FeeSummaryRespDto> toInternalFeeSummaryDto(List<lukelin.his.domain.entity.Internal_account.Fee> allFeeList) {
        List<FeeSummaryRespDto> feeDtoList = new ArrayList<>();
        for (lukelin.his.domain.entity.Internal_account.Fee fee : allFeeList) {
            FeeSummaryRespDto summaryDto;
            Optional<FeeSummaryRespDto> optionalSummaryDto;
            optionalSummaryDto = feeDtoList.stream().
                    filter(dto ->
                            (dto.getItem().getUuid().equals(fee.getItem().getUuid())
                                    && dto.getPatientSignInId().equals(fee.getPatientSignIn().getUuid()))
                                    && dto.getUnitAmount().compareTo(fee.getUnitAmount()) == 0
                    )
                    .findFirst();

            if (optionalSummaryDto.isPresent()) {
                summaryDto = optionalSummaryDto.get();
                summaryDto.setQuantity(summaryDto.getQuantity().add(new BigDecimal(fee.getQuantity())));
                BigDecimal feeTotalAmount = new BigDecimal(fee.getQuantity()).multiply(fee.getUnitAmount()).setScale(2, RoundingMode.HALF_UP);
                summaryDto.setTotalAmount(summaryDto.getTotalAmount().add(feeTotalAmount));
            } else {
                summaryDto = new FeeSummaryRespDto();
                summaryDto.setPatientSignInId(fee.getPatientSignIn().getUuid());
                summaryDto.setPatientName(fee.getPatientSignIn().getPatient().getName());
                summaryDto.setItem(fee.getItem().toDto());
                summaryDto.setQuantity(new BigDecimal(fee.getQuantity()));
                summaryDto.setUomName(fee.getItem().getUom());
                summaryDto.setUnitAmount(fee.getUnitAmount());
                BigDecimal totalAmount = summaryDto.getQuantity().multiply(summaryDto.getUnitAmount()).setScale(2, RoundingMode.HALF_UP);
                summaryDto.setTotalAmount(totalAmount);
                feeDtoList.add(summaryDto);
            }
        }

        for (FeeSummaryRespDto summaryDto : feeDtoList) {
            summaryDto.setDisplayQuantityInfo(summaryDto.getQuantity() + summaryDto.getUomName());
            summaryDto.setDisplayUnitAmount(summaryDto.getUnitAmount().toString() + '元');
        }
        return feeDtoList;
    }

    public static List<PaymentSummaryResp> toPaymentSummaryList(List<Payment> paymentList) {

        List<PaymentSummaryResp> summaryRespList = new ArrayList<>();
        Optional<PaymentSummaryResp> optionalPaymentSummary;
        PaymentSummaryResp summaryResp;
        for (Payment payment : paymentList) {
            optionalPaymentSummary = summaryRespList.stream()
                    .filter(s -> s.getWhoCreated().equals(payment.getWhoCreatedName()))
                    .findFirst();
            if (optionalPaymentSummary.isPresent()) {
                summaryResp = optionalPaymentSummary.get();
            } else {
                summaryResp = new PaymentSummaryResp();
                summaryResp.setWhoCreated(payment.getWhoCreatedName());
                List<PaymentRespDto> detailList = new ArrayList<>();
                summaryResp.setPaymentDetailList(detailList);
                summaryRespList.add(summaryResp);
            }

            summaryResp.getPaymentDetailList().add(payment.toDto());
            BigDecimal paymentValue = payment.getAmount();
            if (payment.getPaymentType() == PaymentType.refund)
                paymentValue = paymentValue.multiply(new BigDecimal("-1"));
            if (payment.getPaymentMethod().getName().equals("微信支付"))
                summaryResp.setTotalWechatAmount(summaryResp.getTotalWechatAmount().add(paymentValue));
            else if (payment.getPaymentMethod().getName().equals("支付宝"))
                summaryResp.setTotalAliAmount(summaryResp.getTotalAliAmount().add(paymentValue));
            else if (payment.getPaymentMethod().getName().equals("现金"))
                summaryResp.setTotalCashAmount(summaryResp.getTotalCashAmount().add(paymentValue));
            else if (payment.getPaymentMethod().getName().equals("银行卡"))
                summaryResp.setTotalBankAmount(summaryResp.getTotalBankAmount().add(paymentValue));
            BigDecimal totalAmount = summaryResp.getTotalAmount().abs();
            summaryResp.setMoneyInChinese(Utils.numberToChinese(totalAmount.toString()));
        }

        Comparator<PaymentRespDto> comparator = Comparator.comparing(PaymentRespDto::getOriginPaymentDate).thenComparing(PaymentRespDto::getWhenCreated);
        for (PaymentSummaryResp summary : summaryRespList)
            summary.getPaymentDetailList().sort(comparator);
        return summaryRespList;
    }
}
