package com.example.bbs.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class BBSCashPackageDTO {
    private UUID packageId;
    private Long branchId;
    private BigDecimal amount;
    private String currency;
    private Boolean sendStatus;
    private Boolean receiveStatus;
    private Timestamp receivedTime;
    private Timestamp sentTime;
}
