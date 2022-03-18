package com.example.cbts.entites;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class CashPackage {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private Cash cash;

    @ManyToOne
    private Bank sender;

    @ManyToOne
    private Bank receiver;

    public CashPackage() {
    }

    public CashPackage(Cash cash, Bank sender, Bank receiver) {
        this.cash = cash;
        this.sender = sender;
        this.receiver = receiver;
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

    public Bank getReceiver() {
        return receiver;
    }

    public void setReceiver(Bank receiver) {
        this.receiver = receiver;
    }
}
