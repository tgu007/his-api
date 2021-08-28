package lukelin.his.dto.internal_account;

import lukelin.his.domain.enums.PatientSignIn.PaymentStatus;
import lukelin.his.dto.internal_account.PatientFilterDto;

import java.util.List;

public class PaymentFilterDto extends PatientFilterDto {

    private List<PaymentStatus> paymentStatusList;

    public List<PaymentStatus> getPaymentStatusList() {
        return paymentStatusList;
    }

    public void setPaymentStatusList(List<PaymentStatus> paymentStatusList) {
        this.paymentStatusList = paymentStatusList;
    }
}
