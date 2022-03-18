package com.example.cbts.service;

import com.example.cbts.entites.Bank;
import com.example.cbts.entites.Cash;
import com.example.cbts.entites.Currency;
import com.example.cbts.entites.Location;
import com.example.cbts.repository.BankRepository;
import com.example.cbts.repository.CurrencyRepository;
import com.example.cbts.request.BankRequest;
import com.example.cbts.request.CashRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CBTSService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public void createBank(BankRequest bankRequest) {
        String bankName = bankRequest.getBankName();
        Location location = new Location(bankRequest.getLatitude(),bankRequest.getLongitude());
        List<Cash> cashList = new ArrayList<>();
        for(CashRequest cashRequest: bankRequest.getCashRequests()) {
            Optional<Currency> currencyResult = currencyRepository.findById(cashRequest.getCurrencyID());
            BigDecimal cashAmount = new BigDecimal(cashRequest.getAmount());
            Cash cash = new Cash(cashAmount, currencyResult.get());
            cashList.add(cash);
        }
        Bank newBank = new Bank(bankName, cashList, location);

        bankRepository.save(newBank);
    }
}
