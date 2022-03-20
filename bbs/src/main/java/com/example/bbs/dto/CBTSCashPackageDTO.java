package com.example.bbs.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class CBTSCashPackageDTO {
    private UUID packageId;
    private Boolean sendStatus;
    private Boolean receiveStatus;
    private Timestamp receivedTime;
    private Timestamp sentTime;
    private Long receiverBank;
}
