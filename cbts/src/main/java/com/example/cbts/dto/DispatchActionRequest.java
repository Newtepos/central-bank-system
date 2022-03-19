package com.example.cbts.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DispatchActionRequest {
    private String method;
    private Timestamp actionTime;
}
