package com.example.bbs.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DispatchActionRequest {
    private String method;
    private Timestamp actionTime;
}
