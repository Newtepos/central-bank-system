package com.example.cbts.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MoneyTruckDTO {
    private long id;
    private String truckName;
    private double latitude;
    private double longitude;
    private Timestamp timestamp;
}
