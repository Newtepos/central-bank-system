package com.example.cbts.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankDTO {

    private long id;
    private String bankName;
    private double latitude;
    private double longitude;
    private List<CashDTO> balance;
    private String url;
}
