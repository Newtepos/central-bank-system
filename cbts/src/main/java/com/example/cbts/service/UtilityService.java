package com.example.cbts.service;

import com.example.cbts.dto.*;
import com.example.cbts.entites.*;
import com.example.cbts.exception.CannotFindDataException;
import com.example.cbts.exception.DataAlreadyExitsException;
import com.example.cbts.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilityService {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    MoneyTruckRepository moneyTruckRepository;

    @Autowired
    CBTSCashPackageRepository cbtsCashPackageRepository;

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    //DTO Utility Function
    public Bank convertBankDtoToEntity(BankDTO bankDTO) {
        Bank bank = new Bank();
        Location location = new Location();
        List<Cash> cashList = new ArrayList<>();
        for(CashDTO cashDTO: bankDTO.getBalance()) {
            Cash cash = new Cash();

            Optional<Currency> currency = currencyRepository.findByCurrency(cashDTO.getCurrency());
            if(currency.isPresent()) {
                cash.setCurrency(currency.get());
            }
            else
            {
                Currency currency1 = new Currency();
                currency1.setCurrency(cashDTO.getCurrency());
                cash.setCurrency(currency1);
            }

            cash.setAmount(cashDTO.getAmount());
            cashList.add(cash);
        }
        location.setLatitude(bankDTO.getLatitude());
        location.setLongitude(bankDTO.getLongitude());
        bank.setBankName(bankDTO.getBankName());
        bank.setLocation(location);
        bank.setBalance(cashList);

        return bank;
    }

    public BankDTO convertBankEntityToDto(Bank bank) {
        BankDTO bankDTO = new BankDTO();
        List<CashDTO> cashDTOList = new ArrayList<>();
        for(Cash cash: bank.getBalance()) {
            CashDTO cashDTO = new CashDTO();
            cashDTO.setAmount(cash.getAmount());
            cashDTO.setCurrency(cash.getCurrency().getCurrency());
            cashDTOList.add(cashDTO);
        }
        bankDTO.setId(bank.getId());
        bankDTO.setBankName(bank.getBankName());
        bankDTO.setLatitude(bank.getLocation().getLatitude());
        bankDTO.setLongitude(bank.getLocation().getLongitude());
        bankDTO.setBalance(cashDTOList);
        return bankDTO;
    }

    public MoneyTruck covertMoneyTruckDtoToEntity(MoneyTruckDTO moneyTruckDTO) {
        MoneyTruck moneyTruck = new MoneyTruck();
        List<Location> locations = new ArrayList<>();
        Location location = new Location();
        location.setLatitude(moneyTruckDTO.getLatitude());
        location.setLongitude(moneyTruckDTO.getLongitude());
        location.setTimestamp(moneyTruckDTO.getTimestamp());
        locations.add(location);
        moneyTruck.setTruckName(moneyTruckDTO.getTruckName());
        moneyTruck.setLocations(locations);
        return moneyTruck;
    }

    public MoneyTruckDTO covertMoneyTruckEntityToDto(MoneyTruck moneyTruck) {
        MoneyTruckDTO moneyTruckDTO = new MoneyTruckDTO();
        moneyTruckDTO.setTruckName(moneyTruck.getTruckName());
        moneyTruckDTO.setId(moneyTruck.getId());
        int lastLocation = moneyTruck.getLocations().size();
        moneyTruckDTO.setLatitude(moneyTruck.getLocations().get(lastLocation-1).getLatitude());
        moneyTruckDTO.setLongitude(moneyTruck.getLocations().get(lastLocation-1).getLongitude());
        moneyTruckDTO.setTimestamp(moneyTruck.getLocations().get(lastLocation-1).getTimestamp());
        return moneyTruckDTO;
    }

    public CBTSCashPackage covertCBTSCashPackageDtoToEntity(CBTSCashPackageDTO dto){
        CBTSCashPackage cashPackage = new CBTSCashPackage();
        Bank receiveBank = bankRepository.getById(dto.getBranchId());
        Optional<Currency> currency = currencyRepository.findByCurrency(dto.getCurrency());
        if(currency.isPresent()) {
            Cash cash = new Cash(dto.getAmount(), currency.get());
            cashPackage.setReceiver(receiveBank);
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

    public CBTSCashPackageDTO covertCBTSCashPackageEntityToDto(CBTSCashPackage cashPackage) {
        CBTSCashPackageDTO cbtsCashPackageDTO = new CBTSCashPackageDTO();
        cbtsCashPackageDTO.setPackageId(cashPackage.getPackageId());
        cbtsCashPackageDTO.setAmount(cashPackage.getCash().getAmount());
        cbtsCashPackageDTO.setCurrency(cashPackage.getCash().getCurrency().getCurrency());
        cbtsCashPackageDTO.setBranchId(cashPackage.getReceiver().getId());
        cbtsCashPackageDTO.setSendStatus(cashPackage.getSendStatus());
        cbtsCashPackageDTO.setSentTime(cashPackage.getSendTime());
        cbtsCashPackageDTO.setReceiveStatus(cashPackage.getReceiveStatus());
        cbtsCashPackageDTO.setReceivedTime(cashPackage.getReceivedTime());
        return cbtsCashPackageDTO;
    }

    public BBSCashPackage covertBBSCashPackageDtoToEntity(BBSCashPackageDTO bbsCashPackageDTO) {
        BBSCashPackage cashPackage = new BBSCashPackage();
        cashPackage.setPackageId(bbsCashPackageDTO.getPackageId());

        Bank senderBank = bankRepository.getById(bbsCashPackageDTO.getBranchId());

        cashPackage.setSender(senderBank);

        if(bbsCashPackageDTO.getReceiveStatus() != null) {
            cashPackage.setReceiveStatus(bbsCashPackageDTO.getReceiveStatus());
        }
        if(bbsCashPackageDTO.getSendStatus() != null) {
            cashPackage.setSendStatus(bbsCashPackageDTO.getSendStatus());
        }
        if(bbsCashPackageDTO.getSentTime() != null) {
            cashPackage.setSendTime(bbsCashPackageDTO.getSentTime());
        }
        if(bbsCashPackageDTO.getReceivedTime() != null) {
            cashPackage.setReceivedTime(bbsCashPackageDTO.getReceivedTime());
        }
        return cashPackage;
    }

    public BBSCashPackageDTO covertBBSCashPackageEntityToDto(BBSCashPackage cashPackage) {
        BBSCashPackageDTO bbsCashPackageDTO = new BBSCashPackageDTO();
        bbsCashPackageDTO.setPackageId(cashPackage.getPackageId());
        bbsCashPackageDTO.setBranchId(cashPackage.getSender().getId());
        bbsCashPackageDTO.setSendStatus(cashPackage.getSendStatus());
        bbsCashPackageDTO.setSentTime(cashPackage.getSendTime());
        bbsCashPackageDTO.setReceiveStatus(cashPackage.getReceiveStatus());
        bbsCashPackageDTO.setReceivedTime(cashPackage.getReceivedTime());
        return bbsCashPackageDTO;
    }


    //Validator Utility Function
    public void validateBankExits(String name) {
        if(bankRepository.findByBankName(name).isPresent()){
            throw new DataAlreadyExitsException("Bank Already Exits");
        }
    }

    public void validateTruckExits(String name) {
        if(moneyTruckRepository.findByTruckName(name).isPresent()) {
            throw new DataAlreadyExitsException("MoneyTruck Already Exits");
        }
    }

    public void bankNotFound(long id) {
        if(bankRepository.findById(id).isEmpty()) {
            throw new CannotFindDataException("Cannot find Bank with ID:" + id);
        }
    }

    public void moneyTruckNotFound(Long id) {
        if(moneyTruckRepository.findById(id).isEmpty()) {
            throw new CannotFindDataException("Cannot find MoneyTruck with ID:" + id);
        }
    }

    public void validateCenterBankFund(String currency, BigDecimal amount) {
        Bank centralBank = bankRepository.getById(1L);
        Optional<Currency> currencyQuery = currencyRepository.findByCurrency(currency);

        if(currencyQuery.isEmpty()) {
            throw new CannotFindDataException("Cannot find " + currency + " in currency CBTS system");
        }

        if(centralBank.getBalance().contains(currencyQuery)) {
            throw new CannotFindDataException("Not Found Currency in CentralBank");
        }

        for(Cash cash: centralBank.getBalance()) {
            if(cash.getCurrency().getCurrency().equals(currency)){
                if(cash.getAmount().compareTo(amount) == -1) {
                    throw new CannotFindDataException("Insufficient amount for " + currency);
                }
            }
        }
    }

    public void validateCBTSCashPackage(UUID cashPackage) {
        if(cbtsCashPackageRepository.findByPackageId(cashPackage).isEmpty()) {
            throw new CannotFindDataException("Cannot find CashPackage with " + cashPackage);
        }
    }

    public void validateBBSCashPackage(UUID cashPackage) {
        if(bbsCashPackageRepository.findByPackageId(cashPackage).isEmpty()) {
            throw new CannotFindDataException("Cannot find CashPackage with " + cashPackage);
        }
    }
}
