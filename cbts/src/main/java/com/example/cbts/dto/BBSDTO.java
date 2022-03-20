package com.example.cbts.dto;

import lombok.Data;

import java.util.List;

@Data
public class BBSDTO {
    private String bankName;
    private double latitude;
    private double longitude;
    private List<CashDTO> balance;
    private String url;
    private Long cbtsKey;
}
