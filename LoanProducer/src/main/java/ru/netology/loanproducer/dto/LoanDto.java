package ru.netology.loanproducer.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;
    private BigDecimal amount;
    private int term;
    private BigDecimal income;
    private BigDecimal dsti;
    private int rating;
    private String status;
}
