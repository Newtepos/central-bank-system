package com.example.cbts.entites;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String bankName;

    @OneToMany
    private List<Cash> balance;

    @OneToOne
    private Location location;

    public Bank() {
    }

    public Bank(String bankName, List<Cash> balance, Location location) {
        this.bankName = bankName;
        this.balance = balance;
        this.location = location;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Cash> getBalance() {
        return balance;
    }

    public void setBalance(List<Cash> balance) {
        this.balance = balance;
    }
}
