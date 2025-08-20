package ru.netology.loanproducer.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanSendEvent {
    private Long id;
    private BigDecimal amount;
    private int term;
    private BigDecimal income;
    private BigDecimal dsti;
    private int rating;
    private String status;
}
