package com.example.bbs.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankDTO {

    private Long cbtsKey;
    private String bankName;
    private double latitude;
    private double longitude;
    private List<CashDTO> balance;

}
