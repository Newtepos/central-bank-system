package com.example.cbts.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CBTSCashPackageDTO {
    private Long branchId;
    private BigDecimal amount;
    private String currency;
}
