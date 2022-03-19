package com.example.cbts.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashDTO {
    private BigDecimal amount;
    private String currency;
}
