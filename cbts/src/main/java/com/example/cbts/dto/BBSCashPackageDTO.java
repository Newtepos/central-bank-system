package com.example.cbts.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class BBSCashPackageDTO {
    private UUID packageId;
    private Long branchId;
    private Boolean sendStatus;
    private Boolean receiveStatus;
    private Timestamp receivedTime;
    private Timestamp sentTime;
}
