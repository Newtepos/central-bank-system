package com.example.bbs.service;

import com.example.bbs.dto.BBSCashPackageDTO;
import com.example.bbs.dto.BankDTO;
import com.example.bbs.dto.CBTSCashPackageDTO;
import com.example.bbs.dto.CashDTO;
import com.example.bbs.entites.*;
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
        bank.setUrl(dto.getUrl());

        return bank;
    }

    public BBSCashPackage covertBBSCashPackageDtoToEntity(BBSCashPackageDTO dto){
        BBSCashPackage cashPackage = new BBSCashPackage();
        Bank senderBank = bankRepository.getByCbtsKey(dto.getBranchId());
        Optional<Currency> currency = currencyRepository.findByCurrency(dto.getCurrency());
        if(currency.isPresent()) {
            Cash cash = new Cash(dto.getAmount(), currency.get());
            cashPackage.setSender(senderBank);
            cashPackage.setCash(cash);
        }
        if(dto.getPackageId() != null) {
            cashPackage.setPackageId(dto.getPackageId());
        }
        if(dto.getReceiveStatus() != null) {
            cashPackage.setReceiveStatus(dto.getReceiveStatus());
        }
        if(dto.getSendStatus() != null) {
            cashPackage.setSendStatus(dto.getSendStatus());
        }
        if(dto.getSentTime() != null) {
            cashPackage.setSendTime(dto.getSentTime());
        }
        if(dto.getReceivedTime() != null) {
            cashPackage.setReceivedTime(dto.getReceivedTime());
        }
        return cashPackage;
    }

    public CBTSCashPackage covertCBTSCashPackageDtoToEntity(CBTSCashPackageDTO dto) {
        CBTSCashPackage cashPackage = new CBTSCashPackage();
        cashPackage.setPackageID(dto.getPackageId());

        Bank senderBank = bankRepository.getByCbtsKey(dto.getReceiverBank());

        cashPackage.setReceiver(senderBank);

        if(dto.getReceiveStatus() != null) {
            cashPackage.setReceiveStatus(dto.getReceiveStatus());
        }
        if(dto.getSendStatus() != null) {
            cashPackage.setSendStatus(dto.getSendStatus());
        }
        if(dto.getSentTime() != null) {
            cashPackage.setSendTime(dto.getSentTime());
        }
        if(dto.getReceivedTime() != null) {
            cashPackage.setReceivedTime(dto.getReceivedTime());
        }
        return cashPackage;
    }



    //Validator
    public void validateBankExits(String bankName, Long key) {
        if(bankRepository.findByBankName(bankName).isPresent() || bankRepository.findByCbtsKey(key).isPresent()) {
            throw new DataAlreadyExitsException("This Bank Already Exist in Database");
        }
    }
}
