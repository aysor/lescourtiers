package ru.netology.loanprocessor.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.netology.loanprocessor.event.LoanEvent;

import java.math.BigDecimal;

@Service
public class LoanCalculator {
    @Value("${loanprocessor-rate}")
    private String rate;
    private BigDecimal loanMonthRate;

    @PostConstruct
    private void init(){
        System.out.println("*******" + rate);
        loanMonthRate = new BigDecimal(Double.parseDouble(rate) / 12);
    }

    public boolean approved(LoanEvent loanEvent) {
        BigDecimal monthlyPayment = monthlyAnnualPayment(loanEvent.getAmount(), loanEvent.getTerm())
                .add(dstiPayment(loanEvent.getDsti(), loanEvent.getIncome()));
        boolean isApproved = monthlyPayment.compareTo(loanEvent.getIncome().divide(new BigDecimal(2))) < 0;
        return isApproved;
    }

    private BigDecimal monthlyAnnualPayment(BigDecimal amount, int term) {
        BigDecimal annuity = BigDecimal.valueOf((loanMonthRate.doubleValue() * Math.pow(1 + loanMonthRate.doubleValue(), term))
                / (Math.pow(1 + loanMonthRate.doubleValue(), term) - 1));
        BigDecimal payment = amount.multiply(annuity);
        return payment;
    }

    private BigDecimal dstiPayment(BigDecimal dsti, BigDecimal income) {
        BigDecimal payment = income.multiply(dsti);
        return payment;
    }
}
