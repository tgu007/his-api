package lukelin.his.dto.yb.SettlementPaymentRequest;

import java.util.*;
import java.math.BigDecimal;

public class SettlementPaymentRequest {

    public SettlementPaymentRequest() {
        this.accountList = new ArrayList<>();
        selfAccount = new SettlementAccount("selfAccount", "个人账户");
        tcAccount = new SettlementAccount("tcAccount", "统筹基金");
        civilAccount = new SettlementAccount("civilAccount", " 公务员补助");
        seriousDiseaseAccount = new SettlementAccount("seriousDiseaseAccount", "大病保险");
        birthAccount = new SettlementAccount("birthAccount", "生育医疗");
        medicalSubsidyAccount = new SettlementAccount("medicalSubsidyAccount", "医疗救助");
        supplementInsuranceAccount = new SettlementAccount("supplementInsuranceAccount", "补充医疗");
        specialCareSubsidy = new SettlementAccount("specialCareSubsidy", "优抚");
        accountList.add(selfAccount);
        accountList.add(tcAccount);
        accountList.add(civilAccount);
        accountList.add(seriousDiseaseAccount);
        accountList.add(birthAccount);
        accountList.add(medicalSubsidyAccount);
        accountList.add(supplementInsuranceAccount);
        accountList.add(specialCareSubsidy);
    }

    private final List<SettlementAccount> accountList;

    public List<SettlementAccount> getAccountList() {
        return accountList;
    }

    private SettlementAccount selfAccount;

    private SettlementAccount tcAccount;

    private SettlementAccount civilAccount;

    private SettlementAccount seriousDiseaseAccount;

    private SettlementAccount birthAccount;

    private SettlementAccount medicalSubsidyAccount;

    private SettlementAccount supplementInsuranceAccount;

    private SettlementAccount specialCareSubsidy;

    private BigDecimal totalWorkingCivilPayment = BigDecimal.ZERO;

    private BigDecimal totalRetireCivilPayment = BigDecimal.ZERO;

    private BigDecimal totalWorkingCompanyPayment = BigDecimal.ZERO;

    private BigDecimal totalRetireCompanyPayment = BigDecimal.ZERO;

    private BigDecimal totalWorkingOtherPayment = BigDecimal.ZERO;

    private BigDecimal totalRetireOtherPayment = BigDecimal.ZERO;

    private BigDecimal totalWorkingPayment = BigDecimal.ZERO;

    private BigDecimal totalRetirePayment = BigDecimal.ZERO;

    private BigDecimal totalPayment = BigDecimal.ZERO;

    public SettlementAccount getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(SettlementAccount selfAccount) {
        this.selfAccount = selfAccount;
    }

    public SettlementAccount getTcAccount() {
        return tcAccount;
    }

    public void setTcAccount(SettlementAccount tcAccount) {
        this.tcAccount = tcAccount;
    }

    public SettlementAccount getCivilAccount() {
        return civilAccount;
    }

    public void setCivilAccount(SettlementAccount civilAccount) {
        this.civilAccount = civilAccount;
    }

    public SettlementAccount getSeriousDiseaseAccount() {
        return seriousDiseaseAccount;
    }

    public void setSeriousDiseaseAccount(SettlementAccount seriousDiseaseAccount) {
        this.seriousDiseaseAccount = seriousDiseaseAccount;
    }

    public SettlementAccount getBirthAccount() {
        return birthAccount;
    }

    public void setBirthAccount(SettlementAccount birthAccount) {
        this.birthAccount = birthAccount;
    }

    public SettlementAccount getMedicalSubsidyAccount() {
        return medicalSubsidyAccount;
    }

    public void setMedicalSubsidyAccount(SettlementAccount medicalSubsidyAccount) {
        this.medicalSubsidyAccount = medicalSubsidyAccount;
    }

    public SettlementAccount getSupplementInsuranceAccount() {
        return supplementInsuranceAccount;
    }

    public void setSupplementInsuranceAccount(SettlementAccount supplementInsuranceAccount) {
        this.supplementInsuranceAccount = supplementInsuranceAccount;
    }

    public SettlementAccount getSpecialCareSubsidy() {
        return specialCareSubsidy;
    }

    public void setSpecialCareSubsidy(SettlementAccount specialCareSubsidy) {
        this.specialCareSubsidy = specialCareSubsidy;
    }

    public BigDecimal getTotalWorkingCivilPayment() {
        return this.accountList.stream().map(a -> a.getWorkingPayment().getCivilTypePayment())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//                this.selfAccount.getWorkingPayment().getCivilTypePayment()
//                .add(this.tcAccount.getWorkingPayment().getCivilTypePayment())
//                .add(this.civilAccount.getWorkingPayment().getCivilTypePayment())
//                .add(this.seriousDiseaseAccount.getWorkingPayment().getCivilTypePayment())
//                .add(this.birthAccount.getWorkingPayment().getCivilTypePayment())
//                .add(this.medicalSubsidyAccount.getWorkingPayment().getCivilTypePayment())
//                .add(this.supplementInsuranceAccount.getWorkingPayment().getCivilTypePayment())
//                .add(this.specialCareSubsidy.getWorkingPayment().getCivilTypePayment());
    }

    public void setTotalWorkingCivilPayment(BigDecimal totalWorkingCivilPayment) {
        this.totalWorkingCivilPayment = totalWorkingCivilPayment;
    }

    public BigDecimal getTotalRetireCivilPayment() {
        return this.accountList.stream().map(a -> a.getRetiredPayment().getCivilTypePayment())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalRetireCivilPayment(BigDecimal totalRetireCivilPayment) {
        this.totalRetireCivilPayment = totalRetireCivilPayment;
    }

    public BigDecimal getTotalWorkingCompanyPayment() {
        return this.accountList.stream().map(a -> a.getWorkingPayment().getCompanyTypePayment())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalWorkingCompanyPayment(BigDecimal totalWorkingCompanyPayment) {
        this.totalWorkingCompanyPayment = totalWorkingCompanyPayment;
    }

    public BigDecimal getTotalRetireCompanyPayment() {
        return this.accountList.stream().map(a -> a.getRetiredPayment().getCompanyTypePayment())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalRetireCompanyPayment(BigDecimal totalRetireCompanyPayment) {
        this.totalRetireCompanyPayment = totalRetireCompanyPayment;
    }

    public BigDecimal getTotalWorkingOtherPayment() {
        return this.accountList.stream().map(a -> a.getWorkingPayment().getOtherTypePayment())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalWorkingOtherPayment(BigDecimal totalWorkingOtherPayment) {
        this.totalWorkingOtherPayment = totalWorkingOtherPayment;
    }

    public BigDecimal getTotalRetireOtherPayment() {
        return this.accountList.stream().map(a -> a.getRetiredPayment().getOtherTypePayment())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalRetireOtherPayment(BigDecimal totalRetireOtherPayment) {
        this.totalRetireOtherPayment = totalRetireOtherPayment;
    }

    public BigDecimal getTotalWorkingPayment() {
        return this.getTotalWorkingCivilPayment().add(this.getTotalWorkingCompanyPayment()).add(this.getTotalWorkingOtherPayment());
    }

    public void setTotalWorkingPayment(BigDecimal totalWorkingPayment) {
        this.totalWorkingPayment = totalWorkingPayment;
    }

    public BigDecimal getTotalRetirePayment() {
        return this.getTotalRetireCivilPayment().add(this.getTotalRetireCompanyPayment()).add(this.getTotalRetireOtherPayment());

    }

    public void setTotalRetirePayment(BigDecimal totalRetirePayment) {
        this.totalRetirePayment = totalRetirePayment;
    }

    public BigDecimal getTotalPayment() {
        return this.getTotalRetirePayment().add(this.getTotalWorkingPayment());
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }
}
