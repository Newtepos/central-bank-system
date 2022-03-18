package com.example.cbts.request;

public class CashRequest {

    private Long currencyID;
    private double amount;

    public CashRequest(Long currencyID, double amount) {
        this.currencyID = currencyID;
        this.amount = amount;
    }

    public Long getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(Long currencyID) {
        this.currencyID = currencyID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
