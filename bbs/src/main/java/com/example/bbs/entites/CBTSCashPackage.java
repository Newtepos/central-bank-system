package com.example.bbs.entites;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class CBTSCashPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID packageId;

    private Boolean sendStatus = false;
    private Boolean receiveStatus = false;

    private Timestamp createdTime = new Timestamp(System.currentTimeMillis());
    private Timestamp sendTime;
    private Timestamp receivedTime;

    @ManyToOne
    private Bank receiver;

    public CBTSCashPackage() {
    }

    public CBTSCashPackage(UUID packageID) {
        this.packageId = packageID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPackageID() {
        return packageId;
    }

    public void setPackageID(UUID packageID) {
        this.packageId = packageID;
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

    public Bank getReceiver() {
        return receiver;
    }

    public void setReceiver(Bank receiver) {
        this.receiver = receiver;
    }
}
