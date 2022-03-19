package com.example.cbts.service;

import com.example.cbts.entites.Bank;
import com.example.cbts.entites.CBTSCashPackage;
import com.example.cbts.entites.Cash;
import com.example.cbts.entites.Currency;
import com.example.cbts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CoreBankingService {

    @Autowired
    BankRepository bankRepository;

    public void decreaseCentralBankBalance(CBTSCashPackage cashPackage) {
        Bank centralBank = bankRepository.getById(1L);

        BigDecimal cashAmount_Package = cashPackage.getCash().getAmount();
        Currency currency_Package = cashPackage.getCash().getCurrency();

        for(Cash cash: centralBank.getBalance()) {
            if(cash.getCurrency().getCurrency().equals(currency_Package.getCurrency())) {
                BigDecimal diffAmount = cash.getAmount().subtract(cashAmount_Package);
                cash.setAmount(diffAmount);
            }
        }

        bankRepository.save(centralBank);
    }
}
