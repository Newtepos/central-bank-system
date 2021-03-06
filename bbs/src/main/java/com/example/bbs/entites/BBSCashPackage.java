package com.example.bbs.entites;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class BBSCashPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private UUID packageId = UUID.randomUUID();

    @OneToOne(cascade = CascadeType.ALL)
    private Cash cash;

    @ManyToOne
    private Bank sender;

    private Boolean sendStatus = false;
    private Boolean receiveStatus = false;

    private Timestamp createdTime = new Timestamp(System.currentTimeMillis());
    private Timestamp sendTime;
    private Timestamp receivedTime;

    public BBSCashPackage() {
    }

    public BBSCashPackage(Cash cash, Bank sender) {
        this.cash = cash;
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

    public Bank getSender() {
        return sender;
    }

    public void setSender(Bank sender) {
        this.sender = sender;
    }

    public Boolean getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Boolean sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Boolean getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Boolean receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Timestamp getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Timestamp receivedTime) {
        this.receivedTime = receivedTime;
    }
}
