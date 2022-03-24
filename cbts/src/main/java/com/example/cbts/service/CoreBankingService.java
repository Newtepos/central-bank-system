package com.example.cbts.service;

import com.example.cbts.cbtspackage.CBTSGateway;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.entites.*;
import com.example.cbts.repository.BankRepository;
import com.example.cbts.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CoreBankingService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CBTSGateway cbtsGateway;

    @Autowired
    CurrencyRepository currencyRepository;

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

    public void increaseCentralBankBalance(BBSCashPackage cashPackage) {
        Bank centralBank = bankRepository.getById(1L);

        //Ask BBS System how many Cash contain in cashPackage
        CashDTO cashDTO = cbtsGateway.readBBSPackage(cashPackage.getPackageId());

        //find currency from string
        Optional<Currency> currency = currencyRepository.findByCurrency(cashDTO.getCurrency());

        //Define amount and currency
        BigDecimal cashAmount_Package = cashDTO.getAmount();
        Currency currency_Package = currency.get();

        for(Cash cash: centralBank.getBalance()) {
            if(cash.getCurrency().getCurrency().equals(currency_Package.getCurrency())) {
                BigDecimal addedAmount = cash.getAmount().add(cashAmount_Package);
                cash.setAmount(addedAmount);
            }
        }
        bankRepository.save(centralBank);
    }
}
