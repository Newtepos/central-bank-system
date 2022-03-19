package com.example.bbs.service;

import com.example.bbs.dto.BankDTO;
import com.example.bbs.dto.CashDTO;
import com.example.bbs.entites.Bank;
import com.example.bbs.entites.Cash;
import com.example.bbs.entites.Currency;
import com.example.bbs.entites.Location;
import com.example.bbs.exception.DataAlreadyExitsException;
import com.example.bbs.repository.BankRepository;
import com.example.bbs.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilityService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CurrencyRepository currencyRepository;


    //Converter
    public Bank covertBankDtoToEntity(BankDTO dto) {
        Bank bank = new Bank();
        bank.setBankName(dto.getBankName());
        bank.setCbtsKey(dto.getCbtsKey());

        Location location = new Location();
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        bank.setLocation(location);

        List<Cash> cashList = new ArrayList<>();
        for(CashDTO cashDTO: dto.getBalance()) {
            Cash cash = new Cash();
            Currency currencyDTO;

            //Check Currency in BBS DB
            Optional<Currency> currency = currencyRepository.findByCurrency(cashDTO.getCurrency());
            //No Currency in BBS DB
            if(currency.isEmpty()) {
                Currency newCurrency = new Currency(cashDTO.getCurrency());
                currencyRepository.save(newCurrency);
            }
            currencyDTO = currencyRepository.findByCurrency(cashDTO.getCurrency()).get();
            cash.setAmount(cashDTO.getAmount());
            cash.setCurrency(currencyDTO);
            cashList.add(cash);
        }

        bank.setBalance(cashList);

        return bank;
    }

    //Validator
    public void validateBankExits(String bankName, Long key) {
        if(bankRepository.findByBankName(bankName).isPresent() || bankRepository.findByCbtsKey(key).isPresent()) {
            throw new DataAlreadyExitsException("This Bank Already Exist in Database");
        }
    }
}
