package com.example.cbts.request;

import java.util.List;

public class BankRequest {

    private String bankName;
    private double longitude;
    private double latitude;

    private List<CashRequest> cashRequests;

    public BankRequest(String bankName, double longitude, double latitude, List<CashRequest> cashRequests) {
        this.bankName = bankName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cashRequests = cashRequests;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<CashRequest> getCashRequests() {
        return cashRequests;
    }

    public void setCashRequests(List<CashRequest> cashRequests) {
        this.cashRequests = cashRequests;
    }
}
