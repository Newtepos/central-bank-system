package com.example.bbs.entites;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long cbtsKey;

    private String bankName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cash> balance;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    public Bank() {
    }

    public Bank(Long cbtsKey, String bankName, List<Cash> balance, Location location) {
        this.cbtsKey = cbtsKey;
        this.bankName = bankName;
        this.balance = balance;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
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

    public Long getCbtsKey() {
        return cbtsKey;
    }

    public void setCbtsKey(Long cbtsKey) {
        this.cbtsKey = cbtsKey;
    }
}
