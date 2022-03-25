package com.example.cbts.dto;

import lombok.Data;

@Data
public class QRCodeDTO {
    private String sendQRCodeBase64;
    private String receiveQRCodeBase64;
}
